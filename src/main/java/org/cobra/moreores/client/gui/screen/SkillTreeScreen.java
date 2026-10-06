package org.cobra.moreores.client.gui.screen;

import net.cobra.api.talents.util.Skill;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.gui.SkillHolderSlot;
import org.cobra.moreores.util.Skills;
import org.cobra.moreores.world.entity.MobEffectSkillEffect;

import java.util.List;

public class SkillTreeScreen extends AbstractContainerScreen<SkillTreeMenu> {
    private static final Identifier TEXTURE = MoreOresModInitializer.id("textures/gui/skill_tree/window.png");
    private static final Identifier INNER_TEXTURE = MoreOresModInitializer.id("inner_window");
    private static final Identifier VIGNETTE_TEXTURE = MoreOresModInitializer.id("vignette");
    private static final Identifier CONNECTOR_TOP_TEXTURE = MoreOresModInitializer.id("connector_top_active");
    private static final Identifier CONNECTOR_BOTTOM_TEXTURE = MoreOresModInitializer.id("connector_bottom_active");
    private static final Identifier CONNECTOR_SIDE_TEXTURE = MoreOresModInitializer.id("connector_side_active");
    public static final int WINDOW_WIDTH = 255;
    public static final int WINDOW_HEIGHT = 127;
    public static final int WINDOW_INSIDE_WIDTH = 232;
    public static final int WINDOW_INSIDE_HEIGHT = 96;

    List<Skill<MobEffectSkillEffect>> skills = List.of(Skills.STRENGTH);

//    public SkillTreeScreen() {
//        //
//    }

    public SkillTreeScreen(SkillTreeMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 256, 226);
    }

    @Override
    protected void init() {
        super.init();
        inventoryLabelY = 1000;
    }

    public String getDescription() {
        return "Example";
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int i = this.leftPos;
        int j = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i, j, 0, 0, this.width, this.height, 256, 256);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, INNER_TEXTURE, i + 12, j + 21, 232, 100);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, VIGNETTE_TEXTURE, i + 12, j + 21, 232, 100);
        for(Slot slot : menu.slots) {
            if(!slot.getItem().isEmpty()) {
                switch (slot.index) {
                    case 0 -> {
                        extractSprite(graphics, CONNECTOR_TOP_TEXTURE, 28, 34);
                        extractSprite(graphics, CONNECTOR_BOTTOM_TEXTURE, 28, 80);
                    }
                    case 1 -> extractSprite(graphics, CONNECTOR_SIDE_TEXTURE, 71, 29);
                }
            }
        }
        for (Skill<MobEffectSkillEffect> skill : skills) {
            if(hoveredSlot instanceof SkillHolderSlot slot && slot.isDisabledSlot() && isHovering(slot, mouseX, mouseY)) {
                graphics.text(this.font, getDescription(), mouseX, mouseY, CommonColors.DARK_GRAY, false);
            }
        }
    }

    private void extractSprite(GuiGraphicsExtractor graphics, Identifier sprite, int x, int y) {
        int i = this.leftPos;
        int j = this.topPos;
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, i + x, j + y, 28, 28);
    }
}