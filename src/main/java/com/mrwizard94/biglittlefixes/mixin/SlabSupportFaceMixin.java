package com.mrwizard94.biglittlefixes.mixin;

import com.mrwizard94.biglittlefixes.features.SlabLightSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Treat bottom slabs as full floor support (UP) and top/double slabs as full ceiling
 * support (DOWN) so any canSurvive / placement check that uses face sturdiness succeeds.
 */
@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class SlabSupportFaceMixin {

    @Inject(
            method = "isFaceSturdy(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/SupportType;)Z",
            at = @At("HEAD"),
            cancellable = true)
    private void biglittlefixes$slabFaceSturdy(
            BlockGetter level,
            BlockPos pos,
            Direction face,
            SupportType supportType,
            CallbackInfoReturnable<Boolean> cir) {
        BlockState self = (BlockState) (Object) this;
        if (SlabLightSupport.forceSturdyFace(self, face)) {
            cir.setReturnValue(true);
        }
    }
}
