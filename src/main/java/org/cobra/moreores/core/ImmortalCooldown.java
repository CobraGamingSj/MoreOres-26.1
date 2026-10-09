
package org.cobra.moreores.core;

import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ImmortalCooldown {

    private static final long COOLDOWN_MS = 10_000L;

    private static final Map<UUID, Long> COOLDOWNS =
            new HashMap<>();

    private ImmortalCooldown() {
    }

    public static boolean isOnCooldown(ServerPlayer player) {
        Long expiresAt = COOLDOWNS.get(player.getUUID());

        if (expiresAt == null) {
            return false;
        }

        if (System.currentTimeMillis() >= expiresAt) {
            COOLDOWNS.remove(player.getUUID());
            return false;
        }

        return true;
    }

    public static void start(ServerPlayer player) {
        COOLDOWNS.put(
                player.getUUID(),
                System.currentTimeMillis() + COOLDOWN_MS
        );
    }
}