package org.cobra.moreores.client.gui;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class SkillHolderSlot extends Slot {
    private boolean disabledSlot;

    public SkillHolderSlot(int x, int y, int slot, Container container) {
        super(container, slot, x, y);
    }

    public boolean isDisabledSlot() {
        return disabledSlot;
    }

    public void setDisabledSlot(boolean disabledSlot) {
        this.disabledSlot = disabledSlot;
    }

    private boolean isAvailable() {
        return switch (index) {
            case 0 -> true;
            case 1, 2 -> !container.getItem(0).isEmpty();
            case 3 -> !container.getItem(1).isEmpty();
            case 4 -> !container.getItem(2).isEmpty();
            case 5 -> !container.getItem(3).isEmpty();
            case 6 -> !container.getItem(4).isEmpty();
            case 7 -> !container.getItem(5).isEmpty();
            case 8 -> !container.getItem(6).isEmpty();
            case 9 -> !container.getItem(7).isEmpty();
            case 10 -> !container.getItem(8).isEmpty();
            default -> false;
        };
    }

    @Override
    public boolean isHighlightable() {
        return !disabledSlot && isAvailable();
    }

    @Override
    public boolean isActive() {
        return !disabledSlot && isHighlightable();
    }
}