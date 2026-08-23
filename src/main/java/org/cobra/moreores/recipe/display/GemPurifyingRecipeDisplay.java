package org.cobra.moreores.recipe.display;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record GemPurifyingRecipeDisplay(SlotDisplay ingredient, SlotDisplay result, SlotDisplay workStation) implements RecipeDisplay {

    @Override
    public SlotDisplay craftingStation() {
        return this.workStation;
    }

    public static final MapCodec<GemPurifyingRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            SlotDisplay.CODEC.fieldOf("ingredientBefore").forGetter(GemPurifyingRecipeDisplay::ingredient),
                            SlotDisplay.CODEC.fieldOf("result").forGetter(GemPurifyingRecipeDisplay::result),
                            SlotDisplay.CODEC.fieldOf("work_station").forGetter(GemPurifyingRecipeDisplay::workStation)
                    )
                    .apply(instance, GemPurifyingRecipeDisplay::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, GemPurifyingRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            SlotDisplay.STREAM_CODEC,
            GemPurifyingRecipeDisplay::ingredient,
            SlotDisplay.STREAM_CODEC,
            GemPurifyingRecipeDisplay::result,
            SlotDisplay.STREAM_CODEC,
            GemPurifyingRecipeDisplay::workStation,
            GemPurifyingRecipeDisplay::new
    );

    public static final Type<GemPurifyingRecipeDisplay> SERIALIZER = new Type<>(CODEC, STREAM_CODEC);

    @Override
    public Type<GemPurifyingRecipeDisplay> type() {
        return SERIALIZER;
    }
}
