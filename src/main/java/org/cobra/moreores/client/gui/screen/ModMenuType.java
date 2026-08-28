package org.cobra.moreores.client.gui.screen;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.networking.block.data.GemCrystallizerDataSynchronizer;
import org.cobra.moreores.networking.block.data.GemPurifierDataSynchronizer;

public class ModMenuType {

    public static final MenuType<GemPurifierMenu> GEM_PURIFIER =
            register("gem_purifier_block", GemPurifierMenu::new, GemPurifierDataSynchronizer.STREAM_CODEC
            );

    public static final MenuType<GemCrystallizerMenu> GEM_CRYSTALLIZER =
            register("gem_crystallizer_block", GemCrystallizerMenu::new, GemCrystallizerDataSynchronizer.STREAM_CODEC);

    private static <S extends AbstractContainerMenu, D extends CustomPacketPayload> ExtendedMenuType<S, D> register(String id, ExtendedMenuType.ExtendedFactory<S, D> factory, StreamCodec<? super RegistryFriendlyByteBuf, D> packetCodec) {
        return Registry.register(BuiltInRegistries.MENU, MoreOresModInitializer.id(id), new ExtendedMenuType<>(factory, packetCodec));
    }

    public static void register() {
        MoreOresModInitializer.LOGGER.info("Loading ModMenuType for " + MoreOresModInitializer.MOD_ID + " mod.");
    }
}
