package com.mrwizard94.biglittlefixes.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Lightweight helper methods for player-oriented QoL logic.
 *
 * <p>These methods are intentionally small and dependency-light, making them easy to reuse
 * across different future fixes.</p>
 */
public final class PlayerHelper {
    private PlayerHelper() {
    }

    public static boolean isValidPlayer(final Player player) {
        return player != null && player.isAlive();
    }

    public static boolean hasHeldItem(final Player player) {
        return isValidPlayer(player) && !player.getMainHandItem().isEmpty();
    }

    public static boolean hasItemInInventory(final Player player, final ItemStack stack) {
        return isValidPlayer(player) && stack != null && !stack.isEmpty() && player.getInventory().contains(stack);
    }
}
