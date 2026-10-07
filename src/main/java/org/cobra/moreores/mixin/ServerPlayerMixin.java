package org.cobra.moreores.mixin;

import net.minecraft.server.level.ServerPlayer;
import org.cobra.moreores.data.SkillManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        SkillManager.tick((ServerPlayer) (Object) this);
    }
}
