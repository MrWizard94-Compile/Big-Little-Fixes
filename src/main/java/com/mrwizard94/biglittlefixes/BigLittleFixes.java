package com.mrwizard94.biglittlefixes;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mrwizard94.biglittlefixes.events.Events;

/**
 * Big Little Fixes mod main class. Provides mod initialization and event registration.
 */
@Mod("biglittlefixes")
public final class BigLittleFixes {
    private static final Logger LOGGER = Logger.getLogger("biglittlefixes");

    /**
     * Main mod entry point. Registers event listeners on the mod event bus.
     */
    public BigLittleFixes() {
        Objects.requireNonNull(FMLJavaModLoadingContext.get(), "FMLJavaModLoadingContext must be available");
        // Register mod event listeners
        FMLJavaModLoadingContext.get().getModEventBus().register(new Events());
        LOGGER.log(Level.INFO, "Big Little Fixes mod skeleton initialized");
    }
}
