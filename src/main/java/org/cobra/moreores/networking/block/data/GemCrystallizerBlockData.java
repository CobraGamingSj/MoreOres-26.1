package org.cobra.moreores.networking.block.data;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.tags.ModItemTags;
import org.cobra.moreores.world.block.entity.gem.machine.GemCrystallizerBlockEntity;
import org.cobra.moreores.world.item.ModItems;
import org.lwjgl.glfw.GLFW;

public record GemCrystallizerBlockData(int keyCode, BlockPos pos) implements CustomPacketPayload {
    public static final Type<GemCrystallizerBlockData> TYPE = new Type<>(MoreOresModInitializer.id("c_block_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, GemCrystallizerBlockData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, GemCrystallizerBlockData::keyCode,
            BlockPos.STREAM_CODEC, GemCrystallizerBlockData::pos,
            GemCrystallizerBlockData::new
    );

    public void handle(ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerLevel world = context.player().level();
            ServerPlayer player = context.player();
            boolean alt = keyCode == GLFW.GLFW_KEY_LEFT_ALT || keyCode == GLFW.GLFW_KEY_RIGHT_ALT;

            if(alt) {
                ItemStack heldStack = player.getItemInHand(InteractionHand.MAIN_HAND);

                if(!heldStack.isEmpty() && world.getBlockEntity(pos) instanceof GemCrystallizerBlockEntity be) {
                    ItemStack energyStack = be.energyStack();
                    ItemStack inputStack = be.ingredientStack();
                    ItemStack inputBeforeStack = be.ingredientAfterStack();
                    ItemStack radiantDustStack = be.radiantDustStack();

                    if(heldStack.is(ModItems.RADIANT_DUST)) {
                        if(radiantDustStack.isEmpty()) {
                            be.setItem(GemCrystallizerBlockEntity.RADIANT_DUST_SLOT, heldStack.copyWithCount(heldStack.getCount()));
                            heldStack.shrink(heldStack.getCount());
                        } else if (ItemStack.isSameItem(radiantDustStack, heldStack) && radiantDustStack.getCount() < radiantDustStack.getMaxStackSize()) {
                            radiantDustStack.grow(heldStack.getCount());
                            heldStack.shrink(heldStack.getCount());
                            be.setChanged();
                        }
                    }

                    if(heldStack.getItem() == ModItems.ENERGY_INGOT) {
                        if(energyStack.isEmpty()) {
                            be.setItem(GemCrystallizerBlockEntity.ENERGY_SOURCE_SLOT, heldStack.copyWithCount(heldStack.getCount()));
                            heldStack.shrink(heldStack.getCount());
                        } else if (ItemStack.isSameItem(energyStack, heldStack) && energyStack.getCount() < energyStack.getMaxStackSize()) {
                            energyStack.grow(heldStack.getCount());
                            heldStack.shrink(heldStack.getCount());
                            be.setChanged();
                        }
                    }

                    if(heldStack.is(ModItemTags.RAW_GEMSTONE)) {
                        if(inputStack.isEmpty()) {
                            be.setItem(GemCrystallizerBlockEntity.INGREDIENT_BEFORE_SLOT, heldStack.copyWithCount(heldStack.getCount()));
                            heldStack.shrink(heldStack.getCount());
                        } else if (ItemStack.isSameItem(inputStack, heldStack)) {
                            inputStack.grow(heldStack.getCount());
                            heldStack.shrink(heldStack.getCount());
                            be.setChanged();
                        }
                    }
                }
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
