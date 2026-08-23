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
import org.cobra.moreores.recipe.GemPurifierRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public record GemPurifierDisplay(EntryIngredient ingredient, EntryIngredient result, Optional<Identifier> location) implements Display {
    public static final DisplaySerializer<GemPurifierDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(ins -> ins.group(
                    EntryIngredient.codec().fieldOf("ingredientGem").forGetter(GemPurifierDisplay::ingredient),
                    EntryIngredient.codec().fieldOf("resultGem").forGetter(GemPurifierDisplay::result),
                    Identifier.CODEC.optionalFieldOf("location").forGetter(GemPurifierDisplay::location)
            ).apply(ins, GemPurifierDisplay::new)),
            StreamCodec.composite(
                    EntryIngredient.streamCodec(),
                    GemPurifierDisplay::ingredient,
                    EntryIngredient.streamCodec(),
                    GemPurifierDisplay::result,
                    ByteBufCodecs.optional(Identifier.STREAM_CODEC),
                    GemPurifierDisplay::location,
                    GemPurifierDisplay::new
            ));

    public GemPurifierDisplay(RecipeHolder<GemPurifierRecipe> entry) {
        this(entry.id().identifier(), entry.value());
    }

    public GemPurifierDisplay(Identifier id, GemPurifierRecipe recipe) {
        this(EntryIngredients.ofIngredient(recipe.ingredient()), EntryIngredients.of(recipe.output().create()), Optional.of(id));
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(ingredient);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(result);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return MOREICommon.GEM_PURIFIER;
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