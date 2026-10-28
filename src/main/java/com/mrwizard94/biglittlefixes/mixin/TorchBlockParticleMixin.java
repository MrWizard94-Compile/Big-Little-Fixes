package com.mrwizard94.biglittlefixes.mixin;

import com.mrwizard94.biglittlefixes.features.SlabLightSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Keep flame/dust particles flush when floor torches sit on bottom slabs.
 */
@Mixin(TorchBlock.class)
public abstract class TorchBlockParticleMixin {

    @Shadow
    protected ParticleOptions flameParticle;

    @Inject(method = "animateTick", at = @At("HEAD"), cancellable = true)
    private void biglittlefixes$flushFlame(
            BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (((Object) this) instanceof WallTorchBlock || ((Object) this) instanceof RedstoneTorchBlock) {
            return;
        }
        if (SlabLightSupport.yOffset(level, pos, state) == 0.0D) {
            return;
        }
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.7D + SlabLightSupport.FLOOR_Y;
        double z = pos.getZ() + 0.5D;
        ParticleOptions flame = this.flameParticle != null ? this.flameParticle : ParticleTypes.FLAME;
        level.addParticle(flame, x, y, z, 0.0D, 0.0D, 0.0D);
        ci.cancel();
    }
}
