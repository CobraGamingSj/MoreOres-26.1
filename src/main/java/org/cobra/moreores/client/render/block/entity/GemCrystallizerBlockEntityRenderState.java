package org.cobra.moreores.client.render.block.entity;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.cobra.moreores.world.block.entity.gem.machine.GemCrystallizerBlockEntity;

public class GemCrystallizerBlockEntityRenderState extends BlockEntityRenderState {

    public GemCrystallizerBlockEntity entity;
    public Level entityWorld;
    public BlockPos lightPos;

    final ItemStackRenderState inputBeforeItemRenderState = new ItemStackRenderState();
    final ItemStackRenderState inputAfterItemRenderState = new ItemStackRenderState();
    final ItemStackRenderState energyItemRenderState = new ItemStackRenderState();
    final ItemStackRenderState resultItemRenderState = new ItemStackRenderState();

    public void setEntity(GemCrystallizerBlockEntity entity) {
        this.entity = entity;
    }
}
