package org.cobra.moreores.util;

import net.minecraft.world.entity.player.Player;

public interface SkillEffect {

    void apply(final Player player);

    void remove(final Player player);
}
