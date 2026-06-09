package com.mrwizard94.biglittlefixes.config;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Shared configuration for Big Little Fixes.
 *
 * <p>Each toggle here controls a core QoL behavior that future fixes can rely on.
 * Values are loaded through Forge's standard config system and can be overridden
 * by the generated config file in the run directory.</p>
 */
public final class BigLittleFixesConfig {
    private BigLittleFixesConfig() {
    }

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue ENABLE_PLACEMENT_ASSIST = BUILDER
            .comment("Enable placement assistance for future QoL fixes.")
            .define("enablePlacementAssist", true);

    public static final ForgeConfigSpec.BooleanValue ENABLE_BLOCK_STATE_CLEANUP = BUILDER
            .comment("Enable block-state cleanup helpers for future block and placement fixes.")
            .define("enableBlockStateCleanup", true);

    public static final ForgeConfigSpec.BooleanValue ENABLE_PLAYER_HELPERS = BUILDER
            .comment("Enable player-focused helper logic for future interaction and utility fixes.")
            .define("enablePlayerHelpers", true);

    public static final ForgeConfigSpec.BooleanValue ENABLE_DEBUG_LOGGING = BUILDER
            .comment("Enable extra debug logging for infrastructure and future feature diagnostics.")
            .define("enableDebugLogging", false);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean isPlacementAssistEnabled() {
        return ENABLE_PLACEMENT_ASSIST.get();
    }

    public static boolean isBlockStateCleanupEnabled() {
        return ENABLE_BLOCK_STATE_CLEANUP.get();
    }

    public static boolean isPlayerHelpersEnabled() {
        return ENABLE_PLAYER_HELPERS.get();
    }

    public static boolean isDebugLoggingEnabled() {
        return ENABLE_DEBUG_LOGGING.get();
    }
}
