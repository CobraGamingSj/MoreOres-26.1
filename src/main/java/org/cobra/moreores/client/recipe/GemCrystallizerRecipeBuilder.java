package org.cobra.moreores.client.recipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.RecipeUnlockedTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.recipe.GemCrystallizerRecipe;
import org.cobra.moreores.world.item.ModItems;

public class GemCrystallizerRecipeBuilder {
    private final Ingredient ingredientBefore;
    private final Ingredient ingredientAfter;
    private final ItemStackTemplate output;
    private final RecipeCategory category;
    private final RecipeUnlockAdvancementBuilder recipeUnlockAdvancementBuilder = new RecipeUnlockAdvancementBuilder();

    public GemCrystallizerRecipeBuilder(Ingredient ingredientBefore, Ingredient ingredientAfter, ItemStackTemplate output, RecipeCategory category) {
        this.ingredientBefore = ingredientBefore;
        this.ingredientAfter = ingredientAfter;
        this.output = output;
        this.category = category;
    }

    public static GemCrystallizerRecipeBuilder crystallizing(Ingredient ingredientBefore, ItemStackTemplate result) {
        return new GemCrystallizerRecipeBuilder(ingredientBefore, Ingredient.of(ModItems.RADIANT), result, RecipeCategory.MISC);
    }

    public static GemCrystallizerRecipeBuilder quartsidian() {
        return new GemCrystallizerRecipeBuilder(Ingredient.of(Items.QUARTZ), Ingredient.of(Blocks.OBSIDIAN.asItem()), new ItemStackTemplate(Blocks.OBSIDIAN.asItem()), RecipeCategory.MISC);
    }

    public GemCrystallizerRecipeBuilder unlocks(String name, Criterion<?> criterion) {
        this.recipeUnlockAdvancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    public void save(RecipeOutput output, String name) {
        ResourceKey<Recipe<?>> recipeId = ResourceKey.create(Registries.RECIPE, MoreOresModInitializer.id(name + "_crystallizing"));
        GemCrystallizerRecipe recipe = new GemCrystallizerRecipe(this.ingredientBefore, this.ingredientAfter, this.output);
//        output.accept(recipeId, recipe, recipeUnlockAdvancementBuilder.build(output, recipeId, recipeId.identifier().withPrefix("recipes/" + this.category.getFolderName() + "/")));
        output.accept(recipeId, recipe, recipeUnlockAdvancementBuilder.build(output, recipeId, RecipeCategory.MISC));
    }
}