package org.cobra.moreores.recipe.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeInput;

public record GemPurifyingRecipeInput(ItemStackTemplate inputStack) implements RecipeInput {
    @Override
    public ItemStack getItem(int slot) {
        return inputStack.create();
    }

    @Override
    public int size() {
        return 1;
    }
}
