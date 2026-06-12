package org.cobra.moreores.world.item;

import org.cobra.moreores.world.entity.GemArrowEntity;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.CommonColors;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;

public class RadiantBowItem extends ProjectileWeaponItem {
    public static final Predicate<ItemStack> GEM_ARROW = itemStack -> itemStack.is(ModItems.GEM_ARROW);

    public RadiantBowItem(Properties settings) {
        super(settings);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return GEM_ARROW;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 120;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return CommonColors.HIGH_CONTRAST_DIAMOND;
    }

    @Override
    protected Projectile createProjectile(Level world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical) {
        GemArrowItem gemArrow = projectileStack.getItem() instanceof GemArrowItem gemArrowItem ? gemArrowItem : (GemArrowItem) ModItems.GEM_ARROW;
        GemArrowEntity persistentProjectileEntity = gemArrow.createArrow(world, projectileStack, shooter, weaponStack);
        if (critical) {
            persistentProjectileEntity.setCritArrow(true);
        }

        return persistentProjectileEntity;
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof Player playerEntity)) {
            return false;
        } else {
            ItemStack itemStack = playerEntity.getProjectile(stack);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                int i = this.getUseDuration(stack, user) - remainingUseTicks;
                float f = getPullProgress(i);
                if (f < 0.1) {
                    return false;
                } else {
                    List<ItemStack> list = draw(stack, itemStack, playerEntity);
                    if (world instanceof ServerLevel serverWorld && !list.isEmpty()) {
                        this.shoot(serverWorld, playerEntity, playerEntity.getUsedItemHand(), stack, list, f * 3.0F, 1.0F, f == 1.0F, null);
                    }

                    world.playSound(
                            null,
                            playerEntity.getX(),
                            playerEntity.getY(),
                            playerEntity.getZ(),
                            SoundEvents.ARROW_SHOOT,
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F
                    );
                    playerEntity.awardStat(Stats.ITEM_USED.get(this));
                    return true;
                }
            }
        }
    }

    public static float getPullProgress(int useTicks) {
        float f = useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        boolean bl = !user.getProjectile(itemStack).isEmpty();
        if (!user.hasInfiniteMaterials() && !bl) {
            return InteractionResult.FAIL;
        } else {
            user.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }
    }
    
    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {
        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + yaw, 0.0F, speed, divergence);
    }
}
