package org.cobra.moreores.data.village;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.tags.ModVillagerTradeTags;

import java.util.Optional;

public class TradeSets {

    public static final ResourceKey<TradeSet> JEWELLER_LEVEL_1 = of("jeweller/level_1");
    public static final ResourceKey<TradeSet> JEWELLER_LEVEL_2 = of("jeweller/level_2");
    public static final ResourceKey<TradeSet> JEWELLER_LEVEL_3 = of("jeweller/level_3");
    public static final ResourceKey<TradeSet> JEWELLER_LEVEL_4 = of("jeweller/level_4");
    public static final ResourceKey<TradeSet> JEWELLER_LEVEL_5 = of("jeweller/level_5");

    public static void bootstrap(BootstrapContext<TradeSet> context) {
        register(context, JEWELLER_LEVEL_1, ModVillagerTradeTags.JEWELLER_LEVEL_1);
        register(context, JEWELLER_LEVEL_2, ModVillagerTradeTags.JEWELLER_LEVEL_2);
        register(context, JEWELLER_LEVEL_3, ModVillagerTradeTags.JEWELLER_LEVEL_3);
        register(context, JEWELLER_LEVEL_4, ModVillagerTradeTags.JEWELLER_LEVEL_4);
        register(context, JEWELLER_LEVEL_5, ModVillagerTradeTags.JEWELLER_LEVEL_5);
    }

    private static ResourceKey<TradeSet> of(final String id) {
        return ResourceKey.create(Registries.TRADE_SET, MoreOresModInitializer.id(id));
    }

    public static Holder.Reference<TradeSet> register(final BootstrapContext<TradeSet> context,
                                                      final ResourceKey<TradeSet> resourceKey, final TagKey<VillagerTrade> tradeTag) {
        return register(context, resourceKey, tradeTag, ConstantValue.exactly(2.0F));
    }

    public static Holder.Reference<TradeSet> register(final BootstrapContext<TradeSet> context, final ResourceKey<TradeSet> resourceKey,
                                                      final TagKey<VillagerTrade> tradeTag, final NumberProvider numberProvider) {
        return context.register(resourceKey, new TradeSet(context.lookup(Registries.VILLAGER_TRADE).getOrThrow(tradeTag),
                numberProvider, false, Optional.of(resourceKey.identifier().withPrefix("trade_set/"))));
    }

}
