package org.cobra.moreores.world.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

public class ResistanceMobEffectSkillEffect extends MobEffectSkillEffect {
    private final Map<Player, Integer> cooldowns = new WeakHashMap<>();
    private final Random random = new Random();

    public ResistanceMobEffectSkillEffect() {
        super(new MobEffectInstance(MobEffects.RESISTANCE, 40, 0, false, false, false));
    }

    @Override
    public void apply(Player user) {
        if (!(user instanceof ServerPlayer player)) {
            return;
        }

        if (player.getHealth() > player.getMaxHealth() / 2.0F) {
            cooldowns.remove(player);
            return;
        }

        int cooldown = cooldowns.getOrDefault(player, 0);

        if (cooldown > 0) {
            cooldowns.put(player, cooldown - 1);
            return;
        }

        super.apply(player);

        cooldowns.put(player, random.nextInt(10, 21));
    }

    @Override
    public void remove(Player user) {
        cooldowns.remove(user);
        super.remove(user);
    }
}
