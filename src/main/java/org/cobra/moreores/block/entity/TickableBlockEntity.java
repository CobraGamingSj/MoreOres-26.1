package org.cobra.moreores.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public interface TickableBlockEntity {

    void tick(Level world, BlockPos pos, BlockState state);

    static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return !world.isClientSide() ? (world0, blockPos, blockState, blockEntity) -> ((TickableBlockEntity) blockEntity).tick(world0, blockPos, blockState) : null;
    }
}
