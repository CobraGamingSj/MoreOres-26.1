package org.cobra.moreores.client.gui.screen;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.cobra.moreores.client.gui.SkillHolderSlot;
import org.cobra.moreores.data.PlayerSkillData;
import org.cobra.moreores.data.SkillManager;
import org.cobra.moreores.data.SkillProgress;
import org.cobra.moreores.util.Skill;
import org.cobra.moreores.util.Skills;

import java.util.List;

public class SkillTreeMenu extends AbstractContainerMenu {

    private final Container container;
    private final Player player;
    private final ContainerData data;

    private final boolean[] unlocked = new boolean[Skills.SKILLS.size()];
    private final boolean[] active = new boolean[Skills.SKILLS.size()];
    private final long[] expiresAt = new long[Skills.SKILLS.size()];

    private final ItemStack[] items =
            new ItemStack[Skills.SKILLS.size()];

    public SkillTreeMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new SimpleContainerData(2));
    }

    public SkillTreeMenu(int containerId, Inventory inventory, ContainerData data) {
        super(
                ModMenuType.SKILL_TREE,
                containerId
        );

        this.container = new SimpleContainer(
                Skills.SKILLS.size()
        );

        this.player = inventory.player;
        this.data = data;

        for (int i = 0; i < items.length; i++) {
            items[i] = ItemStack.EMPTY;
        }

        addSkillSlots();

        addPlayerGenericInventory(inventory);
        addPlayerHotbarInventory(inventory);

        addDataSlots(data);

        if (player instanceof ServerPlayer) {
            refreshSkillStates();
        }
    }

    public void refreshSkillStates() {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        PlayerSkillData skillData = SkillManager.getData(serverPlayer);

        int unlockedBits = 0;
        int activeBits = 0;

        for (int i = 0; i < Skills.SKILLS.size(); i++) {
            Skill skill = Skills.SKILLS.get(i);

            SkillProgress progress =
                    skillData.getSkillProgress(skill.id());

            boolean isUnlocked = progress.isUnlocked();
            boolean isActive = progress.isActive();

            unlocked[i] = isUnlocked;
            active[i] = isActive;

            if (isUnlocked) {
                unlockedBits |= (1 << i);
            }

            if (isActive) {
                activeBits |= (1 << i);
            }

            if (isActive && !progress.getGem().isEmpty()) {
                container.setItem(i, progress.getGem().copy());
                items[i] = progress.getGem().copy();
            } else {
                container.setItem(i, ItemStack.EMPTY);
                items[i] = ItemStack.EMPTY;
            }
        }

        data.set(0, unlockedBits);
        data.set(1, activeBits);
    }

    public long getExpiresAt(int skillIndex) {
        return expiresAt[skillIndex];
    }

    public void applySkillGems(List<ItemStack> gems, List<Long> expiresAt) {

        for (int i = 0;
             i < Skills.SKILLS.size() && i < gems.size();
             i++) {

            ItemStack gem = gems.get(i).copy();

            items[i] = gem;

            container.setItem(
                    i,
                    gem
            );

            this.expiresAt[i] =
                    i < expiresAt.size()
                            ? expiresAt.get(i)
                            : 0L;
        }
    }

    private void addSkillSlots() {
        addSkillSlot(Skills.STRENGTH, 26, 63, 0
        );

        addSkillSlot(
                Skills.NIGHT_VISION,
                54,
                35,
                1
        );

        addSkillSlot(
                Skills.REGENERATION,
                54,
                91,
                2
        );

        addSkillSlot(
                Skills.RESISTANCE,
                94,
                35,
                3
        );

        addSkillSlot(
                Skills.SPEED,
                94,
                91,
                4
        );

        addSkillSlot(
                Skills.HEALTH_BOOST,
                134,
                35,
                5
        );

        addSkillSlot(
                Skills.HASTE,
                134,
                91,
                6
        );

        addSkillSlot(
                Skills.SLOW_FALL,
                174,
                35,
                7
        );

        addSkillSlot(
                Skills.JUMP_BOOST,
                174,
                91,
                8
        );

        addSkillSlot(
                Skills.KNOCKBACK_RESISTANCE,
                214,
                35,
                9
        );

        addSkillSlot(
                Skills.IMMORTAL,
                214,
                91,
                10
        );
    }

    private void addSkillSlot(
            Skill skill,
            int x,
            int y,
            int slotIndex
    ) {
        addSlot(
                new SkillHolderSlot(
                        x,
                        y,
                        slotIndex,
                        container,
                        this,
                        skill
                )
        );
    }

    public Player getPlayer() {
        return player;
    }

    public int getSkillIndex(Skill skill) {
        return Skills.SKILLS.indexOf(skill);
    }

    /**
     * Checks permanent unlock state.
     *
     * This reads the synchronized ContainerData on the client.
     */
    public boolean isSkillUnlocked(Skill skill) {

        int index = getSkillIndex(skill);

        if (index < 0) {
            return false;
        }

        return (
                data.get(0)
                        & (1 << index)
        ) != 0;
    }

    /**
     * Checks temporary active state.
     */
    public boolean isSkillActive(Skill skill) {

        int index = getSkillIndex(skill);

        if (index < 0) {
            return false;
        }

        return (
                data.get(1)
                        & (1 << index)
        ) != 0;
    }

    /**
     * Checks whether the player has unlocked the
     * prerequisite for this skill.
     *
     * This is only used by the GUI.
     *
     * The server performs the actual validation.
     */
    public boolean canUnlockSkill(Skill skill) {

        if (isSkillUnlocked(skill)) {
            return true;
        }

        if (skill.prerequisite() == null) {
            return true;
        }

        int prerequisiteIndex = -1;

        for (int i = 0; i < Skills.SKILLS.size(); i++) {

            Skill prerequisite =
                    Skills.SKILLS.get(i);

            if (prerequisite.id().equals(
                    skill.prerequisite()
            )) {
                prerequisiteIndex = i;
                break;
            }
        }

        if (prerequisiteIndex < 0) {
            return false;
        }

        return (
                data.get(0)
                        & (1 << prerequisiteIndex)
        ) != 0;
    }

    /**
     * Handles clicks on skill slots.
     *
     * Client:
     *     sends ActivateSkillPayload
     *
     * Server:
     *     does NOT perform activation here.
     *     The C2S packet handler calls SkillManager.
     */
    @Override
    public void clicked(
            int slotId,
            int buttonNum,
            ContainerInput containerInput,
            Player player
    ) {
        super.clicked(
                slotId,
                buttonNum,
                containerInput,
                player
        );

        if (player.level().isClientSide()) {
            return;
        }

        if (slotId < 0 || slotId >= slots.size()) {
            return;
        }

        Slot slot = slots.get(slotId);

        if (!(slot instanceof SkillHolderSlot skillSlot)) {
            return;
        }

        Skill skill = skillSlot.getSkill();

        if (skillSlot.isSkillActive()) {
            return;
        }

        if (!skillSlot.canUnlockSkill()) {
            return;
        }

        ItemStack inserted = skillSlot.getItem();

        if (inserted.isEmpty()) {
            return;
        }

        if (!inserted.is(skill.requiredGem())) {
            return;
        }

        SkillManager.activateFromSlot(
                (ServerPlayer) player,
                skill,
                skillSlot
        );
    }

    /**
     * Prevent shift-clicking items into the skill slots.
     */
    @Override
    public ItemStack quickMoveStack(
            Player player,
            int slotIndex
    ) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    private void addPlayerGenericInventory(
            Inventory playerInventory
    ) {

        for (int row = 0; row < 3; row++) {

            for (int column = 0; column < 9; column++) {

                addSlot(
                        new Slot(
                                playerInventory,
                                column
                                        + row * 9
                                        + 9,
                                48
                                        + column * 18,
                                141
                                        + row * 18
                        )
                );
            }
        }
    }

    private void addPlayerHotbarInventory(
            Inventory playerInventory
    ) {

        for (int i = 0; i < 9; i++) {

            addSlot(
                    new Slot(
                            playerInventory,
                            i,
                            48 + i * 18,
                            199
                    )
            );
        }
    }
}