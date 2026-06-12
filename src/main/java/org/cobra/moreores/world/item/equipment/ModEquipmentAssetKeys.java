package org.cobra.moreores.world.item.equipment;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.cobra.moreores.MoreOresModInitializer;

public interface ModEquipmentAssetKeys {
    ResourceKey<? extends Registry<EquipmentAsset>> REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));

    ResourceKey<EquipmentAsset> RUBY = register("ruby");
    ResourceKey<EquipmentAsset> SAPPHIRE = register("sapphire");
    ResourceKey<EquipmentAsset> RADIANT = register("radiant");

    static ResourceKey<EquipmentAsset> register(String id) {
        return ResourceKey.create(REGISTRY_KEY, MoreOresModInitializer.id(id));
    }
}
