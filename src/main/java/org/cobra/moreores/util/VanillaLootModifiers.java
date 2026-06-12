package org.cobra.moreores.util;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class VanillaLootModifiers {
    private static final ResourceKey<LootTable> JUNGLE_PYRAMID_KEY = ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace("chests/jungle_temple"));
    private static final ResourceKey<LootTable> ABANDONED_MINESHAFT_KEY = ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace("chests/jungle_temple"));
    private static final ResourceKey<LootTable> PLAINS_VILLAGE_TOOLSMITH_KEY = ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace("chests/abandoned_mineshaft"));
    private static final ResourceKey<LootTable> ELDER_GUARDIAN_KEY = ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace("entities/elder_guardian"));

    public static void modifyVanillaLoot() {

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (JUNGLE_PYRAMID_KEY.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.5f)) //50% Chances
                        .add(LootItem.lootTableItem(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 1.0f)));

                tableBuilder.withPool(poolBuilder);
            }
            if(ABANDONED_MINESHAFT_KEY.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                        .add(LootItem.lootTableItem(ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)));

                tableBuilder.withPool(poolBuilder);
            }
            if (ELDER_GUARDIAN_KEY.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.0025f)) //0.25% Chances
                        .add(LootItem.lootTableItem(ModItems.GUARDIAN_ARMOR_TRIM_SMITHING_TEMPLATE))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 1.0f)));

                tableBuilder.withPool(poolBuilder);
            }
            if (PLAINS_VILLAGE_TOOLSMITH_KEY.equals(key)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.05f)) //5% Chances
                        .add(LootItem.lootTableItem(ModItems.RUBY))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)));
                LootPool.Builder poolBuilder2 = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.0015f)) //0.15% Chances
                        .add(LootItem.lootTableItem(ModItems.RADIANT))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)));
                LootPool.Builder poolBuilder3 = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.015f)) //1.5% Chances
                        .add(LootItem.lootTableItem(ModItems.GREEN_GARNET))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)));
                LootPool.Builder poolBuilder4 = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.05f)) //5% Chances
                        .add(LootItem.lootTableItem(ModItems.SAPPHIRE))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)));
                LootPool.Builder poolBuilder5 = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.05f)) //5% Chances
                        .add(LootItem.lootTableItem(ModItems.TOPAZ))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)));
                LootPool.Builder poolBuilder6 = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.005f)) //0.5% Chances
                        .add(LootItem.lootTableItem(ModItems.PYROPE))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 1.0f)));

                tableBuilder.withPool(poolBuilder);
                tableBuilder.withPool(poolBuilder2);
                tableBuilder.withPool(poolBuilder3);
                tableBuilder.withPool(poolBuilder4);
                tableBuilder.withPool(poolBuilder5);
                tableBuilder.withPool(poolBuilder6);
        }
        });

        MoreOresModInitializer.LOGGER.info("Modifying VanillaLootTables for " + MoreOresModInitializer.MOD_ID + " mod.");
    }
}
