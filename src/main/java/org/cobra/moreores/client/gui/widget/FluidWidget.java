package org.cobra.moreores.client.gui.widget;

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.cobra.moreores.client.gui.util.ScreenHelperUtils;
import org.cobra.moreores.util.FluidStack;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FluidWidget implements Renderable, LayoutElement {
    private final SingleVariantStorage<FluidVariant> fluidStorage;
    private final Supplier<BlockPos> pos;
    private final int width, height;

    private int x, y;

    public FluidWidget(SingleVariantStorage<FluidVariant> fluidStorage, int x, int y, int width, int height, Supplier<BlockPos> pos) {
        this.fluidStorage = fluidStorage;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.pos = pos;
    }

    public static Builder builder(SingleVariantStorage<FluidVariant> fluidStorage) {
        return new Builder(fluidStorage);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float deltaTicks) {
        long amount = this.fluidStorage.amount;
        if(amount <= 0) return;
        Fluid fluid = this.fluidStorage.variant.getFluid();
        long capacity = this.fluidStorage.getCapacity();
        int fluidHeight = Math.round(((float)amount / capacity) * this.height);
        FluidRenderHandler handler = FluidRenderingRegistry.get(fluid);
        if(handler == null) return;
        BlockPos blockPos = pos.get();
        FluidState fluidState = fluid.defaultFluidState();
        BlockAndTintGetter world = Minecraft.getInstance().level;
        if(world == null) return;
        TextureAtlasSprite sprite = handler.getFluidSprites(world, blockPos, fluidState)[1];
        int tintColor = handler.getFluidColor(world, blockPos, fluidState);
        float red = (tintColor >> 16 & 0xFF) /255F;
        float green = (tintColor >> 8 & 0xFF) /255F;
        float blue = (tintColor & 0xFF) /255F;
        ScreenHelperUtils.extractTiledFluidSprite(extractor, sprite, this.x, this.y + this.height - fluidHeight, this.width, fluidHeight, 1F, red, green, blue);

        if(isPointWithinBounds(this.x, this.y, this.width, this.height, mouseX, mouseY)) {
            drawTooltip(extractor, mouseX, mouseY);
        }
    }

    protected void drawTooltip(GuiGraphicsExtractor extractor, int mouseX, int  mouseY) {
        Fluid fluid = this.fluidStorage.variant.getFluid();
        long fluidAmount = this.fluidStorage.getAmount();
        long fluidCapacity = this.fluidStorage.getCapacity();

        Font textRenderer = Minecraft.getInstance().font;
        if(fluid != null && fluidAmount > 0) {
            List<Component> texts = List.of(
                    Component.translatable(fluid.defaultFluidState().createLegacyBlock().getBlock().getDescriptionId()),
                    Component.literal("%s / %s mB".formatted(FluidStack.convertDropletsToMb(fluidAmount), FluidStack.convertDropletsToMb(fluidCapacity))).withStyle(ChatFormatting.BLUE)
            );
            extractor.setComponentTooltipForNextFrame(textRenderer, texts, mouseX, mouseY);
        }
    }

    private static boolean isPointWithinBounds(int x, int y, int width, int height, int pointX, int pointY) {
        return pointX >= x && pointX <= x + width &&
                pointY >= y && pointY <= y + height;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void visitWidgets(Consumer<AbstractWidget> consumer) {}

    public static class Builder {
        private final SingleVariantStorage<FluidVariant> fluidStorage;
        private Supplier<BlockPos> posSupplier = () -> null;
        private int x, y;
        private int width, height;

        public Builder(SingleVariantStorage<FluidVariant> fluidStorage) {
            this.fluidStorage = fluidStorage;
        }

        public Builder x(int x) {
            this.x = x;
            return this;
        }

        public Builder y(int y) {
            this.y = y;
            return this;
        }

        public Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder bounds(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder posSupplier(Supplier<BlockPos> posSupplier) {
            this.posSupplier = posSupplier;
            return this;
        }

        public FluidWidget build() {
            return new FluidWidget(this.fluidStorage, this.x, this.y, this.width, this.height, this.posSupplier);
        }
    }
}