package org.cobra.moreores.world.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.cobra.moreores.core.ResourceHelper;

public class ModEntityTypes {

    public static final ResourceHelper.EntityResource RESOURCE = ResourceHelper.EntityResource.INSTANCE;
    
    public static final EntityType<GemArrowEntity> GEM_ARROW_ENTITY = RESOURCE.register(
            "gem_arrow",
            EntityType.Builder.<GemArrowEntity>of(GemArrowEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).clientTrackingRange(4).noLootTable().updateInterval(20).eyeHeight(0.13f));
    
    public static void register() {
        
    }
}
