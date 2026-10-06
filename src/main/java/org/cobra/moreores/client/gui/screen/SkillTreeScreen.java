package org.cobra.moreores.client.gui.screen;

import net.cobra.api.talents.util.Skill;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.entity.MobEffectSkillEffect;

import java.util.List;

public class SkillTreeScreen extends AbstractContainerScreen<SkillTreeMenu> {
    private static final Identifier TEXTURE = MoreOresModInitializer.id("textures/gui/skill_tree/window.png");
    private static final Identifier INNER_TEXTURE = MoreOresModInitializer.id("inner_window");
    public static final int WINDOW_WIDTH = 255;
    public static final int WINDOW_HEIGHT = 127;
    public static final int WINDOW_INSIDE_WIDTH = 232;
    public static final int WINDOW_INSIDE_HEIGHT = 96;

    List<Skill<MobEffectSkillEffect>> skills;

//    public SkillTreeScreen() {
//        //
//    }

    public SkillTreeScreen(SkillTreeMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int i = this.leftPos;
        int j = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i, j, 0, 0, this.width, this.height, 256, 256);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, INNER_TEXTURE, i + 12, j + 21, 232, 96);
//        for (Skill<MobEffectSkillEffect> skill : skills) {
//            Identifier skillTexture = MoreOresModInitializer.id("textures/gui/sprite/conainer/skill_tree/" + skill.id().getPath());
//            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, skillTexture, 15, 60, 16, 16);
//        }
    }
}