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
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.block.entity.gem.machine.GemCrystallizerBlockEntity;
import org.joml.Matrix3x2fStack;

import java.util.List;

@Environment(EnvType.CLIENT)
public class GemCrystallizerScreen extends AbstractGemMachineScreen<GemCrystallizerBlockEntity, GemCrystallizerMenu> {
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;
    private static final Identifier TEXTURE = MoreOresModInitializer.id("textures/gui/container/gem_crystallizer/gem_crystallizer_gui.png");
    private static final Identifier START_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/start.png"), 
            PAUSE_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/pause.png"), 
            RESUME_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/resume.png"), 
            STOP_BUTTON = MoreOresModInitializer.id("textures/gui/container/button/stop.png");

    private static final Identifier EMPTY_RUBY_TEXTURE = MoreOresModInitializer.id("container/slot/empty_ruby");
    private static final Identifier EMPTY_SAPPHIRE_TEXTURE = MoreOresModInitializer.id("container/slot/empty_sapphire");
    private static final Identifier EMPTY_GARNET_TEXTURE = MoreOresModInitializer.id("container/slot/empty_garnet");
    private static final Identifier EMPTY_PERIDOT_TEXTURE = MoreOresModInitializer.id("container/slot/empty_peridot");
    private static final Identifier EMPTY_JADE_TEXTURE = MoreOresModInitializer.id("container/slot/empty_jade");
    private static final Identifier EMPTY_PYROPE_TEXTURE = MoreOresModInitializer.id("container/slot/empty_pyrope");
    private static final Identifier EMPTY_KYAWTHUITE_TEXTURE = MoreOresModInitializer.id("container/slot/empty_kyawthuite");
    private static final Identifier EMPTY_RADIANT_TEXTURE = MoreOresModInitializer.id("container/slot/empty_radiant");
    private static final Identifier EMPTY_QUARTZ_TEXTURE = MoreOresModInitializer.id("container/slot/empty_quartz");
    
    private final CyclingSlotBackground energyIngotSlotIcon = new CyclingSlotBackground(3);
    private final CyclingSlotBackground inputBeforeIngotSlotIcon = new CyclingSlotBackground(0);
    private final CyclingSlotBackground inputAfterIngotSlotIcon = new CyclingSlotBackground(1);
    
    public GemCrystallizerScreen(GemCrystallizerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 207, 196);
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
        return 112;
    }

    @Override
    protected int getStartButtonPosY() {
        return 8;
    }

    @Override
    protected int getPauseButtonPosX() {
        return 160;
    }

    @Override
    protected int getPauseButtonPosY() {
        return 8;
    }

    @Override
    protected int getResumeButtonPosX() {
        return 112;
    }

    @Override
    protected int getResumeButtonPosY() {
        return 56;
    }

    @Override
    protected int getStopButtonPosX() {
        return 160;
    }

    @Override
    protected int getStopButtonPosY() {
        return 56;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.energyIngotSlotIcon.tick(getEnergyIngotSlotTexture());
        this.inputBeforeIngotSlotIcon.tick(getInputSlotTexture());
        this.inputAfterIngotSlotIcon.tick(getInputSlotTexture());
    }

    private List<Identifier> getEnergyIngotSlotTexture() {
        return List.of(MoreOresModInitializer.id("container/slot/empty_ingot"), MoreOresModInitializer.id("container/slot/energy_ingot_faded"));
    }

    private List<Identifier> getInputSlotTexture() {
        return List.of(EMPTY_RUBY_TEXTURE, EMPTY_SAPPHIRE_TEXTURE, EMPTY_GARNET_TEXTURE, EMPTY_KYAWTHUITE_TEXTURE,
                EMPTY_PERIDOT_TEXTURE, EMPTY_JADE_TEXTURE, EMPTY_PYROPE_TEXTURE, EMPTY_RADIANT_TEXTURE, EMPTY_QUARTZ_TEXTURE);
    }

    @Override
    protected void extractProgressArrow(GuiGraphicsExtractor extractor, int leftPos, int topPos) {
        if(this.menu.isPolishing()) {
            extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos + 70, topPos + 41, 207, 0, 11, this.menu.progressGetter(), TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
    }

    @Override
    protected void extractSlot(GuiGraphicsExtractor graphics, Slot slot, int mouseX, int mouseY) {
        if(slot.index == 2) {
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
    public void extractBackground(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float delta) {
        super.extractBackground(extractor, mouseX, mouseY, delta);
        renderRadiantDust(extractor, this.leftPos, this.topPos);
        if(this.menu.getBlockEntity().energyStack().isEmpty()) {
            this.energyIngotSlotIcon.extractRenderState(this.menu, extractor, delta, this.leftPos, this.topPos);
        }
        if(this.menu.getBlockEntity().ingredientStack().isEmpty() && this.menu.getBlockEntity().ingredientAfterStack().isEmpty()) {
            this.inputBeforeIngotSlotIcon.extractRenderState(this.menu, extractor, delta, this.leftPos, this.topPos);
            this.inputAfterIngotSlotIcon.extractRenderState(this.menu, extractor, delta, this.leftPos, this.topPos);
        }
        ItemStack resultStack = this.previewResultStack;
        Slot outputSlot = this.menu.getSlot(2);

        int x = this.leftPos + outputSlot.x;
        int y = this.topPos + outputSlot.y;

        if(outputSlot.getItem().isEmpty()) {
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
    }

    @Override
    protected void extractRedstoneStorage(GuiGraphicsExtractor extractor, int leftPos, int topPos) {
        int k = menu.getRedstoneDust();
        int l = Mth.clamp((k * 16 + 10000 - 1) / 10000, 0, 16);

        int startX = leftPos + 92;
        int startY = topPos + 79;
        int endY = topPos + 83;

        extractor.fillGradient(startX, startY, startX + l, endY, CommonColors.RED, -7667712);
    }

    private void renderRadiantDust(GuiGraphicsExtractor context, int x, int y) {
        int k = menu.getDustCount();
        int l = Mth.clamp((18 * k + 10000 - 1) / 10000, 0, 18);
        if(l > 0) {
            context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 38, y + 97, 207, 29, l, 4, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
    }

    @Override
    protected void extractEnergyStorage(GuiGraphicsExtractor extractor, int leftPos, int topPos) {
        int energyBarSize = Mth.ceil(this.menu.getEnergyPercent() * 44);

        int startY = topPos + 43 + 44 - energyBarSize;
        int endY = topPos + 43 + 44;

        int barX1 = leftPos + 13;
        int barX2 = barX1 + 16;

        extractor.fillGradient(barX1, startY, barX2, endY, CommonColors.DARK_PURPLE, CommonColors.RED);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float delta) {
        extractBackground(extractor, mouseX, mouseY, delta);
        super.extractContents(extractor, mouseX, mouseY, delta);
        int energyBarSize = Mth.ceil(this.menu.getEnergyPercent() * 44);
        int k = Mth.clamp((18 * menu.getDustCount() + 10000 - 1) / 10000, 0, 18);
        int l = Mth.clamp((menu.getRedstoneDust() * 16 + 10000 - 1) / 10000, 0, 16);
        if (isHovering(13, 43 + 44 - energyBarSize, 16, energyBarSize, mouseX, mouseY)) {
            extractor.setTooltipForNextFrame(this.font, Component.literal(this.menu.getEnergy() + " / " + this.menu.getEnergyCap() + " J").withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.BOLD), mouseX, mouseY);
        }
        if (isHovering(38, 97, k, 4, mouseX, mouseY)) {
            extractor.setTooltipForNextFrame(this.font, Component.literal(this.menu.getDustCount() + " Particles").withStyle(ChatFormatting.RED), mouseX, mouseY);
        }
        if (isHovering(92, 79, l, 4, mouseX, mouseY)) {
            extractor.setTooltipForNextFrame(this.font, Component.literal(this.menu.getRedstoneDust() + " Particles").withStyle(ChatFormatting.RED), mouseX, mouseY);
        }
    }
}