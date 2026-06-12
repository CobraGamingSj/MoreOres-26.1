package org.cobra.moreores.item;

import org.cobra.moreores.MoreOresModInitializer;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

public class GemItem extends Item {
    public GemItem(Properties settings, String name) {
        super(settings.rarity(Rarity.RARE).fireResistant().trimMaterial(ResourceKey.create(Registries.TRIM_MATERIAL, MoreOresModInitializer.id(name))));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        textConsumer.accept(Component.literal("Gemstone").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD));
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
    }
}
