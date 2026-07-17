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
import org.cobra.moreores.world.block.entity.gem.machine.GemPurifierBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class GemPurifierBlockEntityRenderer implements BlockEntityRenderer<GemPurifierBlockEntity, GemPurifierBlockEntityRenderState> {
    private final BlockEntityRendererProvider.Context context;
    private final ItemModelResolver itemModelManager;

    public GemPurifierBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
        this.itemModelManager = context.itemModelResolver();
    }

    private void renderItem(ItemStackRenderState state, PoseStack matrices,
                            SubmitNodeCollector queue,
                            float x, float z, float rotationAngle, int light) {
        matrices.pushPose();

        matrices.translate(0.5, 0, 0.5);
        matrices.mulPose(Axis.YP.rotationDegrees(rotationAngle));
        matrices.translate(-0.5, 0, -0.5);

        matrices.translate(x, 0.9F, z);
        matrices.scale(0.25f, 0.25f, 0.25f);
        if(state == GemPurifierBlockEntityRenderState.INSTANCE.resultItemRenderState) {
            matrices.scale(0.5f, 0.5f, 0.5f);
        }
        matrices.mulPose(Axis.XP.rotationDegrees(-270));
        matrices.mulPose(Axis.ZP.rotationDegrees(270));

        state.submit(matrices, queue, light, OverlayTexture.NO_OVERLAY, 0);
        matrices.popPose();
    }

    private float getRotationAngle(GemPurifierBlockEntity entity) {
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
    public void extractRenderState(GemPurifierBlockEntity blockEntity, GemPurifierBlockEntityRenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
        state.setEntity(blockEntity);
        state.entityWorld = blockEntity.getLevel();
        state.lightPos = blockEntity.getBlockPos();

        itemModelManager.updateForTopItem(state.inputItemRenderState, blockEntity.getItem(GemPurifierBlockEntity.INGREDIENT_SLOT),
                ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
        itemModelManager.updateForTopItem(state.energyItemRenderState, blockEntity.getItem(GemPurifierBlockEntity.ENERGY_SOURCE_SLOT),
                ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
        itemModelManager.updateForTopItem(state.resultItemRenderState, blockEntity.getItem(GemPurifierBlockEntity.RESULT_SLOT),
                ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
    }

    private int getLightLevel(Level world, BlockPos pos) {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos);
        int sLight = world.getBrightness(LightLayer.SKY, pos);
        return LightCoordsUtil.pack(bLight, sLight);
    }

    @Override
    public GemPurifierBlockEntityRenderState createRenderState() {
        return new GemPurifierBlockEntityRenderState();
    }

    @Override
    public void submit(GemPurifierBlockEntityRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
        GemPurifierBlockEntity entity = state.entity;
        if (entity == null || entity.getLevel() == null) return;

        int light = getLightLevel(state.entityWorld, state.lightPos);
        float rotationAngles = getRotationAngle(entity);

        renderItem(state.inputItemRenderState, matrices, queue, 0.75f, 0.25f, rotationAngles, light);
        renderItem(state.energyItemRenderState, matrices, queue, 0.25f, 0.25f, rotationAngles, light);
        renderItem(state.resultItemRenderState, matrices, queue, 0.5f, 0.685f, rotationAngles, light);
    }

    public BlockEntityRendererProvider.Context context() {
        return context;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GemPurifierBlockEntityRenderer) obj;
        return Objects.equals(this.context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context);
    }

    @Override
    public String toString() {
        return "GemPurifierBlockEntityRenderer[" +
                "context=" + context + ']';
    }

}
