package org.cobra.moreores.data;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;

public final class SkillAttachments {

    public static final AttachmentType<PlayerSkillData> PLAYER_SKILL_DATA =
            AttachmentRegistry.create(
                    Identifier.fromNamespaceAndPath(
                            "moreores",
                            "player_skill_data"
                    ),
                    builder -> builder
                            .initializer(PlayerSkillData::new)
                            .persistent(PlayerSkillData.CODEC)
                            .copyOnDeath()
            );

    private SkillAttachments() {
    }
}