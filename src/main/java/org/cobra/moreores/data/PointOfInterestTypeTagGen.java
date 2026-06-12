package org.cobra.moreores.data;

import org.cobra.moreores.MoreOresModInitializer;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class PointOfInterestTypeTagGen extends TagsProvider<PoiType> {
    private static final String JEWEL_KEY = "jewel_poi";

    public PointOfInterestTypeTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.POINT_OF_INTEREST_TYPE, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.getOrCreateRawBuilder(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptionalElement(MoreOresModInitializer.id(JEWEL_KEY));
    }
}