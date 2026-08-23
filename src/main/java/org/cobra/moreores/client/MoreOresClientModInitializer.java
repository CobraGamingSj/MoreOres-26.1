package org.cobra.moreores.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.gui.screen.GemCrystallizerScreen;
import org.cobra.moreores.client.gui.screen.GemPurifierScreen;
import org.cobra.moreores.client.gui.screen.ModMenuType;
import org.cobra.moreores.client.render.block.entity.GemCrystallizerBlockEntityRenderer;
import org.cobra.moreores.client.render.block.entity.GemPurifierBlockEntityRenderer;
import org.cobra.moreores.client.render.item.entity.GemArrowEntityRenderer;
import org.cobra.moreores.client.render.item.model.GemArrowEntityModel;
import org.cobra.moreores.networking.ModS2CNetworkRegistries;
import org.cobra.moreores.world.block.entity.ModBlockEntityTypes;
import org.cobra.moreores.world.entity.ModEntityTypes;
import org.cobra.moreores.world.item.ModItems;

public class MoreOresClientModInitializer implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {

        ModS2CNetworkRegistries.registerClientS2C();

        MenuScreens.register(ModMenuType.GEM_PURIFIER, GemPurifierScreen::new);
        MenuScreens.register(ModMenuType.GEM_CRYSTALLIZER, GemCrystallizerScreen::new);

        BlockEntityRenderers.register(ModBlockEntityTypes.GEM_PURIFIER, GemPurifierBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntityTypes.GEM_CRYSTALLIZER, GemCrystallizerBlockEntityRenderer::new);
        ModelLayerRegistry.registerModelLayer(GemArrowEntityModel.ARROW, GemArrowEntityModel::getTexturedModelData);
        EntityRenderers.register(ModEntityTypes.GEM_ARROW, GemArrowEntityRenderer::new);
    }
}