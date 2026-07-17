package org.cobra.moreores.client.gui.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.cobra.moreores.world.block.entity.gem.AbstractGemMachineBlockEntity;

public interface MenuHelper<T extends AbstractGemMachineBlockEntity<?>> {

    void addPlayerGenericInventory(Inventory playerInventory);

    void addPlayerHotbarInventory(Inventory playerInventory);

    T getBlockEntity();
}
