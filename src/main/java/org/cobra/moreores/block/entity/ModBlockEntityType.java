package org.cobra.moreores.block.entity;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.block.ModBlocks;
import org.cobra.moreores.block.entity.gem.GemCrystallizeBlockEntity;
import org.cobra.moreores.block.entity.gem.GemPurifierBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntityType {

    public static final BlockEntityType<GemPurifierBlockEntity> GEM_PURIFIER_BLOCK_ENTITY =
            register("gem_purifier_block", FabricBlockEntityTypeBuilder.create(GemPurifierBlockEntity::new, ModBlocks.GEM_PURIFIER_BLOCK));

    public static final BlockEntityType<GemCrystallizeBlockEntity> GEM_CRYSTALLIZE_BLOCK_ENTITY =
            register("gem_crystallizer_block", FabricBlockEntityTypeBuilder.create(GemCrystallizeBlockEntity::new, ModBlocks.GEM_CRYSTALLIZER_BLOCK));

    private static <BE extends BlockEntity> BlockEntityType<BE> register(String id, FabricBlockEntityTypeBuilder<BE> factory) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, id), factory.build());
    }

    public static void register() {
        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, GEM_PURIFIER_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> blockEntity.fluidStorage), GEM_PURIFIER_BLOCK_ENTITY);
        MoreOresModInitializer.LOGGER.info("Loading ModBlockEntityTypes for {} mod.", MoreOresModInitializer.MOD_ID);
    }
}
