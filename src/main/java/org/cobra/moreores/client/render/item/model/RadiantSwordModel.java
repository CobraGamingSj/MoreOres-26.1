package org.cobra.moreores.client.render.item.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.cobra.moreores.MoreOresModInitializer;

public class RadiantSwordModel extends EntityModel<EntityRenderState> {
    public static final ModelLayerLocation MODEL_LAYER= new ModelLayerLocation(MoreOresModInitializer.id("radiant_sword"), "main");

	public RadiantSwordModel(ModelPart root) {
        super(root);
	}
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 2.0F, 2.0F, CubeDeformation.NONE), PartPose.ZERO);
		return LayerDefinition.create(modelData, 16, 16);
	}
}