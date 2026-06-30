package org.cobra.moreores.client.render.block.entity;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.cobra.moreores.world.block.entity.gem.GemCrystallizeBlockEntity;

public class GemCrystalizerBlockEntityRenderState extends BlockEntityRenderState {

    public GemCrystallizeBlockEntity entity;
    public Level entityWorld;
    public BlockPos lightPos;

    final ItemStackRenderState inputBeforeItemRenderState = new ItemStackRenderState();
    final ItemStackRenderState inputAfterItemRenderState = new ItemStackRenderState();
    final ItemStackRenderState energyItemRenderState = new ItemStackRenderState();
    final ItemStackRenderState resultItemRenderState = new ItemStackRenderState();

    public void setEntity(GemCrystallizeBlockEntity entity) {
        this.entity = entity;
    }
}
