package org.cobra.moreores.item.util.impl;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.cobra.moreores.item.util.GemCategory;

public interface IGem {
    String getName();
    GemCategory category();
    Item[] items();
    
    IGem EMPTY = new IGem() {
        @Override
        public String getName() {
            return "empty";
        }

        @Override
        public GemCategory category() {
            return GemCategory.NONE;
        }

        @Override
        public Item[] items() {
            return new Item[]{Items.AIR};
        }
    };
}
