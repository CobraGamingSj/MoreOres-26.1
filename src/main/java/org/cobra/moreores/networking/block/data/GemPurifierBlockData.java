package org.cobra.moreores.networking.block.data;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.block.entity.gem.GemPurifierBlockEntity;
import org.cobra.moreores.world.item.ModItems;
import org.cobra.moreores.core.registry.tag.ModItemTags;
import org.lwjgl.glfw.GLFW;

public record GemPurifierBlockData(int keyCode, BlockPos pos) implements CustomPacketPayload {
    public static final Type<GemPurifierBlockData> ID = new Type<>(MoreOresModInitializer.id("block_key"));

    public static final StreamCodec<RegistryFriendlyByteBuf, GemPurifierBlockData> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, GemPurifierBlockData::keyCode,
            BlockPos.STREAM_CODEC, GemPurifierBlockData::pos,
            GemPurifierBlockData::new
    );

    public void handle(ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerLevel world = context.player().level();
            ServerPlayer player = context.player();
            boolean alt = keyCode == GLFW.GLFW_KEY_LEFT_ALT || keyCode == GLFW.GLFW_KEY_RIGHT_ALT;

            if(world.getBlockEntity(pos) instanceof GemPurifierBlockEntity be) {
                if(alt) {

                    ItemStack heldStack = player.getMainHandItem();

                    ItemStack energyStack = be.energyStack();
                    ItemStack fluidStack = be.fluidStack();
                    ItemStack inputStack = be.ingredientStack();

                    if((heldStack.getItem() == ModItems.ENERGY_INGOT || heldStack.getItem() == ModBlocks.ENERGY_BLOCK.asItem())) {
                        if(energyStack.isEmpty()) {
                            be.setItem(GemPurifierBlockEntity.ENERGY_SOURCE_SLOT, heldStack.copyWithCount(heldStack.getCount()));
                            heldStack.shrink(heldStack.getCount());} else if (ItemStack.isSameItem(energyStack, heldStack) && energyStack.getCount() < energyStack.getMaxStackSize()) {
                            energyStack.grow(heldStack.getCount());
                            heldStack.shrink(heldStack.getCount());
                            be.setChanged();
                        }
                    }

                    if(heldStack.is(Items.WATER_BUCKET)) {
                        if(fluidStack.isEmpty()) {
                            be.setItem(GemPurifierBlockEntity.WATER_SOURCE_SLOT, heldStack.copy());
                            heldStack.shrink(1);
                        } else if (ItemStack.isSameItem(fluidStack, heldStack)) {
                            fluidStack.grow(heldStack.getCount());
                            heldStack.shrink(heldStack.getCount());
                            be.setChanged();
                        }
                    }

                    if(heldStack.is(ModItemTags.RAW_GEMSTONE) || heldStack.is(ModItemTags.RAW_GEMSTONE_BLOCKS)) {
                        if(inputStack.isEmpty()) {
                            be.setItem(GemPurifierBlockEntity.INGREDIENT_SLOT, heldStack.copyWithCount(heldStack.getCount()));
                            heldStack.shrink(heldStack.getCount());
                        } else if (ItemStack.isSameItem(inputStack, heldStack)) {
                            inputStack.grow(heldStack.getCount());
                            heldStack.shrink(heldStack.getCount());
                            be.setChanged();
                        }
                    }
                } else {
                    player.openMenu(be);
                }
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
