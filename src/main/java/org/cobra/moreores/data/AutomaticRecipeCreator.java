package org.cobra.moreores.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CookingBookCategory;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.client.recipe.GemCrystallizerRecipeJsonBuilder;
import org.cobra.moreores.client.recipe.GemPolishingRecipeJsonBuilder;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.world.item.equipment.trim.ModArmorTrimPatterns;
import org.cobra.moreores.core.registry.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AutomaticRecipeCreator extends FabricRecipeProvider {
    private static final Map<Item, SmithingData> SMITHING_DATA = Map.ofEntries(
            Map.entry(Items.NETHERITE_SWORD, new SmithingData(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, ModItems.RUBY_SWORD, ModItemTags.RUBY_TOOL_MATERIALS)),
            Map.entry(Items.NETHERITE_PICKAXE, new SmithingData(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, ModItems.RUBY_PICKAXE, ModItemTags.RUBY_TOOL_MATERIALS)),
            Map.entry(Items.NETHERITE_AXE, new SmithingData(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, ModItems.RUBY_AXE, ModItemTags.RUBY_TOOL_MATERIALS)),
            Map.entry(Items.NETHERITE_HOE, new SmithingData(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, ModItems.RUBY_HOE, ModItemTags.RUBY_TOOL_MATERIALS)),
            Map.entry(Items.NETHERITE_SHOVEL, new SmithingData(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, ModItems.RUBY_SHOVEL, ModItemTags.RUBY_TOOL_MATERIALS)),
            Map.entry(Items.NETHERITE_HELMET, new SmithingData(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, ModItems.RUBY_HELMET, ModItemTags.RUBY_TOOL_MATERIALS)),
            Map.entry(Items.NETHERITE_CHESTPLATE, new SmithingData(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, ModItems.RUBY_CHESTPLATE, ModItemTags.RUBY_TOOL_MATERIALS)),
            Map.entry(Items.NETHERITE_LEGGINGS, new SmithingData(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, ModItems.RUBY_LEGGINGS, ModItemTags.RUBY_TOOL_MATERIALS)),
            Map.entry(Items.NETHERITE_BOOTS, new SmithingData(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, ModItems.RUBY_BOOTS, ModItemTags.RUBY_TOOL_MATERIALS)),
            Map.entry(Items.NETHERITE_NAUTILUS_ARMOR, new SmithingData(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, ModItems.RUBY_NAUTILUS_ARMOR, ModItemTags.RUBY_TOOL_MATERIALS)),
            Map.entry(Items.NETHERITE_SPEAR, new SmithingData(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, ModItems.RUBY_SPEAR, ModItemTags.RUBY_TOOL_MATERIALS)),
            Map.entry(ModItems.SAPPHIRE_SWORD, new SmithingData(ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE, ModItems.RADIANT_SWORD, ModItemTags.RADIANT_TOOL_MATERIALS)),
            Map.entry(ModItems.SAPPHIRE_PICKAXE, new SmithingData(ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE, ModItems.RADIANT_PICKAXE, ModItemTags.RADIANT_TOOL_MATERIALS)),
            Map.entry(ModItems.SAPPHIRE_AXE, new SmithingData(ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE, ModItems.RADIANT_AXE, ModItemTags.RADIANT_TOOL_MATERIALS)),
            Map.entry(ModItems.SAPPHIRE_HOE, new SmithingData(ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE, ModItems.RADIANT_HOE, ModItemTags.RADIANT_TOOL_MATERIALS)),
            Map.entry(ModItems.SAPPHIRE_SHOVEL, new SmithingData(ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE, ModItems.RADIANT_SHOVEL, ModItemTags.RADIANT_TOOL_MATERIALS)),
            Map.entry(ModItems.SAPPHIRE_HELMET, new SmithingData(ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE, ModItems.RADIANT_HELMET, ModItemTags.RADIANT_TOOL_MATERIALS)),
            Map.entry(ModItems.SAPPHIRE_CHESTPLATE, new SmithingData(ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE, ModItems.RADIANT_CHESTPLATE, ModItemTags.RADIANT_TOOL_MATERIALS)),
            Map.entry(ModItems.SAPPHIRE_LEGGINGS, new SmithingData(ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE, ModItems.RADIANT_LEGGINGS, ModItemTags.RADIANT_TOOL_MATERIALS)),
            Map.entry(ModItems.SAPPHIRE_BOOTS, new SmithingData(ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE, ModItems.RADIANT_BOOTS, ModItemTags.RADIANT_TOOL_MATERIALS))
    );

    private static final Map<Item, Item> SMELTABLES = Map.ofEntries(
            Map.entry(ModBlocks.RUBY_ORE.asItem(), ModItems.RUBY),
            Map.entry(ModBlocks.DEEPSLATE_RUBY_ORE.asItem(), ModItems.RUBY),
            Map.entry(ModBlocks.SAPPHIRE_ORE.asItem(), ModItems.SAPPHIRE),
            Map.entry(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.asItem(), ModItems.SAPPHIRE),
            Map.entry(ModBlocks.GREEN_SAPPHIRE_ORE.asItem(), ModItems.GREEN_SAPPHIRE),
            Map.entry(ModBlocks.DEEPSLATE_GREEN_SAPPHIRE_ORE.asItem(), ModItems.GREEN_SAPPHIRE),
            Map.entry(ModBlocks.BLUE_GARNET_ORE.asItem(), ModItems.BLUE_GARNET),
            Map.entry(ModBlocks.DEEPSLATE_BLUE_GARNET_ORE.asItem(), ModItems.BLUE_GARNET),
            Map.entry(ModBlocks.PINK_GARNET_ORE.asItem(), ModItems.PINK_GARNET),
            Map.entry(ModBlocks.DEEPSLATE_PINK_GARNET_ORE.asItem(), ModItems.PINK_GARNET),
            Map.entry(ModBlocks.GREEN_GARNET_ORE.asItem(), ModItems.GREEN_GARNET),
            Map.entry(ModBlocks.DEEPSLATE_GREEN_GARNET_ORE.asItem(), ModItems.GREEN_GARNET),
            Map.entry(ModBlocks.KYAWTHUITE_ORE.asItem(), ModItems.KYAWTHUITE),
            Map.entry(ModBlocks.DEEPSLATE_KYAWTHUITE_ORE.asItem(), ModItems.KYAWTHUITE),
            Map.entry(ModBlocks.TOPAZ_ORE.asItem(), ModItems.TOPAZ),
            Map.entry(ModBlocks.DEEPSLATE_TOPAZ_ORE.asItem(), ModItems.TOPAZ),
            Map.entry(ModBlocks.WHITE_TOPAZ_ORE.asItem(), ModItems.WHITE_TOPAZ),
            Map.entry(ModBlocks.DEEPSLATE_WHITE_TOPAZ_ORE.asItem(), ModItems.WHITE_TOPAZ),
            Map.entry(ModBlocks.PERIDOT_ORE.asItem(), ModItems.PERIDOT),
            Map.entry(ModBlocks.DEEPSLATE_PERIDOT_ORE.asItem(), ModItems.PERIDOT),
            Map.entry(ModBlocks.JADE_ORE.asItem(), ModItems.JADE),
            Map.entry(ModBlocks.DEEPSLATE_JADE_ORE.asItem(), ModItems.JADE),
            Map.entry(ModBlocks.PYROPE_ORE.asItem(), ModItems.PYROPE),
            Map.entry(ModBlocks.DEEPSLATE_PYROPE_ORE.asItem(), ModItems.PYROPE),
            Map.entry(ModItems.RAW_RUBY, ModItems.RUBY),
            Map.entry(ModItems.RAW_SAPPHIRE, ModItems.SAPPHIRE),
            Map.entry(ModItems.RAW_GREEN_SAPPHIRE, ModItems.GREEN_SAPPHIRE),
            Map.entry(ModItems.RAW_BLUE_GARNET, ModItems.BLUE_GARNET),
            Map.entry(ModItems.RAW_PINK_GARNET, ModItems.PINK_GARNET),
            Map.entry(ModItems.RAW_GREEN_GARNET, ModItems.GREEN_GARNET),
            Map.entry(ModItems.RAW_KYAWTHUITE, ModItems.KYAWTHUITE),
            Map.entry(ModItems.RAW_TOPAZ, ModItems.TOPAZ),
            Map.entry(ModItems.RAW_WHITE_TOPAZ, ModItems.WHITE_TOPAZ),
            Map.entry(ModItems.RAW_PERIDOT, ModItems.PERIDOT),
            Map.entry(ModItems.RAW_JADE, ModItems.JADE),
            Map.entry(ModItems.RAW_PYROPE, ModItems.PYROPE)
            );


    private static final Map<Item, Item> GEM_POLISHABLES = Map.ofEntries(
            Map.entry(ModItems.RAW_RUBY, ModItems.RUBY),
            Map.entry(ModBlocks.RAW_RUBY_BLOCK.asItem(),  ModBlocks.RUBY_BLOCK.asItem()),
            Map.entry(ModItems.RAW_SAPPHIRE, ModItems.SAPPHIRE),
            Map.entry(ModBlocks.RAW_SAPPHIRE_BLOCK.asItem(), ModBlocks.SAPPHIRE_BLOCK.asItem()),
            Map.entry(ModItems.RAW_GREEN_SAPPHIRE, ModItems.GREEN_SAPPHIRE),
            Map.entry(ModBlocks.RAW_GREEN_SAPPHIRE_BLOCK.asItem(), ModBlocks.GREEN_SAPPHIRE_BLOCK.asItem()),
            Map.entry(ModItems.RAW_BLUE_GARNET, ModItems.BLUE_GARNET),
            Map.entry(ModBlocks.RAW_BLUE_GARNET_BLOCK.asItem(), ModBlocks.BLUE_GARNET_BLOCK.asItem()),
            Map.entry(ModItems.RAW_PINK_GARNET, ModItems.PINK_GARNET),
            Map.entry(ModBlocks.RAW_PINK_GARNET_BLOCK.asItem(), ModBlocks.PINK_GARNET_BLOCK.asItem()),
            Map.entry(ModItems.RAW_GREEN_GARNET, ModItems.GREEN_GARNET),
            Map.entry(ModBlocks.RAW_GREEN_GARNET_BLOCK.asItem(), ModBlocks.GREEN_GARNET_BLOCK.asItem()),
            Map.entry(ModItems.RAW_KYAWTHUITE, ModItems.KYAWTHUITE),
            Map.entry(ModBlocks.RAW_KYAWTHUITE_BLOCK.asItem(), ModBlocks.KYAWTHUITE_BLOCK.asItem()),
            Map.entry(ModItems.RAW_TOPAZ, ModItems.TOPAZ),
            Map.entry(ModBlocks.RAW_TOPAZ_BLOCK.asItem(), ModBlocks.TOPAZ_BLOCK.asItem()),
            Map.entry(ModItems.RAW_WHITE_TOPAZ, ModItems.WHITE_TOPAZ),
            Map.entry(ModBlocks.RAW_WHITE_TOPAZ_BLOCK.asItem(), ModBlocks.WHITE_TOPAZ_BLOCK.asItem()),
            Map.entry(ModItems.RAW_PERIDOT, ModItems.PERIDOT),
            Map.entry(ModBlocks.RAW_PERIDOT_BLOCK.asItem(), ModBlocks.PERIDOT_BLOCK.asItem()),
            Map.entry(ModItems.RAW_JADE, ModItems.JADE),
            Map.entry(ModBlocks.RAW_JADE_BLOCK.asItem(), ModBlocks.JADE_BLOCK.asItem()),
            Map.entry(ModItems.RAW_PYROPE, ModItems.PYROPE),
            Map.entry(ModBlocks.RAW_PYROPE_BLOCK.asItem(), ModBlocks.PYROPE_BLOCK.asItem())
    );

    private static final Map<Item, Item> GEM_INFUSES = Map.ofEntries(
            Map.entry(ModItems.RUBY, ModItems.ALEXANDRITE),
            Map.entry(ModBlocks.RUBY_BLOCK.asItem(),  ModBlocks.ALEXANDRITE_BLOCK.asItem()),
            Map.entry(ModItems.SAPPHIRE, ModItems.KASHMIR_SAPPHIRE),
            Map.entry(ModBlocks.SAPPHIRE_BLOCK.asItem(), ModBlocks.KASHMIR_SAPPHIRE_BLOCK.asItem()),
            Map.entry(ModItems.GREEN_SAPPHIRE, ModItems.CRYSTALLITE),
            Map.entry(ModBlocks.GREEN_SAPPHIRE_BLOCK.asItem(), ModBlocks.CRYSTALLITE_BLOCK.asItem()),
            Map.entry(ModItems.BLUE_GARNET, ModItems.CRIMSON_GARNET),
            Map.entry(ModBlocks.BLUE_GARNET_BLOCK.asItem(), ModBlocks.CRIMSON_GARNET_BLOCK.asItem()),
            Map.entry(ModItems.PINK_GARNET, ModItems.RADIANT_AMETHYST),
            Map.entry(ModBlocks.PINK_GARNET_BLOCK.asItem(), ModBlocks.RADIANT_AMETHYST_BLOCK.asItem()),
            Map.entry(ModItems.GREEN_GARNET, ModItems.LIMESTONE),
            Map.entry(ModBlocks.GREEN_GARNET_BLOCK.asItem(), ModBlocks.LIMESTONE_BLOCK.asItem()),
            Map.entry(ModItems.KYAWTHUITE, ModItems.ORANGE_ZIRCON),
            Map.entry(ModBlocks.KYAWTHUITE_BLOCK.asItem(), ModBlocks.ORANGE_ZIRCON_BLOCK.asItem()),
            Map.entry(ModItems.WHITE_TOPAZ, ModItems.MOONSTONE),
            Map.entry(ModBlocks.WHITE_TOPAZ_BLOCK.asItem(), ModBlocks.MOONSTONE_BLOCK.asItem()),
            Map.entry(ModItems.PERIDOT, ModItems.OPAL),
            Map.entry(ModBlocks.PERIDOT_BLOCK.asItem(), ModBlocks.OPAL_BLOCK.asItem()),
            Map.entry(ModItems.JADE, ModItems.GRANDIDIERITE),
            Map.entry(ModBlocks.JADE_BLOCK.asItem(), ModBlocks.GRANDIDIERITE_BLOCK.asItem()),
            Map.entry(ModItems.PYROPE, ModItems.RED_BERYL),
            Map.entry(ModBlocks.PYROPE_BLOCK.asItem(), ModBlocks.RED_BERYL_BLOCK.asItem())
    );

    private static final  Map<Item, Item> SAPPHIRE_MAP = Map.ofEntries(
            Map.entry(ModItems.RUBY_SWORD, ModItems.SAPPHIRE_SWORD),
            Map.entry(ModItems.RUBY_PICKAXE, ModItems.SAPPHIRE_PICKAXE),
            Map.entry(ModItems.RUBY_AXE, ModItems.SAPPHIRE_AXE),
            Map.entry(ModItems.RUBY_SHOVEL, ModItems.SAPPHIRE_SHOVEL),
            Map.entry(ModItems.RUBY_HOE, ModItems.SAPPHIRE_HOE),
            Map.entry(ModItems.RUBY_SPEAR, ModItems.SAPPHIRE_SPEAR),
            Map.entry(ModItems.RUBY_HELMET, ModItems.SAPPHIRE_HELMET),
            Map.entry(ModItems.RUBY_CHESTPLATE, ModItems.SAPPHIRE_CHESTPLATE),
            Map.entry(ModItems.RUBY_LEGGINGS, ModItems.SAPPHIRE_LEGGINGS),
            Map.entry(ModItems.RUBY_BOOTS, ModItems.SAPPHIRE_BOOTS),
            Map.entry(ModItems.RUBY_NAUTILUS_ARMOR, ModItems.SAPPHIRE_NAUTILUS_ARMOR)
    );

    public AutomaticRecipeCreator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public RecipeProvider createRecipeProvider(HolderLookup.Provider wrapperLookup, RecipeOutput recipeExporter) {
        return new RecipeProvider(wrapperLookup, recipeExporter) {
            @Override
            public void buildRecipes() {
                int defaultSmeltingTime = 1500;
                int defaultBlastingTime = 750;

                for (Block block : BuiltInRegistries.BLOCK) {
                    Identifier id = BuiltInRegistries.BLOCK.getKey(block);

                    if(id.getNamespace().equals(MoreOresModInitializer.MOD_ID)) {
                        if(block.defaultBlockState().is(ModBlocks.GEM_CRYSTALLIZER_BLOCK) || block.defaultBlockState().is(ModBlocks.GEM_PURIFIER_BLOCK)) {
                            continue;
                        }
                        String path = id.getPath();
                        if(path.endsWith("_block")) {
                            String itemName = path.replace("_block", "");
                            Item item = BuiltInRegistries.ITEM.getValue(MoreOresModInitializer.id(itemName));
                            if(block.defaultBlockState().is(ModBlocks.RADIANT_BLOCK)) {
                                shaped(RecipeCategory.MISC, ModItems.RADIANT, 1)
                                        .pattern("aaa")
                                        .pattern("aba")
                                        .pattern("aaa")
                                        .define('a', ModBlocks.RUBY_BLOCK)
                                        .define('b', Items.DIAMOND)
                                        .unlockedBy(getHasName(ModBlocks.RUBY_BLOCK), has(ModBlocks.RUBY_BLOCK))
                                        .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                                        .save(output, MoreOresModInitializer.recipeKey(getSimpleRecipeName(ModItems.RADIANT) + "_from_ruby"));
                                nineBlockStorageRecipes(RecipeCategory.MISC, ModItems.RADIANT, RecipeCategory.MISC, ModBlocks.RADIANT_BLOCK);
                                continue;
                            }
                            if(block.defaultBlockState().is(ModBlocks.ENERGY_BLOCK)) {
                                shaped(RecipeCategory.MISC, ModBlocks.ENERGY_BLOCK, 1)
                                        .pattern("aaa")
                                        .pattern("aba")
                                        .pattern("aaa")
                                        .define('a', ModItems.RADIANT)
                                        .define('b', Blocks.TNT)
                                        .unlockedBy(getHasName(ModItems.RADIANT), has(ModItems.RADIANT))
                                        .unlockedBy(getHasName(Blocks.TNT), has(Blocks.TNT))
                                        .save(output, MoreOresModInitializer.recipeKey(getSimpleRecipeName(ModBlocks.ENERGY_BLOCK) + "_from_radiant"));

                                shapeless(RecipeCategory.MISC, ModItems.ENERGY_INGOT, 9)
                                        .unlockedBy(getHasName(ModBlocks.ENERGY_BLOCK), has(ModItems.ENERGY_INGOT))
                                        .requires(ModItems.ENERGY_INGOT)
                                        .save(output, MoreOresModInitializer.recipeKey(getSimpleRecipeName(ModItems.ENERGY_INGOT)));
                                continue;
                            }

                            nineBlockStorageRecipes(RecipeCategory.BUILDING_BLOCKS, item, RecipeCategory.DECORATIONS, block);
                        }
                    }
                }

                for(Map.Entry<Item, Item> entry : SAPPHIRE_MAP.entrySet()) {
                    Item inputItem = entry.getKey();
                    Item outputItem = entry.getValue();
                    String path = BuiltInRegistries.ITEM.getKey(outputItem).getPath();
                    RecipeCategory category = (path.contains("_pickaxe") || path.contains("hoe") || path.contains("_shovel"))
                            ? RecipeCategory.TOOLS : RecipeCategory.COMBAT;
                    shaped(category, outputItem)
                            .pattern("aaa")
                            .pattern("aba")
                            .pattern("aaa")
                            .define('a', ModItems.SAPPHIRE)
                            .define('b', inputItem)
                            .unlockedBy(getHasName(ModItems.SAPPHIRE),  has(ModItems.SAPPHIRE))
                            .unlockedBy(getHasName(inputItem), has(inputItem))
                            .save(output, MoreOresModInitializer.recipeKey(getSimpleRecipeName(outputItem)));
                }

                for (Map.Entry<Item, SmithingData> entry : SMITHING_DATA.entrySet()) {
                    Item baseItem = entry.getKey();
                    SmithingData data = entry.getValue();
                    Item result = data.result();
                    Item template = data.template();
                    TagKey<Item> tag = data.toolTag();
                    String path = BuiltInRegistries.ITEM.getKey(result).getPath();
                    RecipeCategory category = (path.contains("_pickaxe") || path.contains("hoe") || path.contains("_shovel"))
                            ? RecipeCategory.TOOLS : RecipeCategory.COMBAT;
                    SmithingTransformRecipeBuilder.smithing(Ingredient.of(template), Ingredient.of(baseItem), tag(tag), category, result)
                            .unlocks(getHasName(ModItems.RUBY), has(tag))
                            .save(output, MoreOresModInitializer.recipeKey(getSimpleRecipeName(result) + "_smithing"));
                }

                for (var entry: SMELTABLES.entrySet()) {
                    var input = entry.getKey();
                    var output = entry.getValue();

                    oreSmelting(List.of(input), RecipeCategory.MISC, CookingBookCategory.MISC, output, .15f, defaultSmeltingTime, output.toString());
                    oreBlasting(List.of(input), RecipeCategory.MISC, CookingBookCategory.MISC, output, .15f, defaultBlastingTime, output.toString());
                }

                for (var entry : GEM_POLISHABLES.entrySet()) {
                    var input = entry.getKey();
                    var result = entry.getValue();

                    createGemPurifying(Ingredient.of(input), result)
                            .criterion(getHasName(input), has(input))
                            .offerTo(output, getSimpleRecipeName(result));
                }

                for (var entry : GEM_INFUSES.entrySet()) {
                    Item inputBefore = entry.getKey();
                    Item result = entry.getValue();

                    createGemInfusion(Ingredient.of(inputBefore), result)
                            .criterion(getHasName(inputBefore), has(result))
                            .offerTo(output, getSimpleRecipeName(result));
                }

                oreBlasting(List.of(ModItems.RUBY), RecipeCategory.MISC, CookingBookCategory.MISC, Items.NETHERITE_INGOT, 0.15f, 450, "netherite");

                shaped(
                        RecipeCategory.REDSTONE, ModBlocks.GEM_CRYSTALLIZER_BLOCK
                )
                        .pattern("aba")
                        .pattern("cdc")
                        .pattern("ccc")
                        .define('a', Items.REDSTONE)
                        .define('b', Ingredient.of(ModItems.ENERGY_INGOT, ModBlocks.ENERGY_BLOCK.asItem()))
                        .define('c', Ingredient.of(Blocks.IRON_BLOCK.asItem()))
                        .define('d', Ingredient.of(ModBlocks.GEM_PURIFIER_BLOCK.asItem()))
                        .unlockedBy(getHasName(ModBlocks.GEM_PURIFIER_BLOCK.asItem()), has(ModBlocks.GEM_PURIFIER_BLOCK.asItem()))
                        .save(output, getSimpleRecipeName(ModBlocks.GEM_CRYSTALLIZER_BLOCK.asItem()));

                trimSmithing(ModItems.GUARDIAN_ARMOR_TRIM_SMITHING_TEMPLATE,
                        ModArmorTrimPatterns.GUARDIAN, ResourceKey.create(Registries.RECIPE, Identifier.withDefaultNamespace(getItemName(ModItems.GUARDIAN_ARMOR_TRIM_SMITHING_TEMPLATE) + "_smithing_trim")));

                GemCrystallizerRecipeJsonBuilder.createQuartsidian()
                        .criterion(getHasName(Items.QUARTZ), has(Items.QUARTZ))
                        .criterion(getHasName(Blocks.OBSIDIAN), has(Blocks.OBSIDIAN))
                        .offerTo(output, getSimpleRecipeName(ModItems.QUARTSIDIAN));

                shaped(RecipeCategory.MISC, ModBlocks.GEM_PURIFIER_BLOCK, 1)
                        .pattern("III")
                        .pattern("III")
                        .pattern("B B")
                        .define('I', Blocks.IRON_BLOCK)
                        .define('B', Blocks.IRON_BARS)
                        .unlockedBy(getHasName(Blocks.IRON_BLOCK), has(Blocks.IRON_BLOCK))
                        .unlockedBy(getHasName(Blocks.IRON_BARS), has(Blocks.IRON_BARS))
                        .save(output, ResourceKey.create(Registries.RECIPE, Identifier.parse(getSimpleRecipeName(ModBlocks.GEM_PURIFIER_BLOCK))));

                shaped(RecipeCategory.REDSTONE, ModBlocks.RUBY_LAMP, 1)
                        .pattern("aba")
                        .pattern("bcb")
                        .pattern("aba")
                        .define('a', Items.REDSTONE)
                        .define('b', ModItems.RUBY)
                        .define('c', Blocks.GLOWSTONE)
                        .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                        .unlockedBy(getHasName(ModItems.RUBY), has(ModItems.RUBY))
                        .unlockedBy(getHasName(Blocks.GLOWSTONE), has(Blocks.GLOWSTONE))
                        .save(output, ResourceKey.create(Registries.RECIPE, Identifier.parse(getSimpleRecipeName(ModBlocks.RUBY_LAMP))));

                shaped(RecipeCategory.MISC, ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE, 2)
                        .pattern("aba")
                        .pattern("aca")
                        .pattern("aaa")
                        .define('a', ModItems.RUBY)
                        .define('b', ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE)
                        .define('c', Blocks.STONE)
                        .unlockedBy(getHasName(ModItems.RUBY), has(ModItems.RUBY))
                        .save(output, MoreOresModInitializer.recipeKey(getSimpleRecipeName(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE) + "_duplication"));

                shaped(RecipeCategory.MISC, ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE, 2)
                        .pattern("aba")
                        .pattern("aca")
                        .pattern("aaa")
                        .define('a', ModItems.SAPPHIRE)
                        .define('b', ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE)
                        .define('c', ModBlocks.RUBY_BLOCK)
                        .unlockedBy(getHasName(ModItems.RUBY), has(ModItems.RUBY))
                        .save(output, MoreOresModInitializer.recipeKey(getSimpleRecipeName(ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE) + "_duplication"));

                shaped(RecipeCategory.MISC, ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE)
                        .pattern("aba")
                        .pattern("aca")
                        .pattern("aaa")
                        .define('a', Blocks.STONE)
                        .define('b', Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
                        .define('c', ModItems.RUBY)
                        .unlockedBy(getHasName(ModItems.RUBY), has(ModItems.RUBY))
                        .unlockedBy(getHasName(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), has(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                        .save(output, MoreOresModInitializer.recipeKey(getSimpleRecipeName(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE)));

                shaped(RecipeCategory.MISC, ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE)
                        .pattern("aba")
                        .pattern("aca")
                        .pattern("aaa")
                        .define('a', ModBlocks.RUBY_BLOCK)
                        .define('b', ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE)
                        .define('c', ModItems.SAPPHIRE)
                        .unlockedBy(getHasName(ModItems.SAPPHIRE), has(ModItems.SAPPHIRE))
                        .unlockedBy(getHasName(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE), has(ModItems.RUBY_UPGRADE_SMITHING_TEMPLATE))
                        .save(output, MoreOresModInitializer.recipeKey(getSimpleRecipeName(ModItems.RADIANT_UPGRADE_SMITHING_TEMPLATE)));
                
                shaped(RecipeCategory.MISC, ModItems.CRYSTAL_OF_ECLIPSE)
                        .pattern("###")
                        .pattern("#AA")
                        .pattern("AAA")
                        .define('#', ModItems.ECLIPSE_GEM_CRYSTALS)
                        .define('A', Blocks.SAND)
                        .unlockedBy(getHasName(Blocks.SAND), has(Blocks.SAND))
                        .save(output, MoreOresModInitializer.recipeKey(getSimpleRecipeName(ModItems.CRYSTAL_OF_ECLIPSE)));
                
                shaped(RecipeCategory.MISC, ModItems.ECLIPSE_GEM)
                        .pattern("abc")
                        .pattern("def")
                        .pattern("ghi")
                        .define('a', ModItems.RADIANT_AMETHYST)
                        .define('b', ModItems.MOONSTONE)
                        .define('c', ModItems.LIMESTONE)
                        .define('d', ModItems.QUARTSIDIAN)
                        .define('e', ModItems.CRYSTAL_OF_ECLIPSE)
                        .define('f', ModItems.ALEXANDRITE)
                        .define('g', ModItems.ORANGE_ZIRCON)
                        .define('h', ModItems.OPAL)
                        .define('i', ModItems.GRANDIDIERITE)
                        .unlockedBy(getHasName(ModItems.CRYSTAL_OF_ECLIPSE), has(ModItems.CRYSTAL_OF_ECLIPSE))
                        .save(output, MoreOresModInitializer.recipeKey(getSimpleRecipeName(ModItems.ECLIPSE_GEM)));
                
                shaped(RecipeCategory.COMBAT, ModItems.RADIANT_BOW)
                        .pattern(" ab")
                        .pattern("a b")
                        .pattern(" ab")
                        .define('a', Items.STRING)
                        .define('b', ModItems.ECLIPSE_GEM)
                        .unlockedBy(getHasName(ModItems.ECLIPSE_GEM), has(ModItems.ECLIPSE_GEM))
                        .save(output, MoreOresModInitializer.recipeKey(getSimpleRecipeName(ModItems.RADIANT_BOW)));

                shaped(RecipeCategory.MISC, ModItems.GEM_ARROW, 32)
                        .pattern("abc")
                        .pattern("def")
                        .pattern("ghi")
                        .define('a', ModItems.RADIANT_AMETHYST)
                        .define('b', ModItems.MOONSTONE)
                        .define('c', ModItems.LIMESTONE)
                        .define('d', ModItems.QUARTSIDIAN)
                        .define('e', Items.ARROW)
                        .define('f', ModItems.ALEXANDRITE)
                        .define('g', ModItems.ORANGE_ZIRCON)
                        .define('h', ModItems.OPAL)
                        .define('i', ModItems.GRANDIDIERITE)
                        .unlockedBy(getHasName(Items.ARROW), has(Items.ARROW))
                        .save(output, MoreOresModInitializer.recipeKey(getSimpleRecipeName(ModItems.GEM_ARROW)));
            }
        };
    }

    public GemPolishingRecipeJsonBuilder createGemPurifying(Ingredient input, Item result) {
        return GemPolishingRecipeJsonBuilder.create(input, new ItemStackTemplate(result), RecipeCategory.MISC);
    }

    public GemCrystallizerRecipeJsonBuilder createGemInfusion(Ingredient inputBefore, Item result) {
        return GemCrystallizerRecipeJsonBuilder.create(inputBefore, new ItemStackTemplate(result), RecipeCategory.MISC);
    }

    @Override
    public String getName() {
        return "Mod Recipes Gen for " + MoreOresModInitializer.MOD_ID;
    }

    private record SmithingData(Item template, Item result, TagKey<Item> toolTag) {

    }
}