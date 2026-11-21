package org.cobra.moreores.mixin;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.item.equipment.trim.ModTrimMaterials;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemModelGenerators.class)
public class ItemModelGeneratorsMixin {

    @Mutable
    @Shadow
    @Final
    public static List<ItemModelGenerators.TrimMaterialData> TRIM_MATERIAL_MODELS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void moreores$addTrimMaterials(CallbackInfo ci) {
        for (TrimMaterials.Palette palette : TrimMaterials.Palette.values()) {
            MoreOresModInitializer.LOGGER.info("Trim palette: {}", palette.name());
        }

        List<ItemModelGenerators.TrimMaterialData> materials = new ArrayList<>(TRIM_MATERIAL_MODELS);

        add(materials, TrimMaterials.Palette.MOREORES_RUBY, ModTrimMaterials.RUBY);
        add(materials, TrimMaterials.Palette.MOREORES_RADIANT, ModTrimMaterials.RADIANT);
        add(materials, TrimMaterials.Palette.MOREORES_SAPPHIRE, ModTrimMaterials.SAPPHIRE);
        add(materials, TrimMaterials.Palette.MOREORES_GREEN_SAPPHIRE, ModTrimMaterials.GREEN_SAPPHIRE);
        add(materials, TrimMaterials.Palette.MOREORES_BLUE_GARNET, ModTrimMaterials.BLUE_GARNET);
        add(materials, TrimMaterials.Palette.MOREORES_PINK_GARNET, ModTrimMaterials.PINK_GARNET);
        add(materials, TrimMaterials.Palette.MOREORES_GREEN_GARNET, ModTrimMaterials.GREEN_GARNET);
        add(materials, TrimMaterials.Palette.MOREORES_KYAWTHUITE, ModTrimMaterials.KYAWTHUITE);
        add(materials, TrimMaterials.Palette.MOREORES_TOPAZ, ModTrimMaterials.TOPAZ);
        add(materials, TrimMaterials.Palette.MOREORES_WHITE_TOPAZ, ModTrimMaterials.WHITE_TOPAZ);
        add(materials, TrimMaterials.Palette.MOREORES_PERIDOT, ModTrimMaterials.PERIDOT);
        add(materials, TrimMaterials.Palette.MOREORES_JADE, ModTrimMaterials.JADE);
        add(materials, TrimMaterials.Palette.MOREORES_PYROPE, ModTrimMaterials.PYROPE);
        add(materials, TrimMaterials.Palette.MOREORES_CRIMSON_GARNET, ModTrimMaterials.CRIMSON_GARNET);
        add(materials, TrimMaterials.Palette.MOREORES_CRYSTALLITE, ModTrimMaterials.CRYSTALLITE);
        add(materials, TrimMaterials.Palette.MOREORES_RADIANT_AMETHYST, ModTrimMaterials.RADIANT_AMETHYST);
        add(materials, TrimMaterials.Palette.MOREORES_LIMESTONE, ModTrimMaterials.LIMESTONE);
        add(materials, TrimMaterials.Palette.MOREORES_MOONSTONE, ModTrimMaterials.MOONSTONE);
        add(materials, TrimMaterials.Palette.MOREORES_ALEXANDRITE, ModTrimMaterials.ALEXANDRITE);
        add(materials, TrimMaterials.Palette.MOREORES_QUARTSIDIAN, ModTrimMaterials.QUARTSIDIAN);
        add(materials, TrimMaterials.Palette.MOREORES_OPAL, ModTrimMaterials.OPAL);
        add(materials, TrimMaterials.Palette.MOREORES_RED_BERYL, ModTrimMaterials.RED_BERYL);

        TRIM_MATERIAL_MODELS = List.copyOf(materials);
    }

    private static void add(
            List<ItemModelGenerators.TrimMaterialData> list,
            TrimMaterials.Palette palette,
            ResourceKey<TrimMaterial> material) {

        list.add(new ItemModelGenerators.TrimMaterialData(palette, material));
    }
}
