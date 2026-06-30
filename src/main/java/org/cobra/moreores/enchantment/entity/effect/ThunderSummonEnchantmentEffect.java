package org.cobra.moreores.enchantment.entity.effect;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record ThunderSummonEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<ThunderSummonEnchantmentEffect> CODEC = MapCodec.unit(ThunderSummonEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity user, Vec3 pos) {
        if (level == 1) {
            EntityTypes.LIGHTNING_BOLT.spawn(world, user.blockPosition(), EntitySpawnReason.TRIGGERED);
        }
        if (level == 2) {
            EntityTypes.LIGHTNING_BOLT.spawn(world, user.blockPosition(), EntitySpawnReason.TRIGGERED);
            EntityTypes.LIGHTNING_BOLT.spawn(world, user.blockPosition(), EntitySpawnReason.TRIGGERED);
        }
        if (level == 3) {
            EntityTypes.LIGHTNING_BOLT.spawn(world, user.blockPosition(), EntitySpawnReason.TRIGGERED);
            EntityTypes.LIGHTNING_BOLT.spawn(world, user.blockPosition(), EntitySpawnReason.TRIGGERED);
            EntityTypes.TNT.spawn(world, user.blockPosition(), EntitySpawnReason.TRIGGERED).setFuse(0);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
