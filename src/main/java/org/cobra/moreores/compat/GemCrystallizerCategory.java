package org.cobra.moreores.compat;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.cobra.moreores.MoreOresModInitializer;
import org.cobra.moreores.world.block.ModBlocks;

import java.util.LinkedList;
import java.util.List;

public class GemCrystallizerCategory implements DisplayCategory<GemCrystallizerDisplay> {
    public static final Identifier TEXTURE = MoreOresModInitializer.id("textures/gui/container/gem_crystallizer/gem_crystallizer_gui.png");

    @Override
    public CategoryIdentifier<? extends GemCrystallizerDisplay> getCategoryIdentifier() {
        return MOREICommon.GEM_CRYSTALLIZER;
    }

    @Override
    public List<Widget> setupDisplay(GemCrystallizerDisplay display, Rectangle bounds) {
        List<Widget> widgets = new LinkedList<>();
        Point startPoint = new Point(bounds.getCenterX() - 91, bounds.getCenterY() - 53);

        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 138, 88)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 47, startPoint.y + 22)).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 87, startPoint.y + 22)).entries(display.getInputEntries().get(1)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 67, startPoint.y + 72)).entries(display.getOutputEntries().getFirst()).markOutput());
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 90;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Gem Crystallizer");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.GEM_CRYSTALLIZER_BLOCK.asItem().getDefaultInstance());
    }
}