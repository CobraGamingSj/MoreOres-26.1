package org.cobra.moreores.recipe.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeInput;

public record GemPurifyingRecipeInput(ItemStack inputStack) implements RecipeInput {
    @Override
    public ItemStack getItem(int slot) {
        return inputStack;
    }

    @Override
    public int size() {
        return 1;
    }
}
