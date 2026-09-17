package org.cobra.moreores.level.gen.feature;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.BlockReplacement;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.HeightMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import org.cobra.moreores.world.block.ModBlocks;

import java.util.List;

public class ModOreFeatures {

    public static final ResourceKey<Feature> ORE_RUBY_SMALL = ModFeatures.of("ore_ruby_small");
    public static final ResourceKey<Feature> ORE_RUBY_MEDIUM = ModFeatures.of("ore_ruby_medium");
    public static final ResourceKey<Feature> ORE_RUBY_LARGE = ModFeatures.of("ore_ruby_large");
    public static final ResourceKey<Feature> ORE_SAPPHIRE_SMALL = ModFeatures.of("ore_sapphire_small");
    public static final ResourceKey<Feature> ORE_SAPPHIRE_MEDIUM = ModFeatures.of("ore_sapphire_medium");
    public static final ResourceKey<Feature> ORE_SAPPHIRE_LARGE = ModFeatures.of("ore_sapphire_large");
    public static final ResourceKey<Feature> ORE_GREEN_SAPPHIRE_SMALL = ModFeatures.of("ore_green_sapphire_small");
    public static final ResourceKey<Feature> ORE_GREEN_SAPPHIRE_MEDIUM = ModFeatures.of("ore_green_sapphire_medium");
    public static final ResourceKey<Feature> ORE_GREEN_SAPPHIRE_LARGE = ModFeatures.of("ore_green_sapphire_large");
    public static final ResourceKey<Feature> ORE_BLUE_GARNET_SMALL = ModFeatures.of("ore_blue_garnet_small");
    public static final ResourceKey<Feature> ORE_BLUE_GARNET_MEDIUM = ModFeatures.of("ore_blue_garnet_medium");
    public static final ResourceKey<Feature> ORE_BLUE_GARNET_LARGE = ModFeatures.of("ore_blue_garnet_large");
    public static final ResourceKey<Feature> ORE_PINK_GARNET_SMALL = ModFeatures.of("ore_pink_garnet_small");
    public static final ResourceKey<Feature> ORE_PINK_GARNET_MEDIUM = ModFeatures.of("ore_pink_garnet_medium");
    public static final ResourceKey<Feature> ORE_PINK_GARNET_LARGE = ModFeatures.of("ore_pink_garnet_large");
    public static final ResourceKey<Feature> ORE_GREEN_GARNET_SMALL = ModFeatures.of("ore_green_garnet_small");
    public static final ResourceKey<Feature> ORE_GREEN_GARNET_MEDIUM = ModFeatures.of("ore_green_garnet_medium");
    public static final ResourceKey<Feature> ORE_GREEN_GARNET_LARGE = ModFeatures.of("ore_green_garnet_large");
    public static final ResourceKey<Feature> ORE_KYAWTHUITE = ModFeatures.of("ore_kyawthuite");
    public static final ResourceKey<Feature> ORE_TOPAZ_SMALL = ModFeatures.of("ore_topaz_small");
    public static final ResourceKey<Feature> ORE_TOPAZ_MEDIUM = ModFeatures.of("ore_topaz_medium");
    public static final ResourceKey<Feature> ORE_TOPAZ_LARGE = ModFeatures.of("ore_topaz_large");
    public static final ResourceKey<Feature> ORE_WHITE_TOPAZ_SMALL = ModFeatures.of("ore_white_topaz_small");
    public static final ResourceKey<Feature> ORE_WHITE_TOPAZ_MEDIUM = ModFeatures.of("ore_white_topaz_medium");
    public static final ResourceKey<Feature> ORE_WHITE_TOPAZ_LARGE = ModFeatures.of("ore_white_topaz_large");
    public static final ResourceKey<Feature> ORE_PERIDOT_SMALL = ModFeatures.of("ore_peridot_small");
    public static final ResourceKey<Feature> ORE_PERIDOT_MEDIUM = ModFeatures.of("ore_peridot_medium");
    public static final ResourceKey<Feature> ORE_PERIDOT_LARGE = ModFeatures.of("ore_peridot_large");
    public static final ResourceKey<Feature> ORE_JADE_SMALL = ModFeatures.of("ore_jade_small");
    public static final ResourceKey<Feature> ORE_JADE_MEDIUM = ModFeatures.of("ore_jade_medium");
    public static final ResourceKey<Feature> ORE_JADE_LARGE = ModFeatures.of("ore_jade_large");
    public static final ResourceKey<Feature> ORE_PYROPE_SMALL = ModFeatures.of("ore_pyrope_small");
    public static final ResourceKey<Feature> ORE_PYROPE_MEDIUM = ModFeatures.of("ore_pyrope_medium");
    public static final ResourceKey<Feature> ORE_PYROPE_LARGE = ModFeatures.of("ore_pyrope_large");
    public static final ResourceKey<Feature> ORE_ECLIPSE_GEM = ModFeatures.of("ore_eclipse_gem");

    public static void bootstrap(BootstrapContext<Feature> context) {
        RuleTest stoneOreReplaceables = RuleTest.either(
                new TagMatchTest(BlockTags.HEIGHT_SPECIFIC_ORE_REPLACEABLES), HeightMatchTest.min(0), new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES)
        );
        RuleTest deepslateOreReplaceables = RuleTest.either(
                new TagMatchTest(BlockTags.HEIGHT_SPECIFIC_ORE_REPLACEABLES), HeightMatchTest.max(8), new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
        );
        List<BlockReplacement> rubyList = List.of(
                BlockReplacement.replace(stoneOreReplaceables, ModBlocks.RUBY_ORE.defaultBlockState()),
                BlockReplacement.replace(deepslateOreReplaceables, ModBlocks.DEEPSLATE_RUBY_ORE.defaultBlockState())
        );
        List<BlockReplacement> sapphireList = List.of(
                BlockReplacement.replace(stoneOreReplaceables, ModBlocks.SAPPHIRE_ORE.defaultBlockState()),
                BlockReplacement.replace(deepslateOreReplaceables, ModBlocks.DEEPSLATE_SAPPHIRE_ORE.defaultBlockState())
        );
        List<BlockReplacement> greenSapphireList = List.of(
                BlockReplacement.replace(stoneOreReplaceables, ModBlocks.GREEN_SAPPHIRE_ORE.defaultBlockState()),
                BlockReplacement.replace(deepslateOreReplaceables, ModBlocks.DEEPSLATE_GREEN_SAPPHIRE_ORE.defaultBlockState())
        );
        List<BlockReplacement> blueGarnetList = List.of(
                BlockReplacement.replace(stoneOreReplaceables, ModBlocks.BLUE_GARNET_ORE.defaultBlockState()),
                BlockReplacement.replace(deepslateOreReplaceables, ModBlocks.DEEPSLATE_BLUE_GARNET_ORE.defaultBlockState())
        );
        List<BlockReplacement> pinkGarnetList = List.of(
                BlockReplacement.replace(stoneOreReplaceables, ModBlocks.PINK_GARNET_ORE.defaultBlockState()),
                BlockReplacement.replace(deepslateOreReplaceables, ModBlocks.DEEPSLATE_PINK_GARNET_ORE.defaultBlockState())
        );
        List<BlockReplacement> greenGarnetList = List.of(
                BlockReplacement.replace(stoneOreReplaceables, ModBlocks.GREEN_GARNET_ORE.defaultBlockState()),
                BlockReplacement.replace(deepslateOreReplaceables, ModBlocks.DEEPSLATE_GREEN_GARNET_ORE.defaultBlockState())
        );
        List<BlockReplacement> kyawthuiteList = List.of(
                BlockReplacement.replace(stoneOreReplaceables, ModBlocks.KYAWTHUITE_ORE.defaultBlockState()),
                BlockReplacement.replace(deepslateOreReplaceables, ModBlocks.DEEPSLATE_KYAWTHUITE_ORE.defaultBlockState())
        );
        List<BlockReplacement> topazList = List.of(
                BlockReplacement.replace(stoneOreReplaceables, ModBlocks.TOPAZ_ORE.defaultBlockState()),
                BlockReplacement.replace(deepslateOreReplaceables, ModBlocks.DEEPSLATE_TOPAZ_ORE.defaultBlockState())
        );
        List<BlockReplacement> whiteTopazList = List.of(
                BlockReplacement.replace(stoneOreReplaceables, ModBlocks.WHITE_TOPAZ_ORE.defaultBlockState()),
                BlockReplacement.replace(deepslateOreReplaceables, ModBlocks.DEEPSLATE_WHITE_TOPAZ_ORE.defaultBlockState())
        );
        List<BlockReplacement> peridotList = List.of(
                BlockReplacement.replace(stoneOreReplaceables, ModBlocks.PERIDOT_ORE.defaultBlockState()),
                BlockReplacement.replace(deepslateOreReplaceables, ModBlocks.DEEPSLATE_PERIDOT_ORE.defaultBlockState())
        );
        List<BlockReplacement> jadeList = List.of(
                BlockReplacement.replace(stoneOreReplaceables, ModBlocks.JADE_ORE.defaultBlockState()),
                BlockReplacement.replace(deepslateOreReplaceables, ModBlocks.DEEPSLATE_JADE_ORE.defaultBlockState())
        );
        List<BlockReplacement> pyropeList = List.of(
                BlockReplacement.replace(stoneOreReplaceables, ModBlocks.PYROPE_ORE.defaultBlockState()),
                BlockReplacement.replace(deepslateOreReplaceables, ModBlocks.DEEPSLATE_PYROPE_ORE.defaultBlockState())
        );

        List<BlockReplacement> eclipseGemList = List.of(
                BlockReplacement.replace(stoneOreReplaceables, ModBlocks.ECLIPSE_GEM_ORE.defaultBlockState())
        );
        
        context.register(ORE_RUBY_SMALL, new OreFeature(rubyList, 1, 0.699F));
        context.register(ORE_RUBY_LARGE, new OreFeature(rubyList, 3, 0.69F));
        context.register(ORE_RUBY_MEDIUM, new OreFeature(rubyList, 3, 0.61F));
        context.register(ORE_SAPPHIRE_SMALL, new OreFeature(sapphireList, 1, 0.696F));
        context.register(ORE_SAPPHIRE_LARGE, new OreFeature(sapphireList, 4, 0.691F));
        context.register(ORE_SAPPHIRE_MEDIUM, new OreFeature(sapphireList, 4, 0.51F));
        context.register(ORE_GREEN_SAPPHIRE_SMALL, new OreFeature(greenSapphireList, 2, 0.616F));
        context.register(ORE_GREEN_SAPPHIRE_LARGE, new OreFeature(greenSapphireList, 4, 0.696F));
        context.register(ORE_GREEN_SAPPHIRE_MEDIUM, new OreFeature(greenSapphireList, 3, 0.57F));
        context.register(ORE_BLUE_GARNET_SMALL, new OreFeature(blueGarnetList, 3, 0.69F));
        context.register(ORE_BLUE_GARNET_LARGE, new OreFeature(blueGarnetList, 4, 0.699F));
        context.register(ORE_BLUE_GARNET_MEDIUM, new OreFeature(blueGarnetList, 3, 0.51F));
        context.register(ORE_PINK_GARNET_SMALL, new OreFeature(pinkGarnetList, 2, 0.52F));
        context.register(ORE_PINK_GARNET_LARGE, new OreFeature(pinkGarnetList, 4, 0.616F));
        context.register(ORE_PINK_GARNET_MEDIUM, new OreFeature(pinkGarnetList, 3, 0.52F));
        context.register(ORE_GREEN_GARNET_SMALL, new OreFeature(greenGarnetList, 2, 0.694F));
        context.register(ORE_GREEN_GARNET_LARGE, new OreFeature(greenGarnetList, 3, 0.695F));
        context.register(ORE_GREEN_GARNET_MEDIUM, new OreFeature(greenGarnetList, 4, 0.5F));
        context.register(ORE_KYAWTHUITE, new OreFeature(kyawthuiteList, 2, 0.0F));
        context.register(ORE_TOPAZ_SMALL, new OreFeature(topazList, 2, 0.69F));
        context.register(ORE_TOPAZ_LARGE, new OreFeature(topazList, 3, 0.695F));
        context.register(ORE_TOPAZ_MEDIUM, new OreFeature(topazList, 4, 0.5F));
        context.register(ORE_WHITE_TOPAZ_SMALL, new OreFeature(whiteTopazList, 2, 0.618F));
        context.register(ORE_WHITE_TOPAZ_LARGE, new OreFeature(whiteTopazList, 4, 0.697F));
        context.register(ORE_WHITE_TOPAZ_MEDIUM, new OreFeature(whiteTopazList, 3, 0.618F));
        context.register(ORE_PERIDOT_SMALL, new OreFeature(peridotList, 3, 0.57F));
        context.register(ORE_PERIDOT_LARGE, new OreFeature(peridotList, 3, 0.699F));
        context.register(ORE_PERIDOT_MEDIUM, new OreFeature(peridotList, 2, 0.697F));
        context.register(ORE_JADE_SMALL, new OreFeature(jadeList, 2, 0.5F));
        context.register(ORE_JADE_LARGE, new OreFeature(jadeList, 4, 0.699F));
        context.register(ORE_JADE_MEDIUM, new OreFeature(jadeList, 2, 0.615F));
        context.register(ORE_PYROPE_SMALL, new OreFeature(pyropeList, 2, 0.55F));
        context.register(ORE_PYROPE_LARGE, new OreFeature(pyropeList, 4, 0.697F));
        context.register(ORE_PYROPE_MEDIUM, new OreFeature(pyropeList, 3, 0.615F));
        context.register(ORE_ECLIPSE_GEM, new OreFeature(eclipseGemList, 3, 0.2F));
    }
}