package org.cobra.moreores.client;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import org.cobra.moreores.world.block.entity.ModBlockEntityTypes;
import org.cobra.moreores.client.gui.screen.GemCrystallizerScreen;
import org.cobra.moreores.client.gui.screen.GemPurifierScreen;
import org.cobra.moreores.client.gui.screen.ModMenuType;
import org.cobra.moreores.client.render.block.entity.GemCrystallizerBlockEntityRenderer;
import org.cobra.moreores.client.render.block.entity.GemPurifierBlockEntityRenderer;
import org.cobra.moreores.client.render.item.entity.GemArrowEntityRenderer;
import org.cobra.moreores.client.render.item.model.GemArrowEntityModel;
import org.cobra.moreores.world.entity.ModEntityTypes;
import org.cobra.moreores.networking.ModS2CNetworkRegistries;
import net.fabricmc.api.ClientModInitializer;

public class MoreOresClientModInitializer implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {

        ModS2CNetworkRegistries.registerClientS2C();

        MenuScreens.register(ModMenuType.GEM_PURIFIER, GemPurifierScreen::new);
        MenuScreens.register(ModMenuType.GEM_CRYSTALLIZER, GemCrystallizerScreen::new);

        BlockEntityRenderers.register(ModBlockEntityTypes.GEM_PURIFIER_BLOCK_ENTITY, GemPurifierBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntityTypes.GEM_CRYSTALLIZE_BLOCK_ENTITY, GemCrystallizerBlockEntityRenderer::new);
        ModelLayerRegistry.registerModelLayer(GemArrowEntityModel.ARROW, GemArrowEntityModel::getTexturedModelData);
        EntityRenderers.register(ModEntityTypes.GEM_ARROW_ENTITY, GemArrowEntityRenderer::new);
    }
}