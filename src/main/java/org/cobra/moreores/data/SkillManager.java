package org.cobra.moreores.data;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.cobra.moreores.util.Skill;
import org.cobra.moreores.util.SkillEffect;
import org.cobra.moreores.util.Skills;

public final class SkillManager {

    private SkillManager() {
    }

    /**
     * Gets the persistent skill data belonging to this player.
     */
    public static PlayerSkillData getData(ServerPlayer player) {
        return player.getAttachedOrCreate(
                SkillAttachments.PLAYER_SKILL_DATA
        );
    }

    /**
     * Checks whether the player has permanently unlocked the skill.
     */
    public static boolean isUnlocked(
            ServerPlayer player,
            Skill skill
    ) {
        return getData(player)
                .getSkillProgress(skill.id())
                .isUnlocked();
    }

    /**
     * Checks whether the skill is currently active.
     */
    public static boolean isActive(
            ServerPlayer player,
            Skill skill
    ) {
        SkillProgress progress =
                getData(player).getSkillProgress(skill.id());

        return progress.isActive();
    }

    /**
     * Checks whether the player is allowed to unlock the skill.
     *
     * Unlocking is permanent.
     *
     * Physical gems do NOT determine whether a prerequisite
     * is unlocked.
     */
    public static boolean canUnlock(
            ServerPlayer player,
            Skill skill
    ) {
        PlayerSkillData data = getData(player);

        SkillProgress progress =
                data.getSkillProgress(skill.id());

        if (progress.isUnlocked()) {
            return false;
        }

        if (skill.prerequisite() == null) {

            return true;
        }

        return data.isUnlocked(skill.prerequisite());
    }

    /**
     * Checks whether the player has the required gemstone.
     */
    public static boolean hasRequiredGem(
            ServerPlayer player,
            Skill skill
    ) {
        Inventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getContainerSize(); i++) {

            ItemStack stack = inventory.getItem(i);

            if (stack.is(skill.requiredGem())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Removes the required gemstone(s) from the player's inventory.
     *
     * Returns false if the player doesn't have enough.
     */
    private static boolean removeRequiredGem(
            ServerPlayer player,
            Skill skill
    ) {
        Inventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getContainerSize(); i++) {

            ItemStack stack = inventory.getItem(i);

            if (!stack.is(skill.requiredGem())) {
                continue;
            }

            stack.shrink(1);
        }

        return false;
    }

    /**
     * Activates a skill.
     *
     * This:
     *
     * 1. Checks the prerequisite.
     * 2. Checks the gemstone.
     * 3. Removes the gemstone from inventory.
     * 4. Permanently unlocks the skill.
     * 5. Stores the gemstone in the skill progress.
     * 6. Stores the expiry timestamp.
     * 7. Applies the effect.
     */
    public static boolean activate(
            ServerPlayer player,
            Skill skill
    ) {
        PlayerSkillData data = getData(player);

        SkillProgress progress =
                data.getSkillProgress(skill.id());

        // Already active.
        if (progress.isActive()) {
            return false;
        }

        if (progress.hasExpired()) {
            deactivate(player, skill);
        }

        if (!progress.isUnlocked()) {

            if (!canUnlock(player, skill)) {
                return false;
            }

            progress.setUnlocked(true);
        }

        if (!hasRequiredGem(player, skill)) {
            return false;
        }

        if (!removeRequiredGem(player, skill)) {
            return false;
        }

        ItemStack storedGem =
                new ItemStack(
                        skill.requiredGem()
                );

        progress.setGem(storedGem);

        long expiresAt =
                System.currentTimeMillis()
                        + skill.duration();

        progress.setExpiresAt(expiresAt);

        SkillEffect effect = skill.effect();

        if (effect != null) {
            effect.apply(player);
        }

        return true;
    }

    /**
     * Deactivates a skill.
     *
     * This removes the effect and returns the stored gemstone
     * to the player's inventory.
     */
    public static void deactivate(
            ServerPlayer player,
            Skill skill
    ) {
        PlayerSkillData data = getData(player);

        SkillProgress progress =
                data.getSkillProgress(skill.id());

        if (!progress.isActive()
                && progress.getGem().isEmpty()) {
            return;
        }

        SkillEffect effect = skill.effect();

        if (effect != null) {
            effect.remove(player);
        }

        ItemStack gem = progress.getGem();

        if (!gem.isEmpty()) {
            if (!player.getInventory().add(gem.copy())) {
                player.drop(
                        gem.copy(),
                        false
                );
            }
        }

        progress.clearActivation();
    }

    /**
     * Called periodically on the server.
     *
     * Checks all currently active skills and removes
     * expired ones.
     */
    public static void tick(ServerPlayer player) {

        PlayerSkillData data = getData(player);

        for (Skill skill : Skills.SKILLS) {

            SkillProgress progress =
                    data.getSkillProgress(skill.id());

            if (!progress.hasExpired()) {
                continue;
            }

            deactivate(player, skill);
        }
    }
}