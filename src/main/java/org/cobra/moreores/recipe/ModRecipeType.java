package org.cobra.moreores.recipe;

import net.minecraft.world.item.crafting.RecipeType;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.core.registry.ResourceHelper;

public class ModRecipeType {
    
    public static final ResourceHelper.RecipeResource RESOURCE = ResourceHelper.RecipeResource.INSTANCE;
    
    public static final RecipeType<GemPurifierRecipe> GEM_PURIFIER = RESOURCE.registerType("gem_purifying");
    public static final RecipeType<GemCrystallizerRecipe> GEM_CRYSTALLIZER = RESOURCE.registerType("gem_crystallizing");
    
    
    
    public static void register() {
        MoreOresModInitializer.LOGGER.info("Loading ModRecipeType for " + MoreOresModInitializer.MOD_ID + " mod.");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }
}
