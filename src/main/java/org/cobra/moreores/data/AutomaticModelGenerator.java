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
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.level.block.Block;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.block.RubyLampBlock;
import org.cobra.moreores.world.item.RadiantBowItem;
import org.cobra.moreores.world.item.equipment.ModEquipmentAssetKeys;

public class AutomaticModelGenerator extends FabricModelProvider {
    public AutomaticModelGenerator(FabricPackOutput output) {
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

            if(id.getNamespace().equals(MoreOresModInitializer.MOD_ID)) {

                if(path.endsWith("_sword") || path.endsWith("_shovel") ||
                        path.endsWith("_axe") || path.endsWith("_hoe") ||  path.endsWith("_pickaxe")) {
                    itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
                    handheld = true;
                } else if (path.endsWith("_spear")) {
                    itemModelGenerator.generateSpear(item);
                    handheld = true;
                } else if (path.startsWith("ruby_")) {
                    assetKey = ModEquipmentAssetKeys.RUBY;
                } else if (path.startsWith("sapphire_")) {
                    assetKey = ModEquipmentAssetKeys.SAPPHIRE;
                } else if (path.startsWith("radiant_")) {
                    assetKey = ModEquipmentAssetKeys.RADIANT;
                }

                if (assetKey != null) {
                    if(path.endsWith("_helmet")) {
                        itemModelGenerator.generateTrimmableItem(
                                item,
                                assetKey,
                                ItemModelGenerators.TRIM_PREFIX_HELMET,
                                false
                        );
                        continue;
                    } else if (path.endsWith("_chestplate")) {
                        itemModelGenerator.generateTrimmableItem(
                                item,
                                assetKey,
                                ItemModelGenerators.TRIM_PREFIX_CHESTPLATE,
                                false
                        );
                        continue;
                    } else if (path.endsWith("_leggings")) {
                        itemModelGenerator.generateTrimmableItem(
                                item,
                                assetKey,
                                ItemModelGenerators.TRIM_PREFIX_LEGGINGS,
                                false
                        );
                        continue;
                    } else if (path.endsWith("_boots")) {
                        itemModelGenerator.generateTrimmableItem(
                                item,
                                assetKey,
                                ItemModelGenerators.TRIM_PREFIX_BOOTS,
                                false
                        );
                        continue;
                    }
                }
                
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
