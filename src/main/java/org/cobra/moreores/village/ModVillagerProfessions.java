package org.cobra.moreores.village;

import com.google.common.collect.ImmutableSet;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.level.block.Block;

public class ModVillagerProfessions {
    public static final ResourceKey<PoiType> JEWEL_POI = poiKey("jewel_poi");
    public static final PoiType JEWEL = registerPoi("jewel_poi", ModBlocks.GEM_PURIFIER_BLOCK);

    public static final ResourceKey<VillagerProfession> JEWELLER = ResourceKey.create(Registries.VILLAGER_PROFESSION, MoreOresModInitializer.id("jeweller"));
    public static final VillagerProfession JEWELLER_KEY = registerProfession("jeweller", JEWEL_POI);

    private static VillagerProfession registerProfession(String id, ResourceKey<PoiType> type) {
        return Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, MoreOresModInitializer.id(id),
                new VillagerProfession(Component.literal("Jeweller"), entry -> entry.is(type), entry -> entry.is(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_SHEPHERD));
    }

    private static PoiType registerPoi(String id, Block block) {
        return PointOfInterestHelper.register(MoreOresModInitializer.id(id), 1, 1, block);
    }

    private static ResourceKey<PoiType> poiKey(String id) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, MoreOresModInitializer.id(id));
    }

    public static void register() {
        MoreOresModInitializer.LOGGER.info("Loading ModVillagerProfessions for " + MoreOresModInitializer.MOD_ID + " mod.");
    }
}