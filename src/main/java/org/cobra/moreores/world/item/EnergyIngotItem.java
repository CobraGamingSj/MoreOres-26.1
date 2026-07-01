package org.cobra.moreores.world.item;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.cobra.moreores.networking.item.data.EnergyIngotC2SPayload;
import org.cobra.moreores.world.block.ModBlocks;

public class EnergyIngotItem extends Item {

    private int lightningStrikes = 0;
    private int requiredStrikes = -1;

    public EnergyIngotItem(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level world = attacker.level();

        if(!world.isClientSide()) {

            if(!attacker.hasInfiniteMaterials()) {
                if (stack.getDamageValue() < stack.getMaxDamage()) {
                    stack.setDamageValue(stack.getDamageValue() + 1);
                }
            }

            LightningBolt lightning = new LightningBolt(EntityTypes.LIGHTNING_BOLT, world);
            lightning.setPosRaw(target.getX(), target.getY(), target.getZ());
            world.addFreshEntity(lightning);
        }

        target.addEffect(new MobEffectInstance(MobEffects.POISON, 4800, 4));
        attacker.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 40, 4));
    }
    
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if(!world.isClientSide()) {
            if (world.getBlockState(pos).is(ModBlocks.RADIANT_BLOCK)) {
                EntityType<LightningBolt> lightningType = EntityTypes.LIGHTNING_BOLT;
                LightningBolt lightning = new LightningBolt(lightningType, world);
                lightning.setPosRaw(pos.getX(), pos.getY(), pos.getZ());
                world.addFreshEntity(lightning);

                if(requiredStrikes == -1) {
                    requiredStrikes = world.getRandom().nextIntBetweenInclusive(4, 7);
                }

                lightningStrikes++;

                if(lightningStrikes >= requiredStrikes) {
                    world.destroyBlock(pos, false);
                    lightning.discard();
                    world.addFreshEntity(new ItemEntity(
                            world,
                            pos.getX() + 2,
                            pos.getY(),
                            pos.getZ() + 2,
                            new ItemStack(ModItems.RADIANT_DUST, 9)
                    ));
                    lightningStrikes = 0;
                }
                return InteractionResult.PASS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        if(world.isClientSide()) {
            if(hand == InteractionHand.OFF_HAND && Minecraft.getInstance().hasControlDown()) {
                ClientPlayNetworking.send(new EnergyIngotC2SPayload());
            }
            return InteractionResult.CONSUME;
        }

        if (hand == InteractionHand.MAIN_HAND) {
            user.addEffect(new MobEffectInstance(MobEffects.INSTANT_HEALTH, 9600, 4, false, false, false));
            user.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 9600, 4, false, false, false));
            user.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 9600, 4, false, false, false));
            user.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 9600, 4, false, false, false));
            user.addEffect(new MobEffectInstance(MobEffects.SATURATION, 9600, 4, false, false, false));
            user.addEffect(new MobEffectInstance(MobEffects.LUCK, 9600, 4, false, false, false));
            if(!user.hasInfiniteMaterials()) {
                if (stack.getDamageValue() < stack.getMaxDamage()) {
                    stack.setDamageValue(stack.getDamageValue() + 1);
                }
            }
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 2.0f, 1.0f);
            return InteractionResult.SUCCESS;
        }

        return super.use(world, user, hand);
    }
}