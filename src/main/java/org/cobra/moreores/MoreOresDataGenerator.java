package org.cobra.moreores;

import org.cobra.moreores.data.*;
import org.cobra.moreores.data.village.TradeSets;
import org.cobra.moreores.enchantment.ModEnchantments;
import org.cobra.moreores.world.item.equipment.trim.ModArmorTrimMaterials;
import org.cobra.moreores.world.item.equipment.trim.ModArmorTrimPatterns;
import org.cobra.moreores.level.gen.feature.ModConfiguredFeatures;
import org.cobra.moreores.level.gen.feature.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.cobra.moreores.world.item.trading.ModVillagerTrades;

public class MoreOresDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(DynamicRegistry::new);
		pack.addProvider(ItemTagGen::new);
		pack.addProvider(BlockTagGen::new);
		pack.addProvider(AutomaticModelGenerator::new);
		pack.addProvider(AdvancementGen::new);
		pack.addProvider(AutomaticLootTableCreator::new);
		pack.addProvider(PointOfInterestTypeTagGen::new);
		pack.addProvider(AutomaticRecipeCreator::new);
		pack.addProvider(AutomaticTranslationCreator::new);
		pack.addProvider(EquipmentAssetsProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
		registryBuilder.add(Registries.TRIM_MATERIAL, ModArmorTrimMaterials::bootstrap);
		registryBuilder.add(Registries.TRIM_PATTERN, ModArmorTrimPatterns::bootstrap);
		registryBuilder.add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);
		registryBuilder.add(Registries.TRADE_SET, TradeSets::bootstrap);
		registryBuilder.add(Registries.VILLAGER_TRADE, ModVillagerTrades::bootstrap);
	}
}