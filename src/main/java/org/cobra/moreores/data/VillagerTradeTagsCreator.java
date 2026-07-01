package org.cobra.moreores.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.trading.VillagerTrade;
import org.cobra.moreores.core.registry.ModVillagerTradeTags;
import org.cobra.moreores.world.item.trading.ModVillagerTrades;

import java.util.concurrent.CompletableFuture;

public class VillagerTradeTagsCreator extends FabricTagsProvider<VillagerTrade> {
    public VillagerTradeTagsCreator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.VILLAGER_TRADE, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(ModVillagerTradeTags.JEWELLER_LEVEL_1)
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_1_COAL_RUBY.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_1_COPPER_INGOT_SAPPHIRE.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_1_IRON_INGOT_GREEN_SAPPHIRE.identifier()))
        ;

        getOrCreateRawBuilder(ModVillagerTradeTags.JEWELLER_LEVEL_2)
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_2_SAPPHIRE_PINK_GARNET.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_2_RUBY_BLUE_GARNET.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_2_GREEN_SAPPHIRE_GREEN_GARNET.identifier()))
        ;

        getOrCreateRawBuilder(ModVillagerTradeTags.JEWELLER_LEVEL_3)
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_3_SAPPHIRE_TOPAZ.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_3_GREEN_GARNET_TOPAZ.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_3_WHITE_TOPAZ_PYROPE.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_3_RUBY_TOPAZ.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_3_RUBY_JADE.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_3_GREEN_SAPPHIRE_PYROPE.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_3_PINK_GARNET_WHITE_TOPAZ.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_3_TOPAZ_PYROPE.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_3_SAPPHIRE_JADE.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_3_SAPPHIRE_PERIDOT.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_3_GREEN_SAPPHIRE_TOPAZ.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_3_WHITE_TOPAZ_JADE.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_3_WHITE_TOPAZ_PERIDOT.identifier()))
        ;

        getOrCreateRawBuilder(ModVillagerTradeTags.JEWELLER_LEVEL_4)
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_4_EMERALD_RUBY.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_4_EMERALD_RADIANT.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_4_EMERALD_SAPPHIRE.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_4_IRON_INGOT_DIAMOND.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_4_NETHERITE_INGOT_BLUE_GARNET.identifier()))
        ;

        getOrCreateRawBuilder(ModVillagerTradeTags.JEWELLER_LEVEL_5)
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_5_EMERALD_TOPAZ.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_5_EMERALD_PYROPE.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_5_IRON_INGOT_PERIDOT.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_5_NETHERITE_INGOT_JADE.identifier()))
                .add(TagEntry.element(ModVillagerTrades.JEWELLER_5_NETHERITE_INGOT_GREEN_GARNET.identifier()))
        ;
    }
}
