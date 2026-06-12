package org.cobra.moreores.world.block;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.sound.ModBlockSoundGroup;

import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import static org.cobra.moreores.MoreOresModInitializer.id;

public class ModBlocks {

    public static final Block ENERGY_BLOCK = register("energy_block", new EnergyBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "energy_block"))).mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(256.0f, 512.0f).strength(512.0f).sound(ModBlockSoundGroup.ENERGY_BLOCK).lightLevel((state) -> {
        return 30;
    })));
    public static final Block RUBY_LAMP = register("ruby_lamp", new RubyLampBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "ruby_lamp"))).destroyTime(0.1f).sound(SoundType.GLASS).lightLevel(state -> state.getValue(RubyLampBlock.LIT) ? 15:0)));

    public static final Block RUBY_BLOCK = register("ruby_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "ruby_block"))).mapColor(MapColor.NETHER).requiresCorrectToolForDrops().strength(5.0f, 5.0f).strength(5.0f)));

    public static final Block RADIANT_BLOCK = registerSolidBlock(
            "radiant_block", s -> new Block(
            s.requiresCorrectToolForDrops()), 5f, 5f);

    public static final Block SAPPHIRE_BLOCK = register("sapphire_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "sapphire_block"))).mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(4.0f, 4.0f).strength(4.0f)));
    public static final Block GREEN_SAPPHIRE_BLOCK = register("green_sapphire_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "green_sapphire_block"))).mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(4.0f, 4.0f).strength(4.0f)));
    public static final Block BLUE_GARNET_BLOCK = register("blue_garnet_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "blue_garnet_block"))).mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(6.0f, 6.5f).strength(7.0f).sound(SoundType.AMETHYST_CLUSTER)));
    public static final Block PINK_GARNET_BLOCK = register("pink_garnet_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "pink_garnet_block"))).mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(6.0f, 6.5f).strength(7.0f).sound(SoundType.AMETHYST_CLUSTER)));
    public static final Block GREEN_GARNET_BLOCK = register("green_garnet_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "green_garnet_block"))).mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(6.0f, 6.5f).strength(7.0f).sound(SoundType.AMETHYST_CLUSTER)));
    public static final Block KYAWTHUITE_BLOCK = registerSolidBlock("kyawthuite_block", s -> new Block(s.requiresCorrectToolForDrops().mapColor(MapColor.COLOR_ORANGE)), 5.5f, 6f);
    public static final Block TOPAZ_BLOCK = register("topaz_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "topaz_block"))).mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(8.0f, 8.0f).strength(9.0f)));
    public static final Block WHITE_TOPAZ_BLOCK = register("white_topaz_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "white_topaz_block"))).requiresCorrectToolForDrops().strength(6.0f, 6.5f).strength(7.0f)));
    public static final Block PERIDOT_BLOCK = register("peridot_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "peridot_block"))).mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(8.0f, 8.0f).strength(9.0f).sound(SoundType.METAL)));
    public static final Block JADE_BLOCK = register("jade_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "jade_block"))).requiresCorrectToolForDrops().strength(6.0f, 6.5f).strength(7.0f)));
    public static final Block PYROPE_BLOCK = register("pyrope_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "pyrope_block"))).requiresCorrectToolForDrops().strength(6.0f, 6.5f).strength(7.0f)));

    public static final Block CRIMSON_GARNET_BLOCK = register("crimson_garnet_block", Block::new);
    public static final Block CRYSTALLITE_BLOCK = register("crystallite_block", Block::new);
    public static final Block RADIANT_AMETHYST_BLOCK = register("radiant_amethyst_block", Block::new);
    public static final Block MOONSTONE_BLOCK = register("moonstone_block", Block::new);
    public static final Block LIMESTONE_BLOCK = register("limestone_block", Block::new);
    public static final Block QUARTSIDIAN_BLOCK = register("quartsidian_block", Block::new);
    public static final Block ALEXANDRITE_BLOCK = register("alexandrite_block", Block::new);
    public static final Block ORANGE_ZIRCON_BLOCK = register("orange_zircon_block", Block::new);
    public static final Block OPAL_BLOCK = register("opal_block", Block::new);
    public static final Block GRANDIDIERITE_BLOCK = register("grandidierite_block", Block::new);
    public static final Block RED_BERYL_BLOCK = register("red_beryl_block", Block::new);
    public static final Block KASHMIR_SAPPHIRE_BLOCK = register("kashmir_sapphire_block", Block::new);

    public static final Block GEM_PURIFIER_BLOCK = register("gem_purifier_block", new GemPurifierBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "gem_purifier_block"))).strength(5f).strength(2.75f, 3f)
            .lightLevel(state -> state.getValue(GemPurifierBlock.REDSTONE_POWERED) ? 5 : 0).requiresCorrectToolForDrops().noOcclusion().sound(SoundType.HEAVY_CORE)));
    public static final Block GEM_CRYSTALLIZER_BLOCK = register("gem_crystallizer_block", new GemCrystallizerBlock(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "gem_crystallizer_block"))).strength(5f).strength(2.75f, 3f)
            .lightLevel(state -> state.getValue(GemCrystallizerBlock.REDSTONE_POWERED) ? 5 : 0).requiresCorrectToolForDrops().noOcclusion().sound(SoundType.HEAVY_CORE)));

    public static final Block RAW_RUBY_BLOCK = register("raw_ruby_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "raw_ruby_block"))).mapColor(MapColor.NETHER).requiresCorrectToolForDrops().strength(6.0f, 6.0f).strength(6.0f)));
    public static final Block RAW_SAPPHIRE_BLOCK = register("raw_sapphire_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "raw_sapphire_block"))).mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(5.0f, 5.0f).strength(5.0f)));
    public static final Block RAW_GREEN_SAPPHIRE_BLOCK = register("raw_green_sapphire_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "raw_green_sapphire_block"))).mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(5.0f, 5.0f).strength(5.0f)));
    public static final Block RAW_BLUE_GARNET_BLOCK = register("raw_blue_garnet_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "raw_blue_garnet_block"))).mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(7.0f, 7.5f).strength(8.0f).sound(SoundType.AMETHYST_CLUSTER)));
    public static final Block RAW_PINK_GARNET_BLOCK = register("raw_pink_garnet_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "raw_pink_garnet_block"))).mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(7.0f, 7.5f).strength(8.0f).sound(SoundType.AMETHYST_CLUSTER)));
    public static final Block RAW_GREEN_GARNET_BLOCK = register("raw_green_garnet_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "raw_green_garnet_block"))).mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(7.0f, 7.5f).strength(8.0f).sound(SoundType.AMETHYST_CLUSTER)));
    public static final Block RAW_KYAWTHUITE_BLOCK = register("raw_kyawthuite_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "raw_kyawthuite_block"))).mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(9.5f, 10.0f).strength(10.0f)));
    public static final Block RAW_TOPAZ_BLOCK = register("raw_topaz_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "raw_topaz_block"))).mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(9.0f, 9.0f).strength(10.0f)));
    public static final Block RAW_WHITE_TOPAZ_BLOCK = register("raw_white_topaz_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "raw_white_topaz_block"))).requiresCorrectToolForDrops().strength(7.0f, 7.5f).strength(8.0f)));
    public static final Block RAW_PERIDOT_BLOCK = register("raw_peridot_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "raw_peridot_block"))).mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(9.0f, 9.0f).strength(10.0f).sound(SoundType.METAL)));
    public static final Block RAW_JADE_BLOCK = register("raw_jade_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "raw_jade_block"))).requiresCorrectToolForDrops().strength(7.0f, 7.5f).strength(8.0f)));
    public static final Block RAW_PYROPE_BLOCK = register("raw_pyrope_block", new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "raw_pyrope_block"))).requiresCorrectToolForDrops().strength(7.0f, 7.5f).strength(8.0f)));

    public static final Block RUBY_ORE = register("ruby_ore", new DropExperienceBlock(UniformInt.of(2, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "ruby_ore"))).requiresCorrectToolForDrops().strength(6.0f, 6.0f).strength(6.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block DEEPSLATE_RUBY_ORE = register("deepslate_ruby_ore", new DropExperienceBlock(UniformInt.of(2, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "deepslate_ruby_ore"))).requiresCorrectToolForDrops().strength(6.5f, 6.5f).strength(6.5f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block SAPPHIRE_ORE = register("sapphire_ore", new DropExperienceBlock(UniformInt.of(2, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "sapphire_ore"))).requiresCorrectToolForDrops().strength(5.0f, 5.0f).strength(5.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block DEEPSLATE_SAPPHIRE_ORE = register("deepslate_sapphire_ore", new DropExperienceBlock(UniformInt.of(2, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "deepslate_sapphire_ore"))).requiresCorrectToolForDrops().strength(5.5f, 5.5f).strength(5.5f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block GREEN_SAPPHIRE_ORE = register("green_sapphire_ore", new DropExperienceBlock(UniformInt.of(2, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "green_sapphire_ore"))).requiresCorrectToolForDrops().strength(5.0f, 5.0f).strength(5.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block DEEPSLATE_GREEN_SAPPHIRE_ORE = register("deepslate_green_sapphire_ore", new DropExperienceBlock(UniformInt.of(2, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "deepslate_green_sapphire_ore"))).requiresCorrectToolForDrops().strength(5.5f, 5.5f).strength(5.5f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block BLUE_GARNET_ORE = register("blue_garnet_ore", new DropExperienceBlock(UniformInt.of(2, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "blue_garnet_ore"))).requiresCorrectToolForDrops().strength(7.0f, 7.5f).strength(8.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block DEEPSLATE_BLUE_GARNET_ORE = register("deepslate_blue_garnet_ore", new DropExperienceBlock(UniformInt.of(2, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "deepslate_blue_garnet_ore"))).requiresCorrectToolForDrops().strength(7.5f, 8.0f).strength(8.5f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block PINK_GARNET_ORE = register("pink_garnet_ore", new DropExperienceBlock(UniformInt.of(2, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "pink_garnet_ore"))).requiresCorrectToolForDrops().strength(7.0f, 7.5f).strength(8.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block DEEPSLATE_PINK_GARNET_ORE = register("deepslate_pink_garnet_ore", new DropExperienceBlock(UniformInt.of(2, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "deepslate_pink_garnet_ore"))).requiresCorrectToolForDrops().strength(7.5f, 8.0f).strength(8.5f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block GREEN_GARNET_ORE = register("green_garnet_ore", new DropExperienceBlock(UniformInt.of(2, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "green_garnet_ore"))).requiresCorrectToolForDrops().strength(7.0f, 7.5f).strength(8.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block DEEPSLATE_GREEN_GARNET_ORE = register("deepslate_green_garnet_ore", new DropExperienceBlock(UniformInt.of(2, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "deepslate_green_garnet_ore"))).requiresCorrectToolForDrops().strength(7.5f, 8.0f).strength(8.5f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block KYAWTHUITE_ORE = registerSolidBlock("kyawthuite_ore", s -> new DropExperienceBlock(UniformInt.of(1, 2), s.requiresCorrectToolForDrops().mapColor(MapColor.COLOR_ORANGE)), 7.5f, 8f);
    public static final Block DEEPSLATE_KYAWTHUITE_ORE = registerSolidBlock("deepslate_kyawthuite_ore", s -> new DropExperienceBlock(UniformInt.of(1, 2), s.requiresCorrectToolForDrops().mapColor(MapColor.COLOR_ORANGE)), 8f, 8.5f);
    public static final Block TOPAZ_ORE = register("topaz_ore", new DropExperienceBlock(UniformInt.of(1, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "topaz_ore"))).requiresCorrectToolForDrops().strength(9.0f, 9.0f).strength(10.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block DEEPSLATE_TOPAZ_ORE = register("deepslate_topaz_ore", new DropExperienceBlock(UniformInt.of(1, 3), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "deepslate_topaz_ore"))).requiresCorrectToolForDrops().strength(9.5f, 9.5f).strength(10.5f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block WHITE_TOPAZ_ORE = register("white_topaz_ore", new DropExperienceBlock(UniformInt.of(3, 5), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "white_topaz_ore"))).requiresCorrectToolForDrops().strength(9.5f, 9.5f).strength(10.5f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block DEEPSLATE_WHITE_TOPAZ_ORE = register("deepslate_white_topaz_ore", new DropExperienceBlock(UniformInt.of(3, 5), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "deepslate_white_topaz_ore"))).requiresCorrectToolForDrops().strength(10.0f, 10.0f).strength(11.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block PERIDOT_ORE = register("peridot_ore", new DropExperienceBlock(UniformInt.of(1, 2), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "peridot_ore"))).requiresCorrectToolForDrops().strength(9.0f, 9.0f).strength(10.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block DEEPSLATE_PERIDOT_ORE = register("deepslate_peridot_ore", new DropExperienceBlock(UniformInt.of(1, 2), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "deepslate_peridot_ore"))).requiresCorrectToolForDrops().strength(9.5f, 9.5f).strength(10.5f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block JADE_ORE = register("jade_ore", new DropExperienceBlock(UniformInt.of(3, 5), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "jade_ore"))).requiresCorrectToolForDrops().strength(10.0f, 10.0f).strength(11.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block DEEPSLATE_JADE_ORE = register("deepslate_jade_ore", new DropExperienceBlock(UniformInt.of(3, 5), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "deepslate_jade_ore"))).requiresCorrectToolForDrops().strength(10.5f, 10.5f).strength(11.5f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block PYROPE_ORE = register("pyrope_ore", new DropExperienceBlock(UniformInt.of(3, 5), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "pyrope_ore"))).requiresCorrectToolForDrops().strength(9.5f, 9.5f).strength(10.5f).sound(SoundType.STONE).mapColor(MapColor.STONE)));
    public static final Block DEEPSLATE_PYROPE_ORE = register("deepslate_pyrope_ore", new DropExperienceBlock(UniformInt.of(3, 5), BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "deepslate_pyrope_ore"))).requiresCorrectToolForDrops().strength(10.0f, 10.0f).strength(11.0f).sound(SoundType.STONE).mapColor(MapColor.STONE)));

    public static final Block ECLIPSE_GEM_ORE = registerSolidBlock("eclipse_gem_ore", s -> new Block(s.mapColor(MapColor.STONE).sound(SoundType.STONE)), 16f, 16f);
    
    public static Block register(String id, Block block) {
        registerBlockItem(id, block);
        return Registry.register(BuiltInRegistries.BLOCK, id(id), block);
    }

    public static Block register(String id, Function<BlockBehaviour.Properties, Block> blockFactory) {
       return registerSolidBlock(id, blockFactory, 7f, 7f);
    }

    public static Block registerSolidBlock(String id, Function<BlockBehaviour.Properties, Block> blockFunction, float strength, float resistance) {
        BlockBehaviour.Properties settings = BlockBehaviour.Properties.of().requiresCorrectToolForDrops().setId(MoreOresModInitializer.blockKey(id)).strength(strength, resistance);
        return register(id, blockFunction.apply(settings));
    }

    public static void registerBlockItem(String id, Block block) {
        ModItems.register(id, settings -> new BlockItem(block, settings.useBlockDescriptionPrefix()));
    }

    public static void register() {
        MoreOresModInitializer.LOGGER.info("Loading ModBlocks for {} mod.", MoreOresModInitializer.MOD_ID);
        int blockCount = 0;
        for(Block block : BuiltInRegistries.BLOCK) {
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);
            if(id.getNamespace().equals(MoreOresModInitializer.MOD_ID)) {
                String name = MoreOresModInitializer.formatName((id.getPath()));
                blockCount++;
                MoreOresModInitializer.LOGGER.info("Registering Block: {}, for {} mod", name, MoreOresModInitializer.MOD_ID);
            }
        }
        MoreOresModInitializer.LOGGER.info("Registered {} Blocks for {} mod", blockCount, MoreOresModInitializer.MOD_ID);
    }
}