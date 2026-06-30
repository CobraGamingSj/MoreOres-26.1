package org.cobra.moreores.world.block.entity.gem;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum MachineEnergyState implements StringRepresentable {
    IDLE("idle"),
    INSERTING("inserting"),
    EXTRACTING("extracting");

    private final String name;

    MachineEnergyState(String name) {
        this.name = name;
    }

    public static final Codec<MachineEnergyState> CODEC = StringRepresentable.fromEnum(MachineEnergyState::values);

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
