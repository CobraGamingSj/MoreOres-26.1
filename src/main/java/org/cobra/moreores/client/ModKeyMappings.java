package org.cobra.moreores.client;

import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeyMappings {

    public static final KeyMapping SKILL_TREE_KEY = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.talents", GLFW.GLFW_KEY_M, KeyMapping.Category.GAMEPLAY));

    public static void register() {

    }
}