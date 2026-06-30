package org.cobra.moreores.world.item.equipment;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import org.cobra.moreores.MoreOresModInitializer;

import java.util.List;

public interface ModEquipmentAssets {
    ResourceKey<EquipmentAsset> RUBY = of("ruby");
    ResourceKey<EquipmentAsset> SAPPHIRE = of("sapphire");
    ResourceKey<EquipmentAsset> RADIANT = of("radiant");

    List<ResourceKey<EquipmentAsset>> EQUIPMENT_ASSETS = List.of(RUBY, SAPPHIRE, RADIANT);
    
    static ResourceKey<EquipmentAsset> of(String id) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, MoreOresModInitializer.id(id));
    }
}
