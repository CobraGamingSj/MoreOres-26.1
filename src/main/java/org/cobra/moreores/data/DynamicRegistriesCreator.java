package org.cobra.moreores.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import java.util.concurrent.CompletableFuture;

public class DynamicRegistriesCreator extends FabricDynamicRegistryProvider {
    public DynamicRegistriesCreator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
        entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
        entries.addAll(registries.lookupOrThrow(Registries.TRIM_MATERIAL));
        entries.addAll(registries.lookupOrThrow(Registries.TRIM_PATTERN));
        entries.addAll(registries.lookupOrThrow(Registries.ENCHANTMENT));
        entries.addAll(registries.lookupOrThrow(Registries.TRADE_SET));
        entries.addAll(registries.lookupOrThrow(Registries.VILLAGER_TRADE));
    }

    @Override
    public String getName() {
        return "Dynamic Registries";
    }
}