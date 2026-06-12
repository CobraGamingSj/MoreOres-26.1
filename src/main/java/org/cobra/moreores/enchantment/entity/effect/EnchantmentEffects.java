package org.cobra.moreores.enchantment.entity.effect;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import org.cobra.moreores.MoreOresModInitializer;

import static org.cobra.moreores.MoreOresModInitializer.LOGGER;

public class EnchantmentEffects {

    public static final MapCodec<? extends EnchantmentEntityEffect> THUNDER_STRIKER = register("thunder_striker", ThunderSummonEnchantmentEffect.CODEC);

    private static MapCodec<? extends EnchantmentEntityEffect> register(String id, MapCodec<? extends EnchantmentEntityEffect> codec) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, id), codec);
    }

    public static void register() {
        LOGGER.info("Loading EnchantmentEffects for " + MoreOresModInitializer.MOD_ID + " mod.");
    }
}
