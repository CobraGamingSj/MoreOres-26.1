package org.cobra.moreores.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.gui.screen.SkillTreeMenu;

import java.util.List;

public record SkillStatePayload(List<ItemStack> gems, List<Long> expiresAt) implements CustomPacketPayload {
    public static final Type<SkillStatePayload> TYPE = new Type<>(MoreOresModInitializer.id("skill_state_payload"));

    public static final StreamCodec<
            RegistryFriendlyByteBuf,
            SkillStatePayload
            > STREAM_CODEC =
            StreamCodec.composite(
                    ItemStack.OPTIONAL_STREAM_CODEC
                            .apply(
                                    ByteBufCodecs.list()
                            ),
                    SkillStatePayload::gems,
                    ByteBufCodecs.VAR_LONG.apply(
                            ByteBufCodecs.list()
                    ),
                    SkillStatePayload::expiresAt,
                    SkillStatePayload::new
            );

    public void handlePacket(ClientPlayNetworking.Context context) {
        context.client().execute(() -> {

            Player player = context.player();

            if (player.containerMenu
                    instanceof SkillTreeMenu menu) {

                menu.applySkillGems(
                        gems(),
                        expiresAt()
                );
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
