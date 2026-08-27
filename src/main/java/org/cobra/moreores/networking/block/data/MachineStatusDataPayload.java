package org.cobra.moreores.networking.block.data;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.block.entity.gem.machine.AbstractGemMachineBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record MachineStatusDataPayload(BlockPos blockPos, String action) implements CustomPacketPayload {
    public static final Type<MachineStatusDataPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, "polishing_state"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MachineStatusDataPayload> STREAM_CODEC = StreamCodec.ofMember((payload, buf) -> {
        buf.writeBlockPos(payload.blockPos);
        buf.writeUtf(payload.action);
    }, buf -> new MachineStatusDataPayload(buf.readBlockPos(), buf.readUtf()));

    public void handle(ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            if(context.player().level().getBlockEntity(blockPos) instanceof AbstractGemMachineBlockEntity<?> be) {
                switch(action) {
                    case "start" -> be.startProcess();
                    case "pause" -> be.pauseProcess();
                    case "resume" -> be.resumeProcess();
                    case "stop" -> be.stopProcess();
                }
                be.setChanged();
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
