package org.cobra.moreores.client.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.gui.widget.FluidWidget;
import org.cobra.moreores.world.block.entity.gem.machine.GemPurifierBlockEntity;

import java.util.List;

@Environment(EnvType.CLIENT)
public class GemPurifierScreen extends AbstractGemMachineScreen<GemPurifierBlockEntity, GemPurifierMenu> {
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;
    private static final Identifier TEXTURE = MoreOresModInitializer.id("textures/gui/container/gem_purifier/gem_purifier_gui_test.png");
    private static final Identifier START_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/start.png"),
        PAUSE_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/pause.png"),
        RESUME_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/resume.png"),
        STOP_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/stop.png");

    private final CyclingSlotBackground energyIngotSlotIcon = new CyclingSlotBackground(2);
    private final CyclingSlotBackground inputSlotIcon = new CyclingSlotBackground(0);
    
    public GemPurifierScreen(GemPurifierMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 226, 201);
    }

    @Override
    protected void init() {
        super.init();

        addRenderableOnly(FluidWidget.creator(menu.blockEntity.fluidStorage).boundingPos(this.leftPos + 10, this.topPos + 42, 20, 44).posSupplier(menu.blockEntity::getBlockPos).create());
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
    protected int getStartButtonPosX() {
        return 32;
    }

    @Override
    protected int getStartButtonPosY() {
        return 92;
    }

    @Override
    protected int getPauseButtonPosX() {
        return 80;
    }

    @Override
    protected int getPauseButtonPosY() {
        return 92;
    }

    @Override
    protected int getResumeButtonPosX() {
        return 32;
    }

    @Override
    protected int getResumeButtonPosY() {
        return 140;
    }

    @Override
    protected int getStopButtonPosX() {
        return 80;
    }

    @Override
    protected int getStopButtonPosY() {
        return 140;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.energyIngotSlotIcon.tick(getEnergyIngotSlotTexture());
        this.inputSlotIcon.tick(getInputSlotTexture());
    }

    private List<Identifier> getEnergyIngotSlotTexture() {
        return List.of(MoreOresModInitializer.id("container/slot/empty_ingot"), MoreOresModInitializer.id("container/slot/energy_ingot_faded"));
    }

    private List<Identifier> getInputSlotTexture() {
        return List.of(MoreOresModInitializer.id("container/slot/empty_raw_gem"));
    }
    
    @Override
    public void renderProgressArrow(GuiGraphicsExtractor context, int leftPos, int topPos) {
        if(this.menu.isPolishing()) {
            context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos + 83, topPos + 31, 207, 0, 10, this.menu.progressGetter(), TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
    }

    @Override
    protected void renderRedstoneDust(GuiGraphicsExtractor extractor, int leftPos, int topPos) {
        int k = menu.getRedstoneDust();
        int l = Mth.clamp((k * 16 + 10000 - 1) / 10000, 0, 16);
        
        int startX = leftPos + 109;
        int startY = topPos + 53;
        int endY = topPos + 57;
        
        extractor.fillGradient(startX, startY, startX + l, endY, CommonColors.RED, CommonColors.SOFT_RED);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        super.extractBackground(context, mouseX, mouseY, delta);
        if(this.menu.getBlockEntity().energyStack().isEmpty()) {
            this.energyIngotSlotIcon.extractRenderState(this.menu, context, delta, this.leftPos, this.topPos);
        }
        if(this.menu.getBlockEntity().ingredientStack().isEmpty()) {
            this.inputSlotIcon.extractRenderState(this.menu, context, delta, this.leftPos, this.topPos);
        }
    }
    
    @Override
    public void renderEnergyHandler(GuiGraphicsExtractor context, int leftPos, int topPos) {
        int energyBarSize = Mth.ceil(this.menu.getEnergyPercent() * 44);
        int gradientStart = CommonColors.BLUE;
        int gradientEnd = CommonColors.GREEN;
        context.fillGradient(leftPos + 40, topPos + 42 + 44 - energyBarSize, leftPos + 40 + 16, topPos + 42 + 44, gradientStart, gradientEnd);
    }
    
    @Override
    public void extractLabels(GuiGraphicsExtractor context, int mouseX, int mouseY) {
        super.extractLabels(context, mouseX, mouseY);
        String name = this.menu.blockEntity.getDisplayName().getString();
        int leftPos = 8;
        int topPos = 8;
        context.text(this.font, name, leftPos, topPos, CommonColors.BLACK, false);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        extractBackground(context, mouseX, mouseY, delta);
        super.extractContents(context, mouseX, mouseY, delta);
        extractTooltip(context, mouseX, mouseY);
        int energyBarSize = Mth.ceil(this.menu.getEnergyPercent() * 44);
        int l = Mth.clamp((menu.getRedstoneDust() * 16 + 10000 - 1) / 10000, 0, 16);
        if (isHovering(40, 42 + 44 - energyBarSize, 16, energyBarSize, mouseX, mouseY)) {
            context.setTooltipForNextFrame(this.font, Component.literal(this.menu.getEnergy() + " / " + this.menu.getEnergyCap() + " J").withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.BOLD), mouseX, mouseY);
        }
        if (isHovering(109, 53, l, 4, mouseX, mouseY)) {
            context.setTooltipForNextFrame(this.font, Component.literal(this.menu.getRedstoneDust() + " Particles").withStyle(ChatFormatting.RED), mouseX, mouseY);
        }
    }
}