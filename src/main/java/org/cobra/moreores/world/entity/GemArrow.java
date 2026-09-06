package org.cobra.moreores.world.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.cobra.moreores.world.item.ModItems;
import org.jspecify.annotations.Nullable;

public class GemArrow extends AbstractArrow {
    public GemArrow(EntityType<? extends AbstractArrow> entityType, Level world) {
        super(entityType, world);
    }
    
    public GemArrow(Level world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(ModEntityTypes.GEM_ARROW, owner, world, stack, shotFrom);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.GEM_ARROW);
    }

//    @Override
//    protected void onHitBlock(BlockHitResult hitResult) {
//        if(!(level() instanceof ServerLevel level)) return;
//        PrimedTnt tnt = new PrimedTnt(EntityTypes.TNT, level);
//        if(!level.getBlockState(hitResult.getBlockPos()).isAir()) {
//            tnt.setPos(this.getX(), this.getY(), this.getZ() + 1);
//            tnt.setFuse(0);
//            level.addFreshEntity(tnt);
//            return;
//        } else {
//            tnt.discard();
//            this.discard();
//        }
//        super.onHitBlock(hitResult);
//    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        Level level = entity.level();
        
        if(level.isClientSide()) {
            return;
        }
        
        entity.hurtServer((ServerLevel) entity.level(), entity.damageSources().arrow(this, null), 5);
        LightningBolt lightningEntity = new LightningBolt(EntityTypes.LIGHTNING_BOLT, entity.level());
        lightningEntity.setPosRaw(entity.getX(), entity.getY(), entity.getZ());
        level.addFreshEntity(lightningEntity);
        int randomSpawnTime = level.getRandom().nextInt(10, tickCount * 20);
        if (!(entity instanceof Player)) {
            entity.setPos(entity.getX(), (entity.getY() * randomSpawnTime) / 20, entity.getZ());
        }
        this.discard();
        super.onHitEntity(entityHitResult);
    }
}