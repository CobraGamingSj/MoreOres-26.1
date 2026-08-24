package org.cobra.moreores.networking.block.data;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.client.gui.screen.GemPurifierMenu;
import org.cobra.moreores.world.block.entity.gem.machine.GemPurifierBlockEntity;

public record GemPurifierScreenSync(ItemStack result, BlockPos blockPos) implements CustomPacketPayload {
    public static final Type<GemPurifierScreenSync> TYPE = new Type<>(MoreOresModInitializer.id("screen_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, GemPurifierScreenSync> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, GemPurifierScreenSync::result,
            BlockPos.STREAM_CODEC, GemPurifierScreenSync::blockPos,
            GemPurifierScreenSync::new
    );

    public void handle(ClientPlayNetworking.Context context) {
        Level level = context.client().level;
        if(level.getBlockEntity(blockPos) instanceof GemPurifierBlockEntity blockEntity) {
            if(context.player().containerMenu instanceof GemPurifierMenu menu) {

            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
