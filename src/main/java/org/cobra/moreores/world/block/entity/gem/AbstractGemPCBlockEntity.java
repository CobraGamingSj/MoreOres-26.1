package org.cobra.moreores.world.block.entity.gem;

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
import org.cobra.moreores.networking.block.data.GemPFEnergyDataPayload;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public abstract class AbstractGemPCBlockEntity<P extends CustomPacketPayload> extends BlockEntity implements ExtendedMenuProvider<P>, ImplementedInventory, TickableBlockEntity {
    protected final NonNullList<ItemStack> main;
    protected PolishingInfusionState polishingInfusionState = PolishingInfusionState.IDLE;
    protected EnergyState energyState = EnergyState.IDLE;
    protected IGemstone gemType = IGemstone.EMPTY;

    public int initialProgress = 0;

    public AbstractGemPCBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.main = NonNullList.withSize(mainStackSize(), ItemStack.EMPTY);
    }

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(getEnergyCapacity(), getMaxEnergyInsert(), getMaxEnergyExtract()) {
        @Override
        public void onFinalCommit() {
            super.onFinalCommit();

            setChanged();

            for(ServerPlayer user : PlayerLookup.tracking((ServerLevel) level, getBlockPos())) {
                ServerPlayNetworking.send(user, new GemPFEnergyDataPayload(this.amount, getBlockPos()));
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
        view.storeNullable("PolishingState", PolishingInfusionState.CODEC, polishingInfusionState);
        view.storeNullable("EnergyState", EnergyState.CODEC, energyState);
    }

    @Override
    public void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        ContainerHelper.loadAllItems(view, main);
        initialProgress = view.getIntOr("Progress", 0);
        energyStorage.amount = view.getLongOr("Energy", 0);
        polishingInfusionState = view.read("PolishingState", PolishingInfusionState.CODEC).orElse(PolishingInfusionState.IDLE);
        energyState = view.read("EnergyState", EnergyState.CODEC).orElse(EnergyState.IDLE);
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

    public abstract GemCategory category();
    
    public IGemstone getGem() {
        return detectGem(resultStack());
    }

    public void setGem(IGemstone gem) {
        gemType = gem;
    }

    public void setEnergyLevel(long energy) {
        this.energyStorage.amount = Math.min(energy, getEnergyCapacity());
    }

    protected boolean hasEnergySourceProviderItem() {
        return this.energyStack().is(ModItems.ENERGY_INGOT) || this.energyStack().is(ModBlocks.ENERGY_BLOCK.asItem());
    }

    protected void increaseProgress() {
        if(this.level.hasNeighborSignal(this.worldPosition)) {
            initialProgress += (int) 2.5;
        } else {
            initialProgress++;
        }
    }

    protected boolean hasEnoughEnergy() {
        return this.energyStorage.amount >= 13;
    }

    protected void insertEnergy() {
        if(!hasEnergySourceProviderItem() || energyStorage.amount >= 1_000_000) {
            energyState = EnergyState.IDLE;
            return;
        }
        long amount = energyStack().is(ModItems.ENERGY_INGOT) ? 102 : 154;
        if(level.hasNeighborSignal(worldPosition)) amount *= (int) 2.5;
        try(Transaction transaction = Transaction.openOuter()) {
            long inserted = energyStorage.insert(amount, transaction);
            transaction.commit();
            if(inserted > 0) energyState = EnergyState.INSERTING;
            else energyState = EnergyState.IDLE;
        }
    }

    protected void extractEnergy() {
        long amount = level.hasNeighborSignal(worldPosition) ? 64 : 13;
        try(Transaction transaction = Transaction.openOuter()) {
            energyStorage.extract(amount, transaction);
            transaction.commit();
        }
        energyState = EnergyState.EXTRACTING;
    }

    protected abstract boolean hasRecipe();

    private void resetProgress() {
        this.initialProgress = 0;
    }

    public void start() {
        if(polishingInfusionState.isIdle() && hasRecipe() && hasEnoughEnergy()) {
            polishingInfusionState = PolishingInfusionState.RUNNING;
        }
    }

    public void pause() {
        if(polishingInfusionState.isRunning()) {
            polishingInfusionState = PolishingInfusionState.PAUSED;
        }
    }

    public void resume() {
        if(polishingInfusionState.isPaused()&& hasRecipe() && hasEnoughEnergy()) {
            polishingInfusionState = PolishingInfusionState.RUNNING;
        }
    }

    public void stop() {
        if(!polishingInfusionState.isIdle()) {
            polishingInfusionState = PolishingInfusionState.IDLE;
            resetProgress();
        }
    }
}
