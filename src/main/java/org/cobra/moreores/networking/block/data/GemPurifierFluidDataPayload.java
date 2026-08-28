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

public record GemPurifierFluidDataPayload(FluidVariant fluidVariant, long fluidAmount, BlockPos blockPos) implements CustomPacketPayload {
    public static final Type<GemPurifierFluidDataPayload> TYPE = new Type<>(MoreOresModInitializer.id("pos_fluid"));

    public void handlePacket(ClientPlayNetworking.Context context) {
        ClientLevel world = context.client().level;
        if (world == null) return;
        if (world.getBlockEntity(this.blockPos) instanceof GemPurifierBlockEntity blockEntity) {
            blockEntity.setFluid(this.fluidVariant, this.fluidAmount);

            if (context.player().containerMenu instanceof GemPurifierMenu screenHandler && screenHandler.getBlockPos().equals(this.blockPos)) {
                blockEntity.setFluid(this.fluidVariant, this.fluidAmount);
            }
        }
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, GemPurifierFluidDataPayload> STREAM_CODEC =
            StreamCodec.composite(
                    FluidVariant.PACKET_CODEC, GemPurifierFluidDataPayload::fluidVariant,
                    ByteBufCodecs.LONG, GemPurifierFluidDataPayload::fluidAmount,
                    BlockPos.STREAM_CODEC, GemPurifierFluidDataPayload::blockPos,
                    GemPurifierFluidDataPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
