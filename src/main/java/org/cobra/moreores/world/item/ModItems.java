package org.cobra.moreores.world.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.equipment.ArmorType;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.core.registry.ResourceHelper;
import org.cobra.moreores.world.item.equipment.ArmorItem;
import org.cobra.moreores.world.item.equipment.ModArmorMaterials;

import static org.cobra.moreores.MoreOresModInitializer.LOGGER;

public class ModItems {

    private static final ResourceHelper.ItemResource RESOURCE = ResourceHelper.ItemResource.INSTANCE;

    public static final Item GEM_DETECTOR = RESOURCE.register("gem_detector", GemDetector::new);

    
//    Gemstones & Ingots
    public static final Item RUBY = RESOURCE.register("ruby", s -> new GemItem(s, "ruby"));
    public static final Item RAW_RUBY = RESOURCE.register("raw_ruby", s -> new Item(s.fireResistant()));
    public static final Item RADIANT = RESOURCE.register("radiant", s -> new GemItem(s, "radiant"));
    public static final Item RADIANT_DUST = RESOURCE.register("radiant_dust", s -> new Item(s.rarity(Rarity.EPIC).fireResistant()));
    public static final Item SAPPHIRE = RESOURCE.register("sapphire", s -> new GemItem(s, "sapphire"));
    public static final Item RAW_SAPPHIRE = RESOURCE.register("raw_sapphire", Item::new);
    public static final Item GREEN_SAPPHIRE = RESOURCE.register("green_sapphire", s -> new GemItem(s, "green_sapphire"));
    public static final Item RAW_GREEN_SAPPHIRE = RESOURCE.register("raw_green_sapphire", Item::new);
    public static final Item PINK_GARNET = RESOURCE.register("pink_garnet", s -> new GemItem(s, "pink_garnet"));
    public static final Item RAW_PINK_GARNET = RESOURCE.register("raw_pink_garnet", Item::new);
    public static final Item BLUE_GARNET = RESOURCE.register("blue_garnet", s -> new GemItem(s, "blue_garnet"));
    public static final Item RAW_BLUE_GARNET = RESOURCE.register("raw_blue_garnet", Item::new);
    public static final Item GREEN_GARNET = RESOURCE.register("green_garnet", s -> new GemItem(s,  "green_garnet"));
    public static final Item RAW_GREEN_GARNET = RESOURCE.register("raw_green_garnet", Item::new);
    public static final Item KYAWTHUITE = RESOURCE.register("kyawthuite", s -> new GemItem(s, "kyawthuite"));
    public static final Item RAW_KYAWTHUITE = RESOURCE.register("raw_kyawthuite", Item::new);
    public static final Item TOPAZ = RESOURCE.register("topaz", s -> new GemItem(s, "topaz"));
    public static final Item RAW_TOPAZ = RESOURCE.register("raw_topaz", Item::new);
    public static final Item PERIDOT = RESOURCE.register("peridot", s -> new GemItem(s, "peridot"));
    public static final Item RAW_PERIDOT = RESOURCE.register("raw_peridot", Item::new);
    public static final Item WHITE_TOPAZ = RESOURCE.register("white_topaz", s -> new GemItem(s, "white_topaz"));
    public static final Item RAW_WHITE_TOPAZ = RESOURCE.register("raw_white_topaz", Item::new);
    public static final Item PYROPE = RESOURCE.register("pyrope", s -> new GemItem(s, "pyrope"));
    public static final Item RAW_PYROPE = RESOURCE.register("raw_pyrope", Item::new);
    public static final Item JADE = RESOURCE.register("jade", s -> new GemItem(s, "jade"));
    public static final Item RAW_JADE = RESOURCE.register("raw_jade", Item::new);

    
    // New Gem Variants {Gem crystallizer}
    public static final Item CRIMSON_GARNET = RESOURCE.register("crimson_garnet", s -> new GemItem(s, "crimson_garnet"));
    public static final Item CRYSTALLITE = RESOURCE.register("crystallite", s -> new GemItem(s, "crystallite"));
    public static final Item RADIANT_AMETHYST = RESOURCE.register("radiant_amethyst", s -> new GemItem(s, "radiant_amethyst"));
    public static final Item MOONSTONE = RESOURCE.register("moonstone", s -> new GemItem(s, "moonstone"));
    public static final Item LIMESTONE = RESOURCE.register("limestone", s -> new GemItem(s, "limestone"));
    public static final Item QUARTSIDIAN = RESOURCE.register("quartsidian", s -> new GemItem(s, "quartsidian"));
    public static final Item ALEXANDRITE = RESOURCE.register("alexandrite", s -> new GemItem(s, "alexandrite"));
    public static final Item ORANGE_ZIRCON = RESOURCE.register("orange_zircon", Item::new);
    public static final Item OPAL = RESOURCE.register("opal", s -> new GemItem(s, "opal"));
    public static final Item GRANDIDIERITE = RESOURCE.register("grandidierite", Item::new);
    public static final Item RED_BERYL = RESOURCE.register("red_beryl", s -> new GemItem(s, "red_beryl"));
    public static final Item KASHMIR_SAPPHIRE = RESOURCE.register("kashmir_sapphire", Item::new);

    public static final Item ENERGY_INGOT = RESOURCE.register("energy_ingot", settings ->  new EnergyIngotItem(settings.fireResistant().rarity(Rarity.RARE)));


    //  Ruby Tools & Weapons
    public static final Item RUBY_SWORD = RESOURCE.registerSword("ruby_sword", settings -> new Item(settings.fireResistant()), 6, -2.1f, ModToolMaterials.RUBY);
    public static final Item RUBY_PICKAXE = RESOURCE.registerPickaxe("ruby_pickaxe", settings ->  new Item(settings.fireResistant()), 2, -3.0f, ModToolMaterials.RUBY);
    public static final Item RUBY_SHOVEL = RESOURCE.registerShovel("ruby_shovel", settings ->  new Item(settings.fireResistant()), 2.5F, -3.0F, ModToolMaterials.RUBY);
    public static final Item RUBY_AXE = RESOURCE.registerAxe("ruby_axe", settings ->  new Item(settings.fireResistant()), 6.0F, -2.1F, ModToolMaterials.RUBY);
    public static final Item RUBY_HOE = RESOURCE.registerHoe("ruby_hoe", settings ->  new Item(settings.fireResistant()), -5.0F, 0.0F, ModToolMaterials.RUBY);
    public static final Item RUBY_SPEAR = RESOURCE.registerSpear("ruby_spear", settings -> new Item(settings.fireResistant()), ModToolMaterials.RUBY,  1.2F, 
            1.3F, 0.35F,
                    2.0F, 6.5F, 5.0F, 
            5.1F, 8.0F, 4.6F);


    //Ruby Armor
    public static final Item RUBY_HELMET = RESOURCE.registerSteveArmor(
            "ruby_helmet",
            Item::new, new Item.Properties(), ModArmorMaterials.RUBY, ArmorType.HELMET
    );
    public static final Item RUBY_CHESTPLATE = RESOURCE.registerSteveArmor(
            "ruby_chestplate",
            Item::new, new Item.Properties(), ModArmorMaterials.RUBY, ArmorType.CHESTPLATE
    );
    public static final Item RUBY_LEGGINGS = RESOURCE.registerSteveArmor(
            "ruby_leggings",
            Item::new, new Item.Properties(), ModArmorMaterials.RUBY, ArmorType.LEGGINGS
    );
    public static final Item RUBY_BOOTS = RESOURCE.registerSteveArmor(
            "ruby_boots",
            Item::new, new Item.Properties(), ModArmorMaterials.RUBY, ArmorType.BOOTS
    );
    public static final Item RUBY_NAUTILUS_ARMOR = RESOURCE.registerNautilusArmor(
            "ruby_nautilus_armor",
            Item::new, new Item.Properties(), ModArmorMaterials.RUBY
    );


//    Sapphire Armor
    public static final Item SAPPHIRE_HELMET = RESOURCE.registerSteveArmor(
            "sapphire_helmet", 
            Item::new, new Item.Properties(), ModArmorMaterials.SAPPHIRE, ArmorType.HELMET
    );
    public static final Item SAPPHIRE_CHESTPLATE = RESOURCE.registerSteveArmor(
            "sapphire_chestplate",
            Item::new, new Item.Properties(), ModArmorMaterials.SAPPHIRE, ArmorType.CHESTPLATE
    );
    public static final Item SAPPHIRE_LEGGINGS = RESOURCE.registerSteveArmor(
            "sapphire_leggings",
            Item::new, new Item.Properties(), ModArmorMaterials.SAPPHIRE, ArmorType.LEGGINGS
    );
    public static final Item SAPPHIRE_BOOTS = RESOURCE.registerSteveArmor(
            "sapphire_boots",
            Item::new, new Item.Properties(), ModArmorMaterials.SAPPHIRE, ArmorType.BOOTS
    );
    public static final Item SAPPHIRE_NAUTILUS_ARMOR = RESOURCE.registerNautilusArmor(
            "sapphire_nautilus_armor",
            Item::new, new Item.Properties(), ModArmorMaterials.SAPPHIRE
    );


//     Radiant Armor
    public static final Item RADIANT_HELMET = RESOURCE.registerSteveArmor(
            "radiant_helmet",
            s -> new ArmorItem(s.rarity(Rarity.EPIC)), new Item.Properties(), ModArmorMaterials.RADIANT, ArmorType.HELMET
    );
    public static final Item RADIANT_CHESTPLATE = RESOURCE.registerSteveArmor(
            "radiant_chestplate",
            s -> new ArmorItem(s.rarity(Rarity.EPIC)), new Item.Properties(), ModArmorMaterials.RADIANT, ArmorType.CHESTPLATE
    );
    public static final Item RADIANT_LEGGINGS = RESOURCE.registerSteveArmor(
            "radiant_leggings",
            s -> new ArmorItem(s.rarity(Rarity.EPIC)), new Item.Properties(), ModArmorMaterials.RADIANT, ArmorType.LEGGINGS
    );
    public static final Item RADIANT_BOOTS = RESOURCE.registerSteveArmor(
            "radiant_boots",
            s -> new ArmorItem(s.rarity(Rarity.EPIC)), new Item.Properties(), ModArmorMaterials.RADIANT, ArmorType.BOOTS
    );
    public static final Item RADIANT_NAUTILUS_ARMOR = RESOURCE.registerNautilusArmor(
            "radiant_nautilus_armor", 
            s -> new Item(s.rarity(Rarity.EPIC).fireResistant()), new Item.Properties(), ModArmorMaterials.RADIANT);


    //Sapphire Tools & Weapons
    public static final Item SAPPHIRE_SWORD = RESOURCE.registerSword(
            "sapphire_sword",
            s-> new Item(s.fireResistant()),
            8, -2.0f, ModToolMaterials.SAPPHIRE);
    public static final Item SAPPHIRE_PICKAXE = RESOURCE.registerPickaxe(
            "sapphire_pickaxe",
            s -> new Item(s.fireResistant()),
            4, -3.0f, ModToolMaterials.SAPPHIRE);
    public static final Item SAPPHIRE_AXE = RESOURCE.registerAxe(
            "sapphire_axe",
            s -> new Item(s.fireResistant()),
            8, -2.0f, ModToolMaterials.SAPPHIRE);
    public static final Item SAPPHIRE_HOE = RESOURCE.registerHoe(
            "sapphire_hoe",
            s -> new Item(s.fireResistant()),
            4, -3.0f, ModToolMaterials.SAPPHIRE);
    public static final Item SAPPHIRE_SHOVEL = RESOURCE.registerShovel(
            "sapphire_shovel",
            s -> new Item(s.fireResistant()),
            3.5F, -3.0F, ModToolMaterials.SAPPHIRE);
    public static final Item SAPPHIRE_SPEAR = RESOURCE.registerSpear("sapphire_spear", settings -> new Item(settings.fireResistant()), ModToolMaterials.SAPPHIRE, 1.25F, 1.4F,
            0.3F, 1.5F, 6.0F, 4.5F, 
            5.1F, 7.65F, 4.6F);


    //    Radiant Tools & Weapons
    public static final Item RADIANT_SWORD = RESOURCE.registerSword(
            "radiant_sword",
            s -> new Item(s.rarity(Rarity.EPIC).fireResistant()),
            32, -1f, ModToolMaterials.RADIANT
    );
    public static final Item RADIANT_PICKAXE = RESOURCE.registerPickaxe(
            "radiant_pickaxe",
            s -> new Item(s.rarity(Rarity.EPIC).fireResistant()),
            20, -1.5f, ModToolMaterials.RADIANT
    );
    public static final Item RADIANT_AXE = RESOURCE.registerAxe(
            "radiant_axe",
            s -> new Item(s.rarity(Rarity.EPIC).fireResistant()),
            32, -1f, ModToolMaterials.RADIANT
    );
    public static final Item RADIANT_SHOVEL = RESOURCE.registerShovel(
            "radiant_shovel",
            s -> new Item(s.rarity(Rarity.EPIC).fireResistant()),
            16, -1.8f, ModToolMaterials.RADIANT
    );
    public static final Item RADIANT_HOE = RESOURCE.registerHoe(
            "radiant_hoe",
            s -> new Item(s.rarity(Rarity.EPIC).fireResistant()),
            12, -2.2f, ModToolMaterials.RADIANT
    );


    //    Smithing Templates
    public static final Item RUBY_UPGRADE_SMITHING_TEMPLATE = RESOURCE.register("ruby_upgrade_smithing_template",
            s -> ModSmithingTemplateItem.createRubyUpgrade(s.rarity(Rarity.UNCOMMON)));
    public static final Item RADIANT_UPGRADE_SMITHING_TEMPLATE = RESOURCE.register("radiant_upgrade_smithing_template",
            s -> ModSmithingTemplateItem.createRadiantUpgrade(s.rarity(Rarity.UNCOMMON)));
    public static final Item GUARDIAN_ARMOR_TRIM_SMITHING_TEMPLATE = RESOURCE.register("guardian_armor_trim_smithing_template",
            s -> SmithingTemplateItem.createArmorTrimTemplate(s.rarity(Rarity.RARE)));

    
    // Bow & Arrow
    public static final Item RADIANT_BOW = RESOURCE.register("radiant_bow", s -> new RadiantBowItem(s.durability(1024).enchantable(2).stacksTo(1).fireResistant().rarity(Rarity.RARE)));
    public static final Item GEM_ARROW = RESOURCE.register("gem_arrow", s -> new GemArrowItem(s.fireResistant().rarity(Rarity.UNCOMMON)));
    
    
    // Eclipse Gem
    public static final Item CRYSTAL_OF_ECLIPSE = RESOURCE.register("crystal_of_eclipse", settings -> new Item(settings.rarity(Rarity.RARE).fireResistant()));
    public static final Item ECLIPSE_GEM = RESOURCE.register("eclipse_gem", settings -> new Item(settings.rarity(Rarity.EPIC).fireResistant()));
    
    public static void register() {
        MoreOresModInitializer.LOGGER.info("Loading ModItems for " + MoreOresModInitializer.MOD_ID + " mod");
        int itemCount = 0;
        for(Item item : BuiltInRegistries.ITEM) {
            Identifier id = BuiltInRegistries.ITEM.getKey(item);
            if(item instanceof BlockItem) {
                continue;
            }
            if(id.getNamespace().equals(MoreOresModInitializer.MOD_ID)) {
                String name = MoreOresModInitializer.formatName(id.getPath());
                if(item == GEM_DETECTOR) {
                    continue;
                }
                itemCount++;
                LOGGER.info("Registering Item: {}, for {} mod", name, MoreOresModInitializer.MOD_ID);
            }
        }
        LOGGER.info("Registered {} Items for {} mod", itemCount, MoreOresModInitializer.MOD_ID);
    }
}