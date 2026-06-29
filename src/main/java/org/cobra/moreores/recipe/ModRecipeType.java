package org.cobra.moreores.recipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.cobra.moreores.MoreOresModInitializer;

public class ModRecipeType {
    
    public static final RecipeType<GemPurifierRecipe> GEM_PURIFIER = register("gem_purifying");
    public static final RecipeType<GemCrystallizerRecipe> GEM_CRYSTALLIZER = register("gem_crystallizing");
    
    private static <T extends Recipe<?>> RecipeType<T> register(String name) {
        return Registry.register(
            BuiltInRegistries.RECIPE_TYPE,
            MoreOresModInitializer.id(name),
            new RecipeType<T>() {
                @Override
                public String toString() {
                    return name;
                }
            }
        );
    }
    
    public static void register() {
        MoreOresModInitializer.LOGGER.info("Loading ModRecipeType for " + MoreOresModInitializer.MOD_ID + " mod.");
    }
}
