package org.cobra.moreores.block.entity.gem;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum PolishingInfusionState implements StringRepresentable {
    IDLE("idle"),
    RUNNING("running"),
    PAUSED("paused");

    private final String name;

    PolishingInfusionState(String name) {
        this.name = name;
    }

    public static final Codec<PolishingInfusionState> CODEC = StringRepresentable.fromEnum(PolishingInfusionState::values);

    public boolean isIdle() {
        return this == IDLE;
    }

    public boolean isRunning() {
        return this == RUNNING;
    }

    public boolean isPaused() {
        return this == PAUSED;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
