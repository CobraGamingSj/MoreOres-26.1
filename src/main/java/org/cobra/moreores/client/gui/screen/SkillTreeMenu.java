package org.cobra.moreores.client.gui.screen;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.cobra.moreores.data.SkillManager;
import org.cobra.moreores.util.Skill;
import org.cobra.moreores.client.gui.SkillHolderSlot;
import org.cobra.moreores.util.Skills;

public class SkillTreeMenu extends AbstractContainerMenu {
    private final Container container;
    private final Player player;

    public SkillTreeMenu(int containerId, Inventory inventory) {
        super(ModMenuType.SKILL_NODE_TREE, containerId);
        this.container = new SimpleContainer(11);
        this.player = inventory.player;
        this.addDataSlots();
        addPlayerGenericInventory(inventory);
        addPlayerHotbarInventory(inventory);
        loadActiveSkills();
    }

    private void addDataSlots() {
        addSkillSlot(Skills.STRENGTH, 26, 63, 0);
        addSkillSlot(Skills.HEALTH_BOOST, 54, 35, 1);
        addSkillSlot(Skills.STRENGTH, 54, 91, 2);
        addSkillSlot(Skills.STRENGTH, 94, 35, 3);
        addSkillSlot(Skills.STRENGTH, 94, 91, 4);
        addSkillSlot(Skills.STRENGTH, 134, 35, 5);
        addSkillSlot(Skills.STRENGTH, 134, 91, 6);
        addSkillSlot(Skills.STRENGTH, 174, 35, 7);
        addSkillSlot(Skills.STRENGTH, 174, 91, 8);
        addSkillSlot(Skills.STRENGTH, 214, 35, 9);
        addSkillSlot(Skills.STRENGTH, 214, 91, 10);
    }

    private void addSkillSlot(Skill skill, int x, int y, int slotIndex) {
        this.addSlot(new SkillHolderSlot(x, y, slotIndex, container, this, skill));
    }

    private void loadActiveSkills() {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        for (int i = 0; i < Skills.SKILLS.size(); i++) {
            Skill skill = Skills.SKILLS.get(i);
            var progress = SkillManager.getData(serverPlayer).getSkillProgress(skill.id());

            if (progress.isActive()) {
                container.setItem(i, progress.getGem().copy());
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isSkillUnlocked(Skill skill) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return false;
        }

        return SkillManager.isUnlocked(serverPlayer, skill);
    }

    public boolean isSkillActive(Skill skill) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return false;
        }

        return SkillManager.isActive(serverPlayer, skill);
    }

    public boolean canUnlockSkill(Skill skill) {
        if(skill.prerequisite() == null) {
            return true;
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return false;
        }

        return SkillManager.isUnlocked(serverPlayer, skill);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    private void addPlayerGenericInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 48 + l * 18, 141 + i * 18));
            }
        }
    }

    private void addPlayerHotbarInventory(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 48 + i * 18, 199));
        }
    }
}