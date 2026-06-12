package org.cobra.moreores.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


public class AdvancementGen extends FabricAdvancementProvider {
    public AdvancementGen(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> consumer) {
        AdvancementHolder rubyGem = Advancement.Builder.advancement()
                .display(
                        ModItems.RUBY, // The display icon
                        Component.translatable("advancement.moreores.gems"), // The title
                        Component.translatable("advancement.moreores.gems.desc"), // The description
                        MoreOresModInitializer.id("block/radiant_block"), // Background image used
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                // The first string used in criterion is the name referenced by other advancements when they want to have 'requirements'
                .addCriterion("ruby", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RUBY))
                .addCriterion("radiant", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RADIANT))
                .addCriterion("sapphire", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SAPPHIRE))
                .addCriterion("green_sapphire", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GREEN_SAPPHIRE))
                .addCriterion("blue_garnet", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BLUE_GARNET))
                .addCriterion("pink_garnet", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PINK_GARNET))
                .addCriterion("green_garnet", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GREEN_GARNET))
                .addCriterion("kyawthuite", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.KYAWTHUITE))
                .addCriterion("topaz", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TOPAZ))
                .addCriterion("white_topaz", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WHITE_TOPAZ))
                .addCriterion("peridot", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PERIDOT))
                .addCriterion("jade", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.JADE))
                .addCriterion("pyrope", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PYROPE))
                .save(consumer, "moreores:is_that_a_gem");

        AdvancementHolder ruby_armor = Advancement.Builder.advancement()
                .display(
                        ModItems.RUBY_CHESTPLATE,
                        Component.translatable("advancement.moreores.ruby_armor").withStyle(ChatFormatting.DARK_AQUA),
                        Component.translatable("advancement.moreores.ruby_armor.desc"),
                        MoreOresModInitializer.id("block/radiant_block"),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .parent(rubyGem)
                .addCriterion("ruby_armor", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RUBY_HELMET, ModItems.RUBY_CHESTPLATE, ModItems.RUBY_LEGGINGS, ModItems.RUBY_BOOTS))
                .save(consumer, "moreores:ruby_armor");

        AdvancementHolder radiant_sword = Advancement.Builder.advancement()
                .display(
                        ModItems.RADIANT_SWORD,
                        Component.translatable("advancement.moreores.radiant_sword").withStyle(ChatFormatting.DARK_PURPLE),
                        Component.translatable("advancement.moreores.radiant_sword.desc"),
                        MoreOresModInitializer.id("block/radiant_block"),
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .parent(rubyGem)
                .addCriterion("radiant_sword", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RADIANT_SWORD))
                .save(consumer, "moreores:overpowered");
    }
}
