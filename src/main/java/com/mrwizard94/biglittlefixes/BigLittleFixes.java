package com.mrwizard94.biglittlefixes;

import com.mojang.logging.LogUtils;
import com.mrwizard94.biglittlefixes.config.BigLittleFixesConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(BigLittleFixes.MODID)
public final class BigLittleFixes {

    public static final String MODID = "biglittlefixes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BigLittleFixes() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BigLittleFixesConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Big Little Fixes loaded — small QoL, big comfort.");
    }
}
