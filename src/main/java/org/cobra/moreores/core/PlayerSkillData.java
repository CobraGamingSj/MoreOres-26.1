package org.cobra.moreores.core;

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

    public PlayerSkillData copy() {
        Map<Identifier, SkillProgress> copiedSkills = new HashMap<>();

        skills.forEach((id, progress) -> {
            copiedSkills.put(
                    id,
                    new SkillProgress(
                            progress.isUnlocked(),
                            progress.getGem().copy(),
                            progress.getExpiresAt()
                    )
            );
        });

        return new PlayerSkillData(copiedSkills);
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