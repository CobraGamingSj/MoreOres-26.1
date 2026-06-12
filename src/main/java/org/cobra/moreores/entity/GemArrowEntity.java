package org.cobra.moreores.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.cobra.moreores.item.ModItems;
import org.jspecify.annotations.Nullable;

public class GemArrowEntity extends AbstractArrow {
    public GemArrowEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
        super(entityType, world);
    }
    
    public GemArrowEntity(Level world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(ModEntityTypes.GEM_ARROW_ENTITY, owner, world, stack, shotFrom);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.GEM_ARROW);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        Level world = entity.level();
        
        if(world.isClientSide()) {
            return;
        }
        
        entity.hurtServer((ServerLevel) entity.level(), entity.damageSources().arrow(this, null), 20);
        LightningBolt lightningEntity = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level());
        lightningEntity.setPosRaw(entity.getX(), entity.getY(), entity.getZ());
        entity.level().addFreshEntity(lightningEntity);
        this.discard();
        super.onHitEntity(entityHitResult);
    }
}