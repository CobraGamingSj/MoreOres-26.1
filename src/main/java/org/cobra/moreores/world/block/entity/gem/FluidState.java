package org.cobra.moreores.world.block.entity.gem;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum FluidState implements StringRepresentable {
    IDLE("idle"),
    FILLING("filling"),
    EMPTYING("emptying");

    private final String name;

    FluidState(String name) {
        this.name = name;
    }

    public static final Codec<FluidState> CODEC = StringRepresentable.fromEnum(FluidState::values);

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
