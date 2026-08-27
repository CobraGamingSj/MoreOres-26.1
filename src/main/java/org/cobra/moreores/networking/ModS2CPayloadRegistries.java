package org.cobra.moreores.networking;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.networking.block.data.*;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static org.cobra.moreores.MoreOresModInitializer.LOGGER;

@SuppressWarnings("Same PaR VAL")
public class ModS2CPayloadRegistries {
    static {
       registerS2C(GemMachineEnergyDataPayload.TYPE, GemMachineEnergyDataPayload.STREAM_CODEC);
       registerS2C(GemPurifierFluidDataPayload.TYPE, GemPurifierFluidDataPayload.STREAM_CODEC);
       registerS2C(GemPurifierDataSynchronizer.TYPE, GemPurifierDataSynchronizer.STREAM_CODEC);
       registerS2C(GemCrystallizerDataSynchronizer.TYPE, GemCrystallizerDataSynchronizer.STREAM_CODEC);
       registerS2C(MachineStatusDataPayload.TYPE, MachineStatusDataPayload.STREAM_CODEC);
       registerS2C(ScreenGhostRenderingS2CPacket.TYPE, ScreenGhostRenderingS2CPacket.STREAM_CODEC);
    }

    public static<T extends CustomPacketPayload> void registerS2C(CustomPacketPayload.Type<T> id, StreamCodec<RegistryFriendlyByteBuf, T> packetCodec) {
        PayloadTypeRegistry.clientboundPlay().register(id, packetCodec);
    }

    public static void registerS2CPackets() {
        LOGGER.info("Loading ModS2CPackets for " + MoreOresModInitializer.MOD_ID + " mod.");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }
}
