package org.cobra.moreores.client.render.item.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.entity.GemArrowEntity;

public class GemArrowEntityRenderer extends ArrowRenderer<GemArrowEntity, ArrowRenderState> {
    public static final Identifier TEXTURE = MoreOresModInitializer.id("textures/entity/item/gem_arrow.png");
    public GemArrowEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected Identifier getTextureLocation(ArrowRenderState state) {
        return TEXTURE;
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    public void extractRenderState(GemArrowEntity persistentProjectileEntity, ArrowRenderState projectileEntityRenderState, float f) {
        super.extractRenderState(persistentProjectileEntity, projectileEntityRenderState, f);
    }
}
