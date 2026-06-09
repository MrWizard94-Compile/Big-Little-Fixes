package net.minecraftforge.fml.javafmlmod;

import net.minecraftforge.eventbus.api.IEventBus;

/** Minimal replacement for the real FMLJavaModLoadingContext used by Forge.
 * Provides access to a project-local singleton event bus for skeleton initialization.
 */
public final class FMLJavaModLoadingContext {
    private static final FMLJavaModLoadingContext INSTANCE = new FMLJavaModLoadingContext();
    private final IEventBus modEventBus = new SimpleEventBus();

    private FMLJavaModLoadingContext() {
    }

    /**
     * Obtain the singleton mod loading context used by the skeleton.
     *
     * @return the singleton FMLJavaModLoadingContext
     */
    public static FMLJavaModLoadingContext get() {
        return INSTANCE;
    }

    /**
     * Get the mod-specific event bus for listener registration.
     *
     * @return mod event bus (never null)
     */
    public IEventBus getModEventBus() {
        return modEventBus;
    }

    // Simple internal EventBus implementation
    private static final class SimpleEventBus implements IEventBus {
        @Override
        public void register(final Object listener) {
            // Intentionally minimal: no runtime dispatch is required for project skeleton.
            // This method exists only to allow registration without external deps.
            if (listener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
        }
    }
}
