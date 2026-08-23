package org.cobra.moreores.client.gui.widget;

import org.cobra.moreores.networking.block.data.GemMachineButtonPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.network.chat.Component;

public class MachineButton extends Button {
    private final Identifier backgroundTexture;
    private final int buttonIndex;
    private final BlockPos blockPos;

    public MachineButton(int x, int y, Component message, Identifier backgroundTexture, int buttonIndex, BlockPos blockPos) {
        super(x, y, 32, 32, message, _ -> {

        }, DEFAULT_NARRATION);
        this.backgroundTexture = backgroundTexture;
        this.buttonIndex = buttonIndex;
        this.blockPos = blockPos;
    }

    @Override
    public void onPress(InputWithModifiers input) {
        ClientPlayNetworking.send(new GemMachineButtonPayload(buttonIndex, blockPos));
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float delta) {
        extractor.blit(RenderPipelines.GUI_TEXTURED, backgroundTexture, getX(), getY(), 0, 0, this.getWidth(), this.getHeight(), 32, 32);
        extractor.outline(getX(), getY(), 32, 32, CommonColors.DARK_GRAY);
        if(isHovered()) {
            extractor.outline(getX(), getY(), 32, 32, CommonColors.BLACK);
        }
    }
}
