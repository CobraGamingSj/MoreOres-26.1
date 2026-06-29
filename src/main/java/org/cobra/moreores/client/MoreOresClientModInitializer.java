package org.cobra.moreores.client;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.block.entity.ModBlockEntityType;
import org.cobra.moreores.client.gui.screen.GemCrystallizerScreen;
import org.cobra.moreores.client.gui.screen.GemPurifierScreen;
import org.cobra.moreores.client.gui.screen.ModMenuType;
import org.cobra.moreores.client.render.block.entity.GemInfusionBlockEntityRenderer;
import org.cobra.moreores.client.render.block.entity.GemPurifierBlockEntityRenderer;
import org.cobra.moreores.client.render.item.entity.GemArrowEntityRenderer;
import org.cobra.moreores.client.render.item.model.GemArrowEntityModel;
import org.cobra.moreores.world.entity.ModEntityTypes;
import org.cobra.moreores.networking.ModS2CNetworks;
import net.fabricmc.api.ClientModInitializer;
import org.lwjgl.glfw.GLFW;

public class MoreOresClientModInitializer implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {

        ModS2CNetworks.registerClientS2C();

        BlockRenderLayerMap.putBlock(ModBlocks.GEM_PURIFIER_BLOCK, ChunkSectionLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ModBlocks.GEM_CRYSTALLIZER_BLOCK, ChunkSectionLayer.TRANSLUCENT);

        MenuScreens.register(ModMenuType.GEM_PURIFIER, GemPurifierScreen::new);
        MenuScreens.register(ModMenuType.GEM_CRYSTALLIZER, GemCrystallizerScreen::new);

        BlockEntityRenderers.register(ModBlockEntityType.GEM_PURIFIER_BLOCK_ENTITY, GemPurifierBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntityType.GEM_CRYSTALLIZE_BLOCK_ENTITY, GemInfusionBlockEntityRenderer::new);
        ModelLayerRegistry.registerModelLayer(GemArrowEntityModel.ARROW, GemArrowEntityModel::getTexturedModelData);
        EntityRenderers.register(ModEntityTypes.GEM_ARROW_ENTITY, GemArrowEntityRenderer::new);
    }
}