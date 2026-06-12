package org.cobra.moreores.level.gen.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.cobra.moreores.MoreOresModInitializer;

public class ModConfiguredFeatures {

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        ModOreConfiguredFeatures.bootstrap(featureRegisterable);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> of(String id) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, id));
    }
}
