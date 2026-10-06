package org.cobra.moreores.world.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.cobra.moreores.client.gui.screen.SkillTreeMenu;
import org.jspecify.annotations.Nullable;

public class SkillItem extends Item implements MenuProvider {
    public SkillItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.openMenu(this);
        return super.use(level, player, hand);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Skill Tree");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new SkillTreeMenu(containerId, inventory);
    }
}
