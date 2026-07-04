package org.cobra.moreores.world.block.entity.gem.machine;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum MachineStatus implements StringRepresentable {
    IDLE("idle"),
    RUNNING("running"),
    PAUSED("paused");

    private final String name;

    MachineStatus(String name) {
        this.name = name;
    }

    public static final Codec<MachineStatus> CODEC = StringRepresentable.fromEnum(MachineStatus::values);

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
