package org.cobra.moreores.world.entity;

import net.cobra.api.talents.effect.SkillEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class MobEffectSkillEffect implements SkillEffect {
    private final MobEffectInstance effect;

    public MobEffectSkillEffect(MobEffectInstance effect) {
        this.effect = effect;
    }

    @Override
    public void apply(Player user, @Nullable LivingEntity target, int level) {
        user.addEffect(effect);
    }
}
