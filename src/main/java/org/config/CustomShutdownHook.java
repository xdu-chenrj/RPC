package org.config;


import lombok.extern.slf4j.Slf4j;

/**
 * when server is closed, do something such as unregister all services;
 */
@Slf4j
public class CustomShutdownHook {
    private static final CustomShutdownHook CUSTOM_SHUTDOWN_HOOK = new CustomShutdownHook();

    public static CustomShutdownHook getCustomShutdownHook() {
        return CUSTOM_SHUTDOWN_HOOK;
    }

    public void clearAll() {

    }

}
