package org.cobra.moreores.world.item.equipment.trim;

import org.cobra.moreores.MoreOresModInitializer;
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

    public static void bootstrap(BootstrapContext<TrimMaterial> registerable) {
        register(registerable, RUBY, Style.EMPTY.withColor(16711680), ModArmorTrimAssets.RUBY);
        register(registerable, RADIANT, Style.EMPTY.withColor(11730944), ModArmorTrimAssets.RADIANT);
        register(registerable, SAPPHIRE, Style.EMPTY.withColor(6875), ModArmorTrimAssets.SAPPHIRE);
        register(registerable, GREEN_SAPPHIRE, Style.EMPTY.withColor(2925312), ModArmorTrimAssets.GREEN_SAPPHIRE);
        register(registerable, BLUE_GARNET, Style.EMPTY.withColor(TextColor.fromRgb(1507522)), ModArmorTrimAssets.BLUE_GARNET);
        register(registerable, PINK_GARNET, Style.EMPTY.withColor(16711927), ModArmorTrimAssets.PINK_GARNET);
        register(registerable, GREEN_GARNET, Style.EMPTY.withColor(65331), ModArmorTrimAssets.GREEN_GARNET);
        register(registerable, KYAWTHUITE, Style.EMPTY.withColor(16737792), ModArmorTrimAssets.KYAWTHUITE);
        register(registerable, TOPAZ, Style.EMPTY.withColor(13713152), ModArmorTrimAssets.TOPAZ);
        register(registerable, WHITE_TOPAZ, Style.EMPTY.withColor(15328482), ModArmorTrimAssets.WHITE_TOPAZ);
        register(registerable, PERIDOT, Style.EMPTY.withColor(52238), ModArmorTrimAssets.PERIDOT);
        register(registerable, JADE, Style.EMPTY.withColor(11140783), ModArmorTrimAssets.JADE);
        register(registerable, PYROPE, Style.EMPTY.withColor(12717839), ModArmorTrimAssets.PYROPE);
    }

    private static void register(BootstrapContext<TrimMaterial> registry, ResourceKey<TrimMaterial> key, Style style, MaterialAssetGroup assets) {
        Component text = Component.translatable(Util.makeDescriptionId("trim_material", key.identifier())).withStyle(style);
        registry.register(key, new TrimMaterial(assets, text));
    }

    private static ResourceKey<TrimMaterial> of(String id) {
        Identifier ID = Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, id);
        return ResourceKey.create(Registries.TRIM_MATERIAL, ID);
    }
}
