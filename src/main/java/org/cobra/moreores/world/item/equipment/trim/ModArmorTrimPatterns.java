package org.cobra.moreores.world.item.equipment.trim;

import org.cobra.moreores.MoreOresModInitializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.equipment.trim.TrimPattern;

public class ModArmorTrimPatterns {
    public static final ResourceKey<TrimPattern> GUARDIAN = of("guardian");

    public static void bootstrap(BootstrapContext<TrimPattern> registerable) {
        register(registerable, GUARDIAN);
    }

    public static void register(BootstrapContext<TrimPattern> registry, ResourceKey<TrimPattern> key) {
        TrimPattern armorTrimPattern = new TrimPattern(getId(key), Component.translatable(Util.makeDescriptionId("trim_pattern", key.identifier())), false);
        registry.register(key, armorTrimPattern);
    }

    public static Identifier getId(ResourceKey<TrimPattern> key) {
        return key.identifier();
    }

    private static ResourceKey<TrimPattern> of(String id) {
        Identifier ID = Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, id);
        return ResourceKey.create(Registries.TRIM_PATTERN, ID);
    }
}
