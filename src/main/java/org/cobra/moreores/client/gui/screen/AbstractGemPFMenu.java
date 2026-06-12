package org.cobra.moreores.client.gui.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.jspecify.annotations.Nullable;

public abstract class AbstractGemPFMenu extends AbstractContainerMenu implements MenuHelper {

    protected final BlockPos pos;

    public AbstractGemPFMenu(@Nullable MenuType<?> type, int syncId, BlockPos pos) {
        super(type, syncId);
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    };

    @Override
    public void addPlayerGenericInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 115 + i * 18));
            }
        }
    }

    @Override
    public void addPlayerHotbarInventory(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 173));
        }
    }

    public void addFirstAdditionalInventory(Container playerInventory) {
        for (int i = 0; i < 8; ++i) {
            this.addSlot(new Slot(playerInventory, 5 + i, 26 + i * 18, 95));
        }
    }

    public void addSecondAdditionalInventory(Container playerInventory) {
        for (int i = 0; i < 4; ++i) {
            this.addSlot(new Slot(playerInventory, 13 + i, 179, 115 + i * 18));
        }
    }
}
