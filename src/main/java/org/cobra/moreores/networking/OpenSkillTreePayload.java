package org.cobra.moreores.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.gui.screen.SkillTreeMenu;
import org.cobra.moreores.data.SkillManager;
import org.jspecify.annotations.Nullable;

public record OpenSkillTreePayload() implements CustomPacketPayload {

    public static final Type<OpenSkillTreePayload> TYPE =
            new Type<>(
                    MoreOresModInitializer.id("open_skill_tree_payload")
            );

    public static final StreamCodec<
            RegistryFriendlyByteBuf,
            OpenSkillTreePayload
            > STREAM_CODEC =
            StreamCodec.unit(new OpenSkillTreePayload());

    public void handle(
            ServerPlayNetworking.Context context
    ) {
        context.server().execute(() -> {
            context.player().openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("Skill Tree");
                }

                @Override
                public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
                    return new SkillTreeMenu(containerId, inventory);
                }
            });
            SkillManager.sendSkillState(context.player());
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}