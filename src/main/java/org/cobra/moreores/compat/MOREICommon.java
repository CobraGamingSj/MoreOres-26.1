package org.cobra.moreores.compat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.recipe.GemCrystallizerRecipe;
import org.cobra.moreores.recipe.GemPurifierRecipe;

public class MOREICommon implements REICommonPlugin {
    public static final CategoryIdentifier<GemPurifierDisplay> GEM_PURIFIER = CategoryIdentifier.of(MoreOresModInitializer.MOD_ID, "gem_purifier");
    public static final CategoryIdentifier<GemCrystallizerDisplay> GEM_CRYSTALLIZER = CategoryIdentifier.of(MoreOresModInitializer.MOD_ID, "gem_crystallizer");

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        REICommonPlugin.super.registerDisplaySerializer(registry);

        registry.register(MoreOresModInitializer.id("gem_purifier_serializer"), GemPurifierDisplay.SERIALIZER);
        registry.register(MoreOresModInitializer.id("gem_crystallizer_serializer"), GemCrystallizerDisplay.SERIALIZER);
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        REICommonPlugin.super.registerDisplays(registry);
        registry.beginRecipeFiller(GemPurifierRecipe.class).fill(GemPurifierDisplay::new);
        registry.beginRecipeFiller(GemCrystallizerRecipe.class).fill(GemCrystallizerDisplay::new);
    }
}