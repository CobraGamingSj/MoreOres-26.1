package org.cobra.moreores.world.item.util.impl;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.cobra.moreores.world.item.util.GemCategory;

public interface Gemstone {
    String getName();
    GemCategory category();
    Item[] items();
    
    Gemstone NONE = new Gemstone() {
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
