package org.cobra.moreores.world.gen.feature;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModOrePlacedFeatures {

    public static final ResourceKey<PlacedFeature> ORE_RUBY = ModPlacedFeatures.of("ore_ruby");
    public static final ResourceKey<PlacedFeature> ORE_RUBY_MEDIUM = ModPlacedFeatures.of("ore_ruby_medium");
    public static final ResourceKey<PlacedFeature> ORE_RUBY_LARGE = ModPlacedFeatures.of("ore_ruby_large");
    public static final ResourceKey<PlacedFeature> ORE_SAPPHIRE = ModPlacedFeatures.of("ore_sapphire");
    public static final ResourceKey<PlacedFeature> ORE_SAPPHIRE_MEDIUM = ModPlacedFeatures.of("ore_sapphire_medium");
    public static final ResourceKey<PlacedFeature> ORE_SAPPHIRE_LARGE = ModPlacedFeatures.of("ore_sapphire_large");
    public static final ResourceKey<PlacedFeature> ORE_GREEN_SAPPHIRE = ModPlacedFeatures.of("ore_green_sapphire");
    public static final ResourceKey<PlacedFeature> ORE_GREEN_SAPPHIRE_MEDIUM = ModPlacedFeatures.of("ore_green_sapphire_medium");
    public static final ResourceKey<PlacedFeature> ORE_GREEN_SAPPHIRE_LARGE = ModPlacedFeatures.of("ore_green_sapphire_large");
    public static final ResourceKey<PlacedFeature> ORE_BLUE_GARNET = ModPlacedFeatures.of("ore_blue_garnet");
    public static final ResourceKey<PlacedFeature> ORE_BLUE_GARNET_MEDIUM = ModPlacedFeatures.of("ore_blue_garnet_medium");
    public static final ResourceKey<PlacedFeature> ORE_BLUE_GARNET_LARGE = ModPlacedFeatures.of("ore_blue_garnet_large");
    public static final ResourceKey<PlacedFeature> ORE_PINK_GARNET = ModPlacedFeatures.of("ore_pink_garnet");
    public static final ResourceKey<PlacedFeature> ORE_PINK_GARNET_MEDIUM = ModPlacedFeatures.of("ore_pink_garnet_medium");
    public static final ResourceKey<PlacedFeature> ORE_PINK_GARNET_LARGE = ModPlacedFeatures.of("ore_pink_garnet_large");
    public static final ResourceKey<PlacedFeature> ORE_GREEN_GARNET = ModPlacedFeatures.of("ore_green_garnet");
    public static final ResourceKey<PlacedFeature> ORE_GREEN_GARNET_MEDIUM = ModPlacedFeatures.of("ore_green_garnet_medium");
    public static final ResourceKey<PlacedFeature> ORE_GREEN_GARNET_LARGE = ModPlacedFeatures.of("ore_green_garnet_large");
    public static final ResourceKey<PlacedFeature> ORE_KYAWTHUITE = ModPlacedFeatures.of("ore_kyawthuite");
    public static final ResourceKey<PlacedFeature> ORE_KYAWTHUITE_DEEPSLATE = ModPlacedFeatures.of("ore_kyawthuite_deepslate");
    public static final ResourceKey<PlacedFeature> ORE_TOPAZ = ModPlacedFeatures.of("ore_topaz");
    public static final ResourceKey<PlacedFeature> ORE_TOPAZ_MEDIUM = ModPlacedFeatures.of("ore_topaz_medium");
    public static final ResourceKey<PlacedFeature> ORE_TOPAZ_LARGE = ModPlacedFeatures.of("ore_topaz_large");
    public static final ResourceKey<PlacedFeature> ORE_WHITE_TOPAZ = ModPlacedFeatures.of("ore_white_topaz");
    public static final ResourceKey<PlacedFeature> ORE_WHITE_TOPAZ_MEDIUM = ModPlacedFeatures.of("ore_white_topaz_medium");
    public static final ResourceKey<PlacedFeature> ORE_WHITE_TOPAZ_LARGE = ModPlacedFeatures.of("ore_white_topaz_large");
    public static final ResourceKey<PlacedFeature> ORE_PERIDOT = ModPlacedFeatures.of("ore_peridot");
    public static final ResourceKey<PlacedFeature> ORE_PERIDOT_MEDIUM = ModPlacedFeatures.of("ore_peridot_medium");
    public static final ResourceKey<PlacedFeature> ORE_PERIDOT_LARGE = ModPlacedFeatures.of("ore_peridot_large");
    public static final ResourceKey<PlacedFeature> ORE_JADE = ModPlacedFeatures.of("ore_jade");
    public static final ResourceKey<PlacedFeature> ORE_JADE_MEDIUM = ModPlacedFeatures.of("ore_jade_medium");
    public static final ResourceKey<PlacedFeature> ORE_JADE_LARGE = ModPlacedFeatures.of("ore_jade_large");
    public static final ResourceKey<PlacedFeature> ORE_PYROPE = ModPlacedFeatures.of("ore_pyrope");
    public static final ResourceKey<PlacedFeature> ORE_PYROPE_MEDIUM = ModPlacedFeatures.of("ore_pyrope_medium");
    public static final ResourceKey<PlacedFeature> ORE_PYROPE_LARGE = ModPlacedFeatures.of("ore_pyrope_large");
    public static final ResourceKey<PlacedFeature> ORE_ECLIPSE_GEM = ModPlacedFeatures.of("ore_eclipse_gem");

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacement.of(count), heightModifier);
    }

    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilter.onAverageOnceEvery(chance), heightModifier);
    }
    public static void bootstrap(BootstrapContext<PlacedFeature> placedFeatureRegisterable) {
        HolderGetter<ConfiguredFeature<?, ?>> placedFeatureRegisterableRegistryLookup = placedFeatureRegisterable.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> registryEntry20 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_RUBY_SMALL);
        Holder<ConfiguredFeature<?, ?>> registryEntry21 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_RUBY_MEDIUM);
        Holder<ConfiguredFeature<?, ?>> registryEntry22 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_RUBY_LARGE);
        Holder<ConfiguredFeature<?, ?>> registryEntry23 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_SAPPHIRE_SMALL);
        Holder<ConfiguredFeature<?, ?>> registryEntry24 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_SAPPHIRE_MEDIUM);
        Holder<ConfiguredFeature<?, ?>> registryEntry25 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_SAPPHIRE_LARGE);
        Holder<ConfiguredFeature<?, ?>> registryEntry26 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_GREEN_SAPPHIRE_SMALL);
        Holder<ConfiguredFeature<?, ?>> registryEntry27 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_GREEN_SAPPHIRE_MEDIUM);
        Holder<ConfiguredFeature<?, ?>> registryEntry28 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_GREEN_SAPPHIRE_LARGE);
        Holder<ConfiguredFeature<?, ?>> registryEntry29 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_BLUE_GARNET_SMALL);
        Holder<ConfiguredFeature<?, ?>> registryEntry30 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_BLUE_GARNET_MEDIUM);
        Holder<ConfiguredFeature<?, ?>> registryEntry31 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_BLUE_GARNET_LARGE);
        Holder<ConfiguredFeature<?, ?>> registryEntry32 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_PINK_GARNET_SMALL);
        Holder<ConfiguredFeature<?, ?>> registryEntry33 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_PINK_GARNET_MEDIUM);
        Holder<ConfiguredFeature<?, ?>> registryEntry34 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_PINK_GARNET_LARGE);
        Holder<ConfiguredFeature<?, ?>> registryEntry35 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_GREEN_GARNET_SMALL);
        Holder<ConfiguredFeature<?, ?>> registryEntry36 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_GREEN_GARNET_MEDIUM);
        Holder<ConfiguredFeature<?, ?>> registryEntry37 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_GREEN_GARNET_LARGE);
        Holder<ConfiguredFeature<?, ?>> registryEntryX = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_KYAWTHUITE);
        Holder<ConfiguredFeature<?, ?>> registryEntry38 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_TOPAZ_SMALL);
        Holder<ConfiguredFeature<?, ?>> registryEntry39 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_TOPAZ_MEDIUM);
        Holder<ConfiguredFeature<?, ?>> registryEntry40 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_TOPAZ_LARGE);
        Holder<ConfiguredFeature<?, ?>> registryEntry41 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_WHITE_TOPAZ_SMALL);
        Holder<ConfiguredFeature<?, ?>> registryEntry42 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_WHITE_TOPAZ_MEDIUM);
        Holder<ConfiguredFeature<?, ?>> registryEntry43 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_WHITE_TOPAZ_LARGE);
        Holder<ConfiguredFeature<?, ?>> registryEntry44 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_PERIDOT_SMALL);
        Holder<ConfiguredFeature<?, ?>> registryEntry45 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_PERIDOT_LARGE);
        Holder<ConfiguredFeature<?, ?>> registryEntry46 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_PERIDOT_MEDIUM);
        Holder<ConfiguredFeature<?, ?>> registryEntry47 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_JADE_SMALL);
        Holder<ConfiguredFeature<?, ?>> registryEntry48 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_JADE_LARGE);
        Holder<ConfiguredFeature<?, ?>> registryEntry49 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_JADE_MEDIUM);
        Holder<ConfiguredFeature<?, ?>> registryEntry50 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_PYROPE_SMALL);
        Holder<ConfiguredFeature<?, ?>> registryEntry51 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_PYROPE_LARGE);
        Holder<ConfiguredFeature<?, ?>> registryEntry52 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_PYROPE_MEDIUM);
        Holder<ConfiguredFeature<?, ?>> registryEntry53 = placedFeatureRegisterableRegistryLookup.getOrThrow(ModOreConfiguredFeatures.ORE_ECLIPSE_GEM);

        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_RUBY,
                registryEntry20,
                modifiersWithCount(5,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(76)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable, ORE_RUBY_MEDIUM, registryEntry21, modifiersWithCount(5,HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-4)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_RUBY_LARGE,
                registryEntry22,
                modifiersWithRarity(6, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(72)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_SAPPHIRE,
                registryEntry23,
                modifiersWithCount(5,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(76)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable, ORE_SAPPHIRE_MEDIUM, registryEntry24, modifiersWithCount(5,HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-4)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_SAPPHIRE_LARGE,
                registryEntry25,
                modifiersWithRarity(5, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(72)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_GREEN_SAPPHIRE,
                registryEntry26,
                modifiersWithCount(5,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(76)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable, ORE_GREEN_SAPPHIRE_MEDIUM, registryEntry27,modifiersWithCount(5,HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-4)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_GREEN_SAPPHIRE_LARGE,
                registryEntry28,
                modifiersWithRarity(6, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(77)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_BLUE_GARNET,
                registryEntry29,
                modifiersWithCount(5,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(75)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable, ORE_BLUE_GARNET_MEDIUM, registryEntry30, modifiersWithCount(5,HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-4)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_BLUE_GARNET_LARGE,
                registryEntry31,
                modifiersWithRarity(4, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(70)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_PINK_GARNET,
                registryEntry32,
                modifiersWithCount(5,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(70)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable, ORE_PINK_GARNET_MEDIUM, registryEntry33, modifiersWithCount(5,HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-4)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_PINK_GARNET_LARGE,
                registryEntry34,
                modifiersWithRarity(4, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(75)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_GREEN_GARNET,
                registryEntry35,
                modifiersWithCount(5,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(71)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable, ORE_GREEN_GARNET_MEDIUM, registryEntry36, modifiersWithCount(5,HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-4)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_GREEN_GARNET_LARGE,
                registryEntry37,
                modifiersWithRarity(6, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(67)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_KYAWTHUITE,
                registryEntryX,
                List.of(
                        CountPlacement.of(2),
                        RarityFilter.onAverageOnceEvery(5), // very rare in stone
                        InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(-32),
                                VerticalAnchor.absolute(40)
                        ),
                        BiomeFilter.biome()
                )
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_KYAWTHUITE_DEEPSLATE,
                registryEntryX,
                List.of(
                        CountPlacement.of(1),
                        RarityFilter.onAverageOnceEvery(5), // deepslate
                        InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(-64),
                                VerticalAnchor.absolute(-20)
                        ),
                        BiomeFilter.biome()
                )
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_TOPAZ,
                registryEntry38,
                modifiersWithCount(5,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(52)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable, ORE_TOPAZ_MEDIUM, registryEntry39, modifiersWithCount(5,HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-4)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_TOPAZ_LARGE,
                registryEntry40,
                modifiersWithRarity(5,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(56)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_WHITE_TOPAZ,
                registryEntry41,
                modifiersWithCount(5,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(67)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable, ORE_WHITE_TOPAZ_MEDIUM, registryEntry42,modifiersWithCount(5,HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-4)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_WHITE_TOPAZ_LARGE,
                registryEntry43,
                modifiersWithRarity(5, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(68)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_PERIDOT,
                registryEntry44,
                modifiersWithCount(5,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(62)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable, ORE_PERIDOT_MEDIUM, registryEntry45, modifiersWithCount(5,HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-4)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_PERIDOT_LARGE,
                registryEntry46,
                modifiersWithRarity(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(68)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_JADE,
                registryEntry47,
                modifiersWithCount(5,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(73)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable, ORE_JADE_MEDIUM, registryEntry48, modifiersWithCount(5,HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-4)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_JADE_LARGE,
                registryEntry49,
                modifiersWithRarity(4,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(74)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_PYROPE,
                registryEntry50,
                modifiersWithCount(5,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(76)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable, ORE_PYROPE_MEDIUM, registryEntry51, modifiersWithCount(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-4)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_PYROPE_LARGE,
                registryEntry52,
                modifiersWithRarity(5, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-20), VerticalAnchor.aboveBottom(72)))
        );
        PlacementUtils.register(
                placedFeatureRegisterable,
                ORE_ECLIPSE_GEM,
                registryEntry53,
                List.of(
                        CountPlacement.of(3),
                        RarityFilter.onAverageOnceEvery(8), // very rare in stone
                        InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(
                                VerticalAnchor.absolute(-20),
                                VerticalAnchor.absolute(40)
                        ),
                        BiomeFilter.biome()
                )
        );
    }
}
