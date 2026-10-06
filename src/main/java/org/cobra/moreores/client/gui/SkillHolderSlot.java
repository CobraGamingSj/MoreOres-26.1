package org.cobra.moreores.client.gui;

import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class SkillHolderSlot extends Slot {

    public final int node;
    public final int x, y;
    public int width, height;
    public ItemStack item;
    private final Container container;

    public SkillHolderSlot(int x, int y, int slot, Container container) {
        super(container, slot, x, y);
        this.x = x;
        this.y = y;
        this.node = slot;
        this.container = container;
    }

    public ItemStack getItem() {
        return this.container.getItem(node);
    }

    public @Nullable Identifier getNoItemIcon() {
        return null;
    }

    public int getMaxStackSize() {
        return this.container.getMaxStackSize();
    }

    public int getMaxStackSize(final ItemStack itemStack) {
        return Math.min(this.getMaxStackSize(), itemStack.getMaxStackSize());
    }

    public boolean isActive() {
        return true;
    }
}
