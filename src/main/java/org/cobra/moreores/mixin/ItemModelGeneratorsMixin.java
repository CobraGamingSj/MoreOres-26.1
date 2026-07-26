package org.cobra.moreores.mixin;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import org.cobra.moreores.world.item.equipment.trim.ModMaterialAssetGroup;
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

        add(materials, ModMaterialAssetGroup.RUBY, ModTrimMaterials.RUBY);
        add(materials, ModMaterialAssetGroup.RADIANT, ModTrimMaterials.RADIANT);
        add(materials, ModMaterialAssetGroup.SAPPHIRE, ModTrimMaterials.SAPPHIRE);
        add(materials, ModMaterialAssetGroup.GREEN_SAPPHIRE, ModTrimMaterials.GREEN_SAPPHIRE);
        add(materials, ModMaterialAssetGroup.BLUE_GARNET, ModTrimMaterials.BLUE_GARNET);
        add(materials, ModMaterialAssetGroup.PINK_GARNET, ModTrimMaterials.PINK_GARNET);
        add(materials, ModMaterialAssetGroup.GREEN_GARNET, ModTrimMaterials.GREEN_GARNET);
        add(materials, ModMaterialAssetGroup.KYAWTHUITE, ModTrimMaterials.KYAWTHUITE);
        add(materials, ModMaterialAssetGroup.TOPAZ, ModTrimMaterials.TOPAZ);
        add(materials, ModMaterialAssetGroup.WHITE_TOPAZ, ModTrimMaterials.WHITE_TOPAZ);
        add(materials, ModMaterialAssetGroup.PERIDOT, ModTrimMaterials.PERIDOT);
        add(materials, ModMaterialAssetGroup.JADE, ModTrimMaterials.JADE);
        add(materials, ModMaterialAssetGroup.PYROPE, ModTrimMaterials.PYROPE);
        add(materials, ModMaterialAssetGroup.CRIMSON_GARNET, ModTrimMaterials.CRIMSON_GARNET);
        add(materials, ModMaterialAssetGroup.CRYSTALLITE, ModTrimMaterials.CRYSTALLITE);
        add(materials, ModMaterialAssetGroup.RADIANT_AMETHYST, ModTrimMaterials.RADIANT_AMETHYST);
        add(materials, ModMaterialAssetGroup.LIMESTONE, ModTrimMaterials.LIMESTONE);
        add(materials, ModMaterialAssetGroup.MOONSTONE, ModTrimMaterials.MOONSTONE);
        add(materials, ModMaterialAssetGroup.ALEXANDRITE, ModTrimMaterials.ALEXANDRITE);
        add(materials, ModMaterialAssetGroup.QUARTSIDIAN, ModTrimMaterials.QUARTSIDIAN);
        add(materials, ModMaterialAssetGroup.OPAL, ModTrimMaterials.OPAL);
        add(materials, ModMaterialAssetGroup.RED_BERYL, ModTrimMaterials.RED_BERYL);

        TRIM_MATERIAL_MODELS = List.copyOf(materials);
    }

    private static void add(
            List<ItemModelGenerators.TrimMaterialData> list,
            MaterialAssetGroup assets,
            ResourceKey<TrimMaterial> material) {

        list.add(new ItemModelGenerators.TrimMaterialData(assets, material));
    }
}
