package org.cobra.moreores.world.block.entity.gem.machine;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.block.entity.ImplementedInventory;
import org.cobra.moreores.world.block.entity.TickableBlockEntity;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.world.item.util.GemCategory;
import org.cobra.moreores.world.item.util.impl.CrystallizationGemstones;
import org.cobra.moreores.world.item.util.impl.IGemstone;
import org.cobra.moreores.world.item.util.impl.PurificationGemstones;
import org.cobra.moreores.networking.block.data.GemMachineEnergyDataPayload;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public abstract class AbstractGemMachineBlockEntity<P extends CustomPacketPayload> extends BlockEntity implements ExtendedMenuProvider<P>, ImplementedInventory, TickableBlockEntity {
    protected final NonNullList<ItemStack> main;
    protected MachineStatus machineStatus = MachineStatus.IDLE;
    protected MachineEnergyState machineEnergyState = MachineEnergyState.IDLE;
    protected IGemstone iGemstone = IGemstone.EMPTY;

    public int initialProgress = 0;

    protected int redstone = 0;
    protected int maxRedstone = 10000;
    protected int redstoneTick;

    protected long previousRemovedEnergyMilestone = 0;
    protected long previousRemovedRedstoneMilestone = 0;
    
    public AbstractGemMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.main = NonNullList.withSize(mainStackSize(), ItemStack.EMPTY);
    }

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(getEnergyCapacity(), getMaxEnergyInsert(), getMaxEnergyExtract()) {
        @Override
        public void onFinalCommit() {
            super.onFinalCommit();

            setChanged();

            for(ServerPlayer user : PlayerLookup.tracking((ServerLevel) level, getBlockPos())) {
                ServerPlayNetworking.send(user, new GemMachineEnergyDataPayload(this.amount, getBlockPos()));
            }
        }
    };

    public abstract int mainStackSize();
    public abstract long getEnergyCapacity();
    public abstract long getMaxEnergyInsert();
    public abstract long getMaxEnergyExtract();
    public abstract RecipeManager.CachedCheck<?, ?> getMatchGetter();
    public abstract int getInitialProgress();

    @Override
    public void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        ContainerHelper.saveAllItems(view, main);
        view.putInt("Progress", initialProgress);
        view.putLong("Energy", energyStorage.amount);
        view.storeNullable("PolishingState", MachineStatus.CODEC, machineStatus);
        view.storeNullable("EnergyState", MachineEnergyState.CODEC, machineEnergyState);
        view.putInt("Redstone", redstone);
        view.putInt("RedstoneTick", redstoneTick);
    }

    @Override
    public void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        ContainerHelper.loadAllItems(view, main);
        initialProgress = view.getIntOr("Progress", 0);
        redstone = view.getIntOr("Redstone", 0);
        redstoneTick = view.getIntOr("RedstoneTick", 0);
        energyStorage.amount = view.getLongOr("Energy", 0);
        machineStatus = view.read("PolishingState", MachineStatus.CODEC).orElse(MachineStatus.IDLE);
        machineEnergyState = view.read("EnergyState", MachineEnergyState.CODEC).orElse(MachineEnergyState.IDLE);
    }

    public long energyAmount() {
        return this.energyStorage.amount;
    }
    
    public IGemstone detectGem(ItemStack stack) {
        Item item = stack.getItem();
        for (PurificationGemstones gems : PurificationGemstones.values()) {
            for (Item item1 : gems.items()) {
                if(item1 == item) {
                    return gems;
                }
            }
        }
        for (CrystallizationGemstones gems : CrystallizationGemstones.values()) {
            for(Item item1 : gems.items()) {
                if(item1 == item) {
                    return gems;
                }
            }
        }
        return IGemstone.EMPTY;
    }

    public int getRedstone() {
        return this.redstone;
    }
    
    public abstract GemCategory category();
    
    public IGemstone getGemstone() {
        return detectGem(resultStack());
    }

    public void setGem(IGemstone gem) {
        iGemstone = gem;
    }

    public void setEnergyAmount(long energy) {
        this.energyStorage.amount = Math.min(energy, getEnergyCapacity());
    }

    protected boolean hasEnergySourceProviderItem() {
        return this.energyStack().is(ModItems.ENERGY_INGOT) || this.energyStack().is(ModBlocks.ENERGY_BLOCK.asItem());
    }

    protected void increaseProgress() {
        if(this.level.hasNeighborSignal(this.worldPosition) || redstone > 0) {
            initialProgress += (int) 2.5;
        } else {
            initialProgress++;
        }
    }

    protected void checkForEnoughEnergyAndConsumeSingle(int energySlot) {
        if(energyAmount() > 10000000) {
            energyStorage.amount = 10000000;
        }
        
        long energy = this.energyAmount();

        long [] milestones = {1000000, 2000000, 3000000, 4000000, 5000000, 6000000, 7000000, 8000000, 9000000, 10000000};

        for(long milestone : milestones) {
            if(energy == milestone && previousRemovedEnergyMilestone < milestone) {
                this.removeItem(energySlot, 1);
                previousRemovedEnergyMilestone = milestone;
                break;
            }
        }
    }
    
    protected void checkForEnoughRedstoneAndConsumeSingle(int slot) {
        if(redstone > 10000) {
            redstone = 10000;
        }
        
        int amount = redstone;

        int [] milestones = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};

        for(long milestone : milestones) {
            if(amount == milestone && previousRemovedRedstoneMilestone < milestone) {
                this.removeItem(slot, 1);
                previousRemovedRedstoneMilestone = milestone;
                break;
            }
        }
    }
    
    protected boolean hasEnoughEnergy() {
        return this.energyStorage.amount >= 13;
    }

    protected void giveEnergy() {
        if(!hasEnergySourceProviderItem() || energyStorage.amount >= 1_000_000) {
            machineEnergyState = MachineEnergyState.IDLE;
            return;
        }
        long amount = energyStack().is(ModItems.ENERGY_INGOT) ? 102 : 154;
        if(level.hasNeighborSignal(worldPosition)) amount *= (int) 2.5;
        try(Transaction transaction = Transaction.openOuter()) {
            long inserted = energyStorage.insert(amount, transaction);
            transaction.commit();
            if(inserted > 0) machineEnergyState = MachineEnergyState.INSERTING;
            else machineEnergyState = MachineEnergyState.IDLE;
        }
    }

    protected void eatEnergy() {
        long amount = level.hasNeighborSignal(worldPosition) ? 64 : 13;
        try(Transaction transaction = Transaction.openOuter()) {
            energyStorage.extract(amount, transaction);
            transaction.commit();
        }
        machineEnergyState = MachineEnergyState.EXTRACTING;
    }

    protected abstract boolean hasRecipe();

    protected void clearProgress() {
        this.initialProgress = 0;
    }

    public void start() {
        if(machineStatus.isIdle() && hasRecipe() && hasEnoughEnergy()) {
            machineStatus = MachineStatus.RUNNING;
        }
    }

    public void pause() {
        if(machineStatus.isRunning()) {
            machineStatus = MachineStatus.PAUSED;
        }
    }

    public void resume() {
        if(machineStatus.isPaused()&& hasRecipe() && hasEnoughEnergy()) {
            machineStatus = MachineStatus.RUNNING;
        }
    }

    public void stop() {
        if(!machineStatus.isIdle()) {
            machineStatus = MachineStatus.IDLE;
            clearProgress();
        }
    }
}
