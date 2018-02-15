package com.canoo.dp.impl.server.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class GenericResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream output;

    public GenericResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new ByteArrayOutputStream();
    }

    public ServletOutputStream getOutputStream() {
        return new ServletOutputStream(){

            @Override
            public void write(final int b) throws IOException {
                output.write(b);
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(final WriteListener writeListener) {

            }
        };
    }

    public PrintWriter getWriter() {
        return new PrintWriter(getOutputStream(),true);
    }

    public void updateWrappedResponse() throws IOException {
        if(output.size() > 0) {
            getResponse().getOutputStream().write(output.toByteArray());
        }
    }
}