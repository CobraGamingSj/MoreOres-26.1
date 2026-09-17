package org.cobra.moreores.mixin;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
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
        List<ItemModelGenerators.TrimMaterialData> materials = new ArrayList<>(TRIM_MATERIAL_MODELS);

        add(materials, TrimMaterials.Palette.RUBY, ModTrimMaterials.RUBY);
        add(materials, TrimMaterials.Palette.RADIANT, ModTrimMaterials.RADIANT);
        add(materials, TrimMaterials.Palette.SAPPHIRE, ModTrimMaterials.SAPPHIRE);
        add(materials, TrimMaterials.Palette.GREEN_SAPPHIRE, ModTrimMaterials.GREEN_SAPPHIRE);
        add(materials, TrimMaterials.Palette.BLUE_GARNET, ModTrimMaterials.BLUE_GARNET);
        add(materials, TrimMaterials.Palette.PINK_GARNET, ModTrimMaterials.PINK_GARNET);
        add(materials, TrimMaterials.Palette.GREEN_GARNET, ModTrimMaterials.GREEN_GARNET);
        add(materials, TrimMaterials.Palette.KYAWTHUITE, ModTrimMaterials.KYAWTHUITE);
        add(materials, TrimMaterials.Palette.TOPAZ, ModTrimMaterials.TOPAZ);
        add(materials, TrimMaterials.Palette.WHITE_TOPAZ, ModTrimMaterials.WHITE_TOPAZ);
        add(materials, TrimMaterials.Palette.PERIDOT, ModTrimMaterials.PERIDOT);
        add(materials, TrimMaterials.Palette.JADE, ModTrimMaterials.JADE);
        add(materials, TrimMaterials.Palette.PYROPE, ModTrimMaterials.PYROPE);
        add(materials, TrimMaterials.Palette.CRIMSON_GARNET, ModTrimMaterials.CRIMSON_GARNET);
        add(materials, TrimMaterials.Palette.CRYSTALLITE, ModTrimMaterials.CRYSTALLITE);
        add(materials, TrimMaterials.Palette.RADIANT_AMETHYST, ModTrimMaterials.RADIANT_AMETHYST);
        add(materials, TrimMaterials.Palette.LIMESTONE, ModTrimMaterials.LIMESTONE);
        add(materials, TrimMaterials.Palette.MOONSTONE, ModTrimMaterials.MOONSTONE);
        add(materials, TrimMaterials.Palette.ALEXANDRITE, ModTrimMaterials.ALEXANDRITE);
        add(materials, TrimMaterials.Palette.QUARTSIDIAN, ModTrimMaterials.QUARTSIDIAN);
        add(materials, TrimMaterials.Palette.OPAL, ModTrimMaterials.OPAL);
        add(materials, TrimMaterials.Palette.RED_BERYL, ModTrimMaterials.RED_BERYL);

        TRIM_MATERIAL_MODELS = List.copyOf(materials);
    }

    private static void add(
            List<ItemModelGenerators.TrimMaterialData> list,
            TrimMaterials.Palette palette,
            ResourceKey<TrimMaterial> material) {

        list.add(new ItemModelGenerators.TrimMaterialData(palette, material));
    }
}
