package org.cobra.moreores.networking;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.networking.block.data.GemCrystallizerDataSynchronizer;
import org.cobra.moreores.networking.block.data.GemMachineEnergyDataPayload;
import org.cobra.moreores.networking.block.data.GemPurifierFluidDataPayload;
import org.cobra.moreores.networking.block.data.GemPurifierDataSynchronizer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import static org.cobra.moreores.MoreOresModInitializer.LOGGER;

public class ModS2CNetworksRegistries {

    public static void registerClientS2C(){
        ClientPlayNetworking.registerGlobalReceiver(GemMachineEnergyDataPayload.ID, GemMachineEnergyDataPayload::handlePacket);
        ClientPlayNetworking.registerGlobalReceiver(GemPurifierFluidDataPayload.ID, GemPurifierFluidDataPayload::handlePacket);
        ClientPlayNetworking.registerGlobalReceiver(GemPurifierDataSynchronizer.ID, GemPurifierDataSynchronizer::handlePacket);
        ClientPlayNetworking.registerGlobalReceiver(GemCrystallizerDataSynchronizer.ID, GemCrystallizerDataSynchronizer::handlePacket);
    }

    public static void register() {
        LOGGER.info("Loading ModS2CNetworks for" + MoreOresModInitializer.MOD_ID + " mod.");
    }
}
