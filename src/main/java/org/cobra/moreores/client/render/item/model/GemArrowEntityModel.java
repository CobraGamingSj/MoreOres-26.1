package org.cobra.moreores.client.render.item.model;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.util.Mth;
import org.cobra.moreores.MoreOresModInitializer;

import java.util.function.UnaryOperator;

public class GemArrowEntityModel extends EntityModel<ArrowRenderState> {
    public static final ModelLayerLocation ARROW = new ModelLayerLocation(MoreOresModInitializer.id("gem_arrow"), "main");
    public GemArrowEntityModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild(
                "back",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F),
                PartPose.offsetAndRotation(-11.0F, 0.0F, 0.0F, (float) (Math.PI / 4), 0.0F, 0.0F).withScale(0.8F)
        );
        CubeListBuilder modelPartBuilder = CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -2.0F, 0.0F, 16.0F, 4.0F, 0.0F, CubeDeformation.NONE, 1.0F, 0.8F);
        modelPartData.addOrReplaceChild("cross_1", modelPartBuilder, PartPose.rotation((float) (Math.PI / 4), 0.0F, 0.0F));
        modelPartData.addOrReplaceChild("cross_2", modelPartBuilder, PartPose.rotation((float) (Math.PI * 3.0 / 4.0), 0.0F, 0.0F));
        return LayerDefinition.create(modelData.transformed((UnaryOperator<PartPose>) modelTransform -> modelTransform.scaled(0.9F)), 32, 32);
    }

    public void setAngles(ArrowRenderState projectileEntityRenderState) {
        super.setupAnim(projectileEntityRenderState);
        if (projectileEntityRenderState.shake > 0.0F) {
            float f = -Mth.sin(projectileEntityRenderState.shake * 3.0F) * projectileEntityRenderState.shake;
            this.root.zRot += f * (float) (Math.PI / 180.0);
        }
    }
}
