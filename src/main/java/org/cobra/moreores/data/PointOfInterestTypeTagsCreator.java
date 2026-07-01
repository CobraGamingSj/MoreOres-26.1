package org.cobra.moreores.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.tags.TagEntry;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import org.cobra.moreores.village.ModVillagerProfessions;

public class PointOfInterestTypeTagsCreator extends FabricTagsProvider<PoiType> {
    public PointOfInterestTypeTagsCreator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.POINT_OF_INTEREST_TYPE, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.getOrCreateRawBuilder(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .add(TagEntry.element(ModVillagerProfessions.JEWEL_POI.identifier()));
    }
}