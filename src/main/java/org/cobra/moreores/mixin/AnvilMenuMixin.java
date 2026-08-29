package org.cobra.moreores.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import org.cobra.moreores.MoreOresModInitializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {

    @Inject(method = "onTake", at = @At("HEAD"))
    private void onTake(Player player, ItemStack stack, CallbackInfo ci) {
        if(player.level().isClientSide()) return;

        if(!(player instanceof ServerPlayer serverPlayer)) return;

        Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());

        String name = stack.getHoverName().getString();
        if(id.getNamespace().equals(MoreOresModInitializer.MOD_ID)) {
            if (stack.hasNonDefault(DataComponents.CUSTOM_NAME)) {
                if (name.equalsIgnoreCase("CobraGamingSJ")) {
                    MoreOresModInitializer.giveBirthdayRewards(serverPlayer);
                    MoreOresModInitializer.LOGGER.info("Gave {} rewards", serverPlayer.getName());
                }
            }
        }
    }
}
