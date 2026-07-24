package org.cobra.moreores.util;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

public record FluidStack(FluidVariant variant, long amount) {

    public FluidVariant getVariant() {
        return variant;
    }

    public long getFluidAmount() {
        return amount;
    }

    public FluidStack withAmount(long newAmount) {
        return new FluidStack(this.variant, newAmount);
    }

    public static long convertDropletsToMb(long drops) {
        return (drops / 81);
    }

    public static long convertMbToDroplets(long milliBuckets) {
        return milliBuckets * 81;
    }
}