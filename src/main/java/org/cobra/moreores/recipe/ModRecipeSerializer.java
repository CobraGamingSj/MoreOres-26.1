package org.cobra.moreores.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.core.registry.ResourceHelper;

public class ModRecipeSerializer {
    
    public static final ResourceHelper.RecipeResource RESOURCE = ResourceHelper.RecipeResource.INSTANCE;
    
    public static final RecipeSerializer<GemPurifierRecipe> GEM_PURIFIER = RESOURCE.registerSerializer("gem_purifying", GemPurifierRecipe.SERIALIZER);
    public static final RecipeSerializer<GemCrystallizerRecipe> GEM_CRYSTALLIZER = RESOURCE.registerSerializer("gem_crystallizing", GemCrystallizerRecipe.SERIALIZER);
    
    public static void register() {
        MoreOresModInitializer.LOGGER.info("Loading ModRecipeSerializer for" + MoreOresModInitializer.MOD_ID + " mod.");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }
}
