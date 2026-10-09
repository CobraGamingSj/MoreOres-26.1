package org.cobra.moreores.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.cobra.moreores.core.SkillManager;
import org.cobra.moreores.util.Skills;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "attack", at = @At("TAIL"))
    private void attack(Entity entity, CallbackInfo ci) {
        Player player = (Player)(Object)this;
        if(!(player instanceof ServerPlayer serverPlayer)) return;
        if(!(entity instanceof LivingEntity livingEntity)) return;

        if(!SkillManager.isActive(serverPlayer, Skills.FIRE_SWORD)) return;

        if(!serverPlayer.getMainHandItem().is(ItemTags.WEAPON_ENCHANTABLE)) return;

        livingEntity.igniteForSeconds(15);
    }
}