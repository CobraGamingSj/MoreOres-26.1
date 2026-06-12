package org.cobra.moreores.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.recipe.book.ModRecipeBookCategories;
import org.cobra.moreores.recipe.display.GemCrystallizingRecipeDisplay;
import org.cobra.moreores.recipe.input.GemInfusionRecipeInput;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class GemCrystallizerRecipe implements Recipe<GemInfusionRecipeInput> {
    public final Ingredient ingredientBefore;
    public final Ingredient ingredientAfter;
    public final ItemStack output;

    @Nullable
    private PlacementInfo ingredientPlacement;

    public GemCrystallizerRecipe(Ingredient ingredientBefore, Ingredient ingredientAfter, ItemStack result) {
        this.ingredientBefore = ingredientBefore;
        this.ingredientAfter = ingredientAfter;
        this.output = result;
    }

    @Override
    public ItemStack assemble(GemInfusionRecipeInput input) {
        return this.output.copy();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "Crystallizing";
    }

    public ItemStack getResult() {
        return this.output;
    }

    public Ingredient getIngredientBefore() {
        return ingredientBefore;
    }

    public Ingredient getIngredientAfter() {
        return ingredientAfter;
    }

    @Override
    public boolean matches(GemInfusionRecipeInput input, Level world) {
        if (world.isClientSide()) return false;
        return this.ingredientBefore.test(input.inputBefore()) && this.ingredientAfter.test(input.inputAfter()) ||
                this.ingredientAfter.test(input.inputBefore()) && this.ingredientBefore.test(input.inputAfter());
    }

    @Override
    public RecipeSerializer<? extends Recipe<GemInfusionRecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<GemInfusionRecipeInput>> getType() {
        return Type.INSTANCE;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new GemCrystallizingRecipeDisplay(
                        Ingredient.optionalIngredientToDisplay(Optional.of(this.ingredientBefore)),
                        Ingredient.optionalIngredientToDisplay(Optional.of(this.ingredientAfter)),
                        new SlotDisplay.ItemStackSlotDisplay(this.output),
                        new SlotDisplay.ItemSlotDisplay(ModBlocks.GEM_CRYSTALLIZER_BLOCK.asItem())
                )
        );
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = PlacementInfo.createFromOptionals(List.of(Optional.of(this.ingredientBefore), Optional.of(this.ingredientAfter)));
        }
        return this.ingredientPlacement;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.GEM_CRYSTALLIZER;
    }

    public List<Ingredient> getIngredients() {
        return List.of(ingredientBefore, ingredientAfter);
    }

    public static class Type implements RecipeType<GemCrystallizerRecipe> {

        //RECIPE PROPERTIES
        public static final Type INSTANCE = new Type();
        public static final String ID = "gem_crystallizing"; //Recipe ID
    }

    public static class Serializer implements RecipeSerializer<GemCrystallizerRecipe> {

        //RECIPE PROPERTIES
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "gem_crystallizing"; //Recipe ID

        //CODEC
        private static final MapCodec<GemCrystallizerRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("gemBefore").forGetter(GemCrystallizerRecipe::getIngredientBefore),
                Ingredient.CODEC.fieldOf("gemAfter").forGetter(GemCrystallizerRecipe::getIngredientAfter),
                ItemStack.CODEC.fieldOf("infusedGem").forGetter(GemCrystallizerRecipe::getResult)
        ).apply(instance, GemCrystallizerRecipe::new));

        @Override
        public MapCodec<GemCrystallizerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, GemCrystallizerRecipe> streamCodec() {
            return StreamCodec.of(Serializer::write, Serializer::read);
        }

        private static void write(RegistryFriendlyByteBuf buf, GemCrystallizerRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getIngredientBefore());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getIngredientAfter());
            ItemStack.STREAM_CODEC.encode(buf, recipe.getResult());
        }

        private static GemCrystallizerRecipe read(RegistryFriendlyByteBuf buf) {
            Ingredient ingredientBefore = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient ingredientAfter = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
            return new GemCrystallizerRecipe(ingredientBefore, ingredientAfter, result);
        }
    }
}
