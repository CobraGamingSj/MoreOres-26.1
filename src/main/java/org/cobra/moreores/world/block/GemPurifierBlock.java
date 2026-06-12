package org.cobra.moreores.world.block;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.block.entity.TickableBlockEntity;
import org.cobra.moreores.world.block.entity.gem.GemPurifierBlockEntity;
import org.cobra.moreores.world.item.util.impl.PurificationGemstones;
import org.cobra.moreores.networking.block.data.GemPurifierBlockData;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public class GemPurifierBlock extends BaseEntityBlock implements EntityBlock {
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 14, 16);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty REDSTONE_POWERED = BooleanProperty.create("redstone_powered");
    public static final EnumProperty<PurificationGemstones> IS_POLISHING = EnumProperty.create("is_polishing", PurificationGemstones.class);
    public static final MapCodec<GemPurifierBlock> CODEC = GemPurifierBlock.simpleCodec(GemPurifierBlock::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    protected GemPurifierBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(REDSTONE_POWERED, false)
                .setValue(IS_POLISHING, PurificationGemstones.EMPTY));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getClockWise()).setValue(REDSTONE_POWERED, ctx.getLevel().hasNeighborSignal(ctx.getClickedPos()))
                .setValue(IS_POLISHING, PurificationGemstones.EMPTY);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GemPurifierBlockEntity(pos, state);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean moved) {
        if (state.getBlock() != state.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GemPurifierBlockEntity) {
                Containers.dropContents(world, pos, (GemPurifierBlockEntity) blockEntity);
                world.updateNeighbourForOutputSignal(pos,this);
            }
            super.affectNeighborsAfterRemoval(state, world, pos, moved);
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (!world.isClientSide()) {
            boolean bl = state.getValue(REDSTONE_POWERED);
            if (bl != world.hasNeighborSignal(pos)) {
                if (bl) {
                    world.scheduleTick(pos, this, 4);
                } else {
                    world.setBlock(pos, state.cycle(REDSTONE_POWERED), Block.UPDATE_CLIENTS);
                    MoreOresModInitializer.LOGGER.info("Receiving Redstone Signal at [{}, {}, {}]", pos.getX(), pos.getY(), pos.getZ());
                }
            }
        }
    }
    
    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BlockState newState = state;

        if(state.getValue(REDSTONE_POWERED) && !world.hasNeighborSignal(pos)) {
            newState = newState.setValue(REDSTONE_POWERED, false);
        }

        if(world.getBlockEntity(pos) instanceof GemPurifierBlockEntity be) {
            newState = newState.setValue(IS_POLISHING, be.getGem());
        }

        world.setBlock(pos, newState, Block.UPDATE_ALL);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if(world.isClientSide()) {
            Window handle = Minecraft.getInstance().getWindow();
            boolean alt = InputConstants.isKeyDown(handle, GLFW.GLFW_KEY_LEFT_ALT);
            if(alt) {
                ClientPlayNetworking.send(new GemPurifierBlockData(GLFW.GLFW_KEY_LEFT_ALT, pos));
                return InteractionResult.CONSUME;
            }
        } else {
            MenuProvider screenHandlerFactory = ((GemPurifierBlockEntity) world.getBlockEntity(pos));
            if (screenHandlerFactory != null) {
                player.openMenu(screenHandlerFactory);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return TickableBlockEntity.createTicker(world, state, type);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return super.mirror(state, mirror);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(REDSTONE_POWERED);
        builder.add(IS_POLISHING);
    }
}