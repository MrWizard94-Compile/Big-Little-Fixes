package com.mrwizard94.biglittlefixes.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Helper methods for inspecting and comparing block-state data.
 *
 * <p>This keeps common block-state logic in one place for future QoL fixes that need
 * to inspect or normalize placement and interaction behavior.</p>
 */
public final class BlockStateHelper {
    private BlockStateHelper() {
    }

    public static boolean isAir(final Level level, final BlockPos pos) {
        return level != null
                && level.isLoaded(pos)
                && level.getBlockState(pos).isAir();
    }

    public static boolean isReplaceable(final Level level, final BlockPos pos) {
        return level != null
                && level.isLoaded(pos)
                && level.getBlockState(pos).canBeReplaced();
    }

    public static boolean hasSolidTop(final Level level, final BlockPos pos) {
        return level != null
                && level.isLoaded(pos)
                && level.getBlockState(pos.above()).isSolidRender(level, pos.above());
    }
}
