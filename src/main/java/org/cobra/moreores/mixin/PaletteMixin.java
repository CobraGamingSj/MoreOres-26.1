package org.cobra.moreores.mixin;

import net.minecraft.world.item.equipment.trim.TrimMaterials;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TrimMaterials.Palette.class)
public enum PaletteMixin {

    RUBY("ruby"),
    RADIANT("radiant"),
    SAPPHIRE("sapphire"),
    GREEN_SAPPHIRE("green_sapphire"),
    BLUE_GARNET("blue_garnet"),
    PINK_GARNET("pink_garnet"),
    GREEN_GARNET("green_garnet"),
    KYAWTHUITE("kyawthuite"),
    TOPAZ("topaz"),
    WHITE_TOPAZ("white_topaz"),
    PERIDOT("peridot"),
    JADE("jade"),
    PYROPE("pyrope"),
    CRIMSON_GARNET("crimson_garnet"),
    CRYSTALLITE("crystallite"),
    RADIANT_AMETHYST("radiant_amethyst"),
    ALEXANDRITE("alexandrite"),
    LIMESTONE("limestone"),
    MOONSTONE("moonstone"),
    QUARTSIDIAN("quartsidian"),
    OPAL("opal"),
    RED_BERYL("red_beryl");

    PaletteMixin(String suffix) {
    }
}
