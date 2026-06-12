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
import org.cobra.moreores.block.ModBlocks;
import org.cobra.moreores.recipe.book.ModRecipeBookCategories;
import org.cobra.moreores.recipe.display.GemPolishingRecipeDisplay;
import org.cobra.moreores.recipe.input.GemPurifyingRecipeInput;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class GemPurifierRecipe implements Recipe<GemPurifyingRecipeInput> {
    public final Ingredient ingredient;
    public final ItemStack output;

    @Nullable
    private PlacementInfo ingredientPlacement;

    public GemPurifierRecipe(Ingredient ingredient, ItemStack result) {
        this.ingredient = ingredient;
        this.output = result;
    }

    @Override
    public ItemStack assemble(GemPurifyingRecipeInput input) {
        return this.output.copy();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "Purifying";
    }

    public ItemStack getResult() {
        return this.output;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public boolean matches(GemPurifyingRecipeInput input, Level world) {
        if (world.isClientSide()) return false;
        return this.ingredient.test(input.inputStack().create());
    }

    @Override
    public RecipeSerializer<? extends Recipe<GemPurifyingRecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<GemPurifyingRecipeInput>> getType() {
        return Type.INSTANCE;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new GemPolishingRecipeDisplay(
                        Ingredient.optionalIngredientToDisplay(Optional.of(this.ingredient)),
                        new SlotDisplay.ItemStackSlotDisplay(this.output.getCraftingRemainder()),
                        new SlotDisplay.ItemSlotDisplay(ModBlocks.GEM_PURIFIER_BLOCK.asItem())
                )
        );
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = PlacementInfo.create(this.ingredient);
        }
        return this.ingredientPlacement;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.GEM_POLISHING;
    }

    public Ingredient getIngredients() {
        return this.ingredient;
    }

    public static class Type implements RecipeType<GemPurifierRecipe> {

        //RECIPE PROPERTIES
        public static final Type INSTANCE = new Type();
        public static final String ID = "gem_polishing"; //Recipe ID
    }

    public static class Serializer implements RecipeSerializer<GemPurifierRecipe> {

        //RECIPE PROPERTIES
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "gem_polishing"; //Recipe ID

        //CODEC
        private static final MapCodec<GemPurifierRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("ingredientGem").forGetter(GemPurifierRecipe::getIngredient),
                ItemStack.CODEC.fieldOf("resultGem").forGetter(GemPurifierRecipe::getResult)
        ).apply(instance, GemPurifierRecipe::new));

        @Override
        public MapCodec<GemPurifierRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, GemPurifierRecipe> streamCodec() {
            return StreamCodec.of(Serializer::write, Serializer::read);
        }

        private static void write(RegistryFriendlyByteBuf buf, GemPurifierRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getIngredient());
            ItemStack.STREAM_CODEC.encode(buf, recipe.getResult());
        }

        private static GemPurifierRecipe read(RegistryFriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
            return new GemPurifierRecipe(ingredient, result);
        }
    }
}
