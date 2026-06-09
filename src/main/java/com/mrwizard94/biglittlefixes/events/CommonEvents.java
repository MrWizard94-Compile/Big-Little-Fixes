package com.mrwizard94.biglittlefixes.events;

import com.mojang.logging.LogUtils;
import com.mrwizard94.biglittlefixes.BigLittleFixes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

/**
 * Common Forge event handlers for shared mod logic.
 *
 * <p>Place future server-side and common lifecycle feature listeners in this class.
 * The event bus registration is handled by the mod entry point.</p>
 */
@Mod.EventBusSubscriber(modid = BigLittleFixes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CommonEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    private CommonEvents() {
    }

    @SubscribeEvent
    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        LOGGER.debug("Common infrastructure setup is ready for future feature hooks.");
    }
}
