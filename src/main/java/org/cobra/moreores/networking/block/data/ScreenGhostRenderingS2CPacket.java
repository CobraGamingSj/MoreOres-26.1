package org.cobra.moreores.networking.block.data;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.gui.screen.AbstractGemMachineScreen;
import org.cobra.moreores.client.gui.screen.GemPurifierScreen;

public record ScreenGhostRenderingS2CPacket(ItemStack result) implements CustomPacketPayload {
    public static final Type<ScreenGhostRenderingS2CPacket> TYPE = new Type<>(MoreOresModInitializer.id("screen_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ScreenGhostRenderingS2CPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC, ScreenGhostRenderingS2CPacket::result,
            ScreenGhostRenderingS2CPacket::new
    );

    public void handlePacket(ClientPlayNetworking.Context context) {
        if(context.client().gui.screen() instanceof AbstractGemMachineScreen<?, ?> screen) {
            screen.setPreviewResultStack(result);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}