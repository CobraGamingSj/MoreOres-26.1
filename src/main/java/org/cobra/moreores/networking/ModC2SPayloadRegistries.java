package org.cobra.moreores.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.networking.block.data.GemCrystallizerBlockData;
import org.cobra.moreores.networking.block.data.GemPurifierBlockData;
import org.cobra.moreores.networking.block.data.GemMachineControlButtonPayload;
import org.cobra.moreores.networking.block.data.MachineStatusDataPayload;
import org.cobra.moreores.networking.item.data.EnergyIngotC2SPayload;

import static org.cobra.moreores.MoreOresModInitializer.LOGGER;

public class ModC2SPayloadRegistries {

    static {
        registerC2S(GemMachineControlButtonPayload.ID, GemMachineControlButtonPayload.PACKET_CODEC);
        registerC2S(MachineStatusDataPayload.ID, MachineStatusDataPayload.PACKET_CODEC);
        registerC2S(GemPurifierBlockData.ID, GemPurifierBlockData.PACKET_CODEC);
        registerC2S(GemCrystallizerBlockData.ID, GemCrystallizerBlockData.PACKET_CODEC);
        registerC2S(EnergyIngotC2SPayload.ID, EnergyIngotC2SPayload.PACKET_CODEC);
    }
    
    public static<T extends CustomPacketPayload> void registerC2S(CustomPacketPayload.Type<T> id, StreamCodec<RegistryFriendlyByteBuf, T> packetCodec) {
        PayloadTypeRegistry.serverboundPlay().register(id, packetCodec);
    }
    
    public static void registerC2SPackets() {
        LOGGER.info("Loading ModC2SPackets for " + MoreOresModInitializer.MOD_ID + " mod.");
    }
    
}
