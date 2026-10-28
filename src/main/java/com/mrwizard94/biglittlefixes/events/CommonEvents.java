package com.mrwizard94.biglittlefixes.events;

import com.mrwizard94.biglittlefixes.BigLittleFixes;
import com.mrwizard94.biglittlefixes.config.BigLittleFixesConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = BigLittleFixes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CommonEvents {

    private CommonEvents() {
    }

    @SubscribeEvent
    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        BigLittleFixes.LOGGER.info(
                "Big Little Fixes — universal slab flush enabled={}",
                BigLittleFixesConfig.slabFriendlyLights.get());
    }
}
