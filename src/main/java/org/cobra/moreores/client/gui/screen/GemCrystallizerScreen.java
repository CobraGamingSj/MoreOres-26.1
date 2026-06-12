package org.cobra.moreores.client.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.cobra.moreores.MoreOresModInitializer;

@Environment(EnvType.CLIENT)
public class GemCrystallizerScreen extends AbstractGemPFScreen<GemCrystallizerMenu> {
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;
    private static final Identifier TEXTURE = MoreOresModInitializer.id("textures/gui/container/gem_crystallizer/gem_crystallizer_gui.png");
    private static final Identifier START_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/start.png");
    private static final Identifier PAUSE_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/pause.png");
    private static final Identifier RESUME_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/resume.png");
    private static final Identifier STOP_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/stop.png");

    public GemCrystallizerScreen(GemCrystallizerMenu handler, Inventory inventory, Component title) {
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
    protected void renderProgressArrow(GuiGraphicsExtractor context, int x, int y) {
        if(this.menu.isPolishing()) {
            context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 70, y + 41, 207, 0, 11, this.menu.progressGetter(), TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        super.extractBackground(context, mouseX, mouseY, delta);
        renderRadiantDust(context, this.leftPos, this.topPos);
    }

    private void renderRadiantDust(GuiGraphicsExtractor context, int x, int y) {
        int k = menu.getDustCount();
        int l = Mth.clamp((18 * k + 10000 - 1) / 10000, 0, 18);
        if(l > 0) {
            context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 38, y + 97, 207, 29, l, 4, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
    }

    @Override
    protected void renderEnergyHandler(GuiGraphicsExtractor context, int x, int y) {
        int energyBarSize = Mth.ceil(this.menu.getEnergyPercent() * 44);

        int startY = y + 43 + 44 - energyBarSize;
        int endY = y + 43 + 44;

        int barX1 = x + 13;
        int barX2 = barX1 + 16;

        context.fillGradient(barX1, startY, barX2, endY, CommonColors.DARK_PURPLE, CommonColors.RED);
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
    public void extractContents(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        super.extractContents(context, mouseX, mouseY, delta);
        int energyBarSize = Mth.ceil(this.menu.getEnergyPercent() * 44);
        int k = Mth.clamp((18 * menu.getDustCount() + 10000 - 1) / 10000, 0, 18);
        if (isHovering(13, 43 + 44 - energyBarSize, 16, energyBarSize, mouseX, mouseY)) {
            context.setTooltipForNextFrame(this.font, Component.literal(this.menu.getEnergy() + " / " + this.menu.getEnergyCap() + " J").withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.BOLD), mouseX, mouseY);
        }
        if (isHovering(38 + 18 - k, 97, k, 4, mouseX, mouseY)) {
            context.setTooltipForNextFrame(this.font, Component.literal(this.menu.getDustCount() + " Particles").withStyle(ChatFormatting.RED),  mouseX, mouseY);
        }
    }
}