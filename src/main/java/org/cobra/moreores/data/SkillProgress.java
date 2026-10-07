package org.cobra.moreores.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

public class SkillProgress {

    public static final Codec<SkillProgress> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.BOOL.fieldOf("unlocked")
                            .forGetter(SkillProgress::isUnlocked),

                    ItemStack.OPTIONAL_CODEC
                            .fieldOf("gem")
                            .forGetter(SkillProgress::getGem),

                    Codec.LONG.fieldOf("expires_at")
                            .forGetter(SkillProgress::getExpiresAt)
            ).apply(instance, SkillProgress::new));

    private boolean unlocked;
    private ItemStack gem;
    private long expiresAt;

    public SkillProgress() {
        this(false, ItemStack.EMPTY, 0L);
    }

    public SkillProgress(
            boolean unlocked,
            ItemStack gem,
            long expiresAt
    ) {
        this.unlocked = unlocked;
        this.gem = gem;
        this.expiresAt = expiresAt;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public ItemStack getGem() {
        return gem;
    }

    public void setGem(ItemStack gem) {
        this.gem = gem;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isActive() {
        return !gem.isEmpty()
                && expiresAt > System.currentTimeMillis();
    }

    public boolean hasExpired() {
        return !gem.isEmpty()
                && expiresAt <= System.currentTimeMillis();
    }

    public void clearActivation() {
        this.gem = ItemStack.EMPTY;
        this.expiresAt = 0L;
    }
}