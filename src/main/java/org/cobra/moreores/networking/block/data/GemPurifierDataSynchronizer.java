package org.cobra.moreores.networking.block.data;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.gui.screen.GemPurifierMenu;
import org.cobra.moreores.world.block.entity.gem.machine.GemPurifierBlockEntity;

public record GemPurifierDataSynchronizer(long energyAmount, int redstone, FluidVariant fluidVariant, long fluidAmount, BlockPos blockPos) implements CustomPacketPayload {

    public static final Type<GemPurifierDataSynchronizer> TYPE = new Type<>(MoreOresModInitializer.id("pos_sync"));

    public void handlePacket(ClientPlayNetworking.Context context) {
        ClientLevel world = context.client().level;
        if (world == null) return;

        if (world.getBlockEntity(this.blockPos) instanceof GemPurifierBlockEntity blockEntity) {
            blockEntity.setEnergyAmount(this.energyAmount);
            blockEntity.setRedstoneAmount(this.redstone);
            blockEntity.setFluid(this.fluidVariant, this.fluidAmount);

            if (context.player().containerMenu instanceof GemPurifierMenu screenHandler && screenHandler.getBlockPos().equals(this.blockPos)) {
                blockEntity.setEnergyAmount(this.energyAmount);
                blockEntity.setRedstoneAmount(this.redstone);
                blockEntity.setFluid(this.fluidVariant, this.fluidAmount);
            }
        }
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, GemPurifierDataSynchronizer> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.LONG, GemPurifierDataSynchronizer::energyAmount,
                    ByteBufCodecs.INT, GemPurifierDataSynchronizer::redstone,
                    FluidVariant.PACKET_CODEC, GemPurifierDataSynchronizer::fluidVariant,
                    ByteBufCodecs.LONG, GemPurifierDataSynchronizer::fluidAmount,
                    BlockPos.STREAM_CODEC, GemPurifierDataSynchronizer::blockPos,
                    GemPurifierDataSynchronizer::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
