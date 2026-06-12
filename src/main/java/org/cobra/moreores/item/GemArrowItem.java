package org.cobra.moreores.item;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import org.cobra.moreores.entity.GemArrowEntity;
import org.cobra.moreores.entity.ModEntityTypes;
import org.jspecify.annotations.Nullable;

public class GemArrowItem extends Item implements ProjectileItem {
    public GemArrowItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public Projectile asProjectile(Level world, Position pos, ItemStack stack, Direction direction) {
        GemArrowEntity gemArrowEntity = new GemArrowEntity(ModEntityTypes.GEM_ARROW_ENTITY, world);
        gemArrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
        return gemArrowEntity;
    }

    public GemArrowEntity createArrow(Level world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
        return new GemArrowEntity(world, shooter, stack, shotFrom);
    }
}
