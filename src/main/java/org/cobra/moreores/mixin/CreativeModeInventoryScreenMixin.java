package org.cobra.moreores.mixin;

import net.fabricmc.fabric.api.client.creativetab.v1.FabricCreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import org.cobra.moreores.client.gui.widget.SkillButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends AbstractContainerScreen<CreativeModeInventoryScreen.ItemPickerMenu>
        implements FabricCreativeModeInventoryScreen {
    @Shadow
    private static CreativeModeTab selectedTab;

    @Shadow
    protected abstract void selectTab(CreativeModeTab tab);

    @Unique
    private CreativeModeTab moreores$previousTab;

    public CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Unique
    private SkillButtonWidget skillButtonWidget;

    @Inject(method = "init", at = @At("HEAD"))
    private void beforeInit(CallbackInfo ci) {
        moreores$previousTab = selectedTab;
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        skillButtonWidget = new SkillButtonWidget(leftPos + 144, topPos + 18);
        skillButtonWidget.visible = false;
        this.addRenderableWidget(skillButtonWidget);
        if(moreores$previousTab != null) {
            selectTab(moreores$previousTab);
        }
    }

    @Inject(method = "selectTab", at = @At("TAIL"))
    private void addSkillButtonWhenNeeded(CreativeModeTab tab, CallbackInfo ci) {
        if (skillButtonWidget == null) {
            return;
        }
        skillButtonWidget.visible = selectedTab.getType() == CreativeModeTab.Type.INVENTORY;
    }
}
