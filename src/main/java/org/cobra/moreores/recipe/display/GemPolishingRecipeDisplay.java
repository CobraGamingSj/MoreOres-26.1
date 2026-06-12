package org.cobra.moreores.recipe.display;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record GemPolishingRecipeDisplay(SlotDisplay ingredient, SlotDisplay result, SlotDisplay workStation) implements RecipeDisplay {

    @Override
    public SlotDisplay craftingStation() {
        return this.workStation;
    }

    public static final MapCodec<GemPolishingRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            SlotDisplay.CODEC.fieldOf("ingredientBefore").forGetter(GemPolishingRecipeDisplay::ingredient),
                            SlotDisplay.CODEC.fieldOf("result").forGetter(GemPolishingRecipeDisplay::result),
                            SlotDisplay.CODEC.fieldOf("work_station").forGetter(GemPolishingRecipeDisplay::workStation)
                    )
                    .apply(instance, GemPolishingRecipeDisplay::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, GemPolishingRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            SlotDisplay.STREAM_CODEC,
            GemPolishingRecipeDisplay::ingredient,
            SlotDisplay.STREAM_CODEC,
            GemPolishingRecipeDisplay::result,
            SlotDisplay.STREAM_CODEC,
            GemPolishingRecipeDisplay::workStation,
            GemPolishingRecipeDisplay::new
    );

    public static final Type<GemPolishingRecipeDisplay> SERIALIZER = new Type<>(CODEC, STREAM_CODEC);

    @Override
    public Type<GemPolishingRecipeDisplay> type() {
        return SERIALIZER;
    }
}
