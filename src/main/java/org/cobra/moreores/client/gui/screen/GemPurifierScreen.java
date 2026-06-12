package org.cobra.moreores.client.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.gui.widget.FluidWidget;
import org.cobra.moreores.client.gui.widget.MachineControlButtonWidget;

@Environment(EnvType.CLIENT)
public class GemPurifierScreen extends AbstractContainerScreen<GemPurifierMenu> {
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;
    private static final Identifier TEXTURE = MoreOresModInitializer.id("textures/gui/container/gem_purifier/gem_purifier_gui.png");
    private static final Identifier START_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/start.png");
    private static final Identifier PAUSE_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/pause.png");
    private static final Identifier RESUME_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/resume.png");
    private static final Identifier STOP_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/stop.png");

    public GemPurifierScreen(GemPurifierMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void init() {
        super.init();
        titleLabelY = 1000;
        inventoryLabelY = 1000;

        addRenderableOnly(FluidWidget.builder(menu.blockEntity.fluidStorage).bounds(this.leftPos + 10, this.topPos + 42, 20, 44).posSupplier(menu.blockEntity::getBlockPos).build());

        Button start = this.addButton("gui.button.gp.start", 0, this.leftPos + 112, topPos + 8, START_BUTTON, Component.literal("Start Polishing"));

        Button pause = this.addButton("gui.button.gp.pause", 1, leftPos + 160, topPos + 8, PAUSE_BUTTON, Component.literal("Pause Polishing"));

        Button resume = this.addButton("gui.button.gp.resume", 2, this.leftPos + 112, this.topPos + 56, RESUME_BUTTON, Component.literal("Resume Polishing"));

        Button stop = this.addButton("gui.button.gp.stop", 3, leftPos + 160, topPos + 56, STOP_BUTTON, Component.literal("Stop Polishing"));

        start.visible = true;
        pause.visible = true;
        resume.visible = true;
        stop.visible = true;
    }

    private Button addButton(String translation, int buttonId, int x, int y, Identifier texture, Component tooltip) {
        Button button = new MachineControlButtonWidget(x, y, Component.translatable(translation), texture, buttonId, menu.blockEntity.getBlockPos());
        button.setTooltip(Tooltip.create(tooltip));
        return this.addRenderableWidget(button);
    }

    private void renderProgressArrow(GuiGraphicsExtractor context, int x, int y) {
        if(this.menu.isPolishing()) {
            context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 83, y + 31, 207, 0, 10, this.menu.progressGetter(), TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
    }

    private void renderEnergyStorageHandler(GuiGraphicsExtractor context, int x, int y) {
        int energyBarSize = Mth.ceil(this.menu.getEnergyPercent() * 44);
        int gradientStart = CommonColors.BLUE;
        int gradientEnd = CommonColors.GREEN;
        context.fillGradient(x + 40, y + 42 + 44 - energyBarSize, x + 40 + 16, y + 42 + 44, gradientStart, gradientEnd);
    }

    @Override
    public void extractLabels(GuiGraphicsExtractor context, int mouseX, int mouseY) {
        super.extractLabels(context, mouseX, mouseY);
        String name = this.menu.blockEntity.getDisplayName().getString();
        int x = 8;
        int y = 8;
        context.text(this.font, name, x, y, CommonColors.BLACK, false);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        int i = this.leftPos;
        int j = this.topPos;
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i, j, 0.0F, 0.0F, this.imageWidth, this.imageHeight, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        renderProgressArrow(context, i, j);
        renderEnergyStorageHandler(context, i, j);

    }

    @Override
    public void extractContents(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        extractBackground(context, mouseX, mouseY, delta);
        super.extractContents(context, mouseX, mouseY, delta);
        extractTooltip(context, mouseX, mouseY);
        int energyBarSize = Mth.ceil(this.menu.getEnergyPercent() * 44);
        if (isHovering(40, 42 + 44 - energyBarSize, 16, energyBarSize, mouseX, mouseY)) {
            context.setTooltipForNextFrame(this.font, Component.literal(this.menu.getEnergy() + " / " + this.menu.getEnergyCap() + " J").withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.BOLD), mouseX, mouseY);
        }
    }
}