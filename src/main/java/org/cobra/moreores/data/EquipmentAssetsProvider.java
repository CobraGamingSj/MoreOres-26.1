package org.cobra.moreores.data;

import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.cobra.moreores.world.item.equipment.ModEquipmentAssets;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class EquipmentAssetsProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public EquipmentAssetsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    private static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> consumer) {
        for (ResourceKey<EquipmentAsset> key : ModEquipmentAssets.EQUIPMENT_ASSETS) {
            consumer.accept(key, 
                    EquipmentClientInfo.builder()
                            .addHumanoidLayers(key.identifier())
                            .addLayers(EquipmentClientInfo.LayerType.NAUTILUS_BODY, 
                                    new EquipmentClientInfo.Layer(key.identifier()))
                            .build());
        }
    }

    @Override
    public CompletableFuture<?> run(final CachedOutput cache) {
        Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> equipmentAssets = new HashMap<>();
        bootstrap((id, asset) -> {
            if (equipmentAssets.putIfAbsent(id, asset) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + id);
            }
        });
        return DataProvider.saveAll(cache, EquipmentClientInfo.CODEC, this.pathProvider::json, equipmentAssets);
    }

    @Override
    public String getName() {
        return "Equipment Asset Definitions";
    }
}
