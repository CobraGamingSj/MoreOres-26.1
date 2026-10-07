package org.cobra.moreores.util;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;

public record Skill(Identifier id, @Nullable Identifier prerequisite, Item requiredGem, SkillEffect effect, long duration) {
}
