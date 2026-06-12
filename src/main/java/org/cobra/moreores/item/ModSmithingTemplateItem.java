package org.cobra.moreores.item;

import org.cobra.moreores.MoreOresModInitializer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import java.util.List;
import java.util.function.Consumer;

public class ModSmithingTemplateItem extends Item {

    public ModSmithingTemplateItem(Component appliesToText, Component ingredientsText, Component titleText, Component baseSlotDescriptionText, Component additionsSlotDescriptionText, List<Identifier> emptyBaseSlotTextures, List<Identifier> emptyAdditionsSlotTextures, Properties settings) {
        super(settings);
        this.appliesToText = appliesToText;
        this.ingredientsText = ingredientsText;
        this.titleText = titleText;
        this.baseSlotDescriptionText = baseSlotDescriptionText;
        this.additionsSlotDescriptionText = additionsSlotDescriptionText;
        this.emptyBaseSlotTextures = emptyBaseSlotTextures;
        this.emptyAdditionsSlotTextures = emptyAdditionsSlotTextures;
    }
    public static final ChatFormatting TITLE_FORMATTING = ChatFormatting.GRAY;
    public static final ChatFormatting DESCRIPTION_FORMATTING = ChatFormatting.BLUE;
    public static final Component INGREDIENTS_TEXT = Component.translatable(Util.makeDescriptionId("item", MoreOresModInitializer.id("smithing_template.ingredients"))).withStyle(TITLE_FORMATTING);
    public static final Component APPLIES_TO_TEXT = Component.translatable(Util.makeDescriptionId("item", MoreOresModInitializer.id("smithing_template.applies_to"))).withStyle(TITLE_FORMATTING);
    private static final Component RUBY_UPGRADE_TEXT = Component.translatable(Util.makeDescriptionId("upgrade", MoreOresModInitializer.id("ruby_upgrade"))).withStyle(TITLE_FORMATTING);
    private static final Component RADIANT_UPGRADE_TEXT = Component.translatable(Util.makeDescriptionId("upgrade", MoreOresModInitializer.id("radiant_upgrade"))).withStyle(TITLE_FORMATTING);
    private static final Component ARMOR_TRIM_APPLIES_TO_TEXT = Component.translatable(Util.makeDescriptionId("item", Identifier.withDefaultNamespace("smithing_template.armor_trim.applies_to"))).withStyle(DESCRIPTION_FORMATTING);
    private static final Component ARMOR_TRIM_INGREDIENTS_TEXT = Component.translatable(Util.makeDescriptionId("item", Identifier.withDefaultNamespace("smithing_template.armor_trim.ingredients"))).withStyle(DESCRIPTION_FORMATTING);
    private static final Component ARMOR_TRIM_BASE_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", Identifier.withDefaultNamespace("smithing_template.armor_trim.base_slot_description")));
    private static final Component ARMOR_TRIM_ADDITIONS_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", Identifier.withDefaultNamespace("smithing_template.armor_trim.additions_slot_description")));
    private static final Component RUBY_UPGRADE_APPLIES_TO_TEXT = Component.translatable(Util.makeDescriptionId("item", MoreOresModInitializer.id("smithing_template.ruby_upgrade.applies_to"))).withStyle(DESCRIPTION_FORMATTING);
    private static final Component RUBY_UPGRADE_INGREDIENTS_TEXT = Component.translatable(Util.makeDescriptionId("item", MoreOresModInitializer.id("smithing_template.ruby_upgrade.ingredients"))).withStyle(DESCRIPTION_FORMATTING);
    private static final Component RUBY_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", MoreOresModInitializer.id("smithing_template.netherite_upgrade.base_slot_description")));
    private static final Component RUBY_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", MoreOresModInitializer.id("smithing_template.netherite_upgrade.additions_slot_description")));

    private static final Component RADIANT_UPGRADE_APPLIES_TO_TEXT = Component.translatable(Util.makeDescriptionId("item", MoreOresModInitializer.id("smithing_template.radiant_upgrade.applies_to"))).withStyle(DESCRIPTION_FORMATTING);
    private static final Component RADIANT_UPGRADE_INGREDIENTS_TEXT = Component.translatable(Util.makeDescriptionId("item", MoreOresModInitializer.id("smithing_template.radiant_upgrade.ingredients"))).withStyle(DESCRIPTION_FORMATTING);
    private static final Component RADIANT_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", MoreOresModInitializer.id("smithing_template.radiant_upgrade.base_slot_description")));
    private static final Component RADIANT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Component.translatable(Util.makeDescriptionId("item", MoreOresModInitializer.id("smithing_template.radiant_upgrade.additions_slot_description")));

    private static final Identifier EMPTY_ARMOR_SLOT_HELMET_TEXTURE = Identifier.withDefaultNamespace("item/empty_armor_slot_helmet");
    private static final Identifier EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE = Identifier.withDefaultNamespace("item/empty_armor_slot_chestplate");
    private static final Identifier EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE = Identifier.withDefaultNamespace("item/empty_armor_slot_leggings");
    private static final Identifier EMPTY_ARMOR_SLOT_BOOTS_TEXTURE = Identifier.withDefaultNamespace("item/empty_armor_slot_boots");
    private static final Identifier EMPTY_SLOT_HOE_TEXTURE = Identifier.withDefaultNamespace("item/empty_slot_hoe");
    private static final Identifier EMPTY_SLOT_AXE_TEXTURE = Identifier.withDefaultNamespace("item/empty_slot_axe");
    private static final Identifier EMPTY_SLOT_SWORD_TEXTURE = Identifier.withDefaultNamespace("item/empty_slot_sword");
    private static final Identifier EMPTY_SLOT_SHOVEL_TEXTURE = Identifier.withDefaultNamespace("item/empty_slot_shovel");
    private static final Identifier EMPTY_SLOT_PICKAXE_TEXTURE = Identifier.withDefaultNamespace("item/empty_slot_pickaxe");
    private static final Identifier EMPTY_SLOT_INGOT_TEXTURE = Identifier.withDefaultNamespace("item/empty_slot_ingot");
    private static final Identifier EMPTY_SLOT_REDSTONE_DUST_TEXTURE = Identifier.withDefaultNamespace("item/empty_slot_redstone_dust");
    private static final Identifier EMPTY_SLOT_QUARTZ_TEXTURE = Identifier.withDefaultNamespace("item/empty_slot_quartz");
    private static final Identifier EMPTY_SLOT_EMERALD_TEXTURE = Identifier.withDefaultNamespace("item/empty_slot_emerald");
    private static final Identifier EMPTY_SLOT_DIAMOND_TEXTURE = Identifier.withDefaultNamespace("item/empty_slot_diamond");
    private static final Identifier EMPTY_SLOT_LAPIS_LAZULI_TEXTURE = Identifier.withDefaultNamespace("item/empty_slot_lapis_lazuli");
    private static final Identifier EMPTY_SLOT_AMETHYST_SHARD_TEXTURE = Identifier.withDefaultNamespace("item/empty_slot_amethyst_shard");
    private final Component appliesToText;
    private final Component ingredientsText;
    private final Component titleText;
    private final Component baseSlotDescriptionText;
    private final Component additionsSlotDescriptionText;
    private final List<Identifier> emptyBaseSlotTextures;
    private final List<Identifier> emptyAdditionsSlotTextures;

    public static ModSmithingTemplateItem of(ResourceKey<TrimPattern> trimPattern) {
        return ModSmithingTemplateItem.of(trimPattern.identifier());
    }

    public static ModSmithingTemplateItem of(Identifier trimPatternIn) {
        return new ModSmithingTemplateItem(ARMOR_TRIM_APPLIES_TO_TEXT, ARMOR_TRIM_INGREDIENTS_TEXT, Component.translatable(Util.makeDescriptionId("trim_pattern", trimPatternIn)).withStyle(TITLE_FORMATTING), ARMOR_TRIM_BASE_SLOT_DESCRIPTION_TEXT, ARMOR_TRIM_ADDITIONS_SLOT_DESCRIPTION_TEXT, ModSmithingTemplateItem.getArmorTrimEmptyBaseSlotTextures(), ModSmithingTemplateItem.getArmorTrimEmptyAdditionsSlotTextures(), new Properties());
    }

    private static List<Identifier> getArmorTrimEmptyBaseSlotTextures() {
        return List.of(EMPTY_ARMOR_SLOT_HELMET_TEXTURE, EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE, EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE, EMPTY_ARMOR_SLOT_BOOTS_TEXTURE);
    }

    public static ModSmithingTemplateItem createRubyUpgrade(Properties settings) {
        return new ModSmithingTemplateItem(
                RUBY_UPGRADE_APPLIES_TO_TEXT,
                RUBY_UPGRADE_INGREDIENTS_TEXT,
                RUBY_UPGRADE_TEXT,
                RUBY_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT,
                RUBY_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT,
                getRubyUpgradeEmptyBaseSlotTextures(),
                getRubyUpgradeEmptyAdditionsSlotTextures(),
                settings);
    }

    public static ModSmithingTemplateItem createRadiantUpgrade(Properties settings) {
        return new ModSmithingTemplateItem(
                RADIANT_UPGRADE_APPLIES_TO_TEXT,
                RADIANT_UPGRADE_INGREDIENTS_TEXT,
                RADIANT_UPGRADE_TEXT,
                RADIANT_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT,
                RADIANT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT,
                getRadiantUpgradeEmptyBaseSlotTextures(),
                getRadiantUpgradeEmptyAdditionsSlotTextures(),
                settings);
    }

    public static List<Identifier> getArmorTrimEmptyAdditionsSlotTextures() {
        return List.of(EMPTY_SLOT_INGOT_TEXTURE, EMPTY_SLOT_REDSTONE_DUST_TEXTURE, EMPTY_SLOT_LAPIS_LAZULI_TEXTURE, EMPTY_SLOT_QUARTZ_TEXTURE, EMPTY_SLOT_DIAMOND_TEXTURE, EMPTY_SLOT_EMERALD_TEXTURE, EMPTY_SLOT_AMETHYST_SHARD_TEXTURE);
    }

    public static List<Identifier> getRubyUpgradeEmptyBaseSlotTextures() {
        return List.of(EMPTY_ARMOR_SLOT_HELMET_TEXTURE, EMPTY_SLOT_SWORD_TEXTURE, EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE, EMPTY_SLOT_PICKAXE_TEXTURE, EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE, EMPTY_SLOT_AXE_TEXTURE, EMPTY_ARMOR_SLOT_BOOTS_TEXTURE, EMPTY_SLOT_HOE_TEXTURE, EMPTY_SLOT_SHOVEL_TEXTURE);
    }

    public static List<Identifier> getRadiantUpgradeEmptyBaseSlotTextures() {
        return List.of(EMPTY_ARMOR_SLOT_HELMET_TEXTURE, EMPTY_SLOT_SWORD_TEXTURE, EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE, EMPTY_SLOT_PICKAXE_TEXTURE, EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE, EMPTY_SLOT_AXE_TEXTURE, EMPTY_ARMOR_SLOT_BOOTS_TEXTURE, EMPTY_SLOT_HOE_TEXTURE, EMPTY_SLOT_SHOVEL_TEXTURE);
    }

    public static List<Identifier> getRubyUpgradeEmptyAdditionsSlotTextures() {
        return List.of(EMPTY_SLOT_INGOT_TEXTURE);
    }

    public static List<Identifier> getRadiantUpgradeEmptyAdditionsSlotTextures() {
        return List.of(EMPTY_SLOT_INGOT_TEXTURE);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
        textConsumer.accept(this.titleText);
        textConsumer.accept(CommonComponents.EMPTY);
        textConsumer.accept(APPLIES_TO_TEXT);
        textConsumer.accept(CommonComponents.space().append(this.appliesToText));
        textConsumer.accept(INGREDIENTS_TEXT);
        textConsumer.accept(CommonComponents.space().append(this.ingredientsText));
    }

    public Component getBaseSlotDescription() {
        return this.baseSlotDescriptionText;
    }

    public Component getAdditionsSlotDescription() {
        return this.additionsSlotDescriptionText;
    }

    public List<Identifier> getEmptyBaseSlotTextures() {
        return this.emptyBaseSlotTextures;
    }

    public List<Identifier> getEmptyAdditionsSlotTextures() {
        return this.emptyAdditionsSlotTextures;
    }
}