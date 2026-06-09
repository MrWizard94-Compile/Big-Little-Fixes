package com.bulkley.biglittlefixes.events;

import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Central event handlers for the Big Little Fixes mod skeleton. Keep handlers small and focused.
 */
public final class Events {
    private static final Logger LOGGER = Logger.getLogger("biglittlefixes.events");

    /** Construct event handler container. */
    public Events() {
        // No-op constructor; event bus will call annotated methods if wiring is provided.
    }

    /**
     * Handle common setup events.
     *
     * @param event the event instance (mod loader-specific in real runtime)
     */
    @SubscribeEvent
    public void onCommonSetup(final Object event) {
        // Minimal, safe handler for the skeleton. Real logic will be added in feature tasks.
        LOGGER.log(Level.FINE, "onCommonSetup invoked with event: " + event);
    }
}
