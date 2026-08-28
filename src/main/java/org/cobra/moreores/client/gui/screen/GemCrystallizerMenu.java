package org.cobra.moreores.client.gui.screen;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.block.entity.gem.machine.GemCrystallizerBlockEntity;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.networking.block.data.GemCrystallizerDataSynchronizer;
import org.cobra.moreores.tags.ModItemTags;

public class GemCrystallizerMenu extends AbstractGemMachineMenu<GemCrystallizerBlockEntity> {
    private final Container inventory;
    private final ContainerLevelAccess context;
    private final ContainerData containerData;

    public GemCrystallizerMenu(int syncId, Inventory playerInventory, GemCrystallizerDataSynchronizer data) {
        this(syncId, playerInventory, playerInventory.player.level().getBlockEntity(data.blockPos()),
                new SimpleContainerData(4));
    }

    public GemCrystallizerMenu(int syncId, Inventory playerInventory, BlockEntity entity, ContainerData containerData) {
        super(ModMenuType.GEM_CRYSTALLIZER, syncId, entity.getBlockPos(), (GemCrystallizerBlockEntity)  entity);
        checkContainerSize((Container) entity, 11);

        this.inventory = (Container) entity;
        this.context = ContainerLevelAccess.create(entity.getLevel(), entity.getBlockPos());
        this.containerData = containerData;

        this.addSlot(new Slot(inventory, 0, 47, 22) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItemTags.GEMSTONE_BLOCKS) || stack.is(ModItemTags.GEMSTONE);
            }
        }); // Input Before

        this.addSlot(new Slot(inventory, 1, 87, 22) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItemTags.GEMSTONE_BLOCKS)
                        || stack.is(ModItemTags.GEMSTONE) || stack.is(Blocks.OBSIDIAN.asItem());
            }
        }); // Input After

        this.addSlot(new Slot(inventory, 2, 64, 72) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItemTags.CRYSTALLIZED);
            }
        }); // Result

        this.addSlot(new Slot(inventory, 3, 13, 21) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItemTags.HAS_ENERGY);
            }
        }); // Energy Input

        this.addSlot(new Slot(inventory, 4, 39, 59) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItems.RADIANT_DUST);
            }
        }); // Radiant Slot

        this.addSlot(new Slot(inventory, 5, 92, 59)); // Redstone Slot

        addSecondAdditionalInventory(inventory);

        addPlayerGenericInventory(playerInventory);
        addPlayerHotbarInventory(playerInventory);

        addDataSlots(containerData);
    }

    @Override
    public void addSecondAdditionalInventory(Container playerInventory) {
        for (int i = 0; i < 5; ++i) {
            this.addSlot(new Slot(playerInventory, 6 + i, 179, 97 + i * 18));
        }
    }

    public boolean isCrystallizing() {
        return containerData.get(0) > 0;
    }

    public int getRedstoneDust() {
        return containerData.get(3);
    }

    public int getDustCount() {
        return containerData.get(2);
    }

    public int progressGetter() {
        int progress = this.containerData.get(0); //Progress
        int maxProgress = this.containerData.get(1); //Max Progress
        int progressArrowSize = 28; //Height of progress arrow

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
                if(!this.moveItemStackTo(originalStack, 18, 54, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(originalStack, stack);
            } else if(invSlot >= 18 && invSlot < 54) {
                if(isValidInput(originalStack)) {
                    if(!this.moveItemStackTo(originalStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (isValidEnergyItem(originalStack)) {
                    if(!this.moveItemStackTo(originalStack, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (isRadiantDust(originalStack)) {
                    if(!this.moveItemStackTo(originalStack, 4, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else {
                    if(!this.moveItemStackTo(originalStack, 6, 18, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else {
                if(!this.moveItemStackTo(originalStack, 18, 54, false)) {
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
        return stack.is(ModItemTags.GEMSTONE_BLOCKS) || stack.is(ModItemTags.RAW_GEMSTONE_BLOCKS) ||
                stack.is(ModItemTags.RAW_GEMSTONE) || stack.is(ModItemTags.GEMSTONE);
    }

    private boolean isValidEnergyItem(ItemStack stack) {
        return stack.is(ModItemTags.HAS_ENERGY);
    }

    private boolean isRadiantDust(ItemStack stack) {
        return stack.is(ModItems.RADIANT_DUST);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.context, player, ModBlocks.GEM_CRYSTALLIZER_BLOCK);
    }
}