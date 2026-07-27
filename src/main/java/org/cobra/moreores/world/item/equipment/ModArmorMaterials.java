package org.cobra.moreores.world.item.equipment;

import org.cobra.moreores.tags.ModItemTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import java.util.EnumMap;

public interface ModArmorMaterials {

    ArmorMaterial RUBY = new ArmorMaterial(38, Util.make(new EnumMap(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 5);
        map.put(ArmorType.LEGGINGS, 8);
        map.put(ArmorType.CHESTPLATE, 10);
        map.put(ArmorType.HELMET, 5);
        map.put(ArmorType.BODY, 13);
    }), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 4.0F, 0.2F, ModItemTags.REPAIRS_RUBY_ARMOR, ModEquipmentAssets.RUBY);
    ArmorMaterial SAPPHIRE = new ArmorMaterial(39, Util.make(new EnumMap(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 7);
        map.put(ArmorType.LEGGINGS, 10);
        map.put(ArmorType.CHESTPLATE, 12);
        map.put(ArmorType.HELMET, 7);
        map.put(ArmorType.BODY, 15);
    }), 17, SoundEvents.ARMOR_EQUIP_LEATHER, 4.5F, 0.3F, ModItemTags.REPAIRS_SAPPHIRE_ARMOR, ModEquipmentAssets.SAPPHIRE);
    ArmorMaterial RADIANT = new ArmorMaterial(81, Util.make(new EnumMap(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 15);
        map.put(ArmorType.LEGGINGS, 18);
        map.put(ArmorType.CHESTPLATE, 20);
        map.put(ArmorType.HELMET, 15);
        map.put(ArmorType.BODY, 23);
    }), 23, SoundEvents.ARMOR_EQUIP_GENERIC, 7.0F, 0.8F, ModItemTags.REPAIRS_RADIANT_ARMOR, ModEquipmentAssets.RADIANT);
}
