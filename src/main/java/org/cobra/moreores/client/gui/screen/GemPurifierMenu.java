package org.cobra.moreores.client.gui.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.block.entity.gem.GemPurifierBlockEntity;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.networking.block.data.GemPurifierDataSynchronizer;
import org.cobra.moreores.core.registry.tag.ModItemTags;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class GemPurifierMenu extends AbstractGemMachineMenu implements MenuHelper {
    private final Container inventory;
    private final ContainerLevelAccess context;
    private final ContainerData propertyDelegate;
    public final GemPurifierBlockEntity blockEntity;

    // Client Side Constructor
    public GemPurifierMenu(int syncId, Inventory playerInventory, GemPurifierDataSynchronizer data) {
        this(syncId, playerInventory, playerInventory.player.level().getBlockEntity(data.blockPos()),
                new SimpleContainerData(2));
    }

    // Main Constructor
    public GemPurifierMenu(int syncId, Inventory playerInventory, BlockEntity blockEntity, ContainerData propertyDelegate) {
        super(ModMenuType.GEM_PURIFIER, syncId, blockEntity.getBlockPos());
        checkContainerSize((Container) blockEntity, 16);

        this.inventory = ((Container) blockEntity);
        this.context = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());
        this.propertyDelegate = propertyDelegate;
        this.blockEntity = (GemPurifierBlockEntity) blockEntity;

        this.addSlot(new Slot(inventory, 0, 79, 11) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItemTags.RAW_GEMSTONE) || stack.is(ModItemTags.RAW_GEMSTONE_BLOCKS);
            }
        }); // Input
        this.addSlot(new Slot(inventory, 1, 79, 61) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItemTags.GEMSTONE) || stack.is(ModItemTags.GEMSTONE_BLOCKS);
            }
        }); // Result
        this.addSlot(new Slot(inventory, 2, 40, 20) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItems.ENERGY_INGOT) || stack.is(ModBlocks.ENERGY_BLOCK.asItem());
            }
        }); // Energy Input
        this.addSlot(new Slot(inventory, 3, 12, 20)); // Water Source

        addFirstAdditionalInventory(inventory);
        addSecondAdditionalInventory(inventory);

        addPlayerGenericInventory(playerInventory);
        addPlayerHotbarInventory(playerInventory);

        addDataSlots(propertyDelegate);
    }

    public boolean isPolishing() {
        return propertyDelegate.get(0) > 0;
    }

    public int progressGetter() {
        int progress = this.propertyDelegate.get(0); //Progress
        int maxProgress = this.propertyDelegate.get(1); //Max Progress
        int progressArrowSize = 27; //Height of progress arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize/ maxProgress : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if(slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            stack = originalStack.copy();

            if(invSlot == 2) {
                if(!this.moveItemStackTo(originalStack, 15, 51, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(originalStack, stack);
            } else if(invSlot >= 15 && invSlot < 51) {
                if(isValidInput(originalStack)) {
                    if(!this.moveItemStackTo(originalStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (isValidEnergyItem(originalStack)) {
                    if(!this.moveItemStackTo(originalStack, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(!this.moveItemStackTo(originalStack, 3, 15, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else {
                if(!this.moveItemStackTo(originalStack, 15, 51, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if(originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return stack;
    }

    private boolean isValidInput(ItemStack stack) {
        return stack.is(ModItemTags.RAW_GEMSTONE);
    }

    private boolean isValidEnergyItem(ItemStack stack) {
        return stack.is(ModItems.ENERGY_INGOT) || stack.is(ModBlocks.ENERGY_BLOCK.asItem());
    }

    private boolean isWaterBucket(ItemStack stack) {
        return stack.is(Items.WATER_BUCKET);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.context, player, ModBlocks.GEM_PURIFIER_BLOCK);
    }

    public void addFirstAdditionalInventory(Container playerInventory) {
        for (int i = 0; i < 8; ++i) {
            this.addSlot(new Slot(playerInventory, 4 + i, 26 + i * 18, 95));
        }
    }

    public void addSecondAdditionalInventory(Container playerInventory) {
        for (int i = 0; i < 4; ++i) {
            this.addSlot(new Slot(playerInventory, 12 +  i, 179, 115 + i * 18));
        }
    }

    @Override
    public BlockEntity getBlockEntity(BlockPos pos, BlockState state, Level world) {
        return this.blockEntity;
    }

    public long getEnergy() {
        return this.blockEntity.energyAmount();
    }

    public long getEnergyCap() {
        return this.blockEntity.energyStorage.getCapacity();
    }

    public float getEnergyPercent() {
        SimpleEnergyStorage energyStorage = this.blockEntity.energyStorage;
        long energy = energyStorage.getAmount();
        long maxEnergy = energyStorage.getCapacity();
        if (maxEnergy == 0 || energy == 0)
            return 0.0F;

        return Mth.clamp((float) energy / (float) maxEnergy, 0.0F, 1.0F);
    }
}