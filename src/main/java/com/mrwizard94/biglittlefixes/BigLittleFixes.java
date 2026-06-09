package com.mrwizard94.biglittlefixes;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * Main mod class for Big Little Fixes.
 * This is intentionally minimal: it sets up the mod event bus and provides
 * hooks for common and client setup. The mod id is defined in a single place.
 */
@Mod(BigLittleFixes.MODID)
public final class BigLittleFixes {
    public static final String MODID = "biglittlefixes";
    private static final Logger LOGGER = LogUtils.getLogger();

    public BigLittleFixes() {
        // Register non-mod-lifecycle listeners on the Forge event bus
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModEvents {
        @SubscribeEvent
        public static void onCommonSetup(final FMLCommonSetupEvent event) {
            LOGGER.info("Big Little Fixes (modid={}) common setup complete.", MODID);
        }
    }

    @SubscribeEvent
    public void onServerStarting(final ServerStartingEvent event) {
        LOGGER.info("Big Little Fixes server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static final class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetupEvent(final FMLClientSetupEvent event) {
            // no-op; kept for future client-specific initialization
            LOGGER.debug("ClientModEvents onClientSetupEvent fired");
        }
    }
}
