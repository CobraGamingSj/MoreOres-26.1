package org.cobra.moreores.world.block.entity.gem;

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
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.block.entity.ImplementedInventory;
import org.cobra.moreores.world.block.entity.TickableBlockEntity;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.world.item.util.GemCategory;
import org.cobra.moreores.world.item.util.impl.CrystallizationGemstones;
import org.cobra.moreores.world.item.util.impl.Gemstone;
import org.cobra.moreores.world.item.util.impl.PurificationGemstones;
import org.cobra.moreores.networking.block.data.GemMachineEnergyDataPayload;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public abstract class AbstractGemMachineBlockEntity<P extends CustomPacketPayload> extends BlockEntity implements ExtendedMenuProvider<P>, ImplementedInventory, TickableBlockEntity {
    protected final NonNullList<ItemStack> main;
    protected MachineStatus machineStatus = MachineStatus.IDLE;
    protected MachineStatus.EnergyState energyState = MachineStatus.EnergyState.IDLE;
    protected Gemstone gemstone = Gemstone.NONE;

    long extractedEnergyAmount = 0;
    
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

    public SimpleEnergyStorage energyStorage() {
        return this.energyStorage;
    }
    
    public abstract int mainStackSize();
    public abstract long getEnergyCapacity();
    public abstract long getMaxEnergyInsert();
    public abstract long getMaxEnergyExtract();

    @Override
    public void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, main);
        output.putInt("Progress", initialProgress);
        output.putLong("Energy", energyStorage.amount);
        output.putInt("Redstone", this.redstone);
        output.putInt("RedstoneTick", this.redstoneTick);
        output.putLong("EnergyExtracted", extractedEnergyAmount);
        output.storeNullable("PolishingState", MachineStatus.CODEC, machineStatus);
        output.storeNullable("EnergyState", MachineStatus.EnergyState.CODEC, energyState);
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, main);
        initialProgress = input.getIntOr("Progress", 0);
        energyStorage.amount = input.getLongOr("Energy", 0);
        this.redstone = input.getIntOr("Redstone", 0);
        this.redstoneTick = input.getIntOr("RedstoneTick", 0);
        extractedEnergyAmount = input.getLongOr("EnergyExtracted", 0);
        machineStatus = input.read("PolishingState", MachineStatus.CODEC).orElse(MachineStatus.IDLE);
        energyState = input.read("EnergyState", MachineStatus.EnergyState.CODEC).orElse(MachineStatus.EnergyState.IDLE);
    }

    public int getRedstone() {
        return this.redstone;
    }

    public void setRedstone(int redstone) {
        this.redstone = redstone;
    }
    
    public Gemstone detectGem(ItemStack stack) {
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
        return Gemstone.NONE;
    }

    public abstract GemCategory category();
    
    public Gemstone gemstone() {
        return detectGem(resultStack());
    }

    public void setGemstone(Gemstone gemstone) {
        this.gemstone = gemstone;
    }

    public void setEnergyAmount(long energy) {
        this.energyStorage.amount = Math.min(energy, getEnergyCapacity());
    }

    protected boolean hasEnergySource() {
        return this.energyStack().is(ModItems.ENERGY_INGOT) || this.energyStack().is(ModBlocks.ENERGY_BLOCK.asItem());
    }

    protected void increaseProgress() {
        if(level == null) {
            return;
        }
        if(this.level.hasNeighborSignal(this.worldPosition)) {
            initialProgress += 3;
        } else {
            initialProgress++;
        }
    }

    public long energyAmount() {
        return this.energyStorage().amount;
    }
    
    protected void validateEnergyAmount(int energySlot) {
        if(energyAmount() > 10000000) {
            energyStorage.amount = 10000000;
        }

        long energy = this.energyStorage.amount;

        long[] milestones = {1000000, 2000000, 3000000, 4000000, 5000000, 6000000, 7000000, 8000000, 9000000, 10000000};

        for(long milestone : milestones) {
            if(energy >= milestone && previousRemovedEnergyMilestone < milestone) {
                this.removeItem(energySlot, 1);
                previousRemovedEnergyMilestone = milestone;
                break;
            }
        }
    }

    protected void validateRedstoneDust(int slot) {
        if(redstone > 10000) {
            redstone = 10000;
        }

        int amount = redstone;

        int [] milestones = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};

        for(long milestone : milestones) {
            if(amount >= milestone && previousRemovedRedstoneMilestone < milestone) {
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
        if(level == null) {
            return;
        }
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

    protected void eatEnergy() {
        if(level == null) {
            return;
        }
        long amount = level.hasNeighborSignal(worldPosition) ? 64 : 13;
        try(Transaction transaction = Transaction.openOuter()) {
            long extracted = energyStorage().extract(amount, transaction);
            extractedEnergyAmount += extracted;
            transaction.commit();
        }
        energyState = MachineStatus.EnergyState.EXTRACTING;
    }

    protected abstract boolean hasRecipe();

    private void resetProgress() {
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
            resetProgress();
            try(Transaction transaction = Transaction.openOuter()) {
                this.energyStorage().insert(extractedEnergyAmount, transaction);
                transaction.commit();
            }
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
