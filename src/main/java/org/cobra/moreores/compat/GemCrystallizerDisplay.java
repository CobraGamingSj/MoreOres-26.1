package org.cobra.moreores.compat;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.cobra.moreores.recipe.GemCrystallizerRecipe;
import org.cobra.moreores.recipe.GemPurifierRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public record GemCrystallizerDisplay(EntryIngredient ingredientBefore, EntryIngredient ingredientAfter, EntryIngredient result, Optional<Identifier> location) implements Display {
    public static final DisplaySerializer<GemCrystallizerDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(ins -> ins.group(
                    EntryIngredient.codec().fieldOf("gemBefore").forGetter(GemCrystallizerDisplay::ingredientBefore),
                    EntryIngredient.codec().fieldOf("gemAfter").forGetter(GemCrystallizerDisplay::ingredientAfter),
                    EntryIngredient.codec().fieldOf("infusedGem").forGetter(GemCrystallizerDisplay::result),
                    Identifier.CODEC.optionalFieldOf("location").forGetter(GemCrystallizerDisplay::location)
            ).apply(ins, GemCrystallizerDisplay::new)),
            StreamCodec.composite(
                    EntryIngredient.streamCodec(),
                    GemCrystallizerDisplay::ingredientBefore,
                    EntryIngredient.streamCodec(),
                    GemCrystallizerDisplay::ingredientAfter,
                    EntryIngredient.streamCodec(),
                    GemCrystallizerDisplay::result,
                    ByteBufCodecs.optional(Identifier.STREAM_CODEC),
                    GemCrystallizerDisplay::location,
                    GemCrystallizerDisplay::new
            ));

    public GemCrystallizerDisplay(RecipeHolder<GemCrystallizerRecipe> entry) {
        this(entry.id().identifier(), entry.value());
    }

    public GemCrystallizerDisplay(Identifier id, GemCrystallizerRecipe recipe) {
        this(EntryIngredients.ofIngredient(recipe.ingredientBefore()), EntryIngredients.ofIngredient(recipe.ingredientAfter()), EntryIngredients.of(recipe.output().create()), Optional.of(id));
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(ingredientBefore, ingredientAfter);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(result);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return MOREICommon.GEM_CRYSTALLIZER;
    }

    @Override
    public Optional<Identifier> getDisplayLocation() {
        return location;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}