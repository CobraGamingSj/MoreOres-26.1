package org.cobra.moreores.village;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PoiHelper;
import net.minecraft.resources.Identifier;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.data.village.TradeSets;
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
    public static final VillagerProfession JEWELLER_KEY = registerProfession("jeweller", new VillagerProfession(
            Component.literal("Jeweller"),
            entry -> entry.is(JEWEL_POI),
            entry -> entry.is(JEWEL_POI),
            ImmutableSet.of(), ImmutableSet.of(),
            SoundEvents.VILLAGER_WORK_SHEPHERD, Int2ObjectMap.ofEntries(
                Int2ObjectMap.entry(1, TradeSets.JEWELLER_LEVEL_1),
                Int2ObjectMap.entry(2, TradeSets.JEWELLER_LEVEL_2),
                Int2ObjectMap.entry(3, TradeSets.JEWELLER_LEVEL_3),
                Int2ObjectMap.entry(4, TradeSets.JEWELLER_LEVEL_4),
                Int2ObjectMap.entry(5, TradeSets.JEWELLER_LEVEL_5)
    )));

    private static VillagerProfession registerProfession(String id, VillagerProfession profession) {
        return Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, MoreOresModInitializer.id(id), profession);
    }

    private static PoiType registerPoi(String id, Block block) {
        return PoiHelper.register(MoreOresModInitializer.id(id), 1, 1, block);
    }

    private static ResourceKey<PoiType> poiKey(String id) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, MoreOresModInitializer.id(id));
    }

    public static void register() {
        int count = 0;
        for (VillagerProfession profession : BuiltInRegistries.VILLAGER_PROFESSION) {
            Identifier id = BuiltInRegistries.VILLAGER_PROFESSION.getKey(profession);
            if(id.getNamespace().equals(MoreOresModInitializer.MOD_ID)) {
                String name = MoreOresModInitializer.formatIdName(id.getPath());
                count++;
                MoreOresModInitializer.LOGGER.info("Registering Villager Profession: {}, for {} mod.", name, MoreOresModInitializer.MOD_ID);
            }
        }
        MoreOresModInitializer.LOGGER.info("Registered {} Villager Profession(s) for {} mod.", count, MoreOresModInitializer.MOD_ID + " mod.");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }
}