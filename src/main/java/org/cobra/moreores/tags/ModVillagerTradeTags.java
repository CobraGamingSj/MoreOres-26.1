package org.cobra.moreores.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.trading.VillagerTrade;
import org.cobra.moreores.MoreOresModInitializer;

public class ModVillagerTradeTags {

    public static final TagKey<VillagerTrade> JEWELLER_LEVEL_1 = of("jeweller/level_1");
    public static final TagKey<VillagerTrade> JEWELLER_LEVEL_2 = of("jeweller/level_2");
    public static final TagKey<VillagerTrade> JEWELLER_LEVEL_3 = of("jeweller/level_3");
    public static final TagKey<VillagerTrade> JEWELLER_LEVEL_4 = of("jeweller/level_4");
    public static final TagKey<VillagerTrade> JEWELLER_LEVEL_5 = of("jeweller/level_5");

    private static TagKey<VillagerTrade> of(String name) {
        return TagKey.create(Registries.VILLAGER_TRADE, MoreOresModInitializer.id(name));
    }
}