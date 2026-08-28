package org.cobra.moreores.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.recipe.book.ModRecipeBookCategories;
import org.cobra.moreores.recipe.display.GemPolishingRecipeDisplay;
import org.cobra.moreores.recipe.input.GemPurifyingRecipeInput;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public record GemPurifierRecipe(Ingredient ingredient, ItemStackTemplate result) implements Recipe<GemPurifyingRecipeInput> {
    
    @Nullable
    private static PlacementInfo placementInfo;


    public static final MapCodec<GemPurifierRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("ingredientGem").forGetter(GemPurifierRecipe::ingredient),
            ItemStackTemplate.CODEC.fieldOf("resultGem").forGetter(GemPurifierRecipe::result)
    ).apply(instance, GemPurifierRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GemPurifierRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, GemPurifierRecipe::ingredient,
            ItemStackTemplate.STREAM_CODEC, GemPurifierRecipe::result,
            GemPurifierRecipe::new
    );
    public static final RecipeSerializer<GemPurifierRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    @Override
    public ItemStack assemble(GemPurifyingRecipeInput input) {
        return this.getResult().copy();
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
        return this.result.create();
    }

    @Override
    public boolean matches(GemPurifyingRecipeInput input, Level world) {
        if (world.isClientSide()) return false;
        return this.ingredient.test(input.inputStack());
    }

    @Override
    public RecipeSerializer<? extends Recipe<GemPurifyingRecipeInput>> getSerializer() {
        return ModRecipeSerializer.GEM_PURIFIER;
    }

    @Override
    public RecipeType<? extends Recipe<GemPurifyingRecipeInput>> getType() {
        return ModRecipeType.GEM_PURIFIER;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new GemPolishingRecipeDisplay(
                        Ingredient.optionalIngredientToDisplay(Optional.of(this.ingredient)),
                        new SlotDisplay.ItemStackSlotDisplay(this.result),
                        new SlotDisplay.ItemSlotDisplay(ModBlocks.GEM_PURIFIER_BLOCK.asItem())
                )
        );
    }

    @Override
    public PlacementInfo placementInfo() {
        if (placementInfo == null) {
            placementInfo = PlacementInfo.create(this.ingredient);
        }
        return placementInfo;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.GEM_POLISHING;
    }
}
