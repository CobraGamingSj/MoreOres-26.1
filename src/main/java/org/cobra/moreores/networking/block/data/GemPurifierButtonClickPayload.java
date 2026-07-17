package org.cobra.moreores.networking.block.data;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.gui.screen.AbstractGemMachineMenu;
import org.cobra.moreores.world.block.entity.gem.machine.AbstractGemMachineBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;

public record GemPurifierButtonClickPayload(int buttonIndex, BlockPos pos) implements CustomPacketPayload {
    public static final Type<GemPurifierButtonClickPayload> ID = new Type<>(MoreOresModInitializer.id("button_click"));

    public static final StreamCodec<RegistryFriendlyByteBuf, GemPurifierButtonClickPayload> PACKET_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, GemPurifierButtonClickPayload::buttonIndex,
                    BlockPos.STREAM_CODEC, GemPurifierButtonClickPayload::pos,
                    GemPurifierButtonClickPayload::new
            );

    public void handle(ServerPlayNetworking.Context context) {
        ServerLevel world = context.server().overworld();

        if(world.getBlockEntity(pos) instanceof AbstractGemMachineBlockEntity<?> blockEntity) {
            switch (buttonIndex) {
                case 0 -> blockEntity.start();
                case 1 -> blockEntity.pause();
                case 2 -> blockEntity.resume();
                case 3 -> blockEntity.stop();
                }

                if((context.player().containerMenu instanceof AbstractGemMachineMenu<?> menu && menu.getBlockPos().equals(pos))) {
                    switch (buttonIndex) {
                        case 0 -> blockEntity.start();
                        case 1 -> blockEntity.pause();
                        case 2 -> blockEntity.resume();
                        case 3 -> blockEntity.stop();
                    }
                }
            }

        MoreOresModInitializer.LOGGER.info("Received button click with ID: {} at {}", buttonIndex, "[" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + "]");

        }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
