package org.cobra.moreores.entity;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.cobra.moreores.MoreOresModInitializer;

public class ModEntityTypes {

    private static final ResourceKey<EntityType<?>> GEM_ARROW = ResourceKey.create(Registries.ENTITY_TYPE, MoreOresModInitializer.id("gem_arrow"));
    
    public static final EntityType<GemArrowEntity> GEM_ARROW_ENTITY = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            MoreOresModInitializer.id("gem_arrow"),
            EntityType.Builder.<GemArrowEntity>of(GemArrowEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).clientTrackingRange(4).noLootTable().updateInterval(20).eyeHeight(0.13f).build(GEM_ARROW));
    
}
