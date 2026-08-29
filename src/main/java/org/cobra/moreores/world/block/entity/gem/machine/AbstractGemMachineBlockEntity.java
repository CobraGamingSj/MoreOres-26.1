package org.cobra.moreores.world.block.entity.gem.machine;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.cobra.moreores.tags.ModItemTags;
import org.cobra.moreores.world.block.entity.ImplementedContainer;
import org.cobra.moreores.world.block.entity.TickableBlockEntity;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.world.item.util.impl.CrystallizationGemstones;
import org.cobra.moreores.world.item.util.impl.IGemstone;
import org.cobra.moreores.world.item.util.impl.PurificationGemstones;
import org.cobra.moreores.networking.block.data.GemMachineEnergyDataPayload;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public abstract class AbstractGemMachineBlockEntity<P extends CustomPacketPayload> extends BlockEntity implements ExtendedMenuProvider<P>, ImplementedContainer, TickableBlockEntity {
    protected final NonNullList<ItemStack> main;
    protected MachineStatus machineStatus = MachineStatus.IDLE;
    protected MachineStatus.EnergyState energyState = MachineStatus.EnergyState.IDLE;
    protected IGemstone gemstone = IGemstone.NONE;

    private int initialProgress = 0;

    protected ItemStack lastPreviewResult = ItemStack.EMPTY;

    long energyExtracted = 0;
    
    protected int redstone = 0;
    protected int maxRedstone = 10000;
    protected int redstoneTick;

    protected long previousRemovedEnergyMilestone = 0;
    protected long previousRemovedRedstoneMilestone = 0;
    
    public AbstractGemMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.main = NonNullList.withSize(mainStackSize(), ItemStack.EMPTY);
    }

    private final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(getEnergyCapacity(), getMaxEnergyInsert(), getMaxEnergyExtract()) {
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

    @Override
    public void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        ContainerHelper.saveAllItems(view, main);
        view.putInt("Progress", initialProgress);
        view.putLong("Energy", energyStorage.amount);
        view.storeNullable("PolishingState", MachineStatus.CODEC, machineStatus);
        view.storeNullable("EnergyState", MachineStatus.EnergyState.CODEC, energyState);
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
        energyState = view.read("EnergyState", MachineStatus.EnergyState.CODEC).orElse(MachineStatus.EnergyState.IDLE);
    }

    public int getInitialProgress() {
        return this.initialProgress;
    }

    public void setInitialProgress(int newProgress) {
        this.initialProgress = newProgress;
    }

    public long energyAmount() {
        return this.energyStorage.amount;
    }
    
    public IGemstone identify(ItemStack stack) {
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
        return IGemstone.NONE;
    }

    public IGemstone gemstone() {
        return identify(resultStack());
    }

    public void setGemstone(IGemstone gemstone) {
        this.gemstone = gemstone;
    }

    public int getRedstone() {
        return this.redstone;
    }

    public SimpleEnergyStorage energyStorage() {
        return this.energyStorage;
    }
    
    public void setEnergyAmount(long energy) {
        this.energyStorage.amount = Math.min(energy, getEnergyCapacity());
    }

    protected boolean hasEnergySource() {
        return this.energyStack().is(ModItemTags.HAS_ENERGY);
    }

    protected void continueTicks() {
        if(this.level.hasNeighborSignal(this.worldPosition) || redstone > 0) {
            initialProgress += 3;
        } else {
            initialProgress++;
        }
    }

    protected void validateEnergyAmount(int energySlot) {
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
    
    protected void validateRedstoneAmount(int slot) {
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

    public void setRedstoneAmount(int newRedstoneAmount) {
        this.redstone = newRedstoneAmount;
    }
    
    protected boolean hasRequiredEnergyAmount() {
        return this.energyStorage.amount >= 13;
    }

    protected void addEnergy() {
        if(!hasEnergySource() || energyStorage.amount >= 1_000_000) {
            energyState = MachineStatus.EnergyState.IDLE;
            return;
        }
        long amount = energyStack().is(ModItems.ENERGY_INGOT) ? 102 : 154;
        if(level.hasNeighborSignal(worldPosition)) amount *= (int) 2.5;
        try(Transaction transaction = Transaction.openOuter()) {
            long inserted = energyStorage.insert(amount, transaction);
            transaction.commit();
            if(inserted > 0) energyState = MachineStatus.EnergyState.INSERTING;
            else energyState = MachineStatus.EnergyState.IDLE;
        }
    }

    protected void consumeEnergy() {
        long amount = level.hasNeighborSignal(worldPosition) ? 64 : 13;
        try(Transaction transaction = Transaction.openOuter()) {
            long extracted = energyStorage.extract(amount, transaction);
            energyExtracted +=  extracted;
            transaction.commit();
        }
        energyState = MachineStatus.EnergyState.EXTRACTING;
    }

    protected abstract boolean checkRecipe();

    protected void clearProgress() {
        this.initialProgress = 0;
    }

    public void startProcess() {
        if(machineStatus.isIdle() && checkRecipe() && hasRequiredEnergyAmount()) {
            machineStatus = MachineStatus.RUNNING;
        }
    }

    public void pauseProcess() {
        if(machineStatus.isRunning()) {
            machineStatus = MachineStatus.PAUSED;
        }
    }

    public void resumeProcess() {
        if(machineStatus.isPaused()&& checkRecipe() && hasRequiredEnergyAmount()) {
            machineStatus = MachineStatus.RUNNING;
        }
    }

    public void stopProcess() {
        if(!machineStatus.isIdle()) {
            machineStatus = MachineStatus.IDLE;
            clearProgress();
            try(Transaction transaction = Transaction.openOuter()) {
                this.energyStorage.insert(energyExtracted, transaction);
                transaction.commit();
            }
            this.energyExtracted = 0;
        }
    }

    public enum MachineStatus implements StringRepresentable {
        IDLE("idle"),
        RUNNING("running"),
        PAUSED("paused");
    
        private final String name;
    
        MachineStatus(String name) {
            this.name = name;
        }
    
        public static final Codec<MachineStatus> CODEC = StringRepresentable.fromEnum(MachineStatus::values);
    
        public boolean isIdle() {
            return this == IDLE;
        }
    
        public boolean isRunning() {
            return this == RUNNING;
        }
    
        public boolean isPaused() {
            return this == PAUSED;
        }
    
        @Override
        public String getSerializedName() {
            return this.name;
        }
    
        public enum EnergyState implements StringRepresentable {
            IDLE("idle"),
            INSERTING("inserting"),
            EXTRACTING("extracting");
        
            private final String name;
        
            EnergyState(String name) {
                this.name = name;
            }
        
            public static final Codec<EnergyState> CODEC = StringRepresentable.fromEnum(EnergyState::values);
        
            @Override
            public String getSerializedName() {
                return this.name;
            }
        }
    }
}
