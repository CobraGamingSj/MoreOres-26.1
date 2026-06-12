package org.cobra.moreores.world.gen.feature;

import org.cobra.moreores.block.ModBlocks;
import java.util.List;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class ModOreConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_RUBY_SMALL = ModConfiguredFeatures.of("ore_ruby_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_RUBY_MEDIUM = ModConfiguredFeatures.of("ore_ruby_medium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_RUBY_LARGE = ModConfiguredFeatures.of("ore_ruby_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SAPPHIRE_SMALL = ModConfiguredFeatures.of("ore_sapphire_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SAPPHIRE_MEDIUM = ModConfiguredFeatures.of("ore_sapphire_medium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SAPPHIRE_LARGE = ModConfiguredFeatures.of("ore_sapphire_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GREEN_SAPPHIRE_SMALL = ModConfiguredFeatures.of("ore_green_sapphire_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GREEN_SAPPHIRE_MEDIUM = ModConfiguredFeatures.of("ore_green_sapphire_medium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GREEN_SAPPHIRE_LARGE = ModConfiguredFeatures.of("ore_green_sapphire_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BLUE_GARNET_SMALL = ModConfiguredFeatures.of("ore_blue_garnet_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BLUE_GARNET_MEDIUM = ModConfiguredFeatures.of("ore_blue_garnet_medium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BLUE_GARNET_LARGE = ModConfiguredFeatures.of("ore_blue_garnet_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PINK_GARNET_SMALL = ModConfiguredFeatures.of("ore_pink_garnet_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PINK_GARNET_MEDIUM = ModConfiguredFeatures.of("ore_pink_garnet_medium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PINK_GARNET_LARGE = ModConfiguredFeatures.of("ore_pink_garnet_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GREEN_GARNET_SMALL = ModConfiguredFeatures.of("ore_green_garnet_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GREEN_GARNET_MEDIUM = ModConfiguredFeatures.of("ore_green_garnet_medium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GREEN_GARNET_LARGE = ModConfiguredFeatures.of("ore_green_garnet_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_KYAWTHUITE = ModConfiguredFeatures.of("ore_kyawthuite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TOPAZ_SMALL = ModConfiguredFeatures.of("ore_topaz_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TOPAZ_MEDIUM = ModConfiguredFeatures.of("ore_topaz_medium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TOPAZ_LARGE = ModConfiguredFeatures.of("ore_topaz_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_WHITE_TOPAZ_SMALL = ModConfiguredFeatures.of("ore_white_topaz_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_WHITE_TOPAZ_MEDIUM = ModConfiguredFeatures.of("ore_white_topaz_medium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_WHITE_TOPAZ_LARGE = ModConfiguredFeatures.of("ore_white_topaz_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PERIDOT_SMALL = ModConfiguredFeatures.of("ore_peridot_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PERIDOT_MEDIUM = ModConfiguredFeatures.of("ore_peridot_medium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PERIDOT_LARGE = ModConfiguredFeatures.of("ore_peridot_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_JADE_SMALL = ModConfiguredFeatures.of("ore_jade_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_JADE_MEDIUM = ModConfiguredFeatures.of("ore_jade_medium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_JADE_LARGE = ModConfiguredFeatures.of("ore_jade_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PYROPE_SMALL = ModConfiguredFeatures.of("ore_pyrope_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PYROPE_MEDIUM = ModConfiguredFeatures.of("ore_pyrope_medium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PYROPE_LARGE = ModConfiguredFeatures.of("ore_pyrope_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ECLIPSE_GEM = ModConfiguredFeatures.of("ore_eclipse_gem");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        RuleTest ruleTest2 = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest ruleTest3 = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreConfiguration.TargetBlockState> rubyList = List.of(
                OreConfiguration.target(ruleTest2, ModBlocks.RUBY_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest3, ModBlocks.DEEPSLATE_RUBY_ORE.defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> sapphireList = List.of(
                OreConfiguration.target(ruleTest2, ModBlocks.SAPPHIRE_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest3, ModBlocks.DEEPSLATE_SAPPHIRE_ORE.defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> greenSapphireList = List.of(
                OreConfiguration.target(ruleTest2, ModBlocks.GREEN_SAPPHIRE_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest3, ModBlocks.DEEPSLATE_GREEN_SAPPHIRE_ORE.defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> blueGarnetList = List.of(
                OreConfiguration.target(ruleTest2, ModBlocks.BLUE_GARNET_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest3, ModBlocks.DEEPSLATE_BLUE_GARNET_ORE.defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> pinkGarnetList = List.of(
                OreConfiguration.target(ruleTest2, ModBlocks.PINK_GARNET_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest3, ModBlocks.DEEPSLATE_PINK_GARNET_ORE.defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> greenGarnetList = List.of(
                OreConfiguration.target(ruleTest2, ModBlocks.GREEN_GARNET_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest3, ModBlocks.DEEPSLATE_GREEN_GARNET_ORE.defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> kyawthuiteList = List.of(
                OreConfiguration.target(ruleTest2, ModBlocks.KYAWTHUITE_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest3, ModBlocks.DEEPSLATE_KYAWTHUITE_ORE.defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> topazList = List.of(
                OreConfiguration.target(ruleTest2, ModBlocks.TOPAZ_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest3, ModBlocks.DEEPSLATE_TOPAZ_ORE.defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> whiteTopazList = List.of(
                OreConfiguration.target(ruleTest2, ModBlocks.WHITE_TOPAZ_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest3, ModBlocks.DEEPSLATE_WHITE_TOPAZ_ORE.defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> peridotList = List.of(
                OreConfiguration.target(ruleTest2, ModBlocks.PERIDOT_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest3, ModBlocks.DEEPSLATE_PERIDOT_ORE.defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> jadeList = List.of(
                OreConfiguration.target(ruleTest2, ModBlocks.JADE_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest3, ModBlocks.DEEPSLATE_JADE_ORE.defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> pyropeList = List.of(
                OreConfiguration.target(ruleTest2, ModBlocks.PYROPE_ORE.defaultBlockState()),
                OreConfiguration.target(ruleTest3, ModBlocks.DEEPSLATE_PYROPE_ORE.defaultBlockState())
        );

        List<OreConfiguration.TargetBlockState> eclipseGemList = List.of(
                OreConfiguration.target(ruleTest2, ModBlocks.ECLIPSE_GEM_ORE.defaultBlockState())
        );
        
        FeatureUtils.register(featureRegisterable, ORE_RUBY_SMALL, Feature.ORE, new OreConfiguration(rubyList, 1, 0.699F));
        FeatureUtils.register(featureRegisterable, ORE_RUBY_LARGE, Feature.ORE, new OreConfiguration(rubyList, 3, 0.69F));
        FeatureUtils.register(featureRegisterable, ORE_RUBY_MEDIUM, Feature.ORE, new OreConfiguration(rubyList, 3, 0.61F));
        FeatureUtils.register(featureRegisterable, ORE_SAPPHIRE_SMALL, Feature.ORE, new OreConfiguration(sapphireList, 1, 0.696F));
        FeatureUtils.register(featureRegisterable, ORE_SAPPHIRE_LARGE, Feature.ORE, new OreConfiguration(sapphireList, 4, 0.691F));
        FeatureUtils.register(featureRegisterable, ORE_SAPPHIRE_MEDIUM, Feature.ORE, new OreConfiguration(sapphireList, 4, 0.51F));
        FeatureUtils.register(featureRegisterable, ORE_GREEN_SAPPHIRE_SMALL, Feature.ORE, new OreConfiguration(greenSapphireList, 2, 0.616F));
        FeatureUtils.register(featureRegisterable, ORE_GREEN_SAPPHIRE_LARGE, Feature.ORE, new OreConfiguration(greenSapphireList, 4, 0.696F));
        FeatureUtils.register(featureRegisterable, ORE_GREEN_SAPPHIRE_MEDIUM, Feature.ORE, new OreConfiguration(greenSapphireList, 3, 0.57F));
        FeatureUtils.register(featureRegisterable, ORE_BLUE_GARNET_SMALL, Feature.ORE, new OreConfiguration(blueGarnetList, 3, 0.69F));
        FeatureUtils.register(featureRegisterable, ORE_BLUE_GARNET_LARGE, Feature.ORE, new OreConfiguration(blueGarnetList, 4, 0.699F));
        FeatureUtils.register(featureRegisterable, ORE_BLUE_GARNET_MEDIUM, Feature.ORE, new OreConfiguration(blueGarnetList, 3, 0.51F));
        FeatureUtils.register(featureRegisterable, ORE_PINK_GARNET_SMALL, Feature.ORE, new OreConfiguration(pinkGarnetList, 2, 0.52F));
        FeatureUtils.register(featureRegisterable, ORE_PINK_GARNET_LARGE, Feature.ORE, new OreConfiguration(pinkGarnetList, 4, 0.616F));
        FeatureUtils.register(featureRegisterable, ORE_PINK_GARNET_MEDIUM, Feature.ORE, new OreConfiguration(pinkGarnetList, 3, 0.52F));
        FeatureUtils.register(featureRegisterable, ORE_GREEN_GARNET_SMALL, Feature.ORE, new OreConfiguration(greenGarnetList, 2, 0.694F));
        FeatureUtils.register(featureRegisterable, ORE_GREEN_GARNET_LARGE, Feature.ORE, new OreConfiguration(greenGarnetList, 3, 0.695F));
        FeatureUtils.register(featureRegisterable, ORE_GREEN_GARNET_MEDIUM, Feature.ORE, new OreConfiguration(greenGarnetList, 4, 0.5F));
        FeatureUtils.register(featureRegisterable, ORE_KYAWTHUITE, Feature.ORE, new OreConfiguration(kyawthuiteList, 2, 0.0F));
        FeatureUtils.register(featureRegisterable, ORE_TOPAZ_SMALL, Feature.ORE, new OreConfiguration(topazList, 2, 0.69F));
        FeatureUtils.register(featureRegisterable, ORE_TOPAZ_LARGE, Feature.ORE, new OreConfiguration(topazList, 3, 0.695F));
        FeatureUtils.register(featureRegisterable, ORE_TOPAZ_MEDIUM, Feature.ORE, new OreConfiguration(topazList, 4, 0.5F));
        FeatureUtils.register(featureRegisterable, ORE_WHITE_TOPAZ_SMALL, Feature.ORE, new OreConfiguration(whiteTopazList, 2, 0.618F));
        FeatureUtils.register(featureRegisterable, ORE_WHITE_TOPAZ_LARGE, Feature.ORE, new OreConfiguration(whiteTopazList, 4, 0.697F));
        FeatureUtils.register(featureRegisterable, ORE_WHITE_TOPAZ_MEDIUM, Feature.ORE, new OreConfiguration(whiteTopazList, 3, 0.618F));
        FeatureUtils.register(featureRegisterable, ORE_PERIDOT_SMALL, Feature.ORE, new OreConfiguration(peridotList, 3, 0.57F));
        FeatureUtils.register(featureRegisterable, ORE_PERIDOT_LARGE, Feature.ORE, new OreConfiguration(peridotList, 3, 0.699F));
        FeatureUtils.register(featureRegisterable, ORE_PERIDOT_MEDIUM, Feature.ORE, new OreConfiguration(peridotList, 2, 0.697F));
        FeatureUtils.register(featureRegisterable, ORE_JADE_SMALL, Feature.ORE, new OreConfiguration(jadeList, 2, 0.5F));
        FeatureUtils.register(featureRegisterable, ORE_JADE_LARGE, Feature.ORE, new OreConfiguration(jadeList, 4, 0.699F));
        FeatureUtils.register(featureRegisterable, ORE_JADE_MEDIUM, Feature.ORE, new OreConfiguration(jadeList, 2, 0.615F));
        FeatureUtils.register(featureRegisterable, ORE_PYROPE_SMALL, Feature.ORE, new OreConfiguration(pyropeList, 2, 0.55F));
        FeatureUtils.register(featureRegisterable, ORE_PYROPE_LARGE, Feature.ORE, new OreConfiguration(pyropeList, 4, 0.697F));
        FeatureUtils.register(featureRegisterable, ORE_PYROPE_MEDIUM, Feature.ORE, new OreConfiguration(pyropeList, 3, 0.615F));
        FeatureUtils.register(featureRegisterable, ORE_ECLIPSE_GEM, Feature.ORE, new OreConfiguration(eclipseGemList, 3, 0.2F));
    }
}