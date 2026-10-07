package org.cobra.moreores.client.gui;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.cobra.moreores.client.gui.screen.SkillTreeMenu;
import org.cobra.moreores.util.Skill;

public class SkillHolderSlot extends Slot {
    private final SkillTreeMenu menu;
    private final Skill skill;

    public SkillHolderSlot(int x, int y, int slot, Container container, SkillTreeMenu menu, Skill skill) {
        super(container, slot, x, y);
        this.menu = menu;
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }

    public boolean isSkillActive() {
        return menu.isSkillActive(skill);
    }

    public boolean isSkillUnlocked() {
        return menu.isSkillUnlocked(skill);
    }

    public boolean canUnlockSkill() {
        return menu.canUnlockSkill(skill);
    }

    @Override
    public boolean isHighlightable() {
        return isActive();
    }

    @Override
    public boolean isActive() {
        if(isSkillActive()) {
            return false;
        }
        return canUnlockSkill();
//        return !isSkillActive();
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        if (isSkillActive()) {
            return false;
        }

        if (!canUnlockSkill()) {
            return false;
        }

        return itemStack.is(skill.requiredGem());
    }

    @Override
    public boolean mayPickup(Player player) {
        if(isSkillActive()) {
            return false;
        }
        return super.mayPickup(player);
    }
}