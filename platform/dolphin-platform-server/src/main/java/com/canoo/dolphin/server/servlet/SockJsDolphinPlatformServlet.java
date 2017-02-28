package com.canoo.dolphin.server.servlet;

import com.canoo.dolphin.server.context.DolphinContext;
import com.canoo.dolphin.server.context.DolphinContextUtils;
import com.canoo.dolphin.util.DolphinRemotingException;
import org.opendolphin.core.comm.Command;
import org.projectodd.sockjs.SockJsConnection;
import org.projectodd.sockjs.SockJsServer;
import org.projectodd.sockjs.servlet.SockJsServlet;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class SockJsDolphinPlatformServlet extends SockJsServlet {

    @Override
    public void init() throws ServletException {
        SockJsServer echoServer = new SockJsServer();

        echoServer.onConnection(new SockJsServer.OnConnectionHandler() {
            @Override
            public void handle(final SockJsConnection connection) {

                getServletContext().log("SockJS client connected with protocol: " + connection.protocol);

                final DolphinContext dolphinContext = DolphinContextUtils.getContextForCurrentThread();

                connection.onData(new SockJsConnection.OnDataHandler() {
                    @Override
                    public void handle(String message) {
                        getServletContext().log("SockJS client send Data: " + message);
                        DolphinContextUtils.setContextForCurrentThread(dolphinContext);
                        try {
                            final List<Command> commands = new ArrayList<>(dolphinContext.getDolphin().getServerConnector().getCodec().decode(message));
                            final List<Command> results = new ArrayList<>(dolphinContext.handle(commands));
                            connection.write(dolphinContext.getDolphin().getServerConnector().getCodec().encode(results));
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new DolphinRemotingException("Unexpected Dolphin Platform error", e);
                        } finally {
                            DolphinContextUtils.setContextForCurrentThread(null);
                        }
                    }
                });

                connection.onClose(new SockJsConnection.OnCloseHandler() {
                    @Override
                    public void handle() {
                        getServletContext().log("SockJS client disconnected");
                        dolphinContext.destroy();
                    }
                });
            }
        });
        setServer(echoServer);

        super.init();
    }
}
