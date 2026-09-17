package org.cobra.moreores.level.storage.loot.providers.number.ints;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.cobra.moreores.MoreOresModInitializer;

public class ModContextIntProviders {

    public static final ResourceKey<ContextIntProvider> COOKING_TIME_ENERGY_INGOT = of("cooking/time_energy_ingot");
    public static final ResourceKey<ContextIntProvider> COOKING_TIME_ENERGY_BLOCK = of("cooking/time_energy_block");

    private static ResourceKey<ContextIntProvider> of(final String location) {
        return ResourceKey.create(Registries.CONTEXT_INT_PROVIDER, MoreOresModInitializer.id(location));
    }

    public static void bootstrap(BootstrapContext<ContextIntProvider> context) {
        HolderGetter<LootItemCondition> predicates = context.lookup(Registries.PREDICATE);
        Holder.Reference<ContextIntProvider> normalBurnTime = context.register(ContextIntProviders.COOKING_NORMAL_BURN_TIME_REDUCTION_FACTOR, new ConstantValue(1));
        Holder.Reference<ContextIntProvider> fastBurnTime = context.register(ContextIntProviders.COOKING_FAST_BURN_TIME_REDUCTION_FACTOR, new ConstantValue(2));
        context.register(COOKING_TIME_ENERGY_INGOT, ContextIntProviders.cooking(predicates, normalBurnTime, fastBurnTime, 24500));
        context.register(COOKING_TIME_ENERGY_BLOCK, ContextIntProviders.cooking(predicates, normalBurnTime, fastBurnTime, 27500));
    }
}