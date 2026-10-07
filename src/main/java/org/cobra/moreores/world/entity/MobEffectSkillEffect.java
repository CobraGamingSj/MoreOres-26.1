package org.cobra.moreores.world.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.cobra.moreores.util.SkillEffect;

public class MobEffectSkillEffect implements SkillEffect {
    private final MobEffectInstance effect;

    public MobEffectSkillEffect(MobEffectInstance effect) {
        this.effect = effect;
    }

    @Override
    public void apply(Player user) {
        user.addEffect(effect);
    }

    @Override
    public void remove(Player player) {
        player.removeEffect(effect.getEffect());
    }
}
