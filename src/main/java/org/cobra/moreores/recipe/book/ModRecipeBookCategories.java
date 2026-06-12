package org.cobra.moreores.recipe.book;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import org.cobra.moreores.MoreOresModInitializer;

public class ModRecipeBookCategories {

    public static final RecipeBookCategory GEM_POLISHING = register("gem_polishing");
    public static final RecipeBookCategory GEM_CRYSTALLIZER = register("gem_crystallizer");

    public static RecipeBookCategory register(String id) {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, id), new RecipeBookCategory());
    }

    public static void register() {
        MoreOresModInitializer.LOGGER.info("Loading ModRecipeBookCategory for " + MoreOresModInitializer.MOD_ID + " mod.");
    }

}
