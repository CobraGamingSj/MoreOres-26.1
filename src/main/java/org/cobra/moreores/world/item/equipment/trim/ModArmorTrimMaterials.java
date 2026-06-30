package org.cobra.moreores.world.item.equipment.trim;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import org.cobra.moreores.MoreOresModInitializer;

public class ModArmorTrimMaterials {
    public static final ResourceKey<TrimMaterial> RUBY = of("ruby");
    public static final ResourceKey<TrimMaterial> RADIANT = of("radiant");
    public static final ResourceKey<TrimMaterial> SAPPHIRE = of("sapphire");
    public static final ResourceKey<TrimMaterial> GREEN_SAPPHIRE = of("green_sapphire");
    public static final ResourceKey<TrimMaterial> BLUE_GARNET = of("blue_garnet");
    public static final ResourceKey<TrimMaterial> PINK_GARNET = of("pink_garnet");
    public static final ResourceKey<TrimMaterial> GREEN_GARNET = of("green_garnet");
    public static final ResourceKey<TrimMaterial> KYAWTHUITE = of("kyawthuite");
    public static final ResourceKey<TrimMaterial> TOPAZ = of("topaz");
    public static final ResourceKey<TrimMaterial> WHITE_TOPAZ = of("white_topaz");
    public static final ResourceKey<TrimMaterial> PERIDOT = of("peridot");
    public static final ResourceKey<TrimMaterial> JADE = of("jade");
    public static final ResourceKey<TrimMaterial> PYROPE = of("pyrope");
    public static final ResourceKey<TrimMaterial> CRIMSON_GARNET = of("crimson_garnet");
    public static final ResourceKey<TrimMaterial> CRYSTALLITE = of("crystallite");
    public static final ResourceKey<TrimMaterial> RADIANT_AMETHYST = of("radiant_amethyst");
    public static final ResourceKey<TrimMaterial> ALEXANDRITE = of("alexandrite");
    public static final ResourceKey<TrimMaterial> LIMESTONE = of("limestone");
    public static final ResourceKey<TrimMaterial> MOONSTONE = of("moonstone");
    public static final ResourceKey<TrimMaterial> QUARTSIDIAN = of("quartsidian");
    public static final ResourceKey<TrimMaterial> OPAL = of("opal");
    public static final ResourceKey<TrimMaterial> RED_BERYL = of("red_beryl");

    public static void bootstrap(BootstrapContext<TrimMaterial> context) {
        register(context, RUBY, Style.EMPTY.withColor(16711680), ModArmorTrimAssets.RUBY);
        register(context, RADIANT, Style.EMPTY.withColor(11730944), ModArmorTrimAssets.RADIANT);
        register(context, SAPPHIRE, Style.EMPTY.withColor(6875), ModArmorTrimAssets.SAPPHIRE);
        register(context, GREEN_SAPPHIRE, Style.EMPTY.withColor(2925312), ModArmorTrimAssets.GREEN_SAPPHIRE);
        register(context, BLUE_GARNET, Style.EMPTY.withColor(TextColor.fromRgb(1507522)), ModArmorTrimAssets.BLUE_GARNET);
        register(context, PINK_GARNET, Style.EMPTY.withColor(16711927), ModArmorTrimAssets.PINK_GARNET);
        register(context, GREEN_GARNET, Style.EMPTY.withColor(65331), ModArmorTrimAssets.GREEN_GARNET);
        register(context, KYAWTHUITE, Style.EMPTY.withColor(16737792), ModArmorTrimAssets.KYAWTHUITE);
        register(context, TOPAZ, Style.EMPTY.withColor(13713152), ModArmorTrimAssets.TOPAZ);
        register(context, WHITE_TOPAZ, Style.EMPTY.withColor(15328482), ModArmorTrimAssets.WHITE_TOPAZ);
        register(context, PERIDOT, Style.EMPTY.withColor(52238), ModArmorTrimAssets.PERIDOT);
        register(context, JADE, Style.EMPTY.withColor(11140783), ModArmorTrimAssets.JADE);
        register(context, PYROPE, Style.EMPTY.withColor(12717839), ModArmorTrimAssets.PYROPE);

        register(context, CRIMSON_GARNET, Style.EMPTY.withColor(12058624), ModArmorTrimAssets.CRIMSON_GARNET);
        register(context, CRYSTALLITE, Style.EMPTY.withColor(2086544), ModArmorTrimAssets.CRYSTALLITE);
        register(context, RADIANT_AMETHYST, Style.EMPTY.withColor(8733893), ModArmorTrimAssets.RADIANT_AMETHYST);
        register(context, LIMESTONE, Style.EMPTY.withColor(65459), ModArmorTrimAssets.LIMESTONE);
        register(context, MOONSTONE, Style.EMPTY.withColor(14935011), ModArmorTrimAssets.MOONSTONE);
        register(context, ALEXANDRITE, Style.EMPTY.withColor(12009953), ModArmorTrimAssets.ALEXANDRITE);
        register(context, QUARTSIDIAN, Style.EMPTY.withColor(5197647), ModArmorTrimAssets.QUARTSIDIAN);
        register(context, OPAL, Style.EMPTY.withColor(5085951), ModArmorTrimAssets.OPAL);
        register(context, RED_BERYL, Style.EMPTY.withColor(15073280), ModArmorTrimAssets.RED_BERYL);
    }

    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> key, Style style, MaterialAssetGroup assets) {
        Component text = Component.translatable(Util.makeDescriptionId("trim_material", key.identifier())).withStyle(style);
        context.register(key, new TrimMaterial(assets, text));
    }

    private static ResourceKey<TrimMaterial> of(String id) {
        Identifier ID = MoreOresModInitializer.id(id);
        return ResourceKey.create(Registries.TRIM_MATERIAL, ID);
    }
}
