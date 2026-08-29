package org.cobra.moreores.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.math.Point;
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

public class GemPurifierCategory implements DisplayCategory<GemPurifierDisplay> {
    public static final Identifier TEXTURE = MoreOresModInitializer.id("textures/gui/container/gem_purifier/gem_purifier_gui_test.png");

    @Override
    public CategoryIdentifier<? extends GemPurifierDisplay> getCategoryIdentifier() {
        return MOREICommon.GEM_PURIFIER;
    }

    @Override
    public List<Widget> setupDisplay(GemPurifierDisplay display, Rectangle bounds) {
        List<Widget> widgets = new LinkedList<>();
        Point startPoint = new Point(bounds.getCenterX() - 91, bounds.getCenterY() - 53);

        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 138, 88)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 79, startPoint.y + 11)).entries(display.getInputEntries().getFirst()).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 79, startPoint.y + 61)).entries(display.getOutputEntries().getFirst()).markOutput());
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 90;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Gem Purifier");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.GEM_PURIFIER_BLOCK.asItem().getDefaultInstance());
    }
}