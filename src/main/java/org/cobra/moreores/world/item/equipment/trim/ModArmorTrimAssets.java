package org.cobra.moreores.world.item.equipment.trim;

import java.util.Map;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;

public class ModArmorTrimAssets {

    public static final MaterialAssetGroup RUBY = of("ruby");
    public static final MaterialAssetGroup RADIANT = of("radiant");
    public static final MaterialAssetGroup SAPPHIRE = of("sapphire");
    public static final MaterialAssetGroup GREEN_SAPPHIRE = of("green_sapphire");
    public static final MaterialAssetGroup BLUE_GARNET = of("blue_garnet");
    public static final MaterialAssetGroup PINK_GARNET = of("pink_garnet");
    public static final MaterialAssetGroup GREEN_GARNET = of("green_garnet");
    public static final MaterialAssetGroup KYAWTHUITE = of("kyawthuite");
    public static final MaterialAssetGroup TOPAZ = of("topaz");
    public static final MaterialAssetGroup WHITE_TOPAZ = of("white_topaz");
    public static final MaterialAssetGroup PERIDOT = of("peridot");
    public static final MaterialAssetGroup JADE = of("jade");
    public static final MaterialAssetGroup PYROPE = of("pyrope");

    public static MaterialAssetGroup of(String suffix) {
        return new MaterialAssetGroup(new MaterialAssetGroup.AssetInfo(suffix), Map.of());
    }

}
