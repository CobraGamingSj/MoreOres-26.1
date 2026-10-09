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

    public SkillHolderSlot(
            int x,
            int y,
            int slot,
            Container container,
            SkillTreeMenu menu,
            Skill skill
    ) {
        super(container, slot, x, y);
        this.menu = menu;
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getSkillIndex() {
        return getContainerSlot();
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

    /**
     * The slot itself should always exist/render.
     *
     * Do NOT return false when the skill is active,
     * otherwise Minecraft can stop rendering the slot/item.
     */
    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public boolean isHighlightable() {
        return !isSkillActive() && canUnlockSkill();
    }

    /**
     * We don't want normal item insertion into skill slots.
     *
     * Activation is handled by SkillTreeMenu.clicked()
     * and the ActivateSkillPayload.
     */
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

    /**
     * Active skill gems cannot be manually removed.
     */
    @Override
    public boolean mayPickup(Player player) {
        return !isSkillActive() && super.mayPickup(player);
    }
}