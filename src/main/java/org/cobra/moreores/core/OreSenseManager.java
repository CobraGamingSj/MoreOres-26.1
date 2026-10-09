package org.cobra.moreores.core;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.cobra.moreores.tags.ModBlockTags;
import org.cobra.moreores.util.Skills;

import java.util.*;

public final class OreSenseManager {

    private static final long COOLDOWN_MS = 300_000L;
    private static final long ROOM_DURATION_MS = 45_000L;

    private static final long RETURN_DELAY_MS = 1500L;

    private static final int ROOM_RADIUS = 5;
    private static final int ROOM_HEIGHT = 3;

    private static final Map<UUID, Long> NEXT_SCAN = new HashMap<>();
    private static final Map<UUID, ActiveVisit> ACTIVE_VISITS = new HashMap<>();

    private OreSenseManager() {}


    public static void tick(ServerPlayer player) {
        UUID uuid = player.getUUID();

        if (ACTIVE_VISITS.containsKey(uuid)) {
            tickReturn(player);
            return;
        }

        if (!SkillManager.isActive(player, Skills.ORE_SENSE)) {
            NEXT_SCAN.remove(uuid);
            return;
        }

        long now = System.currentTimeMillis();
        Long nextScan = NEXT_SCAN.get(uuid);

        if (nextScan == null || now >= nextScan) {
            NEXT_SCAN.put(uuid, now + COOLDOWN_MS);
            detectOre(player);
        }
    }

    private static void tickReturn(ServerPlayer player) {
        UUID uuid = player.getUUID();
        ActiveVisit visit = ACTIVE_VISITS.get(uuid);

        if (visit == null) {
            return;
        }

        long now = System.currentTimeMillis();
        ServerLevel roomLevel = player.server.getLevel(visit.roomDimension());

        boolean allOresMined = roomLevel != null
                && !visit.orePositions().isEmpty()
                && visit.orePositions().stream().allMatch(pos ->
                roomLevel.getBlockState(pos).isAir());

        long allMinedAt = visit.allMinedAt();

        if (allOresMined && allMinedAt == 0L) {
            allMinedAt = now;

            visit = new ActiveVisit(
                    visit.dimension(),
                    visit.x(), visit.y(), visit.z(),
                    visit.yaw(), visit.pitch(),
                    visit.roomDimension(),
                    visit.originalBlocks(),
                    visit.orePositions(),
                    visit.returnAt(),
                    allMinedAt
            );

            ACTIVE_VISITS.put(uuid, visit);
        }

        boolean shouldReturn = now >= visit.returnAt()
                || (allMinedAt != 0L
                && now - allMinedAt >= RETURN_DELAY_MS);

        if (!shouldReturn) {
            return;
        }

        ACTIVE_VISITS.remove(uuid);

        ServerLevel originalLevel = player.server.getLevel(visit.dimension());

        if (originalLevel != null) {
            player.teleportTo(
                    originalLevel,
                    visit.x(), visit.y(), visit.z(),
                    Set.of(),
                    visit.yaw(), visit.pitch(),
                    false
            );
        }

        restoreRoom(player, visit);
        NEXT_SCAN.put(uuid, System.currentTimeMillis() + COOLDOWN_MS);
     }

    public static void detectOre(ServerPlayer player) {
        ServerLevel level = player.level();
        BlockPos center = player.blockPosition();

        int minX = (center.getX() >> 4) << 4;
        int minZ = (center.getZ() >> 4) << 4;

        BlockPos min = new BlockPos(minX, level.getMinY(), minZ);
        BlockPos max = new BlockPos(
                minX + 15,
                level.getMaxY() - 1,
                minZ + 15
        );

        BlockPos target = null;
        int matches = 0;
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            if (!level.hasChunkAt(pos)) {
                continue;
            }

            if (!level.getBlockState(pos).is(ModBlockTags.ORE_SENSE_TARGETS)) {
                continue;
            }

            matches++;

            if (player.getRandom().nextInt(matches) == 0) {
                target = pos.immutable();
            }
        }

        if (target != null) {
            teleportToOre(player, level, target);
        }
    }

    private static void teleportToOre(
            ServerPlayer player,
            ServerLevel level,
            BlockPos orePos
    ) {
        Map<BlockPos, BlockState> originalBlocks = new HashMap<>();

        Set<BlockPos> orePositions = new HashSet<>();

        BlockPos spawn = createRoom(level, orePos, player, originalBlocks, orePositions);

        player.sendSystemMessage(Component.literal(
                "Tracked ores: " + orePositions.size()
        ));

        if (spawn == null) {
            restoreBlocks(level, originalBlocks);

            player.sendSystemMessage(
                    Component.literal("Couldn't create a safe ore viewing room.")
                            .withStyle(ChatFormatting.RED),
                    true
            );
            return;
        }

        UUID uuid = player.getUUID();

        ACTIVE_VISITS.put(uuid, new ActiveVisit(
                player.level().dimension(),
                player.getX(),
                player.getY(),
                player.getZ(),
                player.getYRot(),
                player.getXRot(),
                level.dimension(),
                originalBlocks,
                orePositions,
                System.currentTimeMillis() + ROOM_DURATION_MS,
                0L
        ));

        player.connection.send(new ClientboundSetTitleTextPacket(
                Component.literal("Ore Found!")
                        .withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD)
        ));

        player.connection.send(new ClientboundSetSubtitleTextPacket(
                Component.literal(
                        "X: " + orePos.getX()
                                + " Y: " + orePos.getY()
                                + " Z: " + orePos.getZ()
                ).withStyle(ChatFormatting.YELLOW)
        ));

        player.connection.send(new ClientboundSetTitlesAnimationPacket(5, 60, 10));

        double dx = orePos.getX() + 0.5 - (spawn.getX() + 0.5);
        double dy = orePos.getY() + 0.5 - (spawn.getY() + 1.0);
        double dz = orePos.getZ() + 0.5 - (spawn.getZ() + 0.5);

        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        float pitch = (float) -Math.toDegrees(
                Math.atan2(dy, horizontalDistance)
        );

        player.teleportTo(
                level,
                spawn.getX() + 0.5,
                spawn.getY(),
                spawn.getZ() + 0.5,
                Set.of(),
                yaw,
                pitch,
                false
        );
    }

    private static BlockPos createRoom(
            ServerLevel level,
            BlockPos orePos,
            ServerPlayer player,
            Map<BlockPos, BlockState> originalBlocks,
            Set<BlockPos> orePositions
    ) {
        int floorY = orePos.getY() - 1;
        int topY = orePos.getY() + ROOM_HEIGHT - 1;

        if (floorY < level.getMinY() || topY >= level.getMaxY()) {
            return null;
        }

        for (int x = -ROOM_RADIUS; x < ROOM_RADIUS; x++) {
            for (int z = -ROOM_RADIUS; z < ROOM_RADIUS; z++) {
                for (int y = floorY; y <= topY; y++) {
                    BlockPos pos = new BlockPos(
                            orePos.getX() + x,
                            y,
                            orePos.getZ() + z
                    );

                    if (!level.hasChunkAt(pos)) {
                        return null;
                    }

                    BlockState state = level.getBlockState(pos);

                    if (state.is(ModBlockTags.ORE_SENSE_TARGETS)) {
                        orePositions.add(pos.immutable());
                        continue;
                    }

                    if (!state.getFluidState().isEmpty()
                            || level.getBlockEntity(pos) != null
                            || state.getDestroySpeed(level, pos) < 0) {
                        return null;
                    }
                }
            }
        }

        for (int x = -ROOM_RADIUS; x < ROOM_RADIUS; x++) {
            for (int z = -ROOM_RADIUS; z < ROOM_RADIUS; z++) {
                BlockPos floor = new BlockPos(
                        orePos.getX() + x,
                        floorY,
                        orePos.getZ() + z
                );

                BlockState floorState = level.getBlockState(floor);

                if (!floorState.is(ModBlockTags.ORE_SENSE_TARGETS)) {
                    originalBlocks.put(floor.immutable(), floorState);
                }

                for (int y = orePos.getY(); y <= topY; y++) {
                    BlockPos pos = new BlockPos(
                            orePos.getX() + x,
                            y,
                            orePos.getZ() + z
                    );

                    BlockState state = level.getBlockState(pos);

                    if (state.isAir()
                            || state.is(ModBlockTags.ORE_SENSE_TARGETS)) {
                        continue;
                    }

                    originalBlocks.put(pos.immutable(), state);
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }

        for (int x = -ROOM_RADIUS; x < ROOM_RADIUS; x++) {
            for (int z = -ROOM_RADIUS; z < ROOM_RADIUS; z++) {
                for (int y = orePos.getY(); y <= topY - 1; y++) {
                    BlockPos feet = new BlockPos(
                            orePos.getX() + x,
                            y,
                            orePos.getZ() + z
                    );

                    if (isSafePosition(level, feet, player)) {
                        return feet.immutable();
                    }
                }
            }
        }

        return null;
    }

    private static boolean isSafePosition(
            ServerLevel level,
            BlockPos feet,
            ServerPlayer player
    ) {
        BlockPos head = feet.above();
        BlockPos floor = feet.below();

        BlockState feetState = level.getBlockState(feet);
        BlockState headState = level.getBlockState(head);
        BlockState floorState = level.getBlockState(floor);

        if (!feetState.getCollisionShape(level, feet).isEmpty()
                || !headState.getCollisionShape(level, head).isEmpty()
                || floorState.getCollisionShape(level, floor).isEmpty()) {
            return false;
        }

        if (!feetState.getFluidState().isEmpty()
                || !headState.getFluidState().isEmpty()
                || !floorState.getFluidState().isEmpty()) {
            return false;
        }

        double x = feet.getX() + 0.5;
        double y = feet.getY();
        double z = feet.getZ() + 0.5;

        var box = player.getBoundingBox().move(
                x - player.getX(),
                y - player.getY(),
                z - player.getZ()
        );

        return level.noCollision(player, box);
    }

    private static void restoreRoom(ServerPlayer player, ActiveVisit visit) {
        ServerLevel roomLevel = player.server.getLevel(visit.roomDimension());

        if (roomLevel != null) {
            restoreBlocks(roomLevel, visit.originalBlocks());
        }
    }

    private static void restoreBlocks(
            ServerLevel level,
            Map<BlockPos, BlockState> originalBlocks
    ) {
        for (Map.Entry<BlockPos, BlockState> entry : originalBlocks.entrySet()) {
            level.setBlock(entry.getKey(), entry.getValue(), 3);
        }

        originalBlocks.clear();
    }

    public static void onDisconnect(ServerPlayer player) {
        UUID uuid = player.getUUID();
        ActiveVisit visit = ACTIVE_VISITS.remove(uuid);

        if (visit != null) {
            restoreRoom(player, visit);
        }

        NEXT_SCAN.remove(uuid);
    }

    private record ActiveVisit(
            ResourceKey<Level> dimension, double x, double y, double z,
            float yaw, float pitch, ResourceKey<Level> roomDimension,
            Map<BlockPos, BlockState> originalBlocks, Set<BlockPos> orePositions, long returnAt, long allMinedAt) {}
}