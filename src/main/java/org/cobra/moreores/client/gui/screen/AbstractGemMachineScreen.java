package org.cobra.moreores.client.gui.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.cobra.moreores.client.gui.widget.MachineButtonWidget;
import org.cobra.moreores.networking.block.data.MachineStatusDataPayload;
import org.cobra.moreores.world.block.entity.gem.machine.AbstractGemMachineBlockEntity;
import org.lwjgl.glfw.GLFW;

public abstract class AbstractGemMachineScreen<T extends AbstractGemMachineBlockEntity<?>, M extends AbstractGemMachineMenu<T>> extends AbstractContainerScreen<M> {
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;

    public AbstractGemMachineScreen(M menu, Inventory inventory, Component title, int imageWidth, int imageHeight) {
        super(menu, inventory, title, imageWidth, imageHeight);
    }

    @Override
    protected void init() {
        super.init();
        titleLabelY = 1000;
        inventoryLabelY = 1000;

        Button start = this.addButton("gui.button.gp.start", 0, this.leftPos + getStartButtonPosX(), this.topPos + getStartButtonPosY(), getStartButtonTexture(), menu instanceof GemPurifierMenu ? Component.literal("Start Purification") : Component.literal("Start Crystallization"));
        Button pause = this.addButton("gui.button.gp.pause", 1, this.leftPos + getPauseButtonPosX(), this.topPos + getPauseButtonPosY(), getPauseButtonTexture(), menu instanceof GemPurifierMenu ? Component.literal("Pause Purification") : Component.literal("Pause Crystallization"));
        Button resume = this.addButton("gui.button.gp.resume", 2, this.leftPos + getResumeButtonPosX(), this.topPos + getResumeButtonPosY(), getResumeButtonTexture(), menu instanceof GemPurifierMenu ? Component.literal("Resume Purification") : Component.literal("Resume Crystallization"));
        Button stop = this.addButton("gui.button.gp.stop", 3, this.leftPos + getStopButtonPosX(), this.topPos + getStopButtonPosY(), getStopButtonTexture(), menu instanceof GemPurifierMenu ? Component.literal("Stop Purification") : Component.literal("Stop Crystallization"));

        start.visible = true;
        pause.visible = true;
        resume.visible = true;
        stop.visible = true;
    }

    private Button addButton(String translation, int buttonIndex, int leftPos, int topPos, Identifier background, Component tooltip) {
        Button button = new MachineButtonWidget(leftPos, topPos, Component.translatable(translation), background, buttonIndex, menu.getBlockPos());
        button.setTooltip(Tooltip.create(tooltip));
        return this.addRenderableWidget(button);
    }

    protected abstract Identifier getBackgroundTexture();
    protected abstract Identifier getStartButtonTexture();
    protected abstract Identifier getPauseButtonTexture();
    protected abstract Identifier getResumeButtonTexture();
    protected abstract Identifier getStopButtonTexture();

    protected abstract int getStartButtonPosX();
    protected abstract int getStartButtonPosY();

    protected abstract int getPauseButtonPosX();
    protected abstract int getPauseButtonPosY();

    protected abstract int getResumeButtonPosX();
    protected abstract int getResumeButtonPosY();

    protected abstract int getStopButtonPosX();
    protected abstract int getStopButtonPosY();
    
    @Override
    public boolean keyPressed(KeyEvent input) {
        if(input.input() == GLFW.GLFW_KEY_S) {
            sendPolishControlPacket("start");
            return true;
        }
        if(input.input() == GLFW.GLFW_KEY_P) {
            sendPolishControlPacket("pause");
            return true;
        }
        if(input.input() == GLFW.GLFW_KEY_R) {
            sendPolishControlPacket("resume");
            return true;
        }
        if(input.input() == GLFW.GLFW_KEY_SLASH) {
            sendPolishControlPacket("stop");
            return true;
        }
        return super.keyPressed(input);
    }

    private void sendPolishControlPacket(String action) {
        ClientPlayNetworking.send(new MachineStatusDataPayload(menu.getBlockPos(), action));
    }

    protected abstract void renderEnergyHandler(GuiGraphicsExtractor graphics, int leftPos, int topPos);
    protected abstract void renderProgressArrow(GuiGraphicsExtractor graphics, int leftPos, int topPos);
    protected abstract void renderRedstoneDust(GuiGraphicsExtractor graphics, int leftPos, int topPos);

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int i = this.leftPos;
        int j = this.topPos;

        graphics.blit(RenderPipelines.GUI_TEXTURED, getBackgroundTexture(), i, j, 0f, 0f, this.imageWidth, this.imageHeight, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        renderEnergyHandler(graphics, i, j);
        renderProgressArrow(graphics,i, j);
        renderRedstoneDust(graphics, i, j);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        extractBackground(graphics, mouseX, mouseY, delta);
        super.extractContents(graphics, mouseX, mouseY, delta);
        extractTooltip(graphics, mouseX, mouseY);
    }
}
