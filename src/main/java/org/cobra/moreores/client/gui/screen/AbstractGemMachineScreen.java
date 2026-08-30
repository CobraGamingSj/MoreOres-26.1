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
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.gui.widget.MachineButton;
import org.cobra.moreores.networking.block.data.MachineStatusDataPayload;
import org.cobra.moreores.world.block.entity.gem.machine.AbstractGemMachineBlockEntity;
import org.joml.Matrix3x2fStack;
import org.lwjgl.glfw.GLFW;

public abstract class AbstractGemMachineScreen<T extends AbstractGemMachineBlockEntity<?>, Menu extends AbstractGemMachineMenu<T>> extends AbstractContainerScreen<Menu> {
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;

    ItemStack previewResultStack = ItemStack.EMPTY;

    protected final Identifier SLOT_HIGHLIGHT_BACK_SPRITE_ = MoreOresModInitializer.id("container/slot_highlight_back");
    protected final Identifier SLOT_HIGHLIGHT_FRONT_SPRITE_ = MoreOresModInitializer.id("container/slot_highlight_front");

    private static final Identifier REI_HELP = MoreOresModInitializer.id("container/rei");

    public AbstractGemMachineScreen(Menu menu, Inventory inventory, Component title, int imageWidth, int imageHeight) {
        super(menu, inventory, title, imageWidth, imageHeight);
    }

    @Override
    protected void init() {
        super.init();
        titleLabelY = 1000;
        inventoryLabelY = 1000;

        Button start = this.addButton("gui.button.gp.start", 0, this.leftPos + getStartButtonPosX(), topPos + getStartButtonPosY(), getStartButtonTexture(), menu instanceof GemPurifierMenu ? Component.literal("Start Purification") : Component.literal("Start Crystallization"));
        Button pause = this.addButton("gui.button.gp.pause", 1, leftPos + getPauseButtonPosX(), topPos + getPauseButtonPosY(), getPauseButtonTexture(), menu instanceof GemPurifierMenu ? Component.literal("Pause Purification") : Component.literal("Pause Crystallization"));
        Button resume = this.addButton("gui.button.gp.resume", 2, this.leftPos + getResumeButtonPosX(), this.topPos + getResumeButtonPosY(), getResumeButtonTexture(), menu instanceof GemPurifierMenu ? Component.literal("Resume Purification") : Component.literal("Resume Crystallization"));
        Button stop = this.addButton("gui.button.gp.stop", 3, leftPos + getStopButtonPosX(), topPos + getStopButtonPosY(), getStopButtonTexture(), menu instanceof GemPurifierMenu ? Component.literal("Stop Purification") : Component.literal("Stop Crystallization"));

        start.visible = true;
        pause.visible = true;
        resume.visible = true;
        stop.visible = true;
    }

    protected Button addButton(String translation, int buttonIndex, int x, int y, Identifier texture, Component tooltip) {
        Button button = new MachineButton(x, y, Component.translatable(translation), texture, buttonIndex, menu.getBlockPos());
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

    protected abstract void extractEnergyStorage(GuiGraphicsExtractor extractor, int leftPos, int topPos);
    protected abstract void extractProgressArrow(GuiGraphicsExtractor extractor, int leftPos, int topPos);
    protected abstract void extractRedstoneStorage(GuiGraphicsExtractor extractor, int leftPos, int topPos);

    private Slot getOutputSlot() {
        if (this.menu instanceof GemPurifierMenu) {
            return this.menu.getSlot(1);
        }

        if (this.menu instanceof GemCrystallizerMenu) {
            return this.menu.getSlot(2);
        }

        return null;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float a) {
        int i = this.leftPos;
        int j = this.topPos;

        extractor.blit(RenderPipelines.GUI_TEXTURED, getBackgroundTexture(), i, j, 0f, 0f, this.imageWidth, this.imageHeight, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        extractEnergyStorage(extractor, i, j);
        extractProgressArrow(extractor,i, j);
        extractRedstoneStorage(extractor, i, j);

        ItemStack resultStack = this.previewResultStack;
        Slot outputSlot = getOutputSlot();

        if(outputSlot != null && outputSlot.getItem().isEmpty()) {
            int x = this.leftPos + outputSlot.x;
            int y = this.topPos + outputSlot.y;

            Matrix3x2fStack matrixStack = extractor.pose();
            matrixStack.pushMatrix();
            matrixStack.translate(x, y);
            matrixStack.scale(1.5f, 1.5f);
            extractor.fakeItem(resultStack, 0, 0);
            matrixStack.popMatrix();

            if (isHovering(outputSlot, mouseX, mouseY)) {
                extractor.setTooltipForNextFrame(this.font, Component.literal("Result: " + resultStack.getItemName().getString()), mouseX, mouseY);
            }
        }

        extractor.blitSprite(RenderPipelines.GUI_TEXTURED, REI_HELP, i - 30, j, 25, 20);
    }

    private boolean isOutputSlot(Slot slot) {
        return (this.menu instanceof GemPurifierMenu && slot.index == 1) || (this.menu instanceof GemCrystallizerMenu && slot.index == 2);
    }

    @Override
    public void extractSlotHighlightBack(GuiGraphicsExtractor graphics) {
        if (this.hoveredSlot != null && this.hoveredSlot.isHighlightable() && isOutputSlot(this.hoveredSlot)) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_BACK_SPRITE_, this.hoveredSlot.x - 4, this.hoveredSlot.y - 4, 32, 32);
            return;
        }
        super.extractSlotHighlightBack(graphics);
    }

    @Override
    public void extractSlotHighlightFront(GuiGraphicsExtractor graphics) {
        if (this.hoveredSlot != null && this.hoveredSlot.isHighlightable() && isOutputSlot(this.hoveredSlot)) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_FRONT_SPRITE_, this.hoveredSlot.x - 4, this.hoveredSlot.y - 4, 32, 32);
            return;
        }
        super.extractSlotHighlightFront(graphics);
    }

    @Override
    protected boolean isHovering(Slot slot, double mouseX, double mouseY) {
        if(isOutputSlot(slot)) {
            return this.isHovering(slot.x, slot.y, 24, 24, mouseX, mouseY);
        }
        return super.isHovering(slot, mouseX, mouseY);
    }

    @Override
    protected void extractSlot(GuiGraphicsExtractor graphics, Slot slot, int mouseX, int mouseY) {
        if(isOutputSlot(slot)) {
            graphics.pose().pushMatrix();

            graphics.pose().translate(slot.x, slot.y);
            graphics.pose().scale(1.5f, 1.5f);

            if(!slot.getItem().isEmpty()) {
                graphics.item(slot.getItem(), 0, 0);
                graphics.itemCount(this.font, slot.getItem(), slot.x - 75, slot.y - 61, null);
            }

            graphics.pose().popMatrix();
            return;
        }
        super.extractSlot(graphics, slot, mouseX, mouseY);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor extractor, int mouseX, int mouseY) {
        super.extractLabels(extractor, mouseX, mouseY);
        String name = this.menu.blockEntity.getDisplayName().getString();
        int leftPos = 8;
        int topPos = 8;
        extractor.text(this.font, name, leftPos, topPos, CommonColors.DARK_GRAY, false);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float delta) {
        extractBackground(extractor, mouseX, mouseY, delta);
        super.extractContents(extractor, mouseX, mouseY, delta);
        extractTooltip(extractor, mouseX, mouseY);
    }

    public void setPreviewResultStack(ItemStack previewResultStack) {
        this.previewResultStack = previewResultStack;
    }
}