package org.cobra.moreores.client.gui.screen;

import net.minecraft.world.entity.player.Inventory;
import org.cobra.moreores.world.block.entity.gem.machine.AbstractGemMachineBlockEntity;

public interface MenuHelper<T extends AbstractGemMachineBlockEntity<?>> {

    void addPlayerGenericInventory(Inventory playerInventory);

    void addPlayerHotbarInventory(Inventory playerInventory);

    T getBlockEntity();
}
