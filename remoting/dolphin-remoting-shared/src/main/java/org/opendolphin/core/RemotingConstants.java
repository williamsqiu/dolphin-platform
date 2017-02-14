package org.opendolphin.core;

/**
 * Created by hendrikebbers on 14.02.17.
 */
public interface RemotingConstants {

    String DOLPHIN_PLATFORM_PREFIX = "dolphin_platform_intern_";

    String RELEASE_EVENT_BUS_COMMAND_NAME = DOLPHIN_PLATFORM_PREFIX + "release";

    String POLL_EVENT_BUS_COMMAND_NAME = DOLPHIN_PLATFORM_PREFIX + "longPoll";

}
