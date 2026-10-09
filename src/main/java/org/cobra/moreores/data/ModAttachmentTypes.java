package org.cobra.moreores.data;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.core.PlayerSkillData;

public final class ModAttachmentTypes {

    public static final AttachmentType<PlayerSkillData> PLAYER_SKILL_DATA =
            AttachmentRegistry.create(
                    MoreOresModInitializer.id("player_skill_data"),
                    builder -> builder
                            .initializer(PlayerSkillData::new)
                            .persistent(PlayerSkillData.CODEC)
                            .copyOnDeath()
            );

    private ModAttachmentTypes() {
    }

    public static void register() {

    }
}