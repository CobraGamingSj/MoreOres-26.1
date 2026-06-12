package org.cobra.moreores.block.entity.gem;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum EnergyState implements StringRepresentable {
    IDLE("idle"),
    INSERTING("inserting"),
    EXTRACTING("extracting");

    private final String name;

    EnergyState(String name) {
        this.name = name;
    }

    public static final Codec<EnergyState> CODEC = StringRepresentable.fromEnum(EnergyState::values);

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
