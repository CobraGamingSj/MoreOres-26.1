package org.cobra.moreores.recipe.book;

import net.minecraft.world.item.crafting.RecipeBookCategory;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.core.ResourceHelper;

public class ModRecipeBookCategories {
    
    public static final ResourceHelper.RecipeResource RESOURCE = ResourceHelper.RecipeResource.INSTANCE;
    
    public static final RecipeBookCategory GEM_POLISHING = RESOURCE.registerBookCategory("gem_polishing");
    public static final RecipeBookCategory GEM_CRYSTALLIZER = RESOURCE.registerBookCategory("gem_crystallizer");
    
    public static void register() {
        MoreOresModInitializer.LOGGER.info("Loading ModRecipeBookCategory for " + MoreOresModInitializer.MOD_ID + " mod.");
    }
}
