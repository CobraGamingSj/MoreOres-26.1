package org.cobra.moreores.mixin;

import net.minecraft.world.item.equipment.trim.TrimMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TrimMaterials.Palette.class)
public enum PaletteMixin {

    MOREORES_RUBY("ruby"),
    MOREORES_RADIANT("radiant"),
    MOREORES_SAPPHIRE("sapphire"),
    MOREORES_GREEN_SAPPHIRE("green_sapphire"),
    MOREORES_BLUE_GARNET("blue_garnet"),
    MOREORES_PINK_GARNET("pink_garnet"),
    MOREORES_GREEN_GARNET("green_garnet"),
    MOREORES_KYAWTHUITE("kyawthuite"),
    MOREORES_TOPAZ("topaz"),
    MOREORES_WHITE_TOPAZ("white_topaz"),
    MOREORES_PERIDOT("peridot"),
    MOREORES_JADE("jade"),
    MOREORES_PYROPE("pyrope"),
    MOREORES_CRIMSON_GARNET("crimson_garnet"),
    MOREORES_CRYSTALLITE("crystallite"),
    MOREORES_RADIANT_AMETHYST("radiant_amethyst"),
    MOREORES_ALEXANDRITE("alexandrite"),
    MOREORES_LIMESTONE("limestone"),
    MOREORES_MOONSTONE("moonstone"),
    MOREORES_QUARTSIDIAN("quartsidian"),
    MOREORES_OPAL("opal"),
    MOREORES_RED_BERYL("red_beryl");

    @Shadow
    PaletteMixin(String suffix) {
    }
}
