package org.cobra.moreores.world.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class ImmortalSkillEffect extends MobEffectSkillEffect {
    public ImmortalSkillEffect() {
        super(new MobEffectInstance(MobEffects.ABSORPTION, -1, 2, false, false, false));
    }

    @Override
    public void apply(Player user) {

    }

    @Override
    public void remove(Player player) {

    }
}