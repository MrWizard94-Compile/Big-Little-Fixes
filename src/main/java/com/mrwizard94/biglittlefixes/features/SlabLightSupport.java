package com.mrwizard94.biglittlefixes.features;

import com.mrwizard94.biglittlefixes.config.BigLittleFixesConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Universal half-slab flush: anything that sits on a full floor block can sit on a bottom
 * slab (and render flush); anything that hangs under a full ceiling can hang under a top
 * slab (and render flush).
 */
public final class SlabLightSupport {

    public static final double FLOOR_Y = -0.5D;
    public static final double CEILING_Y = 0.5D;

    private SlabLightSupport() {
    }

    public static boolean enabled() {
        try {
            return BigLittleFixesConfig.slabFriendlyLights.get();
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean isBottomSlabState(BlockState state) {
        return state.getBlock() instanceof SlabBlock
                && state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM;
    }

    public static boolean isTopSlabState(BlockState state) {
        if (!(state.getBlock() instanceof SlabBlock)) {
            return false;
        }
        SlabType type = state.getValue(SlabBlock.TYPE);
        return type == SlabType.TOP || type == SlabType.DOUBLE;
    }

    /**
     * Bottom slabs fully support UP; top/double slabs fully support DOWN.
     * Makes vanilla / modded canSurvive & placement treat half-slabs like full blocks
     * for floor/ceiling attachment.
     */
    public static boolean forceSturdyFace(BlockState slabState, Direction face) {
        if (!enabled() || !(slabState.getBlock() instanceof SlabBlock)) {
            return false;
        }
        SlabType type = slabState.getValue(SlabBlock.TYPE);
        if (face == Direction.UP && (type == SlabType.BOTTOM || type == SlabType.DOUBLE)) {
            return true;
        }
        return face == Direction.DOWN && (type == SlabType.TOP || type == SlabType.DOUBLE);
    }

    /**
     * World-space Y translation for flush meshing / collision for the block at {@code pos}.
     */
    public static double yOffset(BlockGetter level, BlockPos pos, BlockState state) {
        if (!enabled() || level == null || pos == null || state == null || state.isAir()) {
            return 0.0D;
        }
        // The slab is the support surface, not the thing being flush-adjusted.
        if (state.getBlock() instanceof SlabBlock) {
            return 0.0D;
        }
        // Pure fluid cells with no collision (water/lava source shapes handled by fluid renderer)
        if (!state.getFluidState().isEmpty() && state.getCollisionShape(level, pos).isEmpty()) {
            return 0.0D;
        }

        BlockState below = level.getBlockState(pos.below());
        BlockState above = level.getBlockState(pos.above());

        if (isBottomSlabState(below) && isFloorAttached(state)) {
            return FLOOR_Y;
        }
        if (isTopSlabState(above) && isCeilingAttached(state, level, pos)) {
            return CEILING_Y;
        }
        return 0.0D;
    }

    /**
     * Sits on the top face of the block below (not wall-only mounts).
     */
    public static boolean isFloorAttached(BlockState state) {
        // Pure wall mounts (wall torch, etc.)
        if (state.getBlock() instanceof WallTorchBlock) {
            return false;
        }
        if (state.hasProperty(BlockStateProperties.ATTACH_FACE)) {
            AttachFace face = state.getValue(BlockStateProperties.ATTACH_FACE);
            // Only floor-face attachments (buttons/grindstones/levers on floor)
            return face == AttachFace.FLOOR;
        }
        // Everything else in the cell above a bottom slab (torches, plates, rails, carpet,
        // flowers, full cubes, standing lanterns, redstone, modded…) sits on that floor.
        return true;
    }

    /**
     * Hangs from / attaches to the underside of the block above.
     */
    public static boolean isCeilingAttached(BlockState state, BlockGetter level, BlockPos pos) {
        if (state.hasProperty(BlockStateProperties.ATTACH_FACE)) {
            return state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING;
        }
        if (state.hasProperty(BlockStateProperties.HANGING)) {
            return state.getValue(BlockStateProperties.HANGING);
        }
        // Lantern without going through property name (already covered by HANGING)
        // Chains, pointed dripstone tip up?, hanging roots — shape heuristic:
        VoxelShape shape = state.getShape(level, pos);
        if (shape.isEmpty()) {
            return false;
        }
        // Occupies mostly the upper portion of the cell → treat as ceiling-hung
        double minY = shape.min(Direction.Axis.Y);
        double maxY = shape.max(Direction.Axis.Y);
        if (minY >= 0.25D && maxY >= 0.9D) {
            return true;
        }
        // Explicit: not a full cube sitting under a top slab (full cubes are floor-flush only)
        return false;
    }

    public static VoxelShape offsetShape(VoxelShape shape, double yOff) {
        if (shape == null || shape.isEmpty() || yOff == 0.0D) {
            return shape;
        }
        return shape.move(0.0D, yOff, 0.0D);
    }

    /** float version for pose stack translate */
    public static float renderYOffset(BlockGetter level, BlockPos pos, BlockState state) {
        return (float) yOffset(level, pos, state);
    }
}
