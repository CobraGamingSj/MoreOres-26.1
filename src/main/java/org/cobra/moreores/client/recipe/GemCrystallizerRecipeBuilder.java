package org.cobra.moreores.client.recipe;

import net.minecraft.world.item.ItemStackTemplate;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.recipe.GemCrystallizerRecipe;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;

public class GemCrystallizerRecipeBuilder {
    private final Ingredient ingredientBefore;
    private final Ingredient ingredientAfter;
    private final ItemStackTemplate output;
    private final RecipeCategory category;
    private final Map<String, Criterion<?>> criterion = new LinkedHashMap<>();

    public GemCrystallizerRecipeBuilder(Ingredient ingredientBefore, Ingredient ingredientAfter, ItemStackTemplate output, RecipeCategory category) {
        this.ingredientBefore = ingredientBefore;
        this.ingredientAfter = ingredientAfter;
        this.output = output;
        this.category = category;
    }

    public static GemCrystallizerRecipeBuilder create(Ingredient ingredientBefore, ItemStackTemplate result, RecipeCategory category) {
        return new GemCrystallizerRecipeBuilder(ingredientBefore, Ingredient.of(ModItems.RADIANT), result, category);
    }

    public static GemCrystallizerRecipeBuilder createQuartsidian() {
        return new GemCrystallizerRecipeBuilder(Ingredient.of(Items.QUARTZ), Ingredient.of(Blocks.OBSIDIAN.asItem()), new ItemStackTemplate(Blocks.OBSIDIAN.asItem()), RecipeCategory.MISC);
    }

    public GemCrystallizerRecipeBuilder criterion(String name, Criterion<?> criterion) {
        this.criterion.put(name, criterion);
        return this;
    }

    public void offerTo(RecipeOutput exporter, String name) {
        ResourceKey<Recipe<?>> recipeId = ResourceKey.create(Registries.RECIPE, MoreOresModInitializer.id(name + "_crystallizing"));
        this.validate(recipeId);
        Advancement.Builder builder = exporter.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criterion.forEach(builder::addCriterion);
        GemCrystallizerRecipe gemcrystallizerRecipe = new GemCrystallizerRecipe(this.ingredientBefore, this.ingredientAfter, this.output);
        exporter.accept(recipeId, gemcrystallizerRecipe, builder.build(recipeId.identifier().withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void validate(ResourceKey<Recipe<?>> recipeId) {
        if (this.criterion.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId + ", missing 'criterion'");
        }
    }
}