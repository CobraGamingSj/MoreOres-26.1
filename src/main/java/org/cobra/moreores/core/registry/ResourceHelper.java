package org.cobra.moreores.core.registry;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.cobra.moreores.MoreOresModInitializer;

import java.util.function.Function;

import static org.cobra.moreores.MoreOresModInitializer.id;

public class ResourceHelper {
    
    public static final class ItemResource {
        private ItemResource() {}
        
        public static final ItemResource INSTANCE = new ItemResource();
        
        private Item register(String id, Item item) {
            return Registry.register(BuiltInRegistries.ITEM, id(id), item);
        }

        public Item register(String id, Function<Item.Properties, Item> item) {
            return register(id, item.apply(new Item.Properties().setId(itemKey(id))));
        }

        public Item registerSteveArmor(String id, Function<Item.Properties, Item> function, Item.Properties properties, ArmorMaterial material, ArmorType type) {
            return register(id, function.apply(properties.setId(itemKey(id)).humanoidArmor(material, type).fireResistant()));
        }

        public Item registerNautilusArmor(String id, Function<Item.Properties, Item> function, Item.Properties properties, ArmorMaterial material) {
            return register(id, function.apply(properties.setId(itemKey(id)).nautilusArmor(material).fireResistant()));
        }
        
        public Item registerSword(String id, Function<Item.Properties, Item> item, float attackDamage, float attackSpeed, ToolMaterial material) {
            return register(id, item.apply(new Item.Properties().setId(itemKey(id)).sword(material, attackDamage, attackSpeed)));
        }

        public Item registerPickaxe(String id, Function<Item.Properties, Item> item, float attackDamage, float attackSpeed, ToolMaterial material) {
            return register(id, item.apply(new Item.Properties().setId(itemKey(id)).pickaxe(material, attackDamage, attackSpeed)));
        }

        public Item registerAxe(String id, Function<Item.Properties, Item> item, float attackDamage, float attackSpeed, ToolMaterial material) {
            return register(id, item.apply(new Item.Properties().setId(itemKey(id)).axe(material, attackDamage, attackSpeed)));
        }

        public Item registerHoe(String id, Function<Item.Properties, Item> item, float attackDamage, float attackSpeed, ToolMaterial material) {
            return register(id, item.apply(new Item.Properties().setId(itemKey(id)).hoe(material, attackDamage, attackSpeed)));
        }

        public Item registerShovel(String id, Function<Item.Properties, Item> item, float attackDamage, float attackSpeed, ToolMaterial material) {
            return register(id, item.apply(new Item.Properties().setId(itemKey(id)).shovel(material, attackDamage, attackSpeed)));
        }

        public Item registerSpear(String id, Function<Item.Properties, Item> item, ToolMaterial material, float swingAnimationSeconds, float chargeDamageMultiplier, float chargeDelaySeconds,
                                         float maxDurationForDismountSeconds, float minSpeedForDismount, float maxDurationForChargeKnockbackInSeconds,
                                         float minSpeedForChargeKnockback, float maxDurationForChargeDamageInSeconds,
                                         float minRelativeSpeedForChargeDamage) {
            return register(id, item.apply(new Item.Properties().spear(material, swingAnimationSeconds, chargeDamageMultiplier, chargeDelaySeconds, maxDurationForDismountSeconds,
                            minSpeedForDismount, maxDurationForChargeKnockbackInSeconds, minSpeedForChargeKnockback, maxDurationForChargeDamageInSeconds, minRelativeSpeedForChargeDamage)
                    .setId(itemKey(id))));
        }
    }
    
    public static final class BlockResource {
        private BlockResource() {}
        
        public static final BlockResource INSTANCE = new BlockResource();
        
        public Block register(String id, Block block) {
            registerBlockItem(id, block);
            return Registry.register(BuiltInRegistries.BLOCK, id(id), block);
        }

        public Block register(String id, Function<BlockBehaviour.Properties, Block> blockFactory) {
            return registerSolidBlock(id, blockFactory, 7f, 7f);
        }

        public Block registerSolidBlock(String id, Function<BlockBehaviour.Properties, Block> blockFunction, float strength, float resistance) {
            BlockBehaviour.Properties settings = BlockBehaviour.Properties.of().requiresCorrectToolForDrops().setId(blockKey(id)).strength(strength, resistance);
            return register(id, blockFunction.apply(settings));
        }

        private void registerBlockItem(String id, Block block) {
            ItemResource.INSTANCE.register(id, settings -> new BlockItem(block, settings.useBlockDescriptionPrefix()));
        }
    }

    public static final class BlockEntityResource {
        private BlockEntityResource() {}

        public static final BlockEntityResource INSTANCE = new BlockEntityResource();

        public <BE extends BlockEntity> BlockEntityType<BE> register(String id, FabricBlockEntityTypeBuilder<BE> factory) {
            return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, id), factory.build());
        }
    }

    public static final class EntityResource {
        private EntityResource() {}

        public static final EntityResource INSTANCE = new EntityResource();

        public <E extends Entity> EntityType<E> register(String id, EntityType.Builder<E> builder) {
            ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, MoreOresModInitializer.id(id));
            return Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, id), builder.build(key));
        }
    }
    
    public static final class MenuResource {
        private MenuResource() {}
        
        public static final MenuResource INSTANCE = new MenuResource();

        public <S extends AbstractContainerMenu, D extends CustomPacketPayload> ExtendedMenuType<S, D> register(String id, ExtendedMenuType.ExtendedFactory<S, D> factory, StreamCodec<? super RegistryFriendlyByteBuf, D> packetCodec) {
            return Registry.register(BuiltInRegistries.MENU, MoreOresModInitializer.id(id), new ExtendedMenuType<>(factory, packetCodec));
        }
    }
    
    public static final class RecipeResource {
        private RecipeResource() {}
        
        public static final RecipeResource INSTANCE = new RecipeResource();

        public <T extends Recipe<?>> RecipeSerializer<T> registerSerializer(String id, RecipeSerializer<T> serializer) {
            return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, MoreOresModInitializer.id(id), serializer);
        }

        public <T extends Recipe<?>> RecipeType<T> registerType(String id) {
            return Registry.register(
                    BuiltInRegistries.RECIPE_TYPE,
                    MoreOresModInitializer.id(id),
                    new RecipeType<T>() {
                        @Override
                        public String toString() {
                            return id;
                        }
                    }
            );
        }

        public RecipeBookCategory registerBookCategory(String id) {
            return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, id), new RecipeBookCategory());
        }
        
        public <T extends RecipeDisplay> void registerDisplay(String id, RecipeDisplay.Type<T> type) {
            Registry.register(BuiltInRegistries.RECIPE_DISPLAY, MoreOresModInitializer.id(id), type);
        }
    }
    
    public static ResourceKey<Block> obtainKey(Block value) {
        return BuiltInRegistries.BLOCK.getResourceKey(value).get();
    }

    public static ResourceKey<Item> obtainKey(Item value) {
        return BuiltInRegistries.ITEM.getResourceKey(value).get();
    }

    public static ResourceKey<Item> itemKey(String id) {
        return ResourceKey.create(Registries.ITEM, id(id));
    }
    
    public static ResourceKey<Block> blockKey(String id) {
        return ResourceKey.create(Registries.BLOCK, id(id));
    }
}