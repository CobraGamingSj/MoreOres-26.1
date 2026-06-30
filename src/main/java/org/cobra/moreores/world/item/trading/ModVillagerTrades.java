package org.cobra.moreores.world.item.trading;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.item.ModItems;

import java.util.List;
import java.util.Optional;

public class ModVillagerTrades {

    public static final ResourceKey<VillagerTrade> JEWELLER_1_COAL_RUBY = of("jeweller/1/coal_ruby");
    public static final ResourceKey<VillagerTrade> JEWELLER_1_COPPER_INGOT_SAPPHIRE = of("jeweller/1/copper_ingot_sapphire");
    public static final ResourceKey<VillagerTrade> JEWELLER_1_IRON_INGOT_GREEN_SAPPHIRE = of("jeweller/1/iron_ingot_green_sapphire");
    public static final ResourceKey<VillagerTrade> JEWELLER_2_RUBY_BLUE_GARNET = of("jeweller/2/ruby_blue_garnet");
    public static final ResourceKey<VillagerTrade> JEWELLER_2_SAPPHIRE_PINK_GARNET = of("jeweller/2/sapphire_pink_garnet");
    public static final ResourceKey<VillagerTrade> JEWELLER_2_GREEN_SAPPHIRE_GREEN_GARNET = of("jeweller/2/green_sapphire_green_garnet");
    public static final ResourceKey<VillagerTrade> JEWELLER_3_RUBY_TOPAZ = of("jeweller/3/ruby_topaz");
    public static final ResourceKey<VillagerTrade> JEWELLER_3_PINK_GARNET_WHITE_TOPAZ = of("jeweller/3/pink_garnet_white_topaz");
    public static final ResourceKey<VillagerTrade> JEWELLER_3_GREEN_GARNET_TOPAZ = of("jeweller/3/green_garnet_topaz");
    public static final ResourceKey<VillagerTrade> JEWELLER_3_SAPPHIRE_TOPAZ = of("jeweller/3/sapphire_topaz");
    public static final ResourceKey<VillagerTrade> JEWELLER_3_GREEN_SAPPHIRE_TOPAZ = of("jeweller/3/green_sapphire_topaz");
    public static final ResourceKey<VillagerTrade> JEWELLER_3_SAPPHIRE_PERIDOT = of("jeweller/3/sapphire_peridot");
    public static final ResourceKey<VillagerTrade> JEWELLER_3_SAPPHIRE_JADE = of("jeweller/3/sapphire_jade");
    public static final ResourceKey<VillagerTrade> JEWELLER_3_GREEN_SAPPHIRE_PYROPE = of("jeweller/3/green_sapphire_pyrope");
    public static final ResourceKey<VillagerTrade> JEWELLER_3_RUBY_JADE = of("jeweller/3/ruby_jade");
    public static final ResourceKey<VillagerTrade> JEWELLER_3_TOPAZ_PYROPE = of("jeweller/3/topaz_pyrope");
    public static final ResourceKey<VillagerTrade> JEWELLER_3_WHITE_TOPAZ_PERIDOT = of("jeweller/3/white_topaz_peridot");
    public static final ResourceKey<VillagerTrade> JEWELLER_3_WHITE_TOPAZ_JADE = of("jeweller/3/white_topaz_jade");
    public static final ResourceKey<VillagerTrade> JEWELLER_3_WHITE_TOPAZ_PYROPE = of("jeweller/3/white_topaz_pyrope");
    public static final ResourceKey<VillagerTrade> JEWELLER_4_EMERALD_RADIANT = of("jeweller/4/emerald_radiant");
    public static final ResourceKey<VillagerTrade> JEWELLER_4_EMERALD_RUBY = of("jeweller/4/emerald_ruby");
    public static final ResourceKey<VillagerTrade> JEWELLER_4_EMERALD_SAPPHIRE = of("jeweller/4/emerald_sapphire");
    public static final ResourceKey<VillagerTrade> JEWELLER_4_NETHERITE_INGOT_BLUE_GARNET = of("jeweller/4/netherite_ingot_blue_garnet");
    public static final ResourceKey<VillagerTrade> JEWELLER_4_IRON_INGOT_DIAMOND = of("jeweller/4/iron_ingot_diamond");
    public static final ResourceKey<VillagerTrade> JEWELLER_5_NETHERITE_INGOT_GREEN_GARNET = of("jeweller/5/netherite_ingot_green_garnet");
    public static final ResourceKey<VillagerTrade> JEWELLER_5_EMERALD_TOPAZ = of("jeweller/5/emerald_topaz");
    public static final ResourceKey<VillagerTrade> JEWELLER_5_EMERALD_PYROPE = of("jeweller/5/emerald_pyrope");
    public static final ResourceKey<VillagerTrade> JEWELLER_5_NETHERITE_INGOT_JADE = of("jeweller/5/netherite_ingot_jade");
    public static final ResourceKey<VillagerTrade> JEWELLER_5_IRON_INGOT_PERIDOT = of("jeweller/5/iron_ingot_peridot");

    public static void bootstrap(BootstrapContext<VillagerTrade> context) {
        register(context, JEWELLER_1_COAL_RUBY, new VillagerTrade(
                new TradeCost(Items.COAL, 24),
                new ItemStackTemplate(ModItems.RUBY, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_1_COPPER_INGOT_SAPPHIRE, new VillagerTrade(
                new TradeCost(Items.COPPER_INGOT, 12),
                new ItemStackTemplate(ModItems.SAPPHIRE, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_1_IRON_INGOT_GREEN_SAPPHIRE, new VillagerTrade(
                new TradeCost(Items.IRON_INGOT, 9),
                new ItemStackTemplate(ModItems.GREEN_SAPPHIRE, 1),
                6, 5, 0.15f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_2_RUBY_BLUE_GARNET, new VillagerTrade(
                new TradeCost(ModItems.RUBY, 2),
                new ItemStackTemplate(ModItems.BLUE_GARNET, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_2_SAPPHIRE_PINK_GARNET, new VillagerTrade(
                new TradeCost(ModItems.SAPPHIRE, 3),
                new ItemStackTemplate(ModItems.PINK_GARNET, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_2_GREEN_SAPPHIRE_GREEN_GARNET, new VillagerTrade(
                new TradeCost(ModItems.GREEN_SAPPHIRE, 3),
                new ItemStackTemplate(ModItems.GREEN_GARNET, 1),
                6, 5, 0.15f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_3_RUBY_TOPAZ, new VillagerTrade(
                new TradeCost(ModItems.RUBY, 8),
                new ItemStackTemplate(ModItems.TOPAZ, 1),
                6, 5, 0.05f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_3_PINK_GARNET_WHITE_TOPAZ, new VillagerTrade(
                new TradeCost(ModItems.PINK_GARNET, 5),
                new ItemStackTemplate(ModItems.WHITE_TOPAZ, 1),
                6, 5, 0.15f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_3_GREEN_GARNET_TOPAZ, new VillagerTrade(
                new TradeCost(ModItems.GREEN_GARNET, 5),
                new ItemStackTemplate(ModItems.TOPAZ, 1),
                6, 5, 0.15f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_3_SAPPHIRE_TOPAZ, new VillagerTrade(
                new TradeCost(ModItems.SAPPHIRE, 10),
                new ItemStackTemplate(ModItems.TOPAZ, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_3_GREEN_SAPPHIRE_TOPAZ, new VillagerTrade(
                new TradeCost(ModItems.GREEN_SAPPHIRE, 3),
                new ItemStackTemplate(ModItems.TOPAZ, 1),
                6, 5, 0.15f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_3_SAPPHIRE_PERIDOT, new VillagerTrade(
                new TradeCost(ModItems.SAPPHIRE, 5),
                new ItemStackTemplate(ModItems.PERIDOT, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_3_SAPPHIRE_JADE, new VillagerTrade(
                new TradeCost(ModItems.SAPPHIRE, 3),
                new ItemStackTemplate(ModItems.JADE, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_3_GREEN_SAPPHIRE_PYROPE, new VillagerTrade(
                new TradeCost(ModItems.GREEN_SAPPHIRE, 3),
                new ItemStackTemplate(ModItems.PYROPE, 1),
                6, 5, 0.15f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_3_RUBY_JADE, new VillagerTrade(
                new TradeCost(ModItems.RUBY, 6),
                new ItemStackTemplate(ModItems.JADE, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_3_TOPAZ_PYROPE, new VillagerTrade(
                new TradeCost(ModItems.TOPAZ, 3),
                new ItemStackTemplate(ModItems.PYROPE, 1),
                6, 5, 0.15f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_3_WHITE_TOPAZ_PERIDOT, new VillagerTrade(
                new TradeCost(ModItems.WHITE_TOPAZ, 2),
                new ItemStackTemplate(ModItems.PERIDOT, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_3_WHITE_TOPAZ_JADE, new VillagerTrade(
                new TradeCost(ModItems.WHITE_TOPAZ, 3),
                new ItemStackTemplate(ModItems.JADE, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_3_WHITE_TOPAZ_PYROPE, new VillagerTrade(
                new TradeCost(ModItems.WHITE_TOPAZ, 3),
                new ItemStackTemplate(ModItems.PYROPE, 1),
                6, 5, 0.15f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_4_EMERALD_RADIANT, new VillagerTrade(
                new TradeCost(Items.EMERALD, 26),
                new ItemStackTemplate(ModItems.RADIANT, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_4_EMERALD_RUBY, new VillagerTrade(
                new TradeCost(Items.EMERALD, 13),
                new ItemStackTemplate(ModItems.RUBY, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_4_EMERALD_SAPPHIRE, new VillagerTrade(
                new TradeCost(Items.EMERALD, 13),
                new ItemStackTemplate(ModItems.SAPPHIRE, 1),
                6, 5, 0.15f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_4_NETHERITE_INGOT_BLUE_GARNET, new VillagerTrade(
                new TradeCost(Items.NETHERITE_INGOT, 5),
                new ItemStackTemplate(ModItems.BLUE_GARNET, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_4_IRON_INGOT_DIAMOND, new VillagerTrade(
                new TradeCost(Items.IRON_INGOT, 15),
                new ItemStackTemplate(Items.DIAMOND, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_5_NETHERITE_INGOT_GREEN_GARNET, new VillagerTrade(
                new TradeCost(Items.NETHERITE_INGOT, 8),
                new ItemStackTemplate(ModItems.GREEN_GARNET, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_5_EMERALD_TOPAZ, new VillagerTrade(
                new TradeCost(Items.EMERALD, 18),
                new ItemStackTemplate(ModItems.TOPAZ, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_5_EMERALD_PYROPE, new VillagerTrade(
                new TradeCost(Items.EMERALD, 21),
                new ItemStackTemplate(ModItems.PYROPE, 1),
                6, 5, 0.15f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_5_NETHERITE_INGOT_JADE, new VillagerTrade(
                new TradeCost(Items.NETHERITE_INGOT, 11),
                new ItemStackTemplate(ModItems.JADE, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
        register(context, JEWELLER_5_IRON_INGOT_PERIDOT, new VillagerTrade(
                new TradeCost(Items.IRON_INGOT, 65),
                new ItemStackTemplate(ModItems.PERIDOT, 1),
                6, 5, 0.5f, Optional.empty(), List.of()
        ));
    }
    
    private static ResourceKey<VillagerTrade> of(String id) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, MoreOresModInitializer.id(id));
    }

    private static void register(BootstrapContext<VillagerTrade> context, ResourceKey<VillagerTrade> key, VillagerTrade trade) {
        context.register(key, trade);
    }
}
