package org.cobra.moreores.networking.block.data;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.block.entity.gem.AbstractGemPCBlockEntity;
import org.cobra.moreores.client.gui.screen.AbstractGemPFMenu;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record GemPFEnergyDataPayload(long energy, BlockPos blockPos) implements CustomPacketPayload {
    public static final Type<GemPFEnergyDataPayload> ID = new Type<>(MoreOresModInitializer.id("pos_energy"));

    public void handlePacket(ClientPlayNetworking.Context context) {
        ClientLevel world = context.client().level;
        if (world == null) return;

        if (world.getBlockEntity(this.blockPos) instanceof AbstractGemPCBlockEntity<?> blockEntity) {
            blockEntity.setEnergyLevel(this.energy);

            if (context.player().containerMenu instanceof AbstractGemPFMenu screenHandler && screenHandler.getPos().equals(this.blockPos)) {
                blockEntity.setEnergyLevel(this.energy);
            }
        }
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, GemPFEnergyDataPayload> PACKET_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.LONG, GemPFEnergyDataPayload::energy,
                    BlockPos.STREAM_CODEC, GemPFEnergyDataPayload::blockPos,
                    GemPFEnergyDataPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
