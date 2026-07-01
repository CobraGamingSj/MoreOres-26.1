package org.cobra.moreores.sound;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import org.cobra.moreores.MoreOresModInitializer;

public class ModSoundType {

    public static final SoundType ENERGY_BLOCK = new SoundType(5f, 1f, SoundEvents.HEAVY_CORE_STEP, SoundEvents.HEAVY_CORE_BREAK, SoundEvents.HEAVY_CORE_PLACE, SoundEvents.HEAVY_CORE_HIT, SoundEvents.HEAVY_CORE_FALL);

    public static void register() {
        MoreOresModInitializer.LOGGER.info("Loading ModBlockSoundGroups for " + MoreOresModInitializer.MOD_ID + " mod.");
    }

}
