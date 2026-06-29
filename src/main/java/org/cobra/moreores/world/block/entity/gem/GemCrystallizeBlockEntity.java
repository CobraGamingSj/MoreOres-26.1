package org.cobra.moreores.world.block.entity.gem;

import org.cobra.moreores.world.block.GemCrystallizerBlock;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.item.util.GemCategory;
import org.cobra.moreores.world.item.util.impl.CrystallizationGemstones;
import org.cobra.moreores.world.item.util.impl.IGemstone;
import org.cobra.moreores.networking.block.data.GemCrystallizerDataSynchronizer;
import org.cobra.moreores.world.block.entity.ModBlockEntityType;
import org.cobra.moreores.client.gui.screen.GemCrystallizerMenu;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.recipe.GemCrystallizerRecipe;
import org.cobra.moreores.recipe.input.GemCrystallizationRecipeInput;
import org.cobra.moreores.core.registry.ModItemTags;
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

public class GemCrystallizeBlockEntity extends AbstractGemPCBlockEntity<GemCrystallizerDataSynchronizer> {

    public static final int INGREDIENT_BEFORE_SLOT = 0;
    public static final int INGREDIENT_AFTER_SLOT = 1;
    public static final int RESULT_SLOT = 2;
    public static final int ENERGY_SOURCE_SLOT = 3;
    public static final int RADIANT_DUST_SLOT = 4;

    private long lastRemovedEnergyMilestone = 0;

    public int dustParticleCount = 0;
    public int maxDust = 10000;
    private int dustTick;

    protected final ContainerData propertyDelegate;
    private int maxProgressTicks = 300;
    private final RecipeManager.CachedCheck<GemCrystallizationRecipeInput, GemCrystallizerRecipe> matchGetter = RecipeManager.createCheck(GemCrystallizerRecipe.Type.INSTANCE);

    public GemCrystallizeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.GEM_CRYSTALLIZE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> GemCrystallizeBlockEntity.this.initialProgress;
                    case 1 -> GemCrystallizeBlockEntity.this.maxProgressTicks;
                    case 2 ->  GemCrystallizeBlockEntity.this.dustParticleCount;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> GemCrystallizeBlockEntity.this.initialProgress = value;
                    case 1 -> GemCrystallizeBlockEntity.this.maxProgressTicks = value;
                    case 2 -> GemCrystallizeBlockEntity.this.dustParticleCount = value;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    public void setDustCount(int dustCount) {
        this.dustParticleCount = dustCount;
    }

    public long energyAmount() {
        return this.energyStorage.amount;
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
        view.storeNullable("GemType", CrystallizationGemstones.CODEC, getGem());
    }

    @Override
    public void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        dustParticleCount = view.getIntOr("DustCount", 0);
        dustTick = view.getIntOr("DustTick", 0);
        gemType = view.read("GemType", CrystallizationGemstones.CODEC).orElse(CrystallizationGemstones.EMPTY);
    }

    @Override
    public int getInitialProgress() {
        return 0;
    }

    @Override
    public int mainStackSize() {
        return 10;
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
        return new GemCrystallizerMenu(syncId, playerInventory, this, this.propertyDelegate);
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
        return new GemCrystallizerDataSynchronizer(energyAmount(), this.dustParticleCount, this.worldPosition);
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
    public void tick(Level world, BlockPos pos, BlockState state) {
        if (world.isClientSide()) {
            return;
        }

        dustTick++;

        IGemstone newGem = getGem();

        if (newGem != this.gemType) {
            setGem(newGem);

            world.sendBlockUpdated(pos, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            setChanged(world, pos, state);
        }

        ItemStack stack = radiantDustStack();
        if(stack.is(ModItems.RADIANT_DUST) && dustParticleCount <= maxDust) {
            dustParticleCount += 2000;
            stack.shrink(1);
            setChanged(world, pos, state);
        }
        setChanged(world, pos, state);

        changeState();
        setChanged(world, pos, state);

        if(polishingInfusionState == PolishingInfusionState.RUNNING) {
            energyState = EnergyState.EXTRACTING;
            setChanged(world, pos, state);
            if (isResultSlotEmptyOrReceivable() && hasRecipe() && hasEnoughEnergy() && dustParticleCount >= 15) {
                this.increaseProgress();
                this.extractEnergy();
                if(dustTick >= 20 && dustParticleCount > 0) {
                    this.dustParticleCount--;
                    this.dustTick = 0;
                    setChanged(world, pos, state);
                }
                setChanged(world, pos, state);
                if (hasInfusionFinished()) {
                    this.getInfusedGem();
                    this.resetProgress();
                    setChanged(world, pos, state);
                }
                setChanged(world, pos, state);
            } else {
                this.resetProgress();
                this.polishingInfusionState = PolishingInfusionState.IDLE;
                setChanged(world, pos, state);
            }
        } else if (polishingInfusionState.isPaused()) {
            energyState = EnergyState.INSERTING;
            insertEnergy();
            setChanged(world, pos, state);
        } else {
            if((energyAmount() < 1_000_000 && hasEnergySourceProviderItem())) {
                energyState = EnergyState.INSERTING;
                insertEnergy();
                setChanged(world, pos, state);
            } else {
                energyState = EnergyState.IDLE;
                setChanged(world, pos, state);
            }
        }

        checkForEnoughEnergyAndRemoveItem();
        setChanged(world, pos, state);
    }

    @Override
    public CrystallizationGemstones getGem() {
        IGemstone gem = super.getGem();
        if(gem instanceof CrystallizationGemstones c) {
            return c;
        }
        return CrystallizationGemstones.EMPTY;
    }
    
    
    
    private void changeState() {
        BlockState state = getBlockState();

        state = state.setValue(GemCrystallizerBlock.IS_POLISHING, getGem());


        if(state != getBlockState()) {
            level.setBlock(worldPosition, state, Block.UPDATE_ALL);
        }
    }
    
    protected void checkForEnoughEnergyAndRemoveItem() {
        long energy = this.energyStorage.amount;

        long [] milestones = {100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 800000, 1000000};

        for(long milestone : milestones) {
            if(energy >= milestone && lastRemovedEnergyMilestone < milestone) {
                this.removeItem(ENERGY_SOURCE_SLOT, 1);
                lastRemovedEnergyMilestone = milestone;
                break;
            }
        }
    }

    private void resetProgress() {
        this.initialProgress = 0;
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