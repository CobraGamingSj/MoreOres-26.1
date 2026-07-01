package org.cobra.moreores.client.gui.screen;

import net.minecraft.world.inventory.MenuType;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.core.registry.ResourceHelper;
import org.cobra.moreores.networking.block.data.GemCrystallizerDataSynchronizer;
import org.cobra.moreores.networking.block.data.GemPurifierDataSynchronizer;

public class ModMenuType {

    public static final ResourceHelper.MenuResource RESOURCE = ResourceHelper.MenuResource.INSTANCE;
    
    public static final MenuType<GemPurifierMenu> GEM_PURIFIER =
            RESOURCE.register("gem_purifier", GemPurifierMenu::new, GemPurifierDataSynchronizer.PACKET_CODEC
            );

    public static final MenuType<GemCrystallizerMenu> GEM_CRYSTALLIZER =
            RESOURCE.register("gem_crystallizer", GemCrystallizerMenu::new, GemCrystallizerDataSynchronizer.PACKET_CODEC);
    
    public static void register() {
        MoreOresModInitializer.LOGGER.info("Loading ModMenuType for " + MoreOresModInitializer.MOD_ID + " mod.");
    }
}
