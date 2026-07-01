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
       registerS2C(GemMachineEnergyDataPayload.ID, GemMachineEnergyDataPayload.PACKET_CODEC);
       registerS2C(GemPurifierFluidDataPayload.ID, GemPurifierFluidDataPayload.PACKET_CODEC);
       registerS2C(GemPurifierDataSynchronizer.ID, GemPurifierDataSynchronizer.PACKET_CODEC);
       registerS2C(GemCrystallizerDataSynchronizer.ID, GemCrystallizerDataSynchronizer.PACKET_CODEC);
       registerS2C(MachineStatusDataPayload.ID, MachineStatusDataPayload.PACKET_CODEC);
    }

    public static<T extends CustomPacketPayload> void registerS2C(CustomPacketPayload.Type<T> id, StreamCodec<RegistryFriendlyByteBuf, T> packetCodec) {
        PayloadTypeRegistry.clientboundPlay().register(id, packetCodec);
    }

    public static void registerS2CPackets() {
        LOGGER.info("Loading ModS2CPackets for " + MoreOresModInitializer.MOD_ID + " mod.");
    }
}
