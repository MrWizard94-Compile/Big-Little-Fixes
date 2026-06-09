package com.mrwizard94.biglittlefixes.events;

import com.mojang.logging.LogUtils;
import com.mrwizard94.biglittlefixes.BigLittleFixes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

/**
 * Client-side Forge event handlers for UI and rendering related QoL work.
 *
 * <p>Place future client-only feature listeners in this class.
 * The event bus registration is handled by the mod entry point.</p>
 */
@Mod.EventBusSubscriber(modid = BigLittleFixes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    private ClientEvents() {
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        LOGGER.debug("Client infrastructure setup is ready for future feature hooks.");
    }
}
