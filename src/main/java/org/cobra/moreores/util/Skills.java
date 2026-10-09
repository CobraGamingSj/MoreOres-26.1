package org.cobra.moreores.util;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.entity.AttributeModifierSkillEffect;
import org.cobra.moreores.world.entity.ImmortalSkillEffect;
import org.cobra.moreores.world.entity.MobEffectSkillEffect;
import org.cobra.moreores.world.entity.ResistanceMobEffectSkillEffect;
import org.cobra.moreores.world.item.ModItems;

import java.util.List;

public class Skills {

    public static final Skill STRENGTH = create("strength", null, ModItems.CRIMSON_GARNET, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 30L * 24L * 60L * 60L * 1000L);

    public static final Skill NIGHT_VISION = create("night_vision", STRENGTH, ModItems.RADIANT_AMETHYST, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill REGENERATION = create("regeneration", STRENGTH, ModItems.GRANDIDIERITE, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill RESISTANCE = create("resistance", NIGHT_VISION, ModItems.CRYSTALLITE,
            new ResistanceMobEffectSkillEffect(), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill HEALTH_BOOST = create("health_boost", RESISTANCE, ModItems.ORANGE_ZIRCON, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.HEALTH_BOOST, -1, 1, false, false, false)
    ), 24L * 60L * 60L * 1000L);

    public static final Skill SPEED = create("speed", REGENERATION, ModItems.OPAL, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill HASTE = create("haste", SPEED, ModItems.ALEXANDRITE, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill JUMP_BOOST = create("jump_boost", HASTE, ModItems.MOONSTONE, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.STRENGTH, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill SLOW_FALL = create("slow_fall", HEALTH_BOOST, ModItems.LIMESTONE, new MobEffectSkillEffect(
            new MobEffectInstance(MobEffects.SLOW_FALLING, -1, 1, false, false, false)
    ), 3L * 24L * 60L * 60L * 1000L);

    public static final Skill KNOCKBACK_RESISTANCE = createForAttribute("knockback_resistance", SLOW_FALL, ModItems.RED_BERYL,
            Attributes.KNOCKBACK_RESISTANCE, 2, AttributeModifier.Operation.ADD_VALUE, 3L * 24L * 60L * 60L * 1000L);

    public static final Skill IMMORTAL = create("immortal", JUMP_BOOST, ModItems.QUARTSIDIAN,
            new ImmortalSkillEffect(), 3L * 24L * 60L * 60L * 1000L);

    public static final List<Skill> SKILLS = List.of(
            STRENGTH,
            NIGHT_VISION,
            REGENERATION,
            RESISTANCE,
            SPEED,
            HEALTH_BOOST,
            HASTE,
            SLOW_FALL,
            JUMP_BOOST,
            KNOCKBACK_RESISTANCE,
            IMMORTAL
    );

    private static Skill create(String id, Skill prerequisite, Item requiredGem, SkillEffect effect, long timeDuration) {
        return new Skill(MoreOresModInitializer.id(id), Component.translatable("skill." + MoreOresModInitializer.MOD_ID + "." + id), prerequisite == null ? null : prerequisite.id(), requiredGem, effect, timeDuration);
    }

    private static Skill createForAttribute(String id, Skill prerequisite, Item requiredGem, Holder<Attribute> attribute, double amount, AttributeModifier.Operation ope, long duration) {
        return create(id, prerequisite, requiredGem, new AttributeModifierSkillEffect(MoreOresModInitializer.id(id), attribute, amount, ope), duration);
    }
}