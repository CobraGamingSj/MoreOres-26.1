package org.cobra.moreores.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DeathProtection;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.waypoints.WaypointTransmitter;
import org.cobra.moreores.core.ImmortalCooldown;
import org.cobra.moreores.core.SkillManager;
import org.cobra.moreores.util.Skills;
import org.cobra.moreores.world.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements WaypointTransmitter, Attackable {
    @Unique
    private float moreores$healthBeforeDamage;

    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "hurtServer", at = @At("HEAD"))
    private void moreores$captureHealth(
            ServerLevel level,
            DamageSource source,
            float damage,
            CallbackInfoReturnable<Boolean> cir
    ) {
        this.moreores$healthBeforeDamage = ((LivingEntity) (Object) this).getHealth();
    }

    @Inject(method = "hurtServer", at = @At("TAIL"))
    private void moreores$lifeSteal(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) return;
        if (!(source.getEntity() instanceof ServerPlayer attacker)) return;
        if (!SkillManager.isActive(attacker, Skills.LIFE_STEAL)) return;

        LivingEntity target = (LivingEntity) (Object) this;
        float damageDealt = this.moreores$healthBeforeDamage - target.getHealth();

        if (damageDealt > 0) {
            attacker.heal(damageDealt * 0.5F);
        }
    }

    @Inject(method = "checkTotemDeathProtection", at = @At("HEAD"), cancellable = true)
    private void use(DamageSource killingDamage, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!(entity instanceof ServerPlayer player)) {
            return;
        }

        if (!SkillManager.isActive(player, Skills.IMMORTAL)) {
            return;
        }

        if(ImmortalCooldown.isOnCooldown(player)) {
            return;
        }

        DeathProtection protection = new DeathProtection(
                List.of(
                new ApplyStatusEffectsConsumeEffect(
                        List.of(
                                new MobEffectInstance(MobEffects.REGENERATION, 900, 1),
                                new MobEffectInstance(MobEffects.ABSORPTION, 100, 1),
                                new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0)
                        )
                )));

        player.setHealth(1.0F);
        protection.applyEffects(new ItemStack(ModItems.QUARTSIDIAN), player);
        ImmortalCooldown.start(player);
        cir.setReturnValue(true);
    }
}