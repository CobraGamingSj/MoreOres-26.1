package org.cobra.moreores.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.cobra.moreores.MoreOresModInitializer;

public class ModBlockTags {

    public static final TagKey<Block> NEEDS_RUBY_TOOL = of("needs_ruby_tool");
    public static final TagKey<Block> INCORRECT_FOR_RUBY_TOOL = of("incorrect_for_ruby_tool");
    public static final TagKey<Block> NEEDS_SAPPHIRE_TOOL = of("needs_sapphire_tool");
    public static final TagKey<Block> INCORRECT_FOR_SAPPHIRE_TOOL = of("incorrect_for_sapphire_tool");
    public static final TagKey<Block> NEEDS_RADIANT_TOOL = of("needs_radiant_tool");
    public static final TagKey<Block> INCORRECT_FOR_RADIANT_TOOL = of("incorrect_for_radiant_tool");
    public static final TagKey<Block> MOD_ORES = of("mod_ores");
    public static final TagKey<Block> RUBY_ORES = of("ruby_ores");
    public static final TagKey<Block> SAPPHIRE_ORES = of("sapphire_ores");
    public static final TagKey<Block> GREEN_SAPPHIRE_ORES = of("green_sapphire_ores");
    public static final TagKey<Block> BLUE_GARNET_ORES = of("blue_garnet_ores");
    public static final TagKey<Block> PINK_GARNET_ORES = of("pink_garnet_ores");
    public static final TagKey<Block> GREEN_GARNET_ORES = of("green_garnet_ores");
    public static final TagKey<Block> KYAWTHUITE_ORES = of("kyawthuite_ores");
    public static final TagKey<Block> TOPAZ_ORES = of("topaz_ores");
    public static final TagKey<Block> WHITE_TOPAZ_ORES = of("white_topaz_ores");
    public static final TagKey<Block> PERIDOT_ORES = of("peridot_ores");
    public static final TagKey<Block> JADE_ORES = of("jade_ores");
    public static final TagKey<Block> PYROPE_ORES = of("pyrope_ores");

    private static TagKey<Block> of(String id) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, id));
    }
}