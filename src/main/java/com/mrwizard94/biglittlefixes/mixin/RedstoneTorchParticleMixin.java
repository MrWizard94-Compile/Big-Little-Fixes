package com.mrwizard94.biglittlefixes.mixin;

import com.mrwizard94.biglittlefixes.features.SlabLightSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RedstoneTorchBlock.class)
public abstract class RedstoneTorchParticleMixin {

    @Inject(method = "animateTick", at = @At("HEAD"), cancellable = true)
    private void biglittlefixes$flushDust(
            BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (SlabLightSupport.yOffset(level, pos, state) == 0.0D) {
            return;
        }
        if (state.hasProperty(RedstoneTorchBlock.LIT) && !state.getValue(RedstoneTorchBlock.LIT)) {
            ci.cancel();
            return;
        }
        double x = pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
        double y = pos.getY() + 0.7D + SlabLightSupport.FLOOR_Y;
        double z = pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
        level.addParticle(DustParticleOptions.REDSTONE, x, y, z, 0.0D, 0.0D, 0.0D);
        ci.cancel();
    }
}
