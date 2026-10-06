package org.cobra.moreores.client.gui.screen;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.cobra.moreores.client.gui.SkillHolderSlot;
import org.cobra.moreores.world.item.ModItems;

public class SkillTreeMenu extends AbstractContainerMenu {
    private final Container container = new SimpleContainer(11);

    public SkillTreeMenu(int containerId, Inventory inventory) {
        super(ModMenuType.SKILL_NODE_TREE, containerId);
        this.addSlot(new SkillHolderSlot(29, 63, 0, container) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return true;
            }
        });
        this.addSlot(new SkillHolderSlot(57, 35, 1, container));
        this.addSlot(new SkillHolderSlot(57, 91, 2, container));
        addPlayerGenericInventory(inventory);
        addPlayerHotbarInventory(inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    private void addPlayerGenericInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 48 + l * 18, 141 + i * 18));
            }
        }
    }

    private void addPlayerHotbarInventory(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 48 + i * 18, 199));
        }
    }
}