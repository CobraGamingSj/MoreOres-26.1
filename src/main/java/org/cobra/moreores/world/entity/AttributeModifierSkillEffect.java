package org.cobra.moreores.world.entity;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.util.SkillEffect;

public record AttributeModifierSkillEffect(Identifier modifierId, Holder<Attribute> attribute, double amount,
                                           AttributeModifier.Operation operation) implements SkillEffect {
    public AttributeModifierSkillEffect(Identifier modifierId, Holder<Attribute> attribute, double amount, AttributeModifier.Operation operation) {
        this.attribute = attribute;
        this.amount = amount;
        this.operation = operation;
        this.modifierId = MoreOresModInitializer.id("skill/" + modifierId.getPath());
    }

    @Override
    public void apply(Player player) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) return;
        instance.removeModifier(modifierId);
        instance.addPermanentModifier(new AttributeModifier(modifierId, amount, operation));
    }

    @Override
    public void remove(Player player) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) return;
        instance.removeModifier(modifierId);
    }
}