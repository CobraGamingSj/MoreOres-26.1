package org.cobra.moreores.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.cobra.moreores.core.registry.ModBlockTags;

public class GemDetector extends Item {
    public GemDetector(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if(!world.isClientSide()) {
            BlockPos playerPos = user.blockPosition();

            int rad = 50;
            for(int x = -rad; x <= rad; x++) {
                for(int y = -rad; y <= rad; y++) {
                    for(int z = -rad; z <= rad; z++) {

                        BlockPos checkPos = playerPos.offset(x, y, z);
                        BlockState state = world.getBlockState(checkPos);

                        if(state.is(ModBlockTags.MOD_ORES)) {
                            user.sendSystemMessage(Component.literal("Ore detected at " + checkPos.toShortString() + ", ore: " + state));
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }

            user.sendSystemMessage(Component.literal("No ore found"));
        }
        return InteractionResult.SUCCESS;
    }
}
