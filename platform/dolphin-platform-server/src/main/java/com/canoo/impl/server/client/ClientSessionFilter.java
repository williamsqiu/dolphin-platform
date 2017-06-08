package com.canoo.impl.server.client;

import com.canoo.dolphin.PlatformConstants;
import com.canoo.dolphin.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ClientSessionFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(ClientSessionFilter.class);

    private static final String DOLPHIN_PLATFORM_INITIALIZED_IN_SESSION = "DOLPHIN_PLATFORM_INITIALIZED_IN_SESSION";

    private final ClientSessionManager clientSessionManager;

    public ClientSessionFilter(final ClientSessionManager clientSessionManager) {
        this.clientSessionManager = Assert.requireNonNull(clientSessionManager, "clientSessionManager");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest servletRequest = (HttpServletRequest) request;
        final HttpServletResponse servletResponse = (HttpServletResponse) response;
        final HttpSession httpSession = Assert.requireNonNull(servletRequest.getSession(), "request.getSession()");

        try {
            final String clientId = servletRequest.getHeader(PlatformConstants.CLIENT_ID_HTTP_HEADER_NAME);
            if (clientId == null || clientId.trim().isEmpty()) {
                try {
                    String createdClientId = clientSessionManager.createClientSession(httpSession);
                    continueRequest(servletRequest, servletResponse, chain, httpSession, createdClientId);
                } catch (MaxSessionCountReachedException e) {
                    LOG.warn("Maximum size for clients in session {} is reached", servletRequest.getSession().getId());
                    servletResponse.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Maximum size for clients in session is reached");
                }
            } else {
                LOG.trace("Trying to find client session {} in http session {}", clientId, httpSession.getId());
                if (!clientSessionManager.checkValidClientSession(httpSession, clientId)) {
                    if (httpSession.getAttribute(DOLPHIN_PLATFORM_INITIALIZED_IN_SESSION) == null) {
                        LOG.warn("Can not find requested client for id {} in session {} (session timeout)", clientId, httpSession.getId());
                        servletResponse.sendError(HttpServletResponse.SC_REQUEST_TIMEOUT, "Can not find requested client (session timeout)!");
                    } else {
                        LOG.warn("Can not find requested client for id {} in session {} (unknown error)", clientId, httpSession.getId());
                        servletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can not find requested client (unknown error)!");
                    }
                } else {
                    continueRequest(servletRequest, servletResponse, chain, httpSession, clientId);
                }
            }
        } catch (Exception e) {
            LOG.error("Error while checking requested client in session " + httpSession.getId() + " (unknown error)", e);
            servletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can not find requested client (unknown error)!");
            return;
        }
    }

    private void continueRequest(HttpServletRequest request, HttpServletResponse response, FilterChain chain, final HttpSession httpSession, final String clientSessionId) throws IOException, ServletException {
        clientSessionManager.setClientSessionForThread(httpSession, clientSessionId);
        try {
            final Object init = httpSession.getAttribute(DOLPHIN_PLATFORM_INITIALIZED_IN_SESSION);
            if (init == null) {
                httpSession.setAttribute(DOLPHIN_PLATFORM_INITIALIZED_IN_SESSION, true);
            }

            response.setHeader(PlatformConstants.CLIENT_ID_HTTP_HEADER_NAME, clientSessionId);
            chain.doFilter(request, response);
        } finally {
            clientSessionManager.resetClientSessionForThread();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Nothing to do here
    }

    @Override
    public void destroy() {
    }
}

