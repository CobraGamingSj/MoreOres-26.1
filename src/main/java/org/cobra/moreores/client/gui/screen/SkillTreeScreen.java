package org.cobra.moreores.client.gui.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.gui.SkillHolderSlot;

import java.util.List;
import java.util.Optional;

public class SkillTreeScreen extends AbstractContainerScreen<SkillTreeMenu> {
    private static final Identifier TEXTURE = MoreOresModInitializer.id("textures/gui/skill_tree/window.png");
    private static final Identifier INNER_TEXTURE = MoreOresModInitializer.id("inner_window");
    private static final Identifier VIGNETTE_TEXTURE = MoreOresModInitializer.id("vignette");
    private static final Identifier CONNECTOR_TOP_TEXTURE = MoreOresModInitializer.id("connector_top_active");
    private static final Identifier CONNECTOR_BOTTOM_TEXTURE = MoreOresModInitializer.id("connector_bottom_active");
    private static final Identifier CONNECTOR_SIDE_TEXTURE = MoreOresModInitializer.id("connector_side_active");

    public SkillTreeScreen(SkillTreeMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 256, 226);
    }

    @Override
    protected void init() {
        super.init();
        inventoryLabelY = 1000;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int i = this.leftPos;
        int j = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i, j, 0F, 0F, this.imageWidth, this.imageHeight, 256, 256);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, INNER_TEXTURE, i + 12, j + 21, 232, 100);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, VIGNETTE_TEXTURE, i + 12, j + 21, 232, 100);
        for(Slot slot : menu.slots) {
            if(!slot.getItem().isEmpty()) {
                switch (slot.index) {
                    case 0 -> {
                        extractSprite(graphics, CONNECTOR_TOP_TEXTURE, 25, 34);
                        extractSprite(graphics, CONNECTOR_BOTTOM_TEXTURE, 25, 80);
                    }
                    case 1 -> extractSprite(graphics, CONNECTOR_SIDE_TEXTURE, 68, 29);
                    case 2 -> extractSprite(graphics, CONNECTOR_SIDE_TEXTURE, 68, 85);
                    case 3 -> extractSprite(graphics, CONNECTOR_SIDE_TEXTURE, 108, 29);
                    case 4 -> extractSprite(graphics, CONNECTOR_SIDE_TEXTURE, 108, 85);
                    case 5 -> extractSprite(graphics, CONNECTOR_SIDE_TEXTURE, 148, 29);
                    case 6 -> extractSprite(graphics, CONNECTOR_SIDE_TEXTURE, 148, 85);
                    case 7 -> extractSprite(graphics, CONNECTOR_SIDE_TEXTURE, 188, 29);
                    case 8 -> extractSprite(graphics, CONNECTOR_SIDE_TEXTURE, 188, 85);
                }
            }
        }
        if(hoveredSlot instanceof SkillHolderSlot slot && isHovering(slot, mouseX, mouseY)) {
            long expiresAt = menu.getExpiresAt(slot.getSkillIndex());
            long remainingTime =
                    Math.max(0L, expiresAt - System.currentTimeMillis());
            long seconds = remainingTime / 1000;

            long hours = seconds / 3600;
            long minutes = seconds % 3600 / 60;
            long remainingSeconds = seconds % 60;

            String time = hours > 0 ? String.format("%dh %02dm %02ds", hours, minutes, remainingSeconds) : String.format("%dm %02ds", minutes, remainingSeconds);
            graphics.setTooltipForNextFrame(this.font,List.of(
                    Component.literal(MoreOresModInitializer.formatIdName(slot.getSkill().id().getPath())).withStyle(ChatFormatting.RED),
                            Component.empty(),
                            Component.literal("When unlocked: ").withStyle(ChatFormatting.GRAY),
                            Component.literal(" ").append(slot.getSkill().description().copy().withStyle(ChatFormatting.BLUE)),
                            Component.literal("Required Gem: ").withStyle(ChatFormatting.GRAY),
                            Component.literal(" ").append(Component.translatable(slot.getSkill().requiredGem().getDescriptionId()).withStyle(ChatFormatting.GREEN)),
                            slot.isSkillActive() ? Component.literal("Expires in: " + time).withStyle(ChatFormatting.AQUA) : Component.empty(),
                    Component.literal(slot.getSkill().id().toString()).withStyle(ChatFormatting.DARK_GRAY)
                    ),
                    Optional.empty(),
                    mouseX, mouseY);
        }
    }

    private void extractSprite(GuiGraphicsExtractor graphics, Identifier sprite, int x, int y) {
        int i = this.leftPos;
        int j = this.topPos;
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, i + x, j + y, 28, 28);
    }
}