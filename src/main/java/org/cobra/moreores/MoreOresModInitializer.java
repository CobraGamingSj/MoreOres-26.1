package org.cobra.moreores;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.registry.FuelValueEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import org.cobra.moreores.client.gui.screen.ModMenuType;
import org.cobra.moreores.core.registry.RewardState;
import org.cobra.moreores.enchantment.entity.effect.EnchantmentEffects;
import org.cobra.moreores.level.gen.BiomeModifiers;
import org.cobra.moreores.networking.ModC2SNetworks;
import org.cobra.moreores.networking.ModC2SPayloadRegistry;
import org.cobra.moreores.networking.ModS2CNetworks;
import org.cobra.moreores.networking.ModS2CPayloadRegistry;
import org.cobra.moreores.recipe.ModRecipeSerializer;
import org.cobra.moreores.recipe.ModRecipeType;
import org.cobra.moreores.recipe.book.ModRecipeBookCategories;
import org.cobra.moreores.recipe.display.GemCrystallizingRecipeDisplay;
import org.cobra.moreores.recipe.display.GemPolishingRecipeDisplay;
import org.cobra.moreores.recipe.display.ModRecipeDisplays;
import org.cobra.moreores.sound.ModBlockSoundGroup;
import org.cobra.moreores.util.VanillaLootModifiers;
import org.cobra.moreores.village.ModVillagerProfessions;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.block.entity.ModBlockEntityTypes;
import org.cobra.moreores.world.entity.ModEntityTypes;
import org.cobra.moreores.world.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreOresModInitializer implements ModInitializer {

	public static final String MOD_ID = "moreores";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id(String id) {
		return Identifier.fromNamespaceAndPath(MOD_ID, id);
	}

	public static String formatName(String path) {
		String[] words = path.split("_");
		StringBuilder builder = new StringBuilder();

		for(int i = 0; i < words.length; i++) {
			String word = words[i];

			builder.append(Character.toUpperCase(word.charAt(0)))
					.append(word.substring(1));

			if(i < words.length - 1) {
				builder.append(" ");
			}
		}
		return builder.toString();
	}

	public static ResourceKey<Recipe<?>> recipeKey(String id) {
		return ResourceKey.create(Registries.RECIPE, id(id));
	}


	// Gemstones Item Group
	public static final CreativeModeTab GEMSTONES = FabricCreativeModeTab.builder()
			.icon(() -> new ItemStack(ModItems.RADIANT))
			.title(Component.translatable("itemGroup.moreores.gemstones"))
			.displayItems((context, entries) -> {
				entries.accept(ModItems.RUBY);
				entries.accept(ModItems.RADIANT);
				entries.accept(ModItems.SAPPHIRE);
				entries.accept(ModItems.GREEN_SAPPHIRE);
				entries.accept(ModItems.BLUE_GARNET);
				entries.accept(ModItems.PINK_GARNET);
				entries.accept(ModItems.GREEN_GARNET);
				entries.accept(ModItems.KYAWTHUITE);
				entries.accept(ModItems.TOPAZ);
				entries.accept(ModItems.WHITE_TOPAZ);
				entries.accept(ModItems.PERIDOT);
				entries.accept(ModItems.JADE);
				entries.accept(ModItems.PYROPE);
				entries.accept(ModItems.CRIMSON_GARNET);
				entries.accept(ModItems.CRYSTALLITE);
				entries.accept(ModItems.RADIANT_AMETHYST);
				entries.accept(ModItems.MOONSTONE);
				entries.accept(ModItems.LIMESTONE);
				entries.accept(ModItems.QUARTSIDIAN);
				entries.accept(ModItems.ALEXANDRITE);
				entries.accept(ModItems.ORANGE_ZIRCON);
				entries.accept(ModItems.OPAL);
				entries.accept(ModItems.GRANDIDIERITE);
				entries.accept(ModItems.RED_BERYL);
				entries.accept(ModItems.KASHMIR_SAPPHIRE);
				entries.accept(ModBlocks.RUBY_BLOCK);
				entries.accept(ModBlocks.RADIANT_BLOCK);
				entries.accept(ModBlocks.SAPPHIRE_BLOCK);
				entries.accept(ModBlocks.GREEN_SAPPHIRE_BLOCK);
				entries.accept(ModBlocks.BLUE_GARNET_BLOCK);
				entries.accept(ModBlocks.PINK_GARNET_BLOCK);
				entries.accept(ModBlocks.GREEN_GARNET_BLOCK);
				entries.accept(ModBlocks.KYAWTHUITE_BLOCK);
				entries.accept(ModBlocks.TOPAZ_BLOCK);
				entries.accept(ModBlocks.WHITE_TOPAZ_BLOCK);
				entries.accept(ModBlocks.PERIDOT_BLOCK);
				entries.accept(ModBlocks.JADE_BLOCK);
				entries.accept(ModBlocks.PYROPE_BLOCK);
				entries.accept(ModBlocks.CRIMSON_GARNET_BLOCK);
				entries.accept(ModBlocks.CRYSTALLITE_BLOCK);
				entries.accept(ModBlocks.RADIANT_AMETHYST_BLOCK);
				entries.accept(ModBlocks.MOONSTONE_BLOCK);
				entries.accept(ModBlocks.LIMESTONE_BLOCK);
				entries.accept(ModBlocks.QUARTSIDIAN_BLOCK);
				entries.accept(ModBlocks.ALEXANDRITE_BLOCK);
				entries.accept(ModBlocks.ORANGE_ZIRCON_BLOCK);
				entries.accept(ModBlocks.OPAL_BLOCK);
				entries.accept(ModBlocks.GRANDIDIERITE_BLOCK);
				entries.accept(ModBlocks.RED_BERYL_BLOCK);
				entries.accept(ModBlocks.KASHMIR_SAPPHIRE_BLOCK);
			}).build();

    

    @Override
	public void onInitialize() {


		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayer player = handler.getPlayer();
			String modVersion = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow().getMetadata().getVersion().getFriendlyString();

			player.addTag("moreores_first_join");
			player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("MoreOres+").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD)));
			player.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal(modVersion).withStyle(ChatFormatting.YELLOW)));
			player.connection.send(new ClientboundSetTitlesAnimationPacket(20, 100, 20));
		});


		ServerMessageEvents.CHAT_MESSAGE.register((msg, sender, params) -> {
			String playerSignature = msg.signedContent().toLowerCase();
			if(playerSignature.contains("happy birthday cobra") || playerSignature.contains("happy birthday") || playerSignature.contains("happy bday") || playerSignature.contains("happy bday cobra")) {
				giveBirthdayRewards(sender);
			}
		});


		// Gemstones Item Group Registry
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id("gemstones"), GEMSTONES);


		// Fuel Registry
		FuelValueEvents.BUILD.register(((builder, context) -> {
			builder.add(ModItems.ENERGY_INGOT, 24500);
			builder.add(ModBlocks.ENERGY_BLOCK, 27500);
		}));


		// Gemstones & Ingots Registry
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(ingredientsEventEntries -> {
			ingredientsEventEntries.insertAfter(Items.RAW_GOLD, ModItems.RAW_RUBY);
			ingredientsEventEntries.insertAfter(ModItems.RAW_RUBY, ModItems.RAW_SAPPHIRE);
			ingredientsEventEntries.insertAfter(ModItems.RAW_SAPPHIRE, ModItems.RAW_GREEN_SAPPHIRE);
			ingredientsEventEntries.insertAfter(ModItems.RAW_GREEN_SAPPHIRE, ModItems.RAW_BLUE_GARNET);
			ingredientsEventEntries.insertAfter(ModItems.RAW_BLUE_GARNET, ModItems.RAW_PINK_GARNET);
			ingredientsEventEntries.insertAfter(ModItems.RAW_PINK_GARNET, ModItems.RAW_GREEN_GARNET);
			ingredientsEventEntries.insertAfter(ModItems.RAW_GREEN_GARNET, ModItems.RAW_KYAWTHUITE);
			ingredientsEventEntries.insertAfter(ModItems.RAW_KYAWTHUITE, ModItems.RAW_TOPAZ);
			ingredientsEventEntries.insertAfter(ModItems.RAW_TOPAZ, ModItems.RAW_WHITE_TOPAZ);
			ingredientsEventEntries.insertAfter(ModItems.RAW_WHITE_TOPAZ, ModItems.RAW_PERIDOT);
			ingredientsEventEntries.insertAfter(ModItems.RAW_PERIDOT, ModItems.RAW_PYROPE);
			ingredientsEventEntries.insertAfter(ModItems.RAW_PYROPE, ModItems.RAW_JADE);

			ingredientsEventEntries.insertAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE);
			ingredientsEventEntries.insertAfter(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE);
			ingredientsEventEntries.insertAfter(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, ModItems.GUARDIAN_ARMOR_TRIM_SMITHING_TEMPLATE);
			ingredientsEventEntries.insertBefore(Items.NETHERITE_INGOT, ModItems.ENERGY_INGOT);
			ingredientsEventEntries.insertAfter(Items.BLAZE_POWDER, ModItems.RADIANT_DUST);
			ingredientsEventEntries.insertAfter(Items.NETHERITE_INGOT, ModItems.RADIANT);
			ingredientsEventEntries.insertAfter(ModItems.RADIANT, ModItems.RUBY);
			ingredientsEventEntries.insertAfter(ModItems.RUBY, ModItems.SAPPHIRE);
			ingredientsEventEntries.insertAfter(ModItems.SAPPHIRE, ModItems.GREEN_SAPPHIRE);
			ingredientsEventEntries.insertAfter(ModItems.GREEN_SAPPHIRE, ModItems.BLUE_GARNET);
			ingredientsEventEntries.insertAfter(ModItems.BLUE_GARNET, ModItems.PINK_GARNET);
			ingredientsEventEntries.insertAfter(ModItems.PINK_GARNET, ModItems.GREEN_GARNET);
			ingredientsEventEntries.insertAfter(ModItems.GREEN_GARNET, ModItems.KYAWTHUITE);
			ingredientsEventEntries.insertAfter(ModItems.KYAWTHUITE, ModItems.TOPAZ);
			ingredientsEventEntries.insertAfter(ModItems.TOPAZ, ModItems.WHITE_TOPAZ);
			ingredientsEventEntries.insertAfter(ModItems.WHITE_TOPAZ, ModItems.PERIDOT);
			ingredientsEventEntries.insertAfter(ModItems.PERIDOT, ModItems.PYROPE);
			ingredientsEventEntries.insertAfter(ModItems.PYROPE, ModItems.JADE);
		});


		// Tools & Music Discs Registry
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(toolEventEntries -> {
			toolEventEntries.insertAfter(Items.NETHERITE_HOE, ModItems.RUBY_SHOVEL);
			toolEventEntries.insertAfter(ModItems.RUBY_SHOVEL, ModItems.RUBY_PICKAXE);
			toolEventEntries.insertAfter(ModItems.RUBY_PICKAXE, ModItems.RUBY_AXE);
			toolEventEntries.insertAfter(ModItems.RUBY_AXE, ModItems.RUBY_HOE);
			toolEventEntries.insertAfter(ModItems.RUBY_HOE, ModItems.SAPPHIRE_SHOVEL);
			toolEventEntries.insertAfter(ModItems.SAPPHIRE_SHOVEL, ModItems.SAPPHIRE_PICKAXE);
			toolEventEntries.insertAfter(ModItems.SAPPHIRE_PICKAXE, ModItems.SAPPHIRE_AXE);
			toolEventEntries.insertAfter(ModItems.SAPPHIRE_AXE, ModItems.SAPPHIRE_HOE);
			toolEventEntries.insertAfter(ModItems.SAPPHIRE_HOE, ModItems.RADIANT_SHOVEL);
			toolEventEntries.insertAfter(ModItems.RADIANT_SHOVEL, ModItems.RADIANT_PICKAXE);
			toolEventEntries.insertAfter(ModItems.RADIANT_PICKAXE, ModItems.RADIANT_AXE);
			toolEventEntries.insertAfter(ModItems.RADIANT_AXE, ModItems.RADIANT_HOE);
		});


		// Weapons & Armors Registry
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(combatEventEntries -> {
			combatEventEntries.insertAfter(Items.NETHERITE_SWORD, ModItems.RUBY_SWORD);
			combatEventEntries.insertAfter(Items.NETHERITE_AXE, ModItems.RUBY_AXE);
			combatEventEntries.insertAfter(ModItems.RUBY_AXE, ModItems.SAPPHIRE_AXE);
			combatEventEntries.insertAfter(ModItems.RUBY_SWORD, ModItems.SAPPHIRE_SWORD);
			combatEventEntries.insertAfter(ModItems.SAPPHIRE_SWORD, ModItems.RADIANT_SWORD);
			combatEventEntries.insertAfter(Items.NETHERITE_BOOTS, ModItems.RUBY_HELMET);
			combatEventEntries.insertAfter(ModItems.RUBY_HELMET, ModItems.RUBY_CHESTPLATE);
			combatEventEntries.insertAfter(ModItems.RUBY_CHESTPLATE, ModItems.RUBY_LEGGINGS);
			combatEventEntries.insertAfter(ModItems.RUBY_LEGGINGS, ModItems.RUBY_BOOTS);
			combatEventEntries.insertAfter(ModItems.RUBY_BOOTS, ModItems.SAPPHIRE_HELMET);
			combatEventEntries.insertAfter(ModItems.SAPPHIRE_HELMET, ModItems.SAPPHIRE_CHESTPLATE);
			combatEventEntries.insertAfter(ModItems.SAPPHIRE_CHESTPLATE, ModItems.SAPPHIRE_LEGGINGS);
			combatEventEntries.insertAfter(ModItems.SAPPHIRE_LEGGINGS, ModItems.SAPPHIRE_BOOTS);
			combatEventEntries.insertAfter(ModItems.SAPPHIRE_BOOTS, ModItems.RADIANT_HELMET);
			combatEventEntries.insertAfter(ModItems.RADIANT_HELMET, ModItems.RADIANT_CHESTPLATE);
			combatEventEntries.insertAfter(ModItems.RADIANT_CHESTPLATE, ModItems.RADIANT_LEGGINGS);
			combatEventEntries.insertAfter(ModItems.RADIANT_LEGGINGS, ModItems.RADIANT_BOOTS);
            combatEventEntries.insertAfter(Items.NETHERITE_SPEAR, ModItems.RUBY_SPEAR);
            combatEventEntries.insertAfter(ModItems.RUBY_SPEAR, ModItems.SAPPHIRE_SPEAR);
            combatEventEntries.insertAfter(Items.NETHERITE_NAUTILUS_ARMOR, ModItems.RUBY_NAUTILUS_ARMOR);
            combatEventEntries.insertAfter(ModItems.RUBY_NAUTILUS_ARMOR, ModItems.SAPPHIRE_NAUTILUS_ARMOR);
			combatEventEntries.insertAfter(Items.BOW, ModItems.RADIANT_BOW);
			combatEventEntries.insertAfter(Items.ARROW, ModItems.GEM_ARROW);
		});


		// Natural Stuff Registry
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(naturalEventEntries -> {
			naturalEventEntries.insertAfter(Blocks.RAW_GOLD_BLOCK, ModBlocks.RAW_RUBY_BLOCK);
			naturalEventEntries.insertAfter(ModBlocks.RAW_RUBY_BLOCK, ModBlocks.RAW_SAPPHIRE_BLOCK);
			naturalEventEntries.insertAfter(ModBlocks.RAW_SAPPHIRE_BLOCK, ModBlocks.RAW_GREEN_SAPPHIRE_BLOCK);
			naturalEventEntries.insertAfter(ModBlocks.RAW_GREEN_SAPPHIRE_BLOCK, ModBlocks.RAW_BLUE_GARNET_BLOCK);
			naturalEventEntries.insertAfter(ModBlocks.RAW_BLUE_GARNET_BLOCK, ModBlocks.RAW_PINK_GARNET_BLOCK);
			naturalEventEntries.insertAfter(ModBlocks.RAW_PINK_GARNET_BLOCK, ModBlocks.RAW_GREEN_GARNET_BLOCK);
			naturalEventEntries.insertAfter(ModBlocks.RAW_GREEN_GARNET_BLOCK, ModBlocks.RAW_KYAWTHUITE_BLOCK);
			naturalEventEntries.insertAfter(ModBlocks.RAW_KYAWTHUITE_BLOCK, ModBlocks.RAW_TOPAZ_BLOCK);
			naturalEventEntries.insertAfter(ModBlocks.RAW_TOPAZ_BLOCK, ModBlocks.RAW_WHITE_TOPAZ_BLOCK);
			naturalEventEntries.insertAfter(ModBlocks.RAW_WHITE_TOPAZ_BLOCK, ModBlocks.RAW_PERIDOT_BLOCK);
			naturalEventEntries.insertAfter(ModBlocks.RAW_PERIDOT_BLOCK, ModBlocks.RAW_PYROPE_BLOCK);
			naturalEventEntries.insertAfter(ModBlocks.RAW_PYROPE_BLOCK, ModBlocks.RAW_JADE_BLOCK);
			naturalEventEntries.insertAfter(Blocks.DEEPSLATE_DIAMOND_ORE, ModBlocks.RUBY_ORE);
			naturalEventEntries.insertAfter(ModBlocks.RUBY_ORE, ModBlocks.DEEPSLATE_RUBY_ORE);
			naturalEventEntries.insertAfter(ModBlocks.DEEPSLATE_RUBY_ORE, ModBlocks.SAPPHIRE_ORE);
			naturalEventEntries.insertAfter(ModBlocks.SAPPHIRE_ORE, ModBlocks.DEEPSLATE_SAPPHIRE_ORE);
			naturalEventEntries.insertAfter(ModBlocks.DEEPSLATE_SAPPHIRE_ORE, ModBlocks.GREEN_SAPPHIRE_ORE);
			naturalEventEntries.insertAfter(ModBlocks.GREEN_SAPPHIRE_ORE, ModBlocks.DEEPSLATE_GREEN_SAPPHIRE_ORE);
			naturalEventEntries.insertAfter(ModBlocks.DEEPSLATE_GREEN_SAPPHIRE_ORE, ModBlocks.BLUE_GARNET_ORE);
			naturalEventEntries.insertAfter(ModBlocks.BLUE_GARNET_ORE, ModBlocks.DEEPSLATE_BLUE_GARNET_ORE);
			naturalEventEntries.insertAfter(ModBlocks.DEEPSLATE_BLUE_GARNET_ORE, ModBlocks.PINK_GARNET_ORE);
			naturalEventEntries.insertAfter(ModBlocks.PINK_GARNET_ORE, ModBlocks.DEEPSLATE_PINK_GARNET_ORE);
			naturalEventEntries.insertAfter(ModBlocks.DEEPSLATE_PINK_GARNET_ORE, ModBlocks.GREEN_GARNET_ORE);
			naturalEventEntries.insertAfter(ModBlocks.GREEN_GARNET_ORE, ModBlocks.DEEPSLATE_GREEN_GARNET_ORE);
			naturalEventEntries.insertAfter(ModBlocks.DEEPSLATE_GREEN_GARNET_ORE, ModBlocks.KYAWTHUITE_ORE);
			naturalEventEntries.insertAfter(ModBlocks.KYAWTHUITE_ORE, ModBlocks.DEEPSLATE_KYAWTHUITE_ORE);
			naturalEventEntries.insertAfter(ModBlocks.DEEPSLATE_KYAWTHUITE_ORE, ModBlocks.TOPAZ_ORE);
			naturalEventEntries.insertAfter(ModBlocks.TOPAZ_ORE, ModBlocks.DEEPSLATE_TOPAZ_ORE);
			naturalEventEntries.insertAfter(ModBlocks.DEEPSLATE_TOPAZ_ORE, ModBlocks.WHITE_TOPAZ_ORE);
			naturalEventEntries.insertAfter(ModBlocks.WHITE_TOPAZ_ORE, ModBlocks.DEEPSLATE_WHITE_TOPAZ_ORE);
			naturalEventEntries.insertAfter(ModBlocks.DEEPSLATE_WHITE_TOPAZ_ORE, ModBlocks.PERIDOT_ORE);
			naturalEventEntries.insertAfter(ModBlocks.PERIDOT_ORE, ModBlocks.DEEPSLATE_PERIDOT_ORE);
			naturalEventEntries.insertAfter(ModBlocks.DEEPSLATE_PERIDOT_ORE, ModBlocks.JADE_ORE);
			naturalEventEntries.insertAfter(ModBlocks.JADE_ORE, ModBlocks.DEEPSLATE_JADE_ORE);
			naturalEventEntries.insertAfter(ModBlocks.DEEPSLATE_JADE_ORE, ModBlocks.PYROPE_ORE);
			naturalEventEntries.insertAfter(ModBlocks.PYROPE_ORE, ModBlocks.DEEPSLATE_PYROPE_ORE);
		});


		// Functional Block Registry
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(functionalEventEntries -> {
			functionalEventEntries.insertAfter(Blocks.BLAST_FURNACE, ModBlocks.ENERGY_BLOCK);
			functionalEventEntries.insertAfter(Blocks.REDSTONE_LAMP, ModBlocks.RUBY_LAMP);
			functionalEventEntries.insertAfter(Blocks.SMITHING_TABLE, ModBlocks.GEM_PURIFIER_BLOCK);
			functionalEventEntries.insertAfter(ModBlocks.GEM_PURIFIER_BLOCK, ModBlocks.GEM_CRYSTALLIZER_BLOCK);
		});


		// Redstone Block Registry
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(redstoneEEventEntries -> {
			redstoneEEventEntries.insertAfter(Blocks.REDSTONE_LAMP, ModBlocks.RUBY_LAMP);
		});

		// Gemstone Blocks Registry
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(buildingBlockEventEntries -> {
			buildingBlockEventEntries.insertBefore(Blocks.NETHERITE_BLOCK, ModBlocks.ENERGY_BLOCK);
			buildingBlockEventEntries.insertAfter(Blocks.NETHERITE_BLOCK, ModBlocks.RUBY_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.RUBY_BLOCK, ModBlocks.RADIANT_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.RADIANT_BLOCK, ModBlocks.SAPPHIRE_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.SAPPHIRE_BLOCK, ModBlocks.GREEN_SAPPHIRE_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.GREEN_SAPPHIRE_BLOCK, ModBlocks.BLUE_GARNET_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.BLUE_GARNET_BLOCK, ModBlocks.PINK_GARNET_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.PINK_GARNET_BLOCK, ModBlocks.GREEN_GARNET_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.GREEN_GARNET_BLOCK, ModBlocks.KYAWTHUITE_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.KYAWTHUITE_BLOCK, ModBlocks.TOPAZ_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.TOPAZ_BLOCK, ModBlocks.WHITE_TOPAZ_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.WHITE_TOPAZ_BLOCK, ModBlocks.PERIDOT_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.PERIDOT_BLOCK, ModBlocks.JADE_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.JADE_BLOCK, ModBlocks.PYROPE_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.PYROPE_BLOCK, ModBlocks.CRIMSON_GARNET_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.CRIMSON_GARNET_BLOCK, ModBlocks.CRYSTALLITE_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.CRYSTALLITE_BLOCK, ModBlocks.RADIANT_AMETHYST_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.RADIANT_AMETHYST_BLOCK, ModBlocks.MOONSTONE_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.MOONSTONE_BLOCK, ModBlocks.LIMESTONE_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.LIMESTONE_BLOCK, ModBlocks.QUARTSIDIAN_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.QUARTSIDIAN_BLOCK, ModBlocks.ALEXANDRITE_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.ALEXANDRITE_BLOCK, ModBlocks.ORANGE_ZIRCON_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.ORANGE_ZIRCON_BLOCK, ModBlocks.OPAL_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.OPAL_BLOCK, ModBlocks.GRANDIDIERITE_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.GRANDIDIERITE_BLOCK, ModBlocks.RED_BERYL_BLOCK);
			buildingBlockEventEntries.insertAfter(ModBlocks.RED_BERYL_BLOCK, ModBlocks.KASHMIR_SAPPHIRE_BLOCK);
		});


		// ModItems Registry
		ModItems.register();


		// ModBlocks Registry
		ModBlocks.register();


		// ModSounds & ModBlockSoundGroups Registry
		ModBlockSoundGroup.register();


		// WorldGeneration Registry
		BiomeModifiers.modifyOreGeneration();


		//Villagers Registry
		ModVillagerProfessions.register();

		
		//ModifyVanillaLootTables
		VanillaLootModifiers.modifyVanillaLoot();


		//ModBlockEntityType Registry
		ModBlockEntityTypes.register();


		//ModScreenHandlers Registry
		ModMenuType.register();


		//ModRecipes Registry
		ModRecipeType.register();
		ModRecipeSerializer.register();


		ModEntityTypes.register();
		
		
		//Networking Registry
		ModS2CNetworks.register();
		ModC2SNetworks.register();
		ModS2CPayloadRegistry.registerS2CPackets();
		ModC2SPayloadRegistry.registerC2SPackets();
		ModC2SNetworks.registerServerC2S();


		//ModRecipeBookCategories Registry
		ModRecipeBookCategories.register();
        ModRecipeDisplays.register();


		//EnchantmentEffects Registry
		EnchantmentEffects.register();
	}


	public static void giveBirthdayRewards(ServerPlayer serverPlayer) {
		ServerLevel world = serverPlayer.level();
		RewardState state = RewardState.get(world);

		if(state.hasClaimed(serverPlayer.getUUID())) {
			serverPlayer.sendSystemMessage(Component.literal("⚠️ You can claim the reward only once!").withStyle(ChatFormatting.RED));
			return;
		}

		serverPlayer.addItem(new ItemStack(ModItems.RUBY, 32));
		serverPlayer.addItem(new ItemStack(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, 9));
		serverPlayer.addItem(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 5));
		serverPlayer.sendSystemMessage(
				Component.literal("🎉 [MoreOres+] ")
						.withStyle(ChatFormatting.GOLD)
						.append(Component.literal("Secret unlocked! ")
								.withStyle(ChatFormatting.YELLOW))
						.append(Component.literal("Happy Birthday CobraGamingSJ ❤️")
								.withStyle(ChatFormatting.LIGHT_PURPLE)),
				false
		);

		state.setClaimed(serverPlayer.getUUID());
	}
}