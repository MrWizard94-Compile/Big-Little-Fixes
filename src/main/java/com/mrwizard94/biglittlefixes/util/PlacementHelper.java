package com.mrwizard94.biglittlefixes.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Small helper methods for placement-related QoL work.
 *
 * <p>This class keeps placement checks simple and reusable for multiple future fixes.
 * It intentionally avoids feature-specific rules so the helpers stay lightweight.</p>
 */
public final class PlacementHelper {
    private PlacementHelper() {
    }

    public static boolean canPlaceAt(final Player player, final Level level, final BlockPos pos) {
        return player != null
                && level != null
                && !level.isClientSide
                && level.isLoaded(pos)
                && level.getBlockState(pos).canBeReplaced();
    }

    public static boolean hasItemForPlacement(final Player player, final ItemStack stack) {
        return player != null
                && stack != null
                && !stack.isEmpty();
    }

    public static BlockPos getTargetPos(final BlockHitResult hitResult) {
        return hitResult != null ? hitResult.getBlockPos() : BlockPos.ZERO;
    }

    public static boolean isSolidSurface(final Level level, final BlockPos pos) {
        return level != null
                && level.isLoaded(pos)
                && level.getBlockState(pos).isSolidRender(level, pos);
    }
}
