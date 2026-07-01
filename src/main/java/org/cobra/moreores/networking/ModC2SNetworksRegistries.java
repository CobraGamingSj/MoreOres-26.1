package org.cobra.moreores.networking;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.networking.block.data.GemCrystallizerBlockData;
import org.cobra.moreores.networking.block.data.GemPurifierBlockData;
import org.cobra.moreores.networking.block.data.GemMachineControlButtonPayload;
import org.cobra.moreores.networking.block.data.MachineStatusDataPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.cobra.moreores.networking.item.data.EnergyIngotC2SPayload;

import static org.cobra.moreores.MoreOresModInitializer.LOGGER;

public class ModC2SNetworksRegistries {

    public static void registerServerC2S() {
        ServerPlayNetworking.registerGlobalReceiver(GemMachineControlButtonPayload.ID, GemMachineControlButtonPayload::handle);
        ServerPlayNetworking.registerGlobalReceiver(MachineStatusDataPayload.ID, MachineStatusDataPayload::handle);
        ServerPlayNetworking.registerGlobalReceiver(GemPurifierBlockData.ID, GemPurifierBlockData::handle);
        ServerPlayNetworking.registerGlobalReceiver(GemCrystallizerBlockData.ID, GemCrystallizerBlockData::handle);
        ServerPlayNetworking.registerGlobalReceiver(EnergyIngotC2SPayload.ID, EnergyIngotC2SPayload::handle);
    }

    public static void register() {
        LOGGER.info("Loading ModServerC2SNetworks for" + MoreOresModInitializer.MOD_ID + " mod.");
    }
}
