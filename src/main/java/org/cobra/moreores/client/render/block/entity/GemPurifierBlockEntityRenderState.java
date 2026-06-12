package org.cobra.moreores.client.render.block.entity;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.cobra.moreores.block.entity.gem.GemPurifierBlockEntity;

public class GemPurifierBlockEntityRenderState extends BlockEntityRenderState {

    public GemPurifierBlockEntity entity;
    public Level entityWorld;
    public BlockPos lightPos;

    final ItemStackRenderState inputItemRenderState = new ItemStackRenderState();
    final ItemStackRenderState energyItemRenderState = new ItemStackRenderState();
    final ItemStackRenderState resultItemRenderState = new ItemStackRenderState();

    public static final GemPurifierBlockEntityRenderState INSTANCE = new GemPurifierBlockEntityRenderState();

    public void setEntity(GemPurifierBlockEntity entity) {
        this.entity = entity;
    }
}
