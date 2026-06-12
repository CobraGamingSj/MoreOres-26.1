package org.cobra.moreores.networking.block.data;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.block.entity.gem.GemPurifierBlockEntity;
import org.cobra.moreores.client.gui.screen.GemPurifierMenu;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record GemPurifierDataSynchronizer(long energy, FluidVariant fluidVariant, long fluid, BlockPos blockPos) implements CustomPacketPayload {

    public static final Type<GemPurifierDataSynchronizer> ID = new Type<>(MoreOresModInitializer.id("pos_sync"));

    public void handlePacket(ClientPlayNetworking.Context context) {
        ClientLevel world = context.client().level;
        if (world == null) return;

        if (world.getBlockEntity(this.blockPos) instanceof GemPurifierBlockEntity blockEntity) {
            blockEntity.setEnergyLevel(this.energy);
            blockEntity.setWaterLevel(this.fluidVariant, this.fluid);

            if (context.player().containerMenu instanceof GemPurifierMenu screenHandler && screenHandler.blockEntity.getBlockPos().equals(this.blockPos)) {
                blockEntity.setEnergyLevel(this.energy);
                blockEntity.setWaterLevel(this.fluidVariant, this.fluid);
            }
        }
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, GemPurifierDataSynchronizer> PACKET_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.LONG, GemPurifierDataSynchronizer::energy,
                    FluidVariant.PACKET_CODEC, GemPurifierDataSynchronizer::fluidVariant,
                    ByteBufCodecs.LONG, GemPurifierDataSynchronizer::fluid,
                    BlockPos.STREAM_CODEC, GemPurifierDataSynchronizer::blockPos,
                    GemPurifierDataSynchronizer::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
