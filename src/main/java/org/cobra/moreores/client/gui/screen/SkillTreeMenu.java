package org.cobra.moreores.client.gui.screen;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.cobra.moreores.world.item.ModItems;

public class SkillTreeMenu extends AbstractContainerMenu {
    private boolean isLocked;

    public SkillTreeMenu(int containerId, Inventory inventory) {
        super(ModMenuType.SKILL_NODE_TREE, containerId);
        this.addSlot(new Slot(inventory, 0, 17, 42) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return !isLocked && itemStack.is(ModItems.CRIMSON_GARNET);
            }
        });
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}