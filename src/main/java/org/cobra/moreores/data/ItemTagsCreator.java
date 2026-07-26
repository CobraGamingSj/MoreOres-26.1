package org.cobra.moreores.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.references.ItemIds;
import org.cobra.moreores.core.registry.ResourceHelper;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagsCreator extends FabricTagsProvider.ItemTagsProvider {
    public ItemTagsCreator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }
    
    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {

        tag(ModItemTags.HAS_ENERGY)
                .add(ResourceHelper.obtainKey(ModItems.ENERGY_INGOT))
                .add(ResourceHelper.obtainKey(ModBlocks.ENERGY_BLOCK.asItem()));
        
        tag(ModItemTags.CRYSTALLIZED)
                .add(ResourceHelper.obtainKey(ModItems.CRIMSON_GARNET))
                .add(ResourceHelper.obtainKey(ModItems.CRYSTALLITE))
                .add(ResourceHelper.obtainKey(ModItems.ALEXANDRITE))
                .add(ResourceHelper.obtainKey(ModItems.ORANGE_ZIRCON))
                .add(ResourceHelper.obtainKey(ModItems.OPAL))
                .add(ResourceHelper.obtainKey(ModItems.QUARTSIDIAN))
                .add(ResourceHelper.obtainKey(ModItems.KASHMIR_SAPPHIRE))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_AMETHYST))
                .add(ResourceHelper.obtainKey(ModItems.LIMESTONE))
                .add(ResourceHelper.obtainKey(ModItems.MOONSTONE))
                .add(ResourceHelper.obtainKey(ModItems.RED_BERYL))
                .add(ResourceHelper.obtainKey(ModItems.GRANDIDIERITE))
                .add(ResourceHelper.obtainKey(ModBlocks.CRIMSON_GARNET_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.ALEXANDRITE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.CRYSTALLITE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.MOONSTONE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.LIMESTONE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.QUARTSIDIAN_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RED_BERYL_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.ORANGE_ZIRCON_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.OPAL_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.KASHMIR_SAPPHIRE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RADIANT_AMETHYST_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.GRANDIDIERITE_BLOCK.asItem()));
        
        tag(ModItemTags.CRYSTALLIZED_GEMSTONES)
                .add(ResourceHelper.obtainKey(ModItems.CRIMSON_GARNET))
                .add(ResourceHelper.obtainKey(ModItems.CRYSTALLITE))
                .add(ResourceHelper.obtainKey(ModItems.ALEXANDRITE))
                .add(ResourceHelper.obtainKey(ModItems.ORANGE_ZIRCON))
                .add(ResourceHelper.obtainKey(ModItems.OPAL))
                .add(ResourceHelper.obtainKey(ModItems.QUARTSIDIAN))
                .add(ResourceHelper.obtainKey(ModItems.KASHMIR_SAPPHIRE))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_AMETHYST))
                .add(ResourceHelper.obtainKey(ModItems.LIMESTONE))
                .add(ResourceHelper.obtainKey(ModItems.MOONSTONE))
                .add(ResourceHelper.obtainKey(ModItems.RED_BERYL))
                .add(ResourceHelper.obtainKey(ModItems.GRANDIDIERITE))
                .add(ResourceHelper.obtainKey(ModItems.CRYSTAL_OF_ECLIPSE));
        
        tag(ModItemTags.CRYSTALLIZED_GEMSTONE_BLOCKS)
                .add(ResourceHelper.obtainKey(ModBlocks.CRIMSON_GARNET_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.ALEXANDRITE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.CRYSTALLITE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.MOONSTONE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.LIMESTONE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.QUARTSIDIAN_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RED_BERYL_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.ORANGE_ZIRCON_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.OPAL_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.KASHMIR_SAPPHIRE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RADIANT_AMETHYST_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.GRANDIDIERITE_BLOCK.asItem()));
        
        tag(ItemTags.SPEARS)
                .add(ResourceHelper.obtainKey(ModItems.RUBY_SPEAR))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_SPEAR));

        tag(ItemTags.TRIM_MATERIALS)
                .add(ResourceHelper.obtainKey(ModItems.RUBY))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE))
                .add(ResourceHelper.obtainKey(ModItems.GREEN_SAPPHIRE))
                .add(ResourceHelper.obtainKey(ModItems.BLUE_GARNET))
                .add(ResourceHelper.obtainKey(ModItems.PINK_GARNET))
                .add(ResourceHelper.obtainKey(ModItems.GREEN_GARNET))
                .add(ResourceHelper.obtainKey(ModItems.KYAWTHUITE))
                .add(ResourceHelper.obtainKey(ModItems.TOPAZ))
                .add(ResourceHelper.obtainKey(ModItems.WHITE_TOPAZ))
                .add(ResourceHelper.obtainKey(ModItems.PERIDOT))
                .add(ResourceHelper.obtainKey(ModItems.JADE))
                .add(ResourceHelper.obtainKey(ModItems.PYROPE))
                .add(ResourceHelper.obtainKey(ModItems.CRIMSON_GARNET))
                .add(ResourceHelper.obtainKey(ModItems.CRYSTALLITE))
                .add(ResourceHelper.obtainKey(ModItems.ALEXANDRITE))
                .add(ResourceHelper.obtainKey(ModItems.ORANGE_ZIRCON))
                .add(ResourceHelper.obtainKey(ModItems.OPAL))
                .add(ResourceHelper.obtainKey(ModItems.QUARTSIDIAN))
                .add(ResourceHelper.obtainKey(ModItems.KASHMIR_SAPPHIRE))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_AMETHYST))
                .add(ResourceHelper.obtainKey(ModItems.LIMESTONE))
                .add(ResourceHelper.obtainKey(ModItems.MOONSTONE))
                .add(ResourceHelper.obtainKey(ModItems.RED_BERYL))
                .add(ResourceHelper.obtainKey(ModItems.GRANDIDIERITE));

        tag(ItemTags.SWORDS)
                .add(ResourceHelper.obtainKey(ModItems.RUBY_SWORD))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_SWORD))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_SWORD));

        tag(ItemTags.PICKAXES)
                .add(ResourceHelper.obtainKey(ModItems.RUBY_PICKAXE))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_PICKAXE))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_PICKAXE));

        tag(ItemTags.AXES)
                .add(ResourceHelper.obtainKey(ModItems.RUBY_AXE))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_AXE))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_AXE));

        tag(ItemTags.SHOVELS)
                .add(ResourceHelper.obtainKey(ModItems.RUBY_SHOVEL))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_SHOVEL))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_SHOVEL));

        tag(ItemTags.HOES)
                .add(ResourceHelper.obtainKey(ModItems.RUBY_HOE))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_HOE))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_HOE));

        tag(ItemTags.FOOT_ARMOR)
                .add(ResourceHelper.obtainKey(ModItems.RUBY_BOOTS))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_BOOTS))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_BOOTS));

        tag(ItemTags.LEG_ARMOR)
                .add(ResourceHelper.obtainKey(ModItems.RUBY_LEGGINGS))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_LEGGINGS))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_LEGGINGS));

        tag(ItemTags.HEAD_ARMOR)
                .add(ResourceHelper.obtainKey(ModItems.RUBY_HELMET))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_HELMET))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_HELMET));

        tag(ItemTags.CHEST_ARMOR)
                .add(ResourceHelper.obtainKey(ModItems.RUBY_CHESTPLATE))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_CHESTPLATE))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_CHESTPLATE));

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ResourceHelper.obtainKey(ModItems.RUBY_HELMET))
                .add(ResourceHelper.obtainKey(ModItems.RUBY_CHESTPLATE))
                .add(ResourceHelper.obtainKey(ModItems.RUBY_LEGGINGS))
                .add(ResourceHelper.obtainKey(ModItems.RUBY_BOOTS))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_HELMET))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_CHESTPLATE))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_LEGGINGS))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE_BOOTS))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_HELMET))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_CHESTPLATE))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_LEGGINGS))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_BOOTS));

        tag(ModItemTags.GEMSTONE)
                .add(ResourceHelper.obtainKey(ModItems.RUBY))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT))
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE))
                .add(ResourceHelper.obtainKey(ModItems.GREEN_SAPPHIRE))
                .add(ResourceHelper.obtainKey(ModItems.BLUE_GARNET))
                .add(ResourceHelper.obtainKey(ModItems.PINK_GARNET))
                .add(ResourceHelper.obtainKey(ModItems.GREEN_GARNET))
                .add(ResourceHelper.obtainKey(ModItems.KYAWTHUITE))
                .add(ResourceHelper.obtainKey(ModItems.TOPAZ))
                .add(ResourceHelper.obtainKey(ModItems.WHITE_TOPAZ))
                .add(ResourceHelper.obtainKey(ModItems.PERIDOT))
                .add(ResourceHelper.obtainKey(ModItems.JADE))
                .add(ResourceHelper.obtainKey(ModItems.PYROPE))
                .add(ItemIds.LAPIS_LAZULI)
                .add(ItemIds.QUARTZ)
                .add(ItemIds.DIAMOND)
                .add(ResourceHelper.obtainKey(ModItems.CRIMSON_GARNET))
                .add(ResourceHelper.obtainKey(ModItems.CRYSTALLITE))
                .add(ResourceHelper.obtainKey(ModItems.ALEXANDRITE))
                .add(ResourceHelper.obtainKey(ModItems.ORANGE_ZIRCON))
                .add(ResourceHelper.obtainKey(ModItems.OPAL))
                .add(ResourceHelper.obtainKey(ModItems.QUARTSIDIAN))
                .add(ResourceHelper.obtainKey(ModItems.KASHMIR_SAPPHIRE))
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_AMETHYST))
                .add(ResourceHelper.obtainKey(ModItems.LIMESTONE))
                .add(ResourceHelper.obtainKey(ModItems.MOONSTONE))
                .add(ResourceHelper.obtainKey(ModItems.RED_BERYL))
                .add(ResourceHelper.obtainKey(ModItems.GRANDIDIERITE))
                .add(ResourceHelper.obtainKey(ModItems.CRYSTAL_OF_ECLIPSE));

        tag(ModItemTags.GEMSTONE_BLOCKS)
                .add(ResourceHelper.obtainKey(ModBlocks.RUBY_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.SAPPHIRE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.GREEN_SAPPHIRE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.BLUE_GARNET_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.PINK_GARNET_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.GREEN_GARNET_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.KYAWTHUITE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.TOPAZ_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.WHITE_TOPAZ_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.PERIDOT_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.JADE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.PYROPE_BLOCK.asItem()));

        tag(ModItemTags.RAW_GEMSTONE)
                .add(ResourceHelper.obtainKey(ModItems.RAW_RUBY))
                .add(ResourceHelper.obtainKey(ModItems.RAW_SAPPHIRE))
                .add(ResourceHelper.obtainKey(ModItems.RAW_GREEN_SAPPHIRE))
                .add(ResourceHelper.obtainKey(ModItems.RAW_BLUE_GARNET))
                .add(ResourceHelper.obtainKey(ModItems.RAW_PINK_GARNET))
                .add(ResourceHelper.obtainKey(ModItems.RAW_GREEN_GARNET))
                .add(ResourceHelper.obtainKey(ModItems.RAW_KYAWTHUITE))
                .add(ResourceHelper.obtainKey(ModItems.RAW_TOPAZ))
                .add(ResourceHelper.obtainKey(ModItems.RAW_WHITE_TOPAZ))
                .add(ResourceHelper.obtainKey(ModItems.RAW_PERIDOT))
                .add(ResourceHelper.obtainKey(ModItems.RAW_JADE))
                .add(ResourceHelper.obtainKey(ModItems.RAW_PYROPE));

        tag(ModItemTags.RAW_GEMSTONE_BLOCKS)
                .add(ResourceHelper.obtainKey(ModBlocks.RAW_RUBY_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RAW_SAPPHIRE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RAW_GREEN_SAPPHIRE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RAW_BLUE_GARNET_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RAW_PINK_GARNET_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RAW_GREEN_GARNET_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RAW_KYAWTHUITE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RAW_TOPAZ_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RAW_WHITE_TOPAZ_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RAW_PERIDOT_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RAW_JADE_BLOCK.asItem()))
                .add(ResourceHelper.obtainKey(ModBlocks.RAW_PYROPE_BLOCK.asItem()));

        tag(ItemTags.ARROWS)
                .add(ResourceHelper.obtainKey(ModItems.GEM_ARROW));
        
        tag(ItemTags.BOW_ENCHANTABLE)
                .add(ResourceHelper.obtainKey(ModItems.RADIANT_BOW));

        tag(ModItemTags.REPAIRS_RUBY_ARMOR)
                .add(ResourceHelper.obtainKey(ModItems.RUBY));

        tag(ModItemTags.REPAIRS_SAPPHIRE_ARMOR)
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE));

        tag(ModItemTags.REPAIRS_RADIANT_ARMOR)
                .add(ResourceHelper.obtainKey(ModItems.RADIANT));

        tag(ModItemTags.RUBY_TOOL_MATERIALS)
                .add(ResourceHelper.obtainKey(ModItems.RUBY));

        tag(ModItemTags.SAPPHIRE_TOOL_MATERIALS)
                .add(ResourceHelper.obtainKey(ModItems.SAPPHIRE));

        tag(ModItemTags.RADIANT_TOOL_MATERIALS)
                .add(ResourceHelper.obtainKey(ModItems.RADIANT));
    }
}