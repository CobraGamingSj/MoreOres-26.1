package org.cobra.moreores.world.block.entity.gem.machine;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.cobra.moreores.networking.block.data.GemPurifierScreenSync;
import org.cobra.moreores.recipe.ModRecipeType;
import org.cobra.moreores.world.block.GemPurifierBlock;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.item.util.impl.IGemstone;
import org.cobra.moreores.world.item.util.impl.PurificationGemstones;
import org.cobra.moreores.networking.block.data.GemPurifierFluidDataPayload;
import org.cobra.moreores.networking.block.data.GemPurifierDataSynchronizer;
import org.cobra.moreores.world.block.entity.ModBlockEntityTypes;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.recipe.GemPurifierRecipe;
import org.cobra.moreores.recipe.input.GemPurifyingRecipeInput;
import org.cobra.moreores.tags.ModItemTags;
import org.cobra.moreores.client.gui.screen.GemPurifierMenu;
import org.cobra.moreores.util.FluidStack;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GemPurifierBlockEntity extends AbstractGemMachineBlockEntity<GemPurifierDataSynchronizer> {

    private FluidState fluidState = FluidState.IDLE;

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant fluidStorage) {
            return FluidStack.convertDropletsToMb(FluidConstants.BUCKET * 810);
        }

        @Override
        protected void onFinalCommit() {
            setChanged();
            for (ServerPlayer user : PlayerLookup.tracking((ServerLevel) level, getBlockPos())) {
                ServerPlayNetworking.send(user, new GemPurifierFluidDataPayload(fluidStorage.variant, fluidStorage.amount, getBlockPos()));
            }
        }
    };

    public static final int INGREDIENT_SLOT = 0;
    public static final int RESULT_SLOT = 1;
    public static final int ENERGY_SOURCE_SLOT = 2;
    public static final int WATER_SOURCE_SLOT = 3;
    public static final int REDSTONE_SLOT = 4;

    private long previousRemovedFluidMilestone = 0;

    private ItemStack lastPreviewResult = ItemStack.EMPTY;

    protected final ContainerData containerData;
    private int maxProgressTick = 384;
    private final RecipeManager.CachedCheck<GemPurifyingRecipeInput, GemPurifierRecipe> matchGetter = RecipeManager.createCheck(ModRecipeType.GEM_PURIFIER);

    public GemPurifierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.GEM_PURIFIER, pos, state);
        this.containerData = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> GemPurifierBlockEntity.this.getInitialProgress();
                    case 1 -> GemPurifierBlockEntity.this.maxProgressTick;
                    case 2 -> GemPurifierBlockEntity.this.redstone;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> GemPurifierBlockEntity.this.setInitialProgress(value);
                    case 1 -> GemPurifierBlockEntity.this.maxProgressTick = value;
                    case 2 -> GemPurifierBlockEntity.this.redstone = value;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    public ItemStack redstoneStack() {
        return getItem(REDSTONE_SLOT);
    }
    
    public void setFluid(FluidVariant variant, long waterLevel) {
        this.fluidStorage.variant = variant;
        this.fluidStorage.amount = waterLevel;
    }
    
    public long waterAmount() {
        return this.fluidStorage.amount;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        return saveWithoutMetadata(registryLookup);
    }

    @Override
    public int mainStackSize() {
        return 17;
    }

    @Override
    public long getEnergyCapacity() {
        return 10000000;
    }

    @Override
    public long getMaxEnergyInsert() {
        return 192000;
    }

    @Override
    public long getMaxEnergyExtract() {
        return 640000;
    }

    @Override
    public void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        view.putLong("gem_purifier.water", fluidStorage.amount);
        view.putInt("MaxProgress", maxProgressTick);
        view.storeNullable("gem_purifier.fluidAmount.variant", FluidVariant.CODEC, fluidStorage.variant);
        view.storeNullable("WaterState", FluidState.CODEC, fluidState);
        view.storeNullable("GemType", PurificationGemstones.CODEC, gemstone());
    }

    @Override
    public void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        fluidStorage.amount = view.getLongOr("gem_purifier.water", 0);
        maxProgressTick = view.getIntOr("MaxProgress", 384);
        fluidStorage.variant = view.read("gem_purifier.fluidAmount.variant", FluidVariant.CODEC).orElse(FluidVariant.blank());
        fluidState = view.read("WaterState", FluidState.CODEC).orElse(FluidState.IDLE);
        gemstone = view.read("GemType", PurificationGemstones.CODEC).orElse(PurificationGemstones.NONE);
    }

    @Override
    public Component getDisplayName() {
        return ModBlocks.GEM_PURIFIER_BLOCK.getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new GemPurifierMenu(syncId, playerInventory, this, this.containerData);
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction side) {
        if (side == Direction.DOWN) {
            return false;
        }

        if (slot == INGREDIENT_SLOT) {
            return ingredientStack().is(ModItemTags.RAW_GEMSTONE);
        }

        if (slot == ENERGY_SOURCE_SLOT) {
            return side == Direction.UP && (this.energyStack().is(ModItems.ENERGY_INGOT) || energyStack().is(ModBlocks.ENERGY_BLOCK.asItem()));  //
        }

        if(slot == WATER_SOURCE_SLOT) {
            return side == Direction.UP && this.fluidStack().is(Items.WATER_BUCKET);
        }

        return false;
    }

    @Override
    public GemPurifierDataSynchronizer getScreenOpeningData(ServerPlayer serverPlayerEntity) {
        return new GemPurifierDataSynchronizer(this.energyAmount(), this.getRedstone(), fluidStorage.variant, fluidStorage.amount, this.worldPosition);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return switch (slot) {
            case INGREDIENT_SLOT->
                    stack.is(ModItemTags.GEMSTONE) || stack.is(ModItemTags.RAW_GEMSTONE);
            case ENERGY_SOURCE_SLOT ->
                    stack.is(ModItems.ENERGY_INGOT) || stack.is(ModBlocks.ENERGY_BLOCK.asItem());
            case WATER_SOURCE_SLOT ->
                    stack.is(Items.WATER_BUCKET);
            case RESULT_SLOT->
                    stack.is(ModItemTags.GEMSTONE);
            default -> false;
        };
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return side == Direction.DOWN && (slot == RESULT_SLOT);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return main;
    }


    // Tick Method
    // Logic per tick
    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) {
            return;
        }

        syncPreviewResult();

        redstoneTick++;
        
        IGemstone newGem = gemstone();

        if (newGem != this.gemstone) {
            setGemstone(newGem);

            level.sendBlockUpdated(pos, getBlockState(), getBlockState(), 3);
        }

        ItemStack stack = redstoneStack();
        if((stack.is(Items.REDSTONE) || level.hasNeighborSignal(pos)) && redstone <= maxRedstone) {
            redstone += 10;
            setChanged(level, pos, state);
        }
        
        changeState();
        if(machineStatus == MachineStatus.RUNNING) {
            energyState = MachineStatus.EnergyState.EXTRACTING;
            if (isResultSlotEmptyOrReceivable() && checkRecipe() && hasRequiredEnergyAmount() && hasEnoughWater()) {
                this.continueTicks();
                if((!level.hasNeighborSignal(pos) || redstone > 0) && redstoneTick >= 20) {
                    redstone--;
                    redstoneTick = 0;
                }
                this.consumeEnergy();
                this.consumeWater();
                if (hasPurificationEnded()) {
                    this.getPurifiedGemstone();
                    this.clearProgress();
                }
                setChanged(level, pos, state);
            } else {
                this.clearProgress();
                this.machineStatus = MachineStatus.IDLE;
                setChanged(level, pos, state);
            }
        } else if (machineStatus.isPaused()) {
            energyState = MachineStatus.EnergyState.INSERTING;
            fluidState = FluidState.FILLING;
            addEnergy();
            fillWater();
        } else {
            if((energyAmount() < 10_000_000 && hasEnergySource()) || (waterAmount() < 810000 && hasWaterBucket())) {
                energyState = MachineStatus.EnergyState.INSERTING;
                addEnergy();
                fluidState = FluidState.FILLING;
                fillWater();
            } else {
                energyState = MachineStatus.EnergyState.IDLE;
                fluidState = FluidState.IDLE;
            }
        }

        validateEnergyAmount(ENERGY_SOURCE_SLOT);
        validateFluidAmount();
        validateRedstoneAmount(REDSTONE_SLOT);
        setChanged(level, pos, state);
    }

    @Override
    public PurificationGemstones gemstone() {
        IGemstone gem = super.gemstone();
        if(gem instanceof PurificationGemstones p) {
            return p;
        }
        return PurificationGemstones.NONE;
    }

    private void changeState() {
        BlockState state = getBlockState();
        state = state.setValue(GemPurifierBlock.IS_POLISHING, gemstone());
        if(state != getBlockState()) {
            level.setBlock(worldPosition, state, Block.UPDATE_ALL);
        }
    }

    private void fillWater() {
        if(!hasWaterBucket() || waterAmount() >= 810000) {
            fluidState = FluidState.IDLE;
            return;
        }
        long amount = 1620;
        try(Transaction transaction = Transaction.openOuter()) {
            long inserted = fluidStorage.insert(FluidVariant.of(Fluids.WATER), FluidStack.convertDropletsToMb(amount), transaction);
            transaction.commit();
            if(inserted > 0) fluidState = FluidState.FILLING;
            else fluidState = FluidState.IDLE;
        }
    }

    private void consumeWater() {
        long amount = 810;
        try(Transaction transaction = Transaction.openOuter()) {
            fluidStorage.extract(FluidVariant.of(Fluids.WATER), FluidStack.convertDropletsToMb(amount), transaction);
            transaction.commit();
        }
        fluidState = FluidState.DRINKING;
    }

    private void validateFluidAmount() {
        if(fluidStorage.amount > 810000) {
            fluidStorage.amount = 810000;
        }
        
        long water = this.fluidStorage.amount;

        long [] milestones = {81000, 162000, 243000, 324000, 405000, 486000, 567000, 648000, 729000, 810000};

        for(long milestone : milestones) {
            if(water == milestone && previousRemovedFluidMilestone < milestone) {
                this.removeItem(WATER_SOURCE_SLOT, 1);
                this.setItem(WATER_SOURCE_SLOT, new ItemStack(Items.BUCKET, 1));
                previousRemovedFluidMilestone = milestone;
                break;
            }
        }
    }

    @Override
    protected boolean hasRequiredEnergyAmount() {
        return this.energyAmount() >= 128;
    }

    private boolean hasEnoughWater() {
        return this.fluidStorage.amount >= 1215;
    }

    private void getPurifiedGemstone() {
        RecipeHolder<GemPurifierRecipe> recipe = getCurrentRecipe().orElseThrow();

        this.removeItem(INGREDIENT_SLOT, 1);

        this.setItem(RESULT_SLOT, new ItemStack(recipe.value().getResult().getItem(),
                this.resultStack().getCount() + recipe.value().getResult().getCount()));
    }
    private boolean hasPurificationEnded() {
        return getInitialProgress() >= maxProgressTick;
    }

    private void syncPreviewResult() {
        Optional<RecipeHolder<GemPurifierRecipe>> recipe = getCurrentRecipe();
        ItemStack result = recipe.map(holder -> holder.value().getResult().copy())
                .orElse(ItemStack.EMPTY);

        if (ItemStack.matches(lastPreviewResult, result)) {
            return;
        }
        lastPreviewResult = result.copy();
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        for (ServerPlayer player : PlayerLookup.tracking(serverLevel, worldPosition)) {
            if (player.containerMenu instanceof GemPurifierMenu menu && menu.getBlockPos().equals(worldPosition)) {
                ServerPlayNetworking.send(player, new GemPurifierScreenSync(result, worldPosition));
            }
        }
    }

    @Override
    public void continueTicks() {
        if(this.level.hasNeighborSignal(this.worldPosition) || redstone > 0) {
            setInitialProgress(getInitialProgress() + 5);
        } else {
            setInitialProgress(getInitialProgress() + 1);
        }
    }

    @Override
    protected boolean checkRecipe() {
        Optional<RecipeHolder<GemPurifierRecipe>> recipe = getCurrentRecipe();

        return recipe.isPresent() && hasRequiredEnergyAmount() && canInsertCountIntoResultSlot(recipe.get().value().getResult())
                && canInsertItemIntoResultSlot(recipe.get().value().getResult().getItem());
    }

    private boolean hasWaterBucket() {
        return this.fluidStack().is(Items.WATER_BUCKET);
    }

    public Optional<RecipeHolder<GemPurifierRecipe>> getCurrentRecipe() {
        ServerLevel serverWorld = (ServerLevel) level;
        if(serverWorld == null) {
            return Optional.empty();
        }
        return this.matchGetter.getRecipeFor(new GemPurifyingRecipeInput(this.ingredientStack()), serverWorld);
    }

    private boolean canInsertItemIntoResultSlot(Item item) {
        return this.resultStack().getItem() == item || this.resultStack().isEmpty() || this.resultStack().is(ModItemTags.GEMSTONE)
                || this.resultStack().is(ModItemTags.RAW_GEMSTONE);
    }

    private boolean canInsertCountIntoResultSlot(ItemStack result) {
        return this.resultStack().getCount() + result.getCount() <= this.resultStack().getMaxStackSize();
    }

    private boolean isResultSlotEmptyOrReceivable() {
        return this.resultStack().isEmpty() || this.resultStack().getCount() < this.resultStack().getMaxStackSize();
    }

    @Override
    protected void addEnergy() {
        if(!hasEnergySource() || energyAmount() >= 10_000_000) {
            energyState = MachineStatus.EnergyState.IDLE;
            return;
        }
        long amount = energyStack().is(ModItems.ENERGY_INGOT) ? 1024 : 1536;
        if(level.hasNeighborSignal(worldPosition)) amount *= 5;
        try(Transaction transaction = Transaction.openOuter()) {
            long inserted = energyStorage().insert(amount, transaction);
            transaction.commit();
            if(inserted > 0) energyState = MachineStatus.EnergyState.INSERTING;
            else energyState = MachineStatus.EnergyState.IDLE;
        }
    }

    @Override
    protected void consumeEnergy() {
        long amount = level.hasNeighborSignal(worldPosition) ? 640 : 128;
        try(Transaction transaction = Transaction.openOuter()) {
            long extracted = energyStorage().extract(amount, transaction);
            energyExtracted += extracted;
            transaction.commit();
        }
        energyState = MachineStatus.EnergyState.EXTRACTING;
    }

    public enum FluidState implements StringRepresentable {
        IDLE("idle"),
        FILLING("filling"),
        DRINKING("emptying");
    
        private final String name;
    
        FluidState(String name) {
            this.name = name;
        }
    
        public static final Codec<FluidState> CODEC = StringRepresentable.fromEnum(FluidState::values);
    
        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}