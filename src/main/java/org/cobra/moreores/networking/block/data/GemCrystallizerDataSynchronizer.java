package org.cobra.moreores.networking.block.data;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.block.entity.gem.GemCrystallizerBlockEntity;
import org.cobra.moreores.client.gui.screen.GemPurifierMenu;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record GemCrystallizerDataSynchronizer(long energy, int redstoneDust, int dustCount, BlockPos blockPos) implements CustomPacketPayload {

    public static final Type<GemCrystallizerDataSynchronizer> ID = new Type<>(MoreOresModInitializer.id("data_pos_sync"));

    public void handlePacket(ClientPlayNetworking.Context context) {
        ClientLevel world = context.client().level;
        if (world == null) return;

        if (world.getBlockEntity(this.blockPos) instanceof GemCrystallizerBlockEntity blockEntity) {
            blockEntity.setEnergyLevel(this.energy);
            blockEntity.setRedstone(this.redstoneDust);
            blockEntity.setDustCount(this.dustCount);

            if (context.player().containerMenu instanceof GemPurifierMenu screenHandler && screenHandler.blockEntity.getBlockPos().equals(this.blockPos)) {
                blockEntity.setEnergyLevel(this.energy);
                blockEntity.setRedstone(this.redstoneDust);
                blockEntity.setDustCount(this.dustCount);
            }
        }
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, GemCrystallizerDataSynchronizer> PACKET_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.LONG, GemCrystallizerDataSynchronizer::energy,
                    ByteBufCodecs.INT, GemCrystallizerDataSynchronizer::redstoneDust,
                    ByteBufCodecs.INT, GemCrystallizerDataSynchronizer::dustCount,
                    BlockPos.STREAM_CODEC, GemCrystallizerDataSynchronizer::blockPos,
                    GemCrystallizerDataSynchronizer::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
