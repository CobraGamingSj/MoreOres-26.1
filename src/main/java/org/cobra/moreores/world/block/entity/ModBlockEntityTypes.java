package org.cobra.moreores.world.block.entity;

import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.core.registry.ResourceHelper;
import org.cobra.moreores.world.block.ModBlocks;
import org.cobra.moreores.world.block.entity.gem.GemCrystallizerBlockEntity;
import org.cobra.moreores.world.block.entity.gem.GemPurifierBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.world.level.block.entity.BlockEntityType;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntityTypes {

    public static final ResourceHelper.BlockEntityResource RESOURCE = ResourceHelper.BlockEntityResource.INSTANCE;
    
    public static final BlockEntityType<GemPurifierBlockEntity> GEM_PURIFIER_BLOCK_ENTITY =
            RESOURCE.register("gem_purifier_block", FabricBlockEntityTypeBuilder.create(GemPurifierBlockEntity::new, ModBlocks.GEM_PURIFIER_BLOCK));

    public static final BlockEntityType<GemCrystallizerBlockEntity> GEM_CRYSTALLIZE_BLOCK_ENTITY =
            RESOURCE.register("gem_crystallizer_block", FabricBlockEntityTypeBuilder.create(GemCrystallizerBlockEntity::new, ModBlocks.GEM_CRYSTALLIZER_BLOCK));
    
    public static void register() {
        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, GEM_PURIFIER_BLOCK_ENTITY);
        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, GEM_CRYSTALLIZE_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> blockEntity.fluidStorage), GEM_PURIFIER_BLOCK_ENTITY);
        MoreOresModInitializer.LOGGER.info("Loading ModBlockEntityTypes for {} mod.", MoreOresModInitializer.MOD_ID);
    }
}
