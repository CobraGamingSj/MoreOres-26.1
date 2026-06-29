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
import org.cobra.moreores.recipe.display.GemCrystallizingRecipeDisplay;
import org.cobra.moreores.recipe.input.GemCrystallizationRecipeInput;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public record GemCrystallizerRecipe(Ingredient ingredientBefore, Ingredient ingredientAfter, ItemStackTemplate output) implements Recipe<GemCrystallizationRecipeInput> {
    
    @Nullable
    private static PlacementInfo placementInfo;
    
    public static final MapCodec<GemCrystallizerRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("gemBefore").forGetter(GemCrystallizerRecipe::ingredientBefore),
            Ingredient.CODEC.fieldOf("gemAfter").forGetter(GemCrystallizerRecipe::ingredientAfter),
            ItemStackTemplate.CODEC.fieldOf("infusedGem").forGetter(GemCrystallizerRecipe::output)
    ).apply(instance, GemCrystallizerRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GemCrystallizerRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, GemCrystallizerRecipe::ingredientBefore,
            Ingredient.CONTENTS_STREAM_CODEC, GemCrystallizerRecipe::ingredientAfter,
            ItemStackTemplate.STREAM_CODEC, GemCrystallizerRecipe::output,
            GemCrystallizerRecipe::new
    );
    
    public static final RecipeSerializer<GemCrystallizerRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);
    
    @Override
    public ItemStack assemble(GemCrystallizationRecipeInput input) {
        return this.getResult().copy();
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
        return this.output.create();
    }

    public Ingredient getIngredientBefore() {
        return ingredientBefore;
    }

    public Ingredient getIngredientAfter() {
        return ingredientAfter;
    }

    @Override
    public boolean matches(GemCrystallizationRecipeInput input, Level world) {
        if (world.isClientSide()) return false;
        return this.ingredientBefore.test(input.inputBefore()) && this.ingredientAfter.test(input.inputAfter()) ||
                this.ingredientAfter.test(input.inputBefore()) && this.ingredientBefore.test(input.inputAfter());
    }

    @Override
    public RecipeSerializer<? extends Recipe<GemCrystallizationRecipeInput>> getSerializer() {
        return ModRecipeSerializer.GEM_CRYSTALLIZER;
    }

    @Override
    public RecipeType<? extends Recipe<GemCrystallizationRecipeInput>> getType() {
        return ModRecipeType.GEM_CRYSTALLIZER;
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
        if (placementInfo == null) {
            placementInfo = PlacementInfo.createFromOptionals(List.of(Optional.of(this.ingredientBefore), Optional.of(this.ingredientAfter)));
        }
        return placementInfo;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.GEM_CRYSTALLIZER;
    }

    public List<Ingredient> getIngredients() {
        return List.of(ingredientBefore, ingredientAfter);
    }
}
