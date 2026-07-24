package org.cobra.moreores.world.block.entity.gem.machine;

import net.minecraft.world.item.Items;
import org.cobra.moreores.recipe.ModRecipeType;
import org.cobra.moreores.world.block.GemCrystallizerBlock;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.item.util.GemCategory;
import org.cobra.moreores.world.item.util.impl.CrystallizationGemstones;
import org.cobra.moreores.world.item.util.impl.IGemstone;
import org.cobra.moreores.networking.block.data.GemCrystallizerDataSynchronizer;
import org.cobra.moreores.world.block.entity.ModBlockEntityTypes;
import org.cobra.moreores.client.gui.screen.GemCrystallizerMenu;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.recipe.GemCrystallizerRecipe;
import org.cobra.moreores.recipe.input.GemCrystallizationRecipeInput;
import org.cobra.moreores.tags.ModItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class GemCrystallizerBlockEntity extends AbstractGemMachineBlockEntity<GemCrystallizerDataSynchronizer> {

    public static final int INGREDIENT_BEFORE_SLOT = 0;
    public static final int INGREDIENT_AFTER_SLOT = 1;
    public static final int RESULT_SLOT = 2;
    public static final int ENERGY_SOURCE_SLOT = 3;
    public static final int RADIANT_DUST_SLOT = 4;
    public static final int REDSTONE_SLOT = 5;

    private long previousRemovedRadiantDustMilestone = 0;

    public int dustParticleCount = 0;
    public int maxDust = 10000;
    private int dustTick;

    protected final ContainerData containerData;
    private int maxProgressTicks = 300;
    private final RecipeManager.CachedCheck<GemCrystallizationRecipeInput, GemCrystallizerRecipe> matchGetter = RecipeManager.createCheck(ModRecipeType.GEM_CRYSTALLIZER);

    public GemCrystallizerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.GEM_CRYSTALLIZER, pos, state);
        this.containerData = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> GemCrystallizerBlockEntity.this.initialProgress;
                    case 1 -> GemCrystallizerBlockEntity.this.maxProgressTicks;
                    case 2 ->  GemCrystallizerBlockEntity.this.dustParticleCount;
                    case 3 ->  GemCrystallizerBlockEntity.this.redstone;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> GemCrystallizerBlockEntity.this.initialProgress = value;
                    case 1 -> GemCrystallizerBlockEntity.this.maxProgressTicks = value;
                    case 2 -> GemCrystallizerBlockEntity.this.dustParticleCount = value;
                    case 3 -> GemCrystallizerBlockEntity.this.redstone = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public ItemStack redstoneStack() {
        return getItem(REDSTONE_SLOT);
    }

    public void setRadiantDust(int dustCount) {
        this.dustParticleCount = dustCount;
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
    public RecipeManager.CachedCheck<GemCrystallizationRecipeInput, GemCrystallizerRecipe> getMatchGetter() {
        return matchGetter;
    }

    @Override
    public void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        view.putInt("DustCount", dustParticleCount);
        view.putInt("DustTick", dustTick);
        view.storeNullable("GemType", CrystallizationGemstones.CODEC, getGemstone());
    }

    @Override
    public void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        dustParticleCount = view.getIntOr("DustCount", 0);
        dustTick = view.getIntOr("DustTick", 0);
        iGemstone = view.read("GemType", CrystallizationGemstones.CODEC).orElse(CrystallizationGemstones.EMPTY);
    }

    @Override
    public int getInitialProgress() {
        return 0;
    }

    @Override
    public int mainStackSize() {
        return 11;
    }

    @Override
    public long getEnergyCapacity() {
        return 1000000;
    }

    @Override
    public long getMaxEnergyInsert() {
        return 19200;
    }

    @Override
    public long getMaxEnergyExtract() {
        return 64000;
    }

    @Override
    public Component getDisplayName() {
        return ModBlocks.GEM_CRYSTALLIZER_BLOCK.getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new GemCrystallizerMenu(syncId, playerInventory, this, this.containerData);
    }

    public ItemStack radiantDustStack() {
        return getItem(RADIANT_DUST_SLOT);
    }

    public ItemStack ingredientAfterStack() {
        return getItem(INGREDIENT_AFTER_SLOT);
    }

    @Override
    public ItemStack resultStack() {
        return getItem(RESULT_SLOT);
    }

    @Override
    public ItemStack energyStack() {
        return getItem(ENERGY_SOURCE_SLOT);
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction side) {
        if (side == Direction.DOWN) {
            return false;
        }

        if (slot == INGREDIENT_BEFORE_SLOT || slot == INGREDIENT_AFTER_SLOT) {
            return ingredientStack().is(ModItemTags.GEMSTONE) || ingredientAfterStack().is(ModItems.RADIANT);
        }

        if (slot == ENERGY_SOURCE_SLOT) {
            return side == Direction.UP && (this.energyStack().is(ModItems.ENERGY_INGOT) || stack.is(ModBlocks.ENERGY_BLOCK.asItem()));  //
        } else if (slot == RADIANT_DUST_SLOT) {
            return side == Direction.UP && this.getItem(RADIANT_DUST_SLOT).is(ModItems.RADIANT_DUST);
        }

        return false;
    }


    @Override
    public GemCrystallizerDataSynchronizer getScreenOpeningData(ServerPlayer serverPlayerEntity) {
        return new GemCrystallizerDataSynchronizer(this.energyAmount(), this.getRedstone(), this.dustParticleCount, this.worldPosition);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return switch (slot) {
            case INGREDIENT_BEFORE_SLOT, INGREDIENT_AFTER_SLOT ->
                    stack.is(ModItemTags.GEMSTONE) || stack.is(ModItemTags.RAW_GEMSTONE);
            case ENERGY_SOURCE_SLOT ->
                    stack.is(ModItems.ENERGY_INGOT) || stack.is(ModBlocks.ENERGY_BLOCK.asItem());
            case RESULT_SLOT->
                    stack.is(ModItemTags.GEMSTONE);
            case RADIANT_DUST_SLOT ->
                stack.is(ModItems.RADIANT_DUST);
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


    @Override
    public GemCategory category() {
        return GemCategory.CRYSTALLIZATION;
    }


    // Tick Method
    // Logic per tick
    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) {
            return;
        }

        dustTick++;
        redstoneTick++;

        IGemstone newGem = getGemstone();

        if (newGem != this.iGemstone) {
            setGem(newGem);

            level.sendBlockUpdated(pos, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            setChanged(level, pos, state);
        }

        ItemStack stack = radiantDustStack();
        if(stack.is(ModItems.RADIANT_DUST) && dustParticleCount <= maxDust) {
            dustParticleCount += 2000;
            setChanged(level, pos, state);
        }
        ItemStack stack1 = redstoneStack();
        if((stack1.is(Items.REDSTONE) || level.hasNeighborSignal(pos)) && redstone <= maxRedstone) {
            redstone += 10;
            setChanged(level, pos, state);
        }

        changeState();
        if(machineStatus == MachineStatus.RUNNING) {
            energyState = MachineStatus.EnergyState.EXTRACTING;
            setChanged(level, pos, state);
            if (isResultSlotEmptyOrReceivable() && hasRecipe() && hasEnoughEnergy() && dustParticleCount >= 15) {
                this.increaseProgress();
                if((!level.hasNeighborSignal(pos) || redstone > 0) && redstoneTick >= 20) {
                    redstone--;
                    redstoneTick = 0;
                }
                this.eatEnergy();
                if(dustParticleCount > 0 && dustTick >= 20) {
                    dustParticleCount--;
                    dustTick = 0;
                    setChanged(level, pos, state);
                }
                setChanged(level, pos, state);
                if (hasInfusionFinished()) {
                    this.getInfusedGem();
                    this.clearProgress();
                    setChanged(level, pos, state);
                }
                setChanged(level, pos, state);
            } else {
                this.clearProgress();
                this.machineStatus = MachineStatus.IDLE;
                setChanged(level, pos, state);
            }
        } else if (machineStatus.isPaused()) {
            energyState = MachineStatus.EnergyState.INSERTING;
            giveEnergy();
            setChanged(level, pos, state);
        } else {
            if((energyAmount() < 1_000_000 && hasEnergySourceProviderItem())) {
                energyState = MachineStatus.EnergyState.INSERTING;
                giveEnergy();
                setChanged(level, pos, state);
            } else {
                energyState = MachineStatus.EnergyState.IDLE;
                setChanged(level, pos, state);
            }
        }

        validateEnergyAmount(ENERGY_SOURCE_SLOT);
        validateRedstoneAmount(REDSTONE_SLOT);
        checkForEnoughRadiantDustAndConsumeSingle();
        setChanged(level, pos, state);
    }

    @Override
    public CrystallizationGemstones getGemstone() {
        IGemstone gem = super.getGemstone();
        if(gem instanceof CrystallizationGemstones c) {
            return c;
        }
        return CrystallizationGemstones.EMPTY;
    }

    @Override
    protected void validateEnergyAmount(int energySlot) {
        if(energyAmount() > 1000000) {
            energyStorage.amount = 1000000;
        }
            
        long energy = this.energyAmount();

        long [] milestones = {100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000};

        for(long milestone : milestones) {
            if(energy >= milestone && previousRemovedEnergyMilestone < milestone) {
                this.removeItem(energySlot, 1);
                previousRemovedEnergyMilestone = milestone;
                break;
            }
        }
    }

    private void changeState() {
        BlockState state = getBlockState();

        state = state.setValue(GemCrystallizerBlock.IS_POLISHING, getGemstone());


        if(state != getBlockState()) {
            level.setBlock(worldPosition, state, Block.UPDATE_ALL);
        }
    }
    
    private void checkForEnoughRadiantDustAndConsumeSingle() {
        if(dustParticleCount > 10000) {
            dustParticleCount = 10000;
        }
        
        long energy = dustParticleCount;

        long [] milestones = {2000, 4000, 6000, 8000, 10000};

        for(long milestone : milestones) {
            if(energy == milestone && previousRemovedRadiantDustMilestone < milestone) {
                this.removeItem(RADIANT_DUST_SLOT, 1);
                previousRemovedRadiantDustMilestone = milestone;
                break;
            }
        }
    }

    private void getInfusedGem() {
        RecipeHolder<GemCrystallizerRecipe> recipe = currentRecipe().orElseThrow();

        this.removeItem(INGREDIENT_BEFORE_SLOT, 1);
        this.removeItem(INGREDIENT_AFTER_SLOT, 1);

        this.setItem(RESULT_SLOT, new ItemStack(recipe.value().getResult().getItem(),
                this.resultStack().getCount() + recipe.value().getResult().getCount()));
    }
    private boolean hasInfusionFinished() {
        return initialProgress >= maxProgressTicks;
    }

    protected boolean hasRecipe() {
        Optional<RecipeHolder<GemCrystallizerRecipe>> recipe = currentRecipe();

        return recipe.isPresent() && hasEnoughEnergy() && canInsertCountIntoResultSlot(recipe.get().value().getResult())
                && canInsertItemIntoResultSlot(recipe.get().value().getResult().getItem());
    }

    private Optional<RecipeHolder<GemCrystallizerRecipe>> currentRecipe() {
        ServerLevel serverWorld = (ServerLevel) level;
        return this.matchGetter.getRecipeFor(new GemCrystallizationRecipeInput(this.ingredientStack(), this.ingredientAfterStack()), serverWorld);
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
}