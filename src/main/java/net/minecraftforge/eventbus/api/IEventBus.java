package net.minecraftforge.eventbus.api;

/** Minimal event bus interface used by the mod skeleton. */
public interface IEventBus {
    /**
     * Register an object whose methods may be annotated with @SubscribeEvent.
     *
     * @param listener the listener instance to register (must not be null)
     */
    void register(Object listener);
}
