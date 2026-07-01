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
import org.cobra.moreores.client.gui.widget.MachineControlButtonWidget;
import org.cobra.moreores.networking.block.data.MachineStatusDataPayload;
import org.lwjgl.glfw.GLFW;

public abstract class AbstractGemMachineScreen<M extends AbstractGemMachineMenu> extends AbstractContainerScreen<M> {
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;

    public AbstractGemMachineScreen(M handler, Inventory inventory, Component title) {
        super(handler, inventory, title, 207, 196);
    }

    @Override
    protected void init() {
        super.init();
        titleLabelY = 1000;
        inventoryLabelY = 1000;

        Button start = this.addButton("gui.button.gp.start", 0, this.leftPos + 112, topPos + 8, getStartButtonTexture(), Component.literal("Start Polishing"));

        Button pause = this.addButton("gui.button.gp.pause", 1, leftPos + 160, topPos + 8, getPauseButtonTexture(), Component.literal("Pause Polishing"));

        Button resume = this.addButton("gui.button.gp.resume", 2, this.leftPos + 112, this.topPos + 56, getResumeButtonTexture(), Component.literal("Resume Polishing"));

        Button stop = this.addButton("gui.button.gp.stop", 3, leftPos + 160, topPos + 56, getStopButtonTexture(), Component.literal("Stop Polishing"));

        start.visible = true;
        pause.visible = true;
        resume.visible = true;
        stop.visible = true;
    }

    protected Button addButton(String translation, int buttonId, int x, int y, Identifier texture, Component tooltip) {
        Button button = new MachineControlButtonWidget(x, y, Component.translatable(translation), texture, buttonId, menu.getPos());
        button.setTooltip(Tooltip.create(tooltip));
        return this.addRenderableWidget(button);
    }

    protected abstract Identifier getBackgroundTexture();
    protected abstract Identifier getStartButtonTexture();
    protected abstract Identifier getPauseButtonTexture();
    protected abstract Identifier getResumeButtonTexture();
    protected abstract Identifier getStopButtonTexture();

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
        ClientPlayNetworking.send(new MachineStatusDataPayload(menu.getPos(), action));
    }

    protected abstract void renderEnergyHandler(GuiGraphicsExtractor context, int x, int y);
    protected abstract void renderProgressArrow(GuiGraphicsExtractor context, int x, int y);

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int i = this.leftPos;
        int j = this.topPos;

        graphics.blit(RenderPipelines.GUI_TEXTURED, getBackgroundTexture(), i, j, 0f, 0f, this.imageWidth, this.imageHeight, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        renderEnergyHandler(graphics, i, j);
        renderProgressArrow(graphics,i, j);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        extractBackground(context, mouseX, mouseY, delta);
        super.extractContents(context, mouseX, mouseY, delta);
        extractTooltip(context, mouseX, mouseY);
    }
}
