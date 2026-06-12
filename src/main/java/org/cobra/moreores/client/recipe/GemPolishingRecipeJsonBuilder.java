package org.cobra.moreores.client.recipe;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.recipe.GemPurifierRecipe;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class GemPolishingRecipeJsonBuilder {
    private final Ingredient ingredient;
    private final ItemStack output;
    private final RecipeCategory category;
    private final Map<String, Criterion<?>> criterion = new LinkedHashMap<>();

    public GemPolishingRecipeJsonBuilder(Ingredient ingredient, ItemStack output, RecipeCategory category) {
        this.ingredient = ingredient;
        this.output = output;
        this.category = category;
    }

    public static GemPolishingRecipeJsonBuilder create(Ingredient ingredient, ItemStack result, RecipeCategory category) {
        return new GemPolishingRecipeJsonBuilder(ingredient, result, category);
    }

    public GemPolishingRecipeJsonBuilder criterion(String name, Criterion<?> criterion) {
        this.criterion.put(name, criterion);
        return this;
    }

    public void offerTo(RecipeOutput exporter, String name) {
        ResourceKey<Recipe<?>> recipeId = ResourceKey.create(Registries.RECIPE, MoreOresModInitializer.id(name + "_polishing"));
        this.validate(recipeId);
        Advancement.Builder builder = exporter.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criterion.forEach(builder::addCriterion);
        GemPurifierRecipe gemPolishingRecipe = new GemPurifierRecipe(this.ingredient, this.output);
        exporter.accept(recipeId, gemPolishingRecipe, builder.build(recipeId.identifier().withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void validate(ResourceKey<Recipe<?>> recipeId) {
        if (this.criterion.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId + ", missing 'criterion'");
        }
    }
}