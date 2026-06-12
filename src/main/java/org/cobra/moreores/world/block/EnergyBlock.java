package org.cobra.moreores.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EnergyBlock extends Block {

    public EnergyBlock(Properties settings) {
        super(settings);
    }
    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (!world.isClientSide() && world instanceof ServerLevel serverWorld) {
            if (pos.getY() == world.getSeaLevel()) {
                serverWorld.getWeatherData().setRaining(true);
                serverWorld.getWeatherData().setRainTime(6000);
                serverWorld.getWeatherData().setThundering(true);
                serverWorld.getWeatherData().setClearWeatherTime(0);
            }
        }
        super.setPlacedBy(world, pos, state, placer, itemStack);
    }

    @Override
    public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
        if (!world.isClientSide() && world instanceof ServerLevel serverWorld) {
            serverWorld.getWeatherData().setRaining(false);
            serverWorld.getWeatherData().setRainTime(0);
            serverWorld.getWeatherData().setThundering(false);
            serverWorld.getWeatherData().setClearWeatherTime(6000);
        }
        super.destroy(world, pos, state);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClientSide()) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 20, 1, false, false));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 100, 1, false, false));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 1, false, false));
                world.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.PLAYER_HURT, SoundSource.PLAYERS, 5.0f, 1.0f);
            }
        }

        super.stepOn(world, pos, state, entity);
    }
}
