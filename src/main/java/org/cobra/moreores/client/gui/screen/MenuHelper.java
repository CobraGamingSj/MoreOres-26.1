package org.cobra.moreores.client.gui.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface MenuHelper {

    void addPlayerGenericInventory(Inventory playerInventory);

    void addPlayerHotbarInventory(Inventory playerInventory);

    BlockEntity getBlockEntity(BlockPos pos, BlockState state, Level world);
}
