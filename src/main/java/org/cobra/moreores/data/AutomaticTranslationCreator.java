package org.cobra.moreores.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.item.GemItem;
import org.cobra.moreores.world.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import java.util.concurrent.CompletableFuture;

public class AutomaticTranslationCreator extends FabricLanguageProvider {
    public AutomaticTranslationCreator(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("gui.button.gp.start", "Start");
        translationBuilder.add("gui.button.gp.pause", "Pause");
        translationBuilder.add("gui.button.gp.resume", "Resume");
        translationBuilder.add("gui.button.gp.stop", "Stop");
        translationBuilder.add("itemGroup.moreores.gemstones", "Gemstones");
        translationBuilder.add("upgrade.moreores.ruby_upgrade", "Smithing Template");
        translationBuilder.add("upgrade.moreores.radiant_upgrade", "Smithing Template");
        translationBuilder.add("item.moreores.smithing_template.applies_to", "Applies to:");
        translationBuilder.add("item.moreores.smithing_template.ruby_upgrade.applies_to", "Netherite Equipment");
        translationBuilder.add("item.moreores.smithing_template.ruby_upgrade.ingredients", "Ruby");
        translationBuilder.add("item.moreores.smithing_template.radiant_upgrade.applies_to", "Sapphire Equipment");
        translationBuilder.add("item.moreores.smithing_template.radiant_upgrade.ingredients", "Sapphire");
        translationBuilder.add("item.moreores.smithing_template.ingredients", "Ingredients:");
        translationBuilder.add("advancement.moreores.gems",  "Is that a gem?");
        translationBuilder.add("advancement.moreores.gems.desc",  "Collect a gemstone");
        translationBuilder.add("advancement.moreores.ruby_armor",  "Cover me in Ruby");
        translationBuilder.add("advancement.moreores.ruby_armor.desc",  "Equip a Ruby Armor");
        translationBuilder.add("advancement.moreores.radiant_sword",  "Overpowered!");
        translationBuilder.add("advancement.moreores.radiant_sword.desc",  "Get a Radiant Sword");
        translationBuilder.add("advancement.moreores.gems_all",  "Pocket full of GEMSTONES!");
        translationBuilder.add("advancement.moreores.gems_all.desc",  "Collect every single gemstone");
        translationBuilder.add("enchantment.moreores.thunder_striker",  "Thunder Striker");
        translationBuilder.add("entity.minecraft.villager.jeweller",  "Jeweller");
        translationBuilder.add("trim_pattern.moreores.guardian",  "Guardian Armor Trim");
        translationBuilder.add("entity.moreores.gem_arrow",  "Gem Arrow");

        for (Item item :  BuiltInRegistries.ITEM) {
            Identifier id = BuiltInRegistries.ITEM.getKey(item);

            if (id.getNamespace().equals(MoreOresModInitializer.MOD_ID)) {
                if(item instanceof GemItem gemItem) {
                    if (gemItem == ModItems.RADIANT) {
                        translationBuilder.add(ModItems.RADIANT, "§1Radiant§r");
                        translationBuilder.add("trim_material.moreores.radiant", "Radiant Material");
                        continue;
                    }
                    String gemIdentifiedPath = BuiltInRegistries.ITEM.getKey(gemItem).getPath();
                    translationBuilder.add(gemItem, MoreOresModInitializer.formatIdName(gemIdentifiedPath));
                    translationBuilder.add("trim_material." + MoreOresModInitializer.MOD_ID + "." + gemIdentifiedPath, MoreOresModInitializer.formatIdName(gemIdentifiedPath) + " Material");
                    continue;
                }

                if(item == ModItems.RADIANT_DUST || item == ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE || item == ModItems.GUARDIAN_ARMOR_TRIM_SMITHING_TEMPLATE ||
                item == ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE || item == ModBlocks.GEM_CRYSTALLIZER_BLOCK.asItem() || item == ModBlocks.GEM_PURIFIER_BLOCK.asItem()) {
                    continue;
                }
                
                String path = id.getPath();

                String translatedName = MoreOresModInitializer.formatIdName(path);

                translationBuilder.add(item, translatedName);
            }
        }
        translationBuilder.add(ModItems.RADIANT_DUST, "§2Radiant Dust§r");
        translationBuilder.add(ModBlocks.GEM_PURIFIER_BLOCK, "Gem Purifier");
        translationBuilder.add(ModBlocks.GEM_CRYSTALLIZER_BLOCK, "Gem Crystallizer");
        translationBuilder.add(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, "Ruby Upgrade");
        translationBuilder.add(ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE, "Radiant Upgrade");
        translationBuilder.add(ModItems.GUARDIAN_ARMOR_TRIM_SMITHING_TEMPLATE, "Guardian Armor Trim");
    }
}
