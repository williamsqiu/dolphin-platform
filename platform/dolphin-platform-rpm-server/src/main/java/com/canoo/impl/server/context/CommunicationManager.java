package com.canoo.impl.server.context;

/**
 * Functional interface that defines the state of a response from the server.
 */
public interface CommunicationManager {

    /**
     * Returns true if commands should be send back to the client
     * @return true if commands should be send back to the client
     */
    boolean hasResponseCommands();
}
