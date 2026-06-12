package org.cobra.moreores.util;

import net.minecraft.world.item.trading.TradeSets;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.item.ModItems;
import org.cobra.moreores.village.ModVillagerProfessions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

public class VillagerTrades {

    public static void register() {
        Trade.registerVillagerOffers(ModVillagerProfessions.JEWELLER, 1, factories -> {
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.COAL, 24),
                    new ItemStack(ModItems.RUBY, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.COPPER_INGOT, 12),
                    new ItemStack(ModItems.SAPPHIRE, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.IRON_INGOT, 9),
                    new ItemStack(ModItems.GREEN_SAPPHIRE, 1),
                    6, 5, 0.15f
            ));
        });
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.JEWELLER, 2, factories -> {
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.RUBY, 2),
                    new ItemStack(ModItems.BLUE_GARNET, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.SAPPHIRE, 3),
                    new ItemStack(ModItems.PINK_GARNET, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.GREEN_SAPPHIRE, 3),
                    new ItemStack(ModItems.GREEN_GARNET, 1),
                    6, 5, 0.15f
            ));
        });
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.JEWELLER, 3, factories -> {
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.RUBY, 8),
                    new ItemStack(ModItems.TOPAZ, 1),
                    6, 5, 0.05f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.PINK_GARNET, 5),
                    new ItemStack(ModItems.WHITE_TOPAZ, 1),
                    6, 5, 0.15f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.GREEN_GARNET, 5),
                    new ItemStack(ModItems.TOPAZ, 1),
                    6, 5, 0.15f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.RADIANT, 13),
                    new ItemStack(ModItems.WHITE_TOPAZ, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.SAPPHIRE, 10),
                    new ItemStack(ModItems.TOPAZ, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.GREEN_SAPPHIRE, 3),
                    new ItemStack(ModItems.TOPAZ, 1),
                    6, 5, 0.15f
            ));
        });
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.JEWELLER, 3, factories -> {
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.SAPPHIRE, 5),
                    new ItemStack(ModItems.PERIDOT, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.SAPPHIRE, 3),
                    new ItemStack(ModItems.JADE, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.GREEN_SAPPHIRE, 3),
                    new ItemStack(ModItems.PYROPE, 1),
                    6, 5, 0.15f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.RADIANT, 15),
                    new ItemStack(ModItems.PERIDOT, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.RUBY, 6),
                    new ItemStack(ModItems.JADE, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.TOPAZ, 3),
                    new ItemStack(ModItems.PYROPE, 1),
                    6, 5, 0.15f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.WHITE_TOPAZ, 2),
                    new ItemStack(ModItems.PERIDOT, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.WHITE_TOPAZ, 3),
                    new ItemStack(ModItems.JADE, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(ModItems.WHITE_TOPAZ, 3),
                    new ItemStack(ModItems.PYROPE, 1),
                    6, 5, 0.15f
            ));
        });
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.JEWELLER, 4, factories -> {
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 14),
                    new ItemStack(ModItems.RADIANT, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 13),
                    new ItemStack(ModItems.RUBY, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 13),
                    new ItemStack(ModItems.SAPPHIRE, 1),
                    6, 5, 0.15f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.NETHERITE_INGOT, 5),
                    new ItemStack(ModItems.BLUE_GARNET, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.IRON_INGOT, 15),
                    new ItemStack(Items.DIAMOND, 1),
                    6, 5, 0.5f
            ));
        });
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.JEWELLER, 5, factories -> {
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.NETHERITE_INGOT, 8),
                    new ItemStack(ModItems.GREEN_GARNET, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 18),
                    new ItemStack(ModItems.TOPAZ, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 21),
                    new ItemStack(ModItems.PYROPE, 1),
                    6, 5, 0.15f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.NETHERITE_INGOT, 11),
                    new ItemStack(ModItems.JADE, 1),
                    6, 5, 0.5f
            ));
            factories.add((world, entity, random) -> new MerchantOffer(
                    new ItemCost(Items.IRON_INGOT, 65),
                    new ItemStack(ModItems.PERIDOT, 1),
                    6, 5, 0.5f
            ));
        });

        MoreOresModInitializer.LOGGER.info("Loading CustomTrades for " + MoreOresModInitializer.MOD_ID + " mod.");
    }
}
