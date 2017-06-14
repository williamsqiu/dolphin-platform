package com.canoo.impl.server.servlet;

import com.canoo.impl.platform.core.Assert;
import com.canoo.impl.server.context.DolphinContext;
import com.canoo.impl.server.context.DolphinContextProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InterruptServlet extends HttpServlet {

    private final DolphinContextProvider dolphinContextProvider;

    public InterruptServlet(final DolphinContextProvider dolphinContextProvider) {
        this.dolphinContextProvider = Assert.requireNonNull(dolphinContextProvider, "dolphinContextProvider");
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse response) throws ServletException, IOException {
        Assert.requireNonNull(response, "response");
        final DolphinContext currentContext = dolphinContextProvider.getCurrentDolphinContext();
        if(currentContext == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or wrong client session id");
        } else {
            currentContext.interrupt();
        }
    }
}
