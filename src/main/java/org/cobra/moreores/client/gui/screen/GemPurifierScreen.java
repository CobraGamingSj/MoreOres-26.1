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
public class GemPurifierScreen extends AbstractGemMachineScreen<GemPurifierMenu> {
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
    protected Identifier getBackgroundTexture() {
        return TEXTURE;
    }

    @Override
    protected Identifier getStartButtonTexture() {
        return START_BUTTON;
    }

    @Override
    protected Identifier getPauseButtonTexture() {
        return PAUSE_BUTTON;
    }

    @Override
    protected Identifier getResumeButtonTexture() {
        return RESUME_BUTTON;
    }

    @Override
    protected Identifier getStopButtonTexture() {
        return STOP_BUTTON;
    }

    @Override
    public void renderProgressArrow(GuiGraphicsExtractor context, int x, int y) {
        if(this.menu.isPolishing()) {
            context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 83, y + 31, 207, 0, 10, this.menu.progressGetter(), TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
    }

    @Override
    public void renderEnergyHandler(GuiGraphicsExtractor context, int x, int y) {
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
        renderEnergyHandler(context, i, j);

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