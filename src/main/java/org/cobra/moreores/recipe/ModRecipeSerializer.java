package org.cobra.moreores.recipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.cobra.moreores.MoreOresModInitializer;

public class ModRecipeSerializer {
    
    public static final RecipeSerializer<GemPurifierRecipe> GEM_PURIFIER = register("gem_purifying", GemPurifierRecipe.SERIALIZER);
    public static final RecipeSerializer<GemCrystallizerRecipe> GEM_CRYSTALLIZER = register("gem_crystallizing", GemCrystallizerRecipe.SERIALIZER);
    
    private static <T extends Recipe<?>> RecipeSerializer<T> register(String name, RecipeSerializer<T> serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, MoreOresModInitializer.id(name), serializer);
    }

    public static void register() {
        MoreOresModInitializer.LOGGER.info("Loading ModRecipeSerializer for" + MoreOresModInitializer.MOD_ID + " mod.");
    }
}
