package com.mrwizard94.biglittlefixes.mixin;

import com.mrwizard94.biglittlefixes.features.SlabLightSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Offsets collision/outline shapes by ±½ block when sitting on a bottom slab or
 * hanging under a top slab — keeps hitboxes flush with the mesh.
 */
@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourShapeOffsetMixin {

    @Unique
    private static final ThreadLocal<Boolean> BLF$REENTRY = ThreadLocal.withInitial(() -> Boolean.FALSE);

    @Inject(method = "getShape", at = @At("RETURN"), cancellable = true)
    private void biglittlefixes$offsetShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context,
            CallbackInfoReturnable<VoxelShape> cir) {
        biglittlefixes$apply(state, level, pos, cir);
    }

    @Inject(method = "getCollisionShape", at = @At("RETURN"), cancellable = true)
    private void biglittlefixes$offsetCollision(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context,
            CallbackInfoReturnable<VoxelShape> cir) {
        biglittlefixes$apply(state, level, pos, cir);
    }

    @Inject(method = "getVisualShape", at = @At("RETURN"), cancellable = true)
    private void biglittlefixes$offsetVisual(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context,
            CallbackInfoReturnable<VoxelShape> cir) {
        biglittlefixes$apply(state, level, pos, cir);
    }

    @Unique
    private static void biglittlefixes$apply(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CallbackInfoReturnable<VoxelShape> cir) {
        if (Boolean.TRUE.equals(BLF$REENTRY.get())) {
            return;
        }
        VoxelShape shape = cir.getReturnValue();
        if (shape == null || shape.isEmpty()) {
            return;
        }
        BLF$REENTRY.set(Boolean.TRUE);
        try {
            double y = SlabLightSupport.yOffset(level, pos, state);
            if (y != 0.0D) {
                cir.setReturnValue(SlabLightSupport.offsetShape(shape, y));
            }
        } finally {
            BLF$REENTRY.set(Boolean.FALSE);
        }
    }
}
