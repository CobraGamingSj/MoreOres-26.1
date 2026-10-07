package org.cobra.moreores.data;

import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class PlayerSkillData {

    public static final Codec<PlayerSkillData> CODEC =
            Codec.unboundedMap(
                    Identifier.CODEC,
                    SkillProgress.CODEC
            ).xmap(
                    PlayerSkillData::new,
                    PlayerSkillData::getSkills
            );

    private final Map<Identifier, SkillProgress> skills;

    public PlayerSkillData() {
        this.skills = new HashMap<>();
    }

    public PlayerSkillData(Map<Identifier, SkillProgress> skills) {
        this.skills = new HashMap<>(skills);
    }

    /**
     * Gets the progress for a skill.
     *
     * If the player has never interacted with the skill,
     * a new SkillProgress is created.
     */
    public SkillProgress getSkillProgress(Identifier skillId) {
        return skills.computeIfAbsent(
                skillId,
                id -> new SkillProgress()
        );
    }

    public boolean isUnlocked(Identifier skillId) {
        return getSkillProgress(skillId).isUnlocked();
    }

    public void unlock(Identifier skillId) {
        getSkillProgress(skillId).setUnlocked(true);
    }

    public boolean isActive(Identifier skillId) {
        return getSkillProgress(skillId).isActive();
    }

    public Map<Identifier, SkillProgress> getSkills() {
        return skills;
    }
}