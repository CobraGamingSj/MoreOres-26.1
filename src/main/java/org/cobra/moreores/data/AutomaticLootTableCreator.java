package org.cobra.moreores.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.block.ModBlocks;
import org.cobra.moreores.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AutomaticLootTableCreator extends FabricBlockLootSubProvider {
    private static final Map<Block, Item> ORE_DROPS = Map.ofEntries(
            Map.entry(ModBlocks.RUBY_ORE, ModItems.RAW_RUBY),
            Map.entry(ModBlocks.DEEPSLATE_RUBY_ORE, ModItems.RAW_RUBY),
            Map.entry(ModBlocks.SAPPHIRE_ORE, ModItems.RAW_SAPPHIRE),
            Map.entry(ModBlocks.DEEPSLATE_SAPPHIRE_ORE, ModItems.RAW_SAPPHIRE),
            Map.entry(ModBlocks.GREEN_SAPPHIRE_ORE, ModItems.RAW_GREEN_SAPPHIRE),
            Map.entry(ModBlocks.DEEPSLATE_GREEN_SAPPHIRE_ORE, ModItems.RAW_GREEN_SAPPHIRE),
            Map.entry(ModBlocks.BLUE_GARNET_ORE, ModItems.RAW_BLUE_GARNET),
            Map.entry(ModBlocks.DEEPSLATE_BLUE_GARNET_ORE, ModItems.RAW_BLUE_GARNET),
            Map.entry(ModBlocks.PINK_GARNET_ORE, ModItems.RAW_PINK_GARNET),
            Map.entry(ModBlocks.DEEPSLATE_PINK_GARNET_ORE, ModItems.RAW_PINK_GARNET),
            Map.entry(ModBlocks.GREEN_GARNET_ORE, ModItems.RAW_GREEN_GARNET),
            Map.entry(ModBlocks.DEEPSLATE_GREEN_GARNET_ORE, ModItems.RAW_GREEN_GARNET),
            Map.entry(ModBlocks.KYAWTHUITE_ORE, ModItems.RAW_KYAWTHUITE),
            Map.entry(ModBlocks.DEEPSLATE_KYAWTHUITE_ORE, ModItems.RAW_KYAWTHUITE),
            Map.entry(ModBlocks.TOPAZ_ORE, ModItems.RAW_TOPAZ),
            Map.entry(ModBlocks.DEEPSLATE_TOPAZ_ORE, ModItems.RAW_TOPAZ),
            Map.entry(ModBlocks.WHITE_TOPAZ_ORE, ModItems.RAW_WHITE_TOPAZ),
            Map.entry(ModBlocks.DEEPSLATE_WHITE_TOPAZ_ORE, ModItems.RAW_WHITE_TOPAZ),
            Map.entry(ModBlocks.PERIDOT_ORE, ModItems.RAW_PERIDOT),
            Map.entry(ModBlocks.DEEPSLATE_PERIDOT_ORE, ModItems.RAW_PERIDOT),
            Map.entry(ModBlocks.JADE_ORE, ModItems.RAW_JADE),
            Map.entry(ModBlocks.DEEPSLATE_JADE_ORE, ModItems.RAW_JADE),
            Map.entry(ModBlocks.PYROPE_ORE, ModItems.RAW_PYROPE),
            Map.entry(ModBlocks.DEEPSLATE_PYROPE_ORE, ModItems.RAW_PYROPE),
            Map.entry(ModBlocks.ECLIPSE_GEM_ORE, ModItems.ECLIPSE_GEM_CRYSTALS)
    );

    public AutomaticLootTableCreator(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {

        for (Block block : BuiltInRegistries.BLOCK) {
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);

            if(id.getNamespace().equals(MoreOresModInitializer.MOD_ID)) {
                if(ORE_DROPS.containsKey(block)) {
                    add(block, createOreDrop(block, ORE_DROPS.get(block)));
                    continue;
                }
                dropSelf(block);
            }
        }
    }
}