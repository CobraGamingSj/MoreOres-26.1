package org.cobra.moreores.recipe.display;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record GemCrystallizingRecipeDisplay(SlotDisplay ingredientBefore, SlotDisplay ingredientAfter, SlotDisplay result, SlotDisplay workStation) implements RecipeDisplay {

    @Override
    public SlotDisplay craftingStation() {
        return this.workStation;
    }

    public static final MapCodec<GemCrystallizingRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            SlotDisplay.CODEC.fieldOf("ingredientBefore").forGetter(GemCrystallizingRecipeDisplay::ingredientBefore),
                            SlotDisplay.CODEC.fieldOf("ingredientAfter").forGetter(GemCrystallizingRecipeDisplay::ingredientAfter),
                            SlotDisplay.CODEC.fieldOf("result").forGetter(GemCrystallizingRecipeDisplay::result),
                            SlotDisplay.CODEC.fieldOf("work_station").forGetter(GemCrystallizingRecipeDisplay::workStation)
                    )
                    .apply(instance, GemCrystallizingRecipeDisplay::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, GemCrystallizingRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            SlotDisplay.STREAM_CODEC,
            GemCrystallizingRecipeDisplay::ingredientBefore,
            SlotDisplay.STREAM_CODEC,
            GemCrystallizingRecipeDisplay::ingredientAfter,
            SlotDisplay.STREAM_CODEC,
            GemCrystallizingRecipeDisplay::result,
            SlotDisplay.STREAM_CODEC,
            GemCrystallizingRecipeDisplay::workStation,
            GemCrystallizingRecipeDisplay::new
    );

    public static final Type<GemCrystallizingRecipeDisplay> SERIALIZER = new Type<>(CODEC, STREAM_CODEC);

    @Override
    public Type<GemCrystallizingRecipeDisplay> type() {
        return SERIALIZER;
    }
}
