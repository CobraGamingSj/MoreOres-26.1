package org.cobra.moreores.world.item.equipment;

import com.google.common.collect.ImmutableMap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.Equippable;

public class ArmorItem extends Item {

    private static final Map<ArmorMaterial, List<MobEffectInstance>> ARMOR_EFFECTS = new ImmutableMap.Builder<ArmorMaterial, List<MobEffectInstance>>()
            .put(ModArmorMaterials.RADIANT, List.of(
                    new MobEffectInstance(MobEffects.REGENERATION, -1, 3, false, false, false),
                    new MobEffectInstance(MobEffects.HEALTH_BOOST, -1, 9, false, false, false)
            )).build();

    public ArmorItem(Properties settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel serverLevel, Entity entity, @Nullable EquipmentSlot slot) {
        if(serverLevel.isClientSide()) {
            return;
        }
        if(entity instanceof Player player) {
            if(hasFullSuitOfArmorOn(player)) {
                if(player.fallDistance >= 5) {
                    int duration = (int) player.fallDistance;
                    player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, duration, 2, false, false, false));
                    if(slot == EquipmentSlot.FEET) {
                        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
                        boots.hurtWithoutBreaking((int) player.fallDistance / 2, player);
                    }
                } else {
                    player.removeEffect(MobEffects.SLOW_FALLING);
                }
                evaluateArmorEffects(player);
            } else {
                player.removeEffect(MobEffects.REGENERATION);
                player.removeEffect(MobEffects.HEALTH_BOOST);
                player.removeEffect(MobEffects.SLOW_FALLING);
            }
        }
        super.inventoryTick(stack, serverLevel, entity, slot);
    }

    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<ArmorMaterial, List<MobEffectInstance>> entry : ARMOR_EFFECTS.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            List<MobEffectInstance> mapStatusEffects = entry.getValue();

            if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffects);
            }
        }
    }

    private void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial, List<MobEffectInstance> mapStatusEffect) {
        boolean hasPlayerEffect = mapStatusEffect.stream().allMatch(statusEffectInstance -> player.hasEffect(statusEffectInstance.getEffect()));

        if(!hasPlayerEffect) {
            for (MobEffectInstance instance : mapStatusEffect) {
                player.addEffect(new MobEffectInstance(instance.getEffect(),
                        instance.getDuration(), instance.getAmplifier(), instance.isAmbient(), instance.isVisible()));
            }
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

        return !helmet.isEmpty() && !chestplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

        Equippable equippableComponentBoots = boots.getComponents().get(DataComponents.EQUIPPABLE);
        Equippable equippableComponentLeggings = leggings.getComponents().get(DataComponents.EQUIPPABLE);
        Equippable equippableComponentBreastplate = chestplate.getComponents().get(DataComponents.EQUIPPABLE);
        Equippable equippableComponentHelmet = helmet.getComponents().get(DataComponents.EQUIPPABLE);

        return equippableComponentBoots.assetId().get().equals(material.assetId()) &&
                equippableComponentLeggings.assetId().get().equals(material.assetId()) &&
                equippableComponentBreastplate.assetId().get().equals(material.assetId()) &&
                equippableComponentHelmet.assetId().get().equals(material.assetId());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, display, builder, tooltipFlag);
        Minecraft client = Minecraft.getInstance();
        Player player = client.player;
        if (player != null && hasFullSuitOfArmorOn(player)) {
            EquipmentSlot slot = player.getEquipmentSlotForItem(stack);
            if(player.getItemBySlot(slot).equals(stack)) {
                builder.accept(Component.literal("Applied Effects: ")
                        .withStyle(ChatFormatting.YELLOW));
                List<MobEffectInstance> effects = ARMOR_EFFECTS.get(ModArmorMaterials.RADIANT);
                if(effects != null) {
                    for (MobEffectInstance effect : effects) {
                        builder.accept(Component.literal(" ").append(Component.translatable(effect.getDescriptionId())).append(" " + (effect.getAmplifier() + 1)).withStyle(ChatFormatting.RED));
                    }
                }
                Equippable self = stack.getComponents().get(DataComponents.EQUIPPABLE);
                if (self != null && self.assetId().isPresent()
                        && self.assetId().get().equals(ModArmorMaterials.RADIANT.assetId())
                        && self.slot() == EquipmentSlot.FEET) {
                    builder.accept(Component.literal("Fall Protection Activated").withStyle(ChatFormatting.BLUE));
                }
            }
        }
    }
}
