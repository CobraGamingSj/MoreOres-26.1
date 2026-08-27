package org.cobra.moreores.networking;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.networking.block.data.GemCrystallizerBlockData;
import org.cobra.moreores.networking.block.data.GemPurifierBlockData;
import org.cobra.moreores.networking.block.data.GemMachineButtonPayload;
import org.cobra.moreores.networking.block.data.MachineStatusDataPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.cobra.moreores.networking.item.data.EnergyIngotC2SPayload;

import static org.cobra.moreores.MoreOresModInitializer.LOGGER;

public class ModC2SNetworkRegistries {

    public static void registerServerC2S() {
        ServerPlayNetworking.registerGlobalReceiver(GemMachineButtonPayload.TYPE, GemMachineButtonPayload::handle);
        ServerPlayNetworking.registerGlobalReceiver(MachineStatusDataPayload.TYPE, MachineStatusDataPayload::handle);
        ServerPlayNetworking.registerGlobalReceiver(GemPurifierBlockData.TYPE, GemPurifierBlockData::handle);
        ServerPlayNetworking.registerGlobalReceiver(GemCrystallizerBlockData.TYPE, GemCrystallizerBlockData::handle);
        ServerPlayNetworking.registerGlobalReceiver(EnergyIngotC2SPayload.TYPE, EnergyIngotC2SPayload::handle);
    }

    public static void register() {
        LOGGER.info("Loading ModServerC2SNetworks for" + MoreOresModInitializer.MOD_ID + " mod.");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }
}
