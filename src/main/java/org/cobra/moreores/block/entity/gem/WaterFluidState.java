package org.cobra.moreores.block.entity.gem;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum WaterFluidState implements StringRepresentable {
    IDLE("idle"),
    FILLING("filling"),
    EMPTYING("emptying");

    private final String name;

    WaterFluidState(String name) {
        this.name = name;
    }

    public static final Codec<WaterFluidState> CODEC = StringRepresentable.fromEnum(WaterFluidState::values);

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
