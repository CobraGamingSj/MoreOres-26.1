package org.cobra.moreores.client.render.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.cobra.moreores.world.block.GemPurifierBlock;
import org.cobra.moreores.world.block.entity.gem.GemCrystallizeBlockEntity;
import org.jetbrains.annotations.Nullable;

public final class GemCrystallizerBlockEntityRenderer implements BlockEntityRenderer<GemCrystallizeBlockEntity, GemCrystalizerBlockEntityRenderState> {
    private final BlockEntityRendererProvider.Context context;
    private final ItemModelResolver itemModelManager;

    public GemCrystallizerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
        this.itemModelManager = context.itemModelResolver();
    }

    private void renderEnergyTray(ItemStackRenderState state, PoseStack matrices,
                                  SubmitNodeCollector queue,
                                  float x, float z, float rotationAngle, int light) {
        matrices.pushPose();

        matrices.translate(0.5, 0, 0.5);
        matrices.mulPose(Axis.YP.rotationDegrees(rotationAngle));
        matrices.translate(-0.5, 0, -0.5);

        matrices.translate(x, 0.9F, z);
        matrices.scale(0.125f, 0.125f, 0.125f);
        matrices.mulPose(Axis.XP.rotationDegrees(-270));
        matrices.mulPose(Axis.ZP.rotationDegrees(270));

        state.submit(matrices, queue, light, OverlayTexture.NO_OVERLAY, 0);
        matrices.popPose();
    }

    // X-axis
    private void renderInputTray(ItemStackRenderState state, PoseStack matrices,
                            SubmitNodeCollector queue,
                            float x, float z, float rotationAngle, int light) {
        matrices.pushPose();

        matrices.translate(0.5, 0, 0.5);
        matrices.mulPose(Axis.YP.rotationDegrees(rotationAngle));
        matrices.translate(-0.5, 0, -0.5);

        matrices.translate(x, 0.9F, z);
        matrices.scale(0.15f, 0.15f, 0.15f);
        matrices.mulPose(Axis.XP.rotationDegrees(-270));
        matrices.mulPose(Axis.ZP.rotationDegrees(270));

        state.submit(matrices, queue, light, OverlayTexture.NO_OVERLAY, 0);
        matrices.popPose();
    }

    private void renderOutputTray(ItemStackRenderState state, PoseStack matrices,
                            SubmitNodeCollector queue,
                            float x, float z, float rotationAngle, int light) {
        matrices.pushPose();

        matrices.translate(0.5, 0, 0.5);
        matrices.mulPose(Axis.YP.rotationDegrees(rotationAngle));
        matrices.translate(-0.5, 0, -0.5);

        matrices.translate(x, 0.9F, z);
        matrices.scale(0.25f, 0.25f, 0.25f);
        matrices.mulPose(Axis.XP.rotationDegrees(-270));
        matrices.mulPose(Axis.ZP.rotationDegrees(270));

        state.submit(matrices, queue, light, OverlayTexture.NO_OVERLAY, 0);
        matrices.popPose();
    }

    private float getRotationAngle(GemCrystallizeBlockEntity entity) {
        if (entity.getLevel() != null) {
            return switch (entity.getBlockState().getValue(GemPurifierBlock.FACING)) {
                case NORTH -> 180f;
                case EAST -> 90f;
                case WEST -> -90f;
                default -> 0f;
            };
        }
        return 0f;
    }

    @Override
    public void extractRenderState(GemCrystallizeBlockEntity blockEntity, GemCrystalizerBlockEntityRenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
        state.setEntity(blockEntity);
        state.entityWorld = blockEntity.getLevel();
        state.lightPos = blockEntity.getBlockPos();

        itemModelManager.updateForTopItem(state.inputBeforeItemRenderState, blockEntity.ingredientStack(),
                ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
        itemModelManager.updateForTopItem(state.inputAfterItemRenderState, blockEntity.ingredientAfterStack(),
                ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
        itemModelManager.updateForTopItem(state.energyItemRenderState, blockEntity.energyStack(),
                ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
        itemModelManager.updateForTopItem(state.resultItemRenderState, blockEntity.resultStack(),
                ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
    }

    @Override
    public void submit(GemCrystalizerBlockEntityRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState camera) {
        GemCrystallizeBlockEntity entity = state.entity;
        if (entity == null || entity.getLevel() == null) return;

        int light = getLightLevel(state.entityWorld, state.lightPos);
        float rotationAngles = getRotationAngle(entity);

        renderEnergyTray(state.energyItemRenderState, matrices, queue, 0.5f, 0.25f, rotationAngles, light);
        renderInputTray(state.inputBeforeItemRenderState, matrices, queue, 0.775f, 0.21f, rotationAngles, light);
        renderInputTray(state.inputAfterItemRenderState, matrices, queue, 0.225f, 0.21f, rotationAngles, light);
        renderOutputTray(state.resultItemRenderState, matrices, queue, 0.51f, 0.675f, rotationAngles, light);
    }

    private int getLightLevel(Level world, BlockPos pos) {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos);
        int sLight = world.getBrightness(LightLayer.SKY, pos);
        return LightCoordsUtil.pack(bLight, sLight);
    }

    @Override
    public GemCrystalizerBlockEntityRenderState createRenderState() {
        return new GemCrystalizerBlockEntityRenderState();
    }

    public BlockEntityRendererProvider.Context context() {
        return context;
    }
}
