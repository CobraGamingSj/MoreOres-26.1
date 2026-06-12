package org.cobra.moreores.item.util.impl;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.cobra.moreores.block.ModBlocks;
import org.cobra.moreores.item.ModItems;
import org.cobra.moreores.item.util.GemCategory;

public enum CrystallizationGems implements IGem, StringRepresentable {
    EMPTY("empty", Items.AIR),
    CRIMSON_GARNET("crimson_garnet", ModItems.CRIMSON_GARNET, ModBlocks.CRIMSON_GARNET_BLOCK.asItem()),
    RADIANT_AMETHYST("radiant_amethyst", ModItems.RADIANT_AMETHYST, ModBlocks.RADIANT_AMETHYST_BLOCK.asItem()),
    CRYSTALLITE("crystallite", ModItems.CRYSTALLITE, ModBlocks.CRYSTALLITE_BLOCK.asItem()),
    ALEXANDRITE("alexandrite", ModItems.ALEXANDRITE, ModBlocks.ALEXANDRITE_BLOCK.asItem()),
    LIMESTONE("limestone", ModItems.LIMESTONE, ModBlocks.LIMESTONE_BLOCK.asItem()),
    MOONSTONE("moonstone", ModItems.MOONSTONE, ModBlocks.MOONSTONE_BLOCK.asItem()),
    QUARTSIDIAN("quartsidian", ModItems.QUARTSIDIAN, ModBlocks.QUARTSIDIAN_BLOCK.asItem()),
    ORANGE_ZIRCON("orange_zircon", ModItems.ORANGE_ZIRCON, ModBlocks.ORANGE_ZIRCON_BLOCK.asItem()),
    OPAL("opal", ModItems.OPAL, ModBlocks.OPAL_BLOCK.asItem()),
    GRANDIDIERITE("grandidierite", ModItems.GRANDIDIERITE, ModBlocks.GRANDIDIERITE_BLOCK.asItem()),
    RED_BERYL("red_beryl", ModItems.RED_BERYL, ModBlocks.RED_BERYL_BLOCK.asItem()),
    KASHMIR_SAPPHIRE("kashmir_sapphire", ModItems.KASHMIR_SAPPHIRE, ModBlocks.KASHMIR_SAPPHIRE_BLOCK.asItem());
    
    private final String name;
    private final Item[] items;

    public static final Codec<CrystallizationGems> CODEC = StringRepresentable.fromEnum(CrystallizationGems::values);
    
    CrystallizationGems(String name, Item... items) {
        this.name = name;
        this.items = items;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public GemCategory category() {
        return GemCategory.CRYSTALLIZATION;
    }

    @Override
    public Item[] items() {
        return items;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
