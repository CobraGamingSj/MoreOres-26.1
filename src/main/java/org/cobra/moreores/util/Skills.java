package org.cobra.moreores.util;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.entity.MobEffectSkillEffect;
import org.cobra.moreores.world.item.ModItems;

import java.util.List;

public class Skills {

    public static final Skill STRENGTH = create("strength", null, ModItems.CRIMSON_GARNET, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill HEALTH_BOOST = create("health_boost", STRENGTH, ModItems.RED_BERYL, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.HEALTH_BOOST, -1, 1, false, false, false)
    ), 24L * 60L * 60L * 1000L);

    public static final Skill NIGHT_VISION = create("night_vison", null, ModItems.CRIMSON_GARNET, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill REGENERATION = create("regeneration", null, ModItems.CRIMSON_GARNET, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill RESISTANCE = create("resistance", null, ModItems.CRIMSON_GARNET, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill SPEED = create("speed", null, ModItems.CRIMSON_GARNET, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill HASTE = create("haste", null, ModItems.CRIMSON_GARNET, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill JUMP_BOOST = create("jump_boost", null, ModItems.CRIMSON_GARNET, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill SLOW_FALL = create("slow_fall", null, ModItems.CRIMSON_GARNET, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill KNOCKBACK_RESISTANCE = create("knockback_resistance", null, ModItems.CRIMSON_GARNET, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill IMMORTAL = create("immortal", null, ModItems.CRIMSON_GARNET, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final List<Skill> SKILLS = List.of(STRENGTH, HEALTH_BOOST, NIGHT_VISION, REGENERATION, RESISTANCE, SPEED, HASTE, JUMP_BOOST, SLOW_FALL, KNOCKBACK_RESISTANCE, IMMORTAL);

    private static Skill create(String id, Skill prerequisite, Item requiredGem, SkillEffect effect, long timeDuration) {
        return new Skill(MoreOresModInitializer.id(id), prerequisite == null ? null : prerequisite.id(), requiredGem, effect, timeDuration);
    }
}