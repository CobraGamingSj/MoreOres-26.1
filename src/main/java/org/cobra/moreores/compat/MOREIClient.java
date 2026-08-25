package org.cobra.moreores.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import org.cobra.moreores.client.gui.screen.GemCrystallizerScreen;
import org.cobra.moreores.client.gui.screen.GemPurifierScreen;
import org.cobra.moreores.world.block.ModBlocks;

public class MOREIClient implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new GemPurifierCategory());
        registry.add(new GemCrystallizerCategory());

        registry.addWorkstations(MOREICommon.GEM_PURIFIER, EntryStacks.of(ModBlocks.GEM_PURIFIER_BLOCK));
        registry.addWorkstations(MOREICommon.GEM_CRYSTALLIZER, EntryStacks.of(ModBlocks.GEM_CRYSTALLIZER_BLOCK));
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 226) / 2) + 78, ((screen.height - 201) / 2) + 30, 20, 25),
                GemPurifierScreen.class, MOREICommon.GEM_PURIFIER);
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 201) / 2) + 78, ((screen.height - 196) / 2) + 30, 20, 25),
                GemCrystallizerScreen.class, MOREICommon.GEM_CRYSTALLIZER);
    }
}