package org.cobra.moreores.client.render.item.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.render.item.model.RadiantSwordModel;
import org.cobra.moreores.world.entity.RadiantSword;

public class RadiantSwordRenderer extends EntityRenderer<RadiantSword, EntityRenderState> {
    private final RadiantSwordModel model;

    public static final Identifier TEXTURE = MoreOresModInitializer.id("textures/entity/item/gem_arrow.png");
    public RadiantSwordRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new RadiantSwordModel(context.bakeLayer(RadiantSwordModel.MODEL_LAYER));
    }

    @Override
    public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        submitNodeCollector.submitModel(
                this.model, state, poseStack, this.getTextureLocation(state), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null
        );
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    Identifier getTextureLocation(EntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}
