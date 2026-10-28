package com.mrwizard94.biglittlefixes.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrwizard94.biglittlefixes.features.SlabLightSupport;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Flush slab lights on the chunk meshing path.
 * World rendering goes through {@code renderBatched}, not the bare vanilla
 * {@code ModelBlockRenderer.tesselateBlock} overload.
 */
@Mixin(BlockRenderDispatcher.class)
public abstract class BlockRenderDispatcherSlabOffsetMixin {

    @Unique
    private static final ThreadLocal<Integer> BLF$DEPTH = ThreadLocal.withInitial(() -> 0);

    // Vanilla 7-arg batched render
    @Inject(
            method = "renderBatched(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;)V",
            at = @At("HEAD"))
    private void biglittlefixes$pushVanilla(
            BlockState state,
            BlockPos pos,
            BlockAndTintGetter level,
            PoseStack poseStack,
            VertexConsumer consumer,
            boolean checkSides,
            RandomSource random,
            CallbackInfo ci) {
        biglittlefixes$push(level, pos, state, poseStack);
    }

    @Inject(
            method = "renderBatched(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;)V",
            at = @At("RETURN"))
    private void biglittlefixes$popVanilla(
            BlockState state,
            BlockPos pos,
            BlockAndTintGetter level,
            PoseStack poseStack,
            VertexConsumer consumer,
            boolean checkSides,
            RandomSource random,
            CallbackInfo ci) {
        biglittlefixes$pop(poseStack);
    }

    // Forge ModelData overload (primary chunk path) — not in SRG; remap=false
    @Inject(
            method = "renderBatched(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;Lnet/minecraftforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V",
            at = @At("HEAD"),
            remap = false)
    private void biglittlefixes$pushForge(
            BlockState state,
            BlockPos pos,
            BlockAndTintGetter level,
            PoseStack poseStack,
            VertexConsumer consumer,
            boolean checkSides,
            RandomSource random,
            ModelData modelData,
            RenderType renderType,
            CallbackInfo ci) {
        biglittlefixes$push(level, pos, state, poseStack);
    }

    @Inject(
            method = "renderBatched(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;Lnet/minecraftforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V",
            at = @At("RETURN"),
            remap = false)
    private void biglittlefixes$popForge(
            BlockState state,
            BlockPos pos,
            BlockAndTintGetter level,
            PoseStack poseStack,
            VertexConsumer consumer,
            boolean checkSides,
            RandomSource random,
            ModelData modelData,
            RenderType renderType,
            CallbackInfo ci) {
        biglittlefixes$pop(poseStack);
    }

    @Unique
    private static void biglittlefixes$push(
            BlockAndTintGetter level, BlockPos pos, BlockState state, PoseStack poseStack) {
        float y = SlabLightSupport.renderYOffset(level, pos, state);
        if (y == 0.0f) {
            return;
        }
        int depth = BLF$DEPTH.get();
        if (depth > 0) {
            BLF$DEPTH.set(depth + 1);
            return;
        }
        poseStack.pushPose();
        poseStack.translate(0.0D, y, 0.0D);
        BLF$DEPTH.set(1);
    }

    @Unique
    private static void biglittlefixes$pop(PoseStack poseStack) {
        int depth = BLF$DEPTH.get();
        if (depth <= 0) {
            return;
        }
        if (depth == 1) {
            poseStack.popPose();
        }
        BLF$DEPTH.set(depth - 1);
    }
}
