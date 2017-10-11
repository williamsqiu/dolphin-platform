/**
 * This package provides a server site event bus for the Dolphin Platform remoting layer. The event bus can be used to send events to other controllers (see {@link com.canoo.platform.remoting.server.DolphinController}) in the same or in external client sessions (see {@link com.canoo.platform.server.client.ClientSession}). Since the eventbus can easily integrated in any managed bean messages can be send from non Dolphin Platform beans to controllers. By doing so a Dolphin Platform controller can for example easily react on messages that are send by a REST endpoint.
 *
 * @author Hendrik Ebbers
 */
package com.canoo.platform.remoting.server.event;