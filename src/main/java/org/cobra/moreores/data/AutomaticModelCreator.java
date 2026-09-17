package org.cobra.moreores.data;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.block.RubyLampBlock;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.world.item.RadiantBowItem;

import java.util.Map;

public class AutomaticModelCreator extends FabricModelProvider {
    public AutomaticModelCreator(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        for (Block block : BuiltInRegistries.BLOCK) {
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);

            if(id.getNamespace().equals(MoreOresModInitializer.MOD_ID)) {
                if(block == ModBlocks.RUBY_LAMP) {
                    Identifier lampOffIdentifier = TexturedModel.CUBE.create(ModBlocks.RUBY_LAMP, blockStateModelGenerator.modelOutput);
                    Identifier lampOnIdentifier = blockStateModelGenerator.createSuffixedVariant(ModBlocks.RUBY_LAMP, "_on", ModelTemplates.CUBE_ALL, TextureMapping::cube);
                    blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(ModBlocks.RUBY_LAMP)
                            .with(BlockModelGenerators.createBooleanModelDispatch(RubyLampBlock.LIT,
                                    new MultiVariant(WeightedList.<Variant>builder().add(new Variant(lampOnIdentifier)).build()),
                                    new MultiVariant(WeightedList.<Variant>builder().add(new Variant(lampOffIdentifier)).build()))));
                    continue;
                } else if (block == ModBlocks.GEM_CRYSTALLIZER_BLOCK || block == ModBlocks.GEM_PURIFIER_BLOCK) {
                    continue;
                }
                blockStateModelGenerator.createTrivialCube(block);
            }
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
       
        for (Item item : BuiltInRegistries.ITEM) {
            if(item instanceof BlockItem) {
                continue;
            }

            Identifier id = BuiltInRegistries.ITEM.getKey(item);
            ResourceKey<EquipmentAsset> assetKey = null;
            String path = id.getPath();

            boolean handheld = false;

            if(id.getNamespace().equals("minecraft")) {
                itemModelGenerator.generateTrimmableItem(Items.TURTLE_HELMET, ItemModelGenerators.TRIM_PREFIX_HELMET, false, Map.of());
                itemModelGenerator.generateTrimmableArmorSet(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS, true, Map.of());
                itemModelGenerator.generateTrimmableArmorSet(
                        Items.COPPER_HELMET,
                        Items.COPPER_CHESTPLATE,
                        Items.COPPER_LEGGINGS,
                        Items.COPPER_BOOTS,
                        false,
                        Map.of(TrimMaterials.Palette.COPPER, TrimMaterials.Palette.COPPER_DARKER)
                );
                itemModelGenerator.generateTrimmableArmorSet(Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS, false, Map.of());
                itemModelGenerator.generateTrimmableArmorSet(
                        Items.IRON_HELMET,
                        Items.IRON_CHESTPLATE,
                        Items.IRON_LEGGINGS,
                        Items.IRON_BOOTS,
                        false,
                        Map.of(TrimMaterials.Palette.IRON, TrimMaterials.Palette.IRON_DARKER)
                );
                itemModelGenerator.generateTrimmableArmorSet(
                        Items.DIAMOND_HELMET,
                        Items.DIAMOND_CHESTPLATE,
                        Items.DIAMOND_LEGGINGS,
                        Items.DIAMOND_BOOTS,
                        false,
                        Map.of(TrimMaterials.Palette.DIAMOND, TrimMaterials.Palette.DIAMOND_DARKER)
                );
                itemModelGenerator.generateTrimmableArmorSet(
                        Items.GOLDEN_HELMET,
                        Items.GOLDEN_CHESTPLATE,
                        Items.GOLDEN_LEGGINGS,
                        Items.GOLDEN_BOOTS,
                        false,
                        Map.of(TrimMaterials.Palette.GOLD, TrimMaterials.Palette.GOLD_DARKER)
                );
                itemModelGenerator.generateTrimmableArmorSet(
                        Items.NETHERITE_HELMET,
                        Items.NETHERITE_CHESTPLATE,
                        Items.NETHERITE_LEGGINGS,
                        Items.NETHERITE_BOOTS,
                        false,
                        Map.of(TrimMaterials.Palette.NETHERITE, TrimMaterials.Palette.NETHERITE_DARKER)
                );
            }
            
            if(id.getNamespace().equals(MoreOresModInitializer.MOD_ID)) {
                if(path.endsWith("_sword") || path.endsWith("_shovel") ||
                        path.endsWith("_axe") || path.endsWith("_hoe") || 
                        path.endsWith("_pickaxe")) {
                    itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
                    handheld = true;
                } else if (path.endsWith("_spear")) {
                    itemModelGenerator.generateSpear(item);
                    handheld = true;
                }

                itemModelGenerator.generateTrimmableArmorSet(ModItems.RUBY_HELMET, ModItems.RUBY_CHESTPLATE, ModItems.RUBY_LEGGINGS, ModItems.RUBY_BOOTS, false, Map.of());
                itemModelGenerator.generateTrimmableArmorSet(ModItems.SAPPHIRE_HELMET, ModItems.SAPPHIRE_CHESTPLATE, ModItems.SAPPHIRE_LEGGINGS, ModItems.SAPPHIRE_BOOTS, false, Map.of());
                itemModelGenerator.generateTrimmableArmorSet(ModItems.RADIANT_HELMET, ModItems.RADIANT_CHESTPLATE, ModItems.RADIANT_LEGGINGS, ModItems.RADIANT_BOOTS, false, Map.of());

                if(item instanceof RadiantBowItem bow) {
                    itemModelGenerator.createFlatItemModel(bow, ModelTemplates.BOW);
                    itemModelGenerator.generateBow(bow);
                    continue;
                }
                
                if(!handheld) itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
            }
        }
    }
}