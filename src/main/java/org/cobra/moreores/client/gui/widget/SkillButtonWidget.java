package org.cobra.moreores.client.gui.widget;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.networking.OpenSkillTreePayload;

public class SkillButtonWidget extends Button {
    public SkillButtonWidget(int x, int y) {
        super(x, y, 16, 16, Component.literal("Open Skill Tree"),
                _ -> ClientPlayNetworking.send(new OpenSkillTreePayload()), DEFAULT_NARRATION);
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, MoreOresModInitializer.id("textures/gui/button/skill.png"), getX(), getY(), 0, 0, this.getWidth(), this.getHeight(), 16, 16);
        graphics.outline(getX(), getY(), 16, 16, CommonColors.DARK_GRAY);
        if(isHovered()) {
            graphics.setTooltipForNextFrame(this.message, mouseX, mouseY);
            graphics.outline(getX(), getY(), 16, 16, CommonColors.BLACK);
        }
    }
}
