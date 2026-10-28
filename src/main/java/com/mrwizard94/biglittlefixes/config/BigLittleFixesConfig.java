package com.mrwizard94.biglittlefixes.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class BigLittleFixesConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue slabFriendlyLights;

    static {
        BUILDER.push("features");
        slabFriendlyLights = BUILDER
                .comment(
                        "Universal half-slab flush:",
                        " - Bottom slabs act as full floors (place + flush mesh/hitbox)",
                        " - Top slabs act as full ceilings for hanging blocks (place + flush)",
                        "Applies to vanilla and modded blocks that use normal face-support checks.")
                .define("slabFriendlyLights", true);
        BUILDER.pop();
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    private BigLittleFixesConfig() {
    }
}
