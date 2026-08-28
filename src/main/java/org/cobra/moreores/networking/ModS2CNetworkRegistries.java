package org.cobra.moreores.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.networking.block.data.*;

import static org.cobra.moreores.MoreOresModInitializer.LOGGER;

public class ModS2CNetworkRegistries {

    public static void registerClientS2C(){
        ClientPlayNetworking.registerGlobalReceiver(GemMachineEnergyDataPayload.TYPE, GemMachineEnergyDataPayload::handlePacket);
        ClientPlayNetworking.registerGlobalReceiver(GemPurifierFluidDataPayload.TYPE, GemPurifierFluidDataPayload::handlePacket);
        ClientPlayNetworking.registerGlobalReceiver(GemPurifierDataSynchronizer.TYPE, GemPurifierDataSynchronizer::handlePacket);
        ClientPlayNetworking.registerGlobalReceiver(GemCrystallizerDataSynchronizer.TYPE, GemCrystallizerDataSynchronizer::handlePacket);
        ClientPlayNetworking.registerGlobalReceiver(ScreenGhostRenderingS2CPacket.TYPE, ScreenGhostRenderingS2CPacket::handlePacket);
    }

    public static void register() {
        LOGGER.info("Loading ModS2CNetworks for" + MoreOresModInitializer.MOD_ID + " mod.");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }
}