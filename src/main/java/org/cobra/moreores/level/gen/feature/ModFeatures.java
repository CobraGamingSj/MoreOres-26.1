package org.cobra.moreores.level.gen.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.cobra.moreores.MoreOresModInitializer;

public class ModFeatures {

    public static void bootstrap(BootstrapContext<Feature> featureRegisterable) {
        ModOreFeatures.bootstrap(featureRegisterable);
    }

    public static ResourceKey<Feature> of(String id) {
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, id));
    }
}
