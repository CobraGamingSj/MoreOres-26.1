package org.cobra.moreores.recipe.display;

import org.cobra.moreores.core.registry.ResourceHelper;

public class ModRecipeDisplays {
    
    public static final ResourceHelper.RecipeResource RESOURCE = ResourceHelper.RecipeResource.INSTANCE;
    
    public static void register() {
        RESOURCE.registerDisplay("gem_purifying", GemPurifyingRecipeDisplay.SERIALIZER);
        RESOURCE.registerDisplay("gem_crystallizing", GemCrystallizingRecipeDisplay.SERIALIZER);
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }
}
