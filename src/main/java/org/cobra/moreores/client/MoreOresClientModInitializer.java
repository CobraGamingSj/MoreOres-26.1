package org.cobra.moreores.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import org.cobra.moreores.client.gui.screen.*;
import org.cobra.moreores.client.render.block.entity.GemCrystallizerBlockEntityRenderer;
import org.cobra.moreores.client.render.block.entity.GemPurifierBlockEntityRenderer;
import org.cobra.moreores.client.render.item.entity.GemArrowRenderer;
import org.cobra.moreores.client.render.item.entity.RadiantSwordRenderer;
import org.cobra.moreores.client.render.item.model.GemArrowEntityModel;
import org.cobra.moreores.client.render.item.model.RadiantSwordModel;
import org.cobra.moreores.networking.ModS2CNetworkRegistries;
import org.cobra.moreores.world.block.entity.ModBlockEntityTypes;
import org.cobra.moreores.world.entity.ModEntityTypes;
import org.lwjgl.glfw.GLFW;

public class MoreOresClientModInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModKeyMappings.register();

//        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
//            if(ModKeyMappings.SKILL_TREE_KEY.isDown()) {
//                client.setScreenAndShow(new SkillTreeScreen());
//            }
//        });

        ModS2CNetworkRegistries.registerClientS2C();

        MenuScreens.register(ModMenuType.GEM_PURIFIER, GemPurifierScreen::new);
        MenuScreens.register(ModMenuType.GEM_CRYSTALLIZER, GemCrystallizerScreen::new);
        MenuScreens.register(ModMenuType.SKILL_NODE_TREE, SkillTreeScreen::new);

        BlockEntityRenderers.register(ModBlockEntityTypes.GEM_PURIFIER, GemPurifierBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntityTypes.GEM_CRYSTALLIZER, GemCrystallizerBlockEntityRenderer::new);
        ModelLayerRegistry.registerModelLayer(GemArrowEntityModel.ARROW, GemArrowEntityModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(RadiantSwordModel.MODEL_LAYER, RadiantSwordModel::getTexturedModelData);
        EntityRenderers.register(ModEntityTypes.GEM_ARROW, GemArrowRenderer::new);
        EntityRenderers.register(ModEntityTypes.RADIANT_SWORD, RadiantSwordRenderer::new);
    }
}