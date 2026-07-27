package org.cobra.moreores.client.gui.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.cobra.moreores.world.block.entity.gem.machine.AbstractGemMachineBlockEntity;
import org.jspecify.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public abstract class AbstractGemMachineMenu<T extends AbstractGemMachineBlockEntity<?>> extends AbstractContainerMenu implements MenuHelper<T> {
    protected final BlockPos blockPos;
    final T blockEntity;

    public AbstractGemMachineMenu(@Nullable MenuType<?> type, int syncId, BlockPos blockPos, T blockEntity) {
        super(type, syncId);
        this.blockPos = blockPos;
        this.blockEntity = blockEntity;
    }

    public BlockPos getBlockPos() {
        return blockPos;
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

    public long getEnergy() {
        return this.blockEntity.energyStorage().getAmount();
    }

    public long getEnergyCap() {
        return this.blockEntity.energyStorage().getCapacity();
    }

    public float getEnergyPercent() {
        SimpleEnergyStorage energyStorage = this.blockEntity.energyStorage();
        long energy = energyStorage.getAmount();
        long maxEnergy = energyStorage.getCapacity();
        if (maxEnergy == 0 || energy == 0)
            return 0.0F;

        return Mth.clamp((float) energy / (float) maxEnergy, 0.0F, 1.0F);
    }
    
    @Override
    public T getBlockEntity() {
        return this.blockEntity;
    }
}
