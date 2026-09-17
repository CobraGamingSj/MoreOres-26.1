package org.cobra.moreores.client.recipe;

import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.recipe.GemPurifierRecipe;

public class GemPurifyingRecipeBuilder {
    private final Ingredient ingredient;
    private final ItemStackTemplate output;
    private final RecipeCategory category;
    private final RecipeUnlockAdvancementBuilder recipeUnlockAdvancementBuilder = new RecipeUnlockAdvancementBuilder();

    public GemPurifyingRecipeBuilder(Ingredient ingredient, ItemStackTemplate output, RecipeCategory category) {
        this.ingredient = ingredient;
        this.output = output;
        this.category = category;
    }

    public static GemPurifyingRecipeBuilder purifying(Ingredient ingredient, ItemStackTemplate result) {
        return new GemPurifyingRecipeBuilder(ingredient, result, RecipeCategory.MISC);
    }

    public GemPurifyingRecipeBuilder unlocks(String name, Criterion<?> criterion) {
        this.recipeUnlockAdvancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    public void save(RecipeOutput output, String name) {
        ResourceKey<Recipe<?>> recipeId = ResourceKey.create(Registries.RECIPE, MoreOresModInitializer.id(name + "_polishing"));
        GemPurifierRecipe recipe = new GemPurifierRecipe(this.ingredient, this.output);
        output.accept(recipeId, recipe, recipeUnlockAdvancementBuilder.build(output, recipeId, RecipeCategory.MISC));
    }
}