package org.cobra.moreores.world.gen.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.cobra.moreores.MoreOresModInitializer;

public class ModPlacedFeatures {

    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable) {
        ModOrePlacedFeatures.bootstrap(featureRegisterable);
    }

    public static ResourceKey<PlacedFeature> of(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, id));
    }
}
