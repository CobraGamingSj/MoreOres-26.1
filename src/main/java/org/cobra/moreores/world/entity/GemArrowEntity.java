package org.cobra.moreores.world.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.cobra.moreores.world.item.ModItems;
import org.jspecify.annotations.Nullable;

public class GemArrowEntity extends AbstractArrow {
    public GemArrowEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
        super(entityType, world);
    }
    
    public GemArrowEntity(Level world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(ModEntityTypes.GEM_ARROW, owner, world, stack, shotFrom);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.GEM_ARROW);
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        if(!(level() instanceof ServerLevel level)) return;
        PrimedTnt tnt = new PrimedTnt(EntityTypes.TNT, level);
        if(!level.getBlockState(hitResult.getBlockPos()).isAir()) {
            tnt.setPos(this.getX(), this.getY(), this.getZ() + 1);
            tnt.setFuse(0);
            level.addFreshEntity(tnt);
            return;
        } else {
            tnt.discard();
            this.discard();
        }
        super.onHitBlock(hitResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        Level level = entity.level();
        
        if(level.isClientSide()) {
            return;
        }
        
        entity.hurtServer((ServerLevel) entity.level(), entity.damageSources().arrow(this, null), 20);
        LightningBolt lightningEntity = new LightningBolt(EntityTypes.LIGHTNING_BOLT, entity.level());
        lightningEntity.setPosRaw(entity.getX(), entity.getY(), entity.getZ());
        entity.level().addFreshEntity(lightningEntity);
        this.discard();
        super.onHitEntity(entityHitResult);
    }
}