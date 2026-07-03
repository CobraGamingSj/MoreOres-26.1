package org.cobra.moreores.networking.block.data;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.gui.screen.AbstractGemMachineMenu;
import org.cobra.moreores.world.block.entity.gem.AbstractGemMachineBlockEntity;
import org.cobra.moreores.client.gui.screen.GemCrystallizerMenu;
import org.cobra.moreores.client.gui.screen.GemPurifierMenu;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;

public record GemMachineButtonPayload(int buttonID, BlockPos pos) implements CustomPacketPayload {
    public static final Type<GemMachineButtonPayload> ID = new Type<>(MoreOresModInitializer.id("button_click"));

    public static final StreamCodec<RegistryFriendlyByteBuf, GemMachineButtonPayload> PACKET_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, GemMachineButtonPayload::buttonID,
                    BlockPos.STREAM_CODEC, GemMachineButtonPayload::pos,
                    GemMachineButtonPayload::new
            );

    public void handle(ServerPlayNetworking.Context context) {
        ServerLevel world = context.server().overworld();

        if(world.getBlockEntity(pos) instanceof AbstractGemMachineBlockEntity<?> blockEntity) {
            switch (buttonID) {
                case 0 -> blockEntity.start();
                case 1 -> blockEntity.pause();
                case 2 -> blockEntity.resume();
                case 3 -> blockEntity.stop();
                }

                if(context.player().containerMenu instanceof AbstractGemMachineMenu menu && menu.getPos().equals(pos)) {
                    switch (buttonID) {
                        case 0 -> blockEntity.start();
                        case 1 -> blockEntity.pause();
                        case 2 -> blockEntity.resume();
                        case 3 -> blockEntity.stop();
                    }
                }
            }

        MoreOresModInitializer.LOGGER.info("Received button click with ID: {} at {}", buttonID, "[" + pos.getX() + " " + pos.getY() + " " + pos.getZ() + "]");
        }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
