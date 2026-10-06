package org.cobra.moreores.util;

import net.cobra.api.talents.effect.SkillEffect;
import net.cobra.api.talents.util.Skill;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.entity.MobEffectSkillEffect;

public class Skills {
    public static final Skill<MobEffectSkillEffect> STRENGTH = create("strength", null, 0, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 0);

    private static <P extends SkillEffect, T extends SkillEffect> Skill<T> create(String id, Skill<P> prerequisite, int requiredLevel, T effect, int buttonIndex) {
        return new Skill<T>(MoreOresModInitializer.id(id), prerequisite, requiredLevel, effect, buttonIndex);
    }
}