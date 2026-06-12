package org.cobra.moreores.core.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.cobra.moreores.MoreOresModInitializer;

public class RewardState extends SavedData {
    private final Set<UUID> playerClaimedRewards = new HashSet<>();
    public static final Codec<RewardState> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.list(Codec.STRING).fieldOf("players").forGetter(state ->
                            state.playerClaimedRewards.stream().map(UUID::toString).toList()
                    )
            ).apply(instance, list -> {
                RewardState state = new RewardState();
                for (String s : list) {
                    state.playerClaimedRewards.add(UUID.fromString(s));
                }
                return state;
            })
    );

    public static final SavedDataType<RewardState> TYPE =
            new SavedDataType<>(
                    MoreOresModInitializer.id("moreores_birthday_rewards"),
                    RewardState::new,
                    CODEC,
                    DataFixTypes.PLAYER // Not required
            );

    public boolean hasClaimed(UUID uuid) {
        return playerClaimedRewards.contains(uuid);
    }

    public void setClaimed(UUID uuid) {
        playerClaimedRewards.add(uuid);
        setDirty();
    }

    public static RewardState get(ServerLevel world) {
       return world.getDataStorage().computeIfAbsent(TYPE);
    }
}
