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
        
        public Item register(String id, Item item) {
            return Registry.register(BuiltInRegistries.ITEM, id(id), item);
        }

        public Item register(String name, Function<Item.Properties, Item> item) {
            return register(name, item.apply(new Item.Properties().setId(itemKey(name))));
        }

        public Item registerSword(String name, Function<Item.Properties, Item> item, float attackDamage, float attackSpeed, ToolMaterial material) {
            return register(name, item.apply(new Item.Properties().setId(itemKey(name)).sword(material, attackDamage, attackSpeed)));
        }

        public Item registerPickaxe(String name, Function<Item.Properties, Item> item, float attackDamage, float attackSpeed, ToolMaterial material) {
            return register(name, item.apply(new Item.Properties().setId(itemKey(name)).pickaxe(material, attackDamage, attackSpeed)));
        }

        public Item registerAxe(String name, Function<Item.Properties, Item> item, float attackDamage, float attackSpeed, ToolMaterial material) {
            return register(name, item.apply(new Item.Properties().setId(itemKey(name)).axe(material, attackDamage, attackSpeed)));
        }

        public Item registerHoe(String name, Function<Item.Properties, Item> item, float attackDamage, float attackSpeed, ToolMaterial material) {
            return register(name, item.apply(new Item.Properties().setId(itemKey(name)).hoe(material, attackDamage, attackSpeed)));
        }

        public Item registerShovel(String name, Function<Item.Properties, Item> item, float attackDamage, float attackSpeed, ToolMaterial material) {
            return register(name, item.apply(new Item.Properties().setId(itemKey(name)).shovel(material, attackDamage, attackSpeed)));
        }

        public Item registerSpear(String name, Function<Item.Properties, Item> item, ToolMaterial material, float swingAnimationSeconds, float chargeDamageMultiplier, float chargeDelaySeconds,
                                         float maxDurationForDismountSeconds, float minSpeedForDismount, float maxDurationForChargeKnockbackInSeconds,
                                         float minSpeedForChargeKnockback, float maxDurationForChargeDamageInSeconds,
                                         float minRelativeSpeedForChargeDamage) {
            return register(name, item.apply(new Item.Properties().spear(material, swingAnimationSeconds, chargeDamageMultiplier, chargeDelaySeconds, maxDurationForDismountSeconds,
                            minSpeedForDismount, maxDurationForChargeKnockbackInSeconds, minSpeedForChargeKnockback, maxDurationForChargeDamageInSeconds, minRelativeSpeedForChargeDamage)
                    .setId(itemKey(name))));
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

        public void registerBlockItem(String id, Block block) {
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

        private EntityResource() {

        }

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

        public <T extends Recipe<?>> RecipeSerializer<T> registerSerializer(String name, RecipeSerializer<T> serializer) {
            return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, MoreOresModInitializer.id(name), serializer);
        }

        public <T extends Recipe<?>> RecipeType<T> registerType(String name) {
            return Registry.register(
                    BuiltInRegistries.RECIPE_TYPE,
                    MoreOresModInitializer.id(name),
                    new RecipeType<T>() {
                        @Override
                        public String toString() {
                            return name;
                        }
                    }
            );
        }

        public RecipeBookCategory registerBookCategory(String id) {
            return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(MoreOresModInitializer.MOD_ID, id), new RecipeBookCategory());
        }
        
        public <T extends RecipeDisplay> void registerDisplay(String name, RecipeDisplay.Type<T> type) {
            Registry.register(BuiltInRegistries.RECIPE_DISPLAY, MoreOresModInitializer.id(name), type);
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