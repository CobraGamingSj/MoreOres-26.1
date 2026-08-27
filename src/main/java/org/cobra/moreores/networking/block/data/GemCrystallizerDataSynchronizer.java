package org.cobra.moreores.networking.block.data;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.block.entity.gem.machine.GemCrystallizerBlockEntity;
import org.cobra.moreores.client.gui.screen.GemPurifierMenu;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record GemCrystallizerDataSynchronizer(long energyAmount, int redstoneDust, int radiantDust, BlockPos blockPos) implements CustomPacketPayload {

    public static final Type<GemCrystallizerDataSynchronizer> TYPE = new Type<>(MoreOresModInitializer.id("data_pos_sync"));

    public void handlePacket(ClientPlayNetworking.Context context) {
        ClientLevel world = context.client().level;
        if (world == null) return;

        if (world.getBlockEntity(this.blockPos) instanceof GemCrystallizerBlockEntity blockEntity) {
            blockEntity.setEnergyAmount(this.energyAmount);
            blockEntity.setRedstoneAmount(this.redstoneDust);
            blockEntity.setRadiantDust(this.radiantDust);

            if (context.player().containerMenu instanceof GemPurifierMenu screenHandler && screenHandler.getBlockPos().equals(this.blockPos)) {
                blockEntity.setEnergyAmount(this.energyAmount);
                blockEntity.setRedstoneAmount(this.redstoneDust);
                blockEntity.setRadiantDust(this.radiantDust);
            }
        }
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, GemCrystallizerDataSynchronizer> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.LONG, GemCrystallizerDataSynchronizer::energyAmount,
                    ByteBufCodecs.INT, GemCrystallizerDataSynchronizer::redstoneDust,
                    ByteBufCodecs.INT, GemCrystallizerDataSynchronizer::radiantDust,
                    BlockPos.STREAM_CODEC, GemCrystallizerDataSynchronizer::blockPos,
                    GemCrystallizerDataSynchronizer::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
