package org.cobra.moreores.world.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.core.registry.ResourceHelper;

public class ModEntityTypes {

    public static final ResourceHelper.EntityResource RESOURCE = ResourceHelper.EntityResource.INSTANCE;
    
    public static final EntityType<GemArrow> GEM_ARROW = RESOURCE.register(
            "gem_arrow",
            EntityType.Builder.<GemArrow>of(GemArrow::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).clientTrackingRange(4).noLootTable().updateInterval(20).eyeHeight(0.13f));
    
    public static void register() {
        MoreOresModInitializer.LOGGER.info("Registering ModEntityTypes for {} mod.", MoreOresModInitializer.MOD_ID);
    }
}