package org.cobra.moreores.mixin;

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
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.waypoints.WaypointTransmitter;
import org.cobra.moreores.data.SkillManager;
import org.cobra.moreores.util.Skills;
import org.cobra.moreores.world.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements WaypointTransmitter, Attackable {
    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "checkTotemDeathProtection", at = @At("HEAD"), cancellable = true)
    private void use(DamageSource killingDamage, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        DeathProtection protection = new DeathProtection(
                List.of(new ClearAllStatusEffectsConsumeEffect(),
                new ApplyStatusEffectsConsumeEffect(
                        List.of(
                                new MobEffectInstance(MobEffects.REGENERATION, 900, 1),
                                new MobEffectInstance(MobEffects.ABSORPTION, 100, 1),
                                new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0)
                        )
                )));

        if (!(entity instanceof ServerPlayer player)) {
            return;
        }

        if (!SkillManager.isActive(player, Skills.IMMORTAL)) {
            return;
        }

        protection.applyEffects(new ItemStack(ModItems.QUARTSIDIAN), player);
        cir.setReturnValue(true);
    }
}