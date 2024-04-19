package com.tiviacz.pizzacraft.init;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.blockentity.content.SauceType;
import com.tiviacz.pizzacraft.items.ArmorMaterials;
import com.tiviacz.pizzacraft.items.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PizzaCraft.MODID);

    public static final RegistryObject<Item> PIZZA = ITEMS.register("pizza", () -> new PizzaBlockItem(ModBlocks.PIZZA.get(), pizzaProperties().stacksTo(1)));
    public static final RegistryObject<Item> RAW_PIZZA = ITEMS.register("raw_pizza", () -> new RawPizzaBlockItem(ModBlocks.RAW_PIZZA.get(), pizzaProperties().stacksTo(1)));
    public static final RegistryObject<Item> DOUGH = registerBlockItem("dough", ModBlocks.DOUGH, pizzaProperties().stacksTo(16));
    public static final RegistryObject<Item> PIZZA_STATION = registerBlockItem("pizza_station", ModBlocks.PIZZA_STATION, pizzaProperties());
    public static final RegistryObject<Item> OVEN = registerBlockItem("oven", ModBlocks.OVEN, pizzaProperties());

    //Chopping Boards
    public static final RegistryObject<Item> OAK_CHOPPING_BOARD = registerBlockItem("oak_chopping_board", ModBlocks.OAK_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> BIRCH_CHOPPING_BOARD = registerBlockItem("birch_chopping_board", ModBlocks.BIRCH_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> SPRUCE_CHOPPING_BOARD = registerBlockItem("spruce_chopping_board", ModBlocks.SPRUCE_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> JUNGLE_CHOPPING_BOARD = registerBlockItem("jungle_chopping_board", ModBlocks.JUNGLE_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> ACACIA_CHOPPING_BOARD = registerBlockItem("acacia_chopping_board", ModBlocks.ACACIA_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> DARK_OAK_CHOPPING_BOARD = registerBlockItem("dark_oak_chopping_board", ModBlocks.DARK_OAK_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> CRIMSON_CHOPPING_BOARD = registerBlockItem("crimson_chopping_board", ModBlocks.CRIMSON_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> WARPED_CHOPPING_BOARD = registerBlockItem("warped_chopping_board", ModBlocks.WARPED_CHOPPING_BOARD, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_CHOPPING_BOARD = registerBlockItem("olive_chopping_board", ModBlocks.OLIVE_CHOPPING_BOARD, pizzaProperties());

    //Basins
    public static final RegistryObject<Item> GRANITE_BASIN = registerBlockItem("granite_basin", ModBlocks.GRANITE_BASIN, pizzaProperties());
    public static final RegistryObject<Item> DIORITE_BASIN = registerBlockItem("diorite_basin", ModBlocks.DIORITE_BASIN, pizzaProperties());
    public static final RegistryObject<Item> ANDESITE_BASIN = registerBlockItem("andesite_basin", ModBlocks.ANDESITE_BASIN, pizzaProperties());
    public static final RegistryObject<Item> BASALT_BASIN = registerBlockItem("basalt_basin", ModBlocks.BASALT_BASIN, pizzaProperties());
    public static final RegistryObject<Item> BLACKSTONE_BASIN = registerBlockItem("blackstone_basin", ModBlocks.BLACKSTONE_BASIN, pizzaProperties());

    //Outfits
    public static final RegistryObject<Item> CHEF_HAT = ITEMS.register("chef_hat", () -> new ChefArmor(ArmorMaterials.CHEF, ArmorItem.Type.HELMET, pizzaProperties()));
    public static final RegistryObject<Item> CHEF_SHIRT = ITEMS.register("chef_shirt", () -> new ChefArmor(ArmorMaterials.CHEF, ArmorItem.Type.CHESTPLATE, pizzaProperties()));
    public static final RegistryObject<Item> CHEF_LEGGINGS = ITEMS.register("chef_leggings", () -> new ChefArmor(ArmorMaterials.CHEF, ArmorItem.Type.LEGGINGS, pizzaProperties()));
    public static final RegistryObject<Item> CHEF_BOOTS = ITEMS.register("chef_boots", () -> new ChefArmor(ArmorMaterials.CHEF, ArmorItem.Type.BOOTS, pizzaProperties()));

    public static final RegistryObject<Item> PIZZA_DELIVERY_CAP = ITEMS.register("pizza_delivery_cap", () -> new PizzaDeliveryArmor(ArmorMaterials.PIZZA_DELIVERY, ArmorItem.Type.HELMET, pizzaProperties()));
    public static final RegistryObject<Item> PIZZA_DELIVERY_SHIRT = ITEMS.register("pizza_delivery_shirt", () -> new PizzaDeliveryArmor(ArmorMaterials.PIZZA_DELIVERY, ArmorItem.Type.CHESTPLATE, pizzaProperties()));
    public static final RegistryObject<Item> PIZZA_DELIVERY_LEGGINGS = ITEMS.register("pizza_delivery_leggings", () -> new PizzaDeliveryArmor(ArmorMaterials.PIZZA_DELIVERY, ArmorItem.Type.LEGGINGS, pizzaProperties()));
    public static final RegistryObject<Item> PIZZA_DELIVERY_BOOTS = ITEMS.register("pizza_delivery_boots", () -> new PizzaDeliveryArmor(ArmorMaterials.PIZZA_DELIVERY, ArmorItem.Type.BOOTS, pizzaProperties()));

    //Gear
    public static final RegistryObject<Item> RED_PIZZA_BAG = ITEMS.register("red_pizza_bag", () -> new PizzaBagItem(ModBlocks.RED_PIZZA_BAG.get(), pizzaProperties().stacksTo(1)));

    //Tools
    public static final RegistryObject<Item> ROLLING_PIN = ITEMS.register("rolling_pin", () -> new Item(pizzaProperties().stacksTo(1).durability(60)));
    public static final RegistryObject<Item> STONE_KNIFE = ITEMS.register("stone_knife", () -> new KnifeItem(Tiers.STONE, 1, -2.0F, pizzaProperties().stacksTo(1)));
    public static final RegistryObject<Item> GOLDEN_KNIFE = ITEMS.register("golden_knife", () -> new KnifeItem(Tiers.GOLD, 1, -2.0F, pizzaProperties().stacksTo(1)));
    public static final RegistryObject<Item> IRON_KNIFE = ITEMS.register("iron_knife", () -> new KnifeItem(Tiers.IRON, 1, -2.0F, pizzaProperties().stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_KNIFE = ITEMS.register("diamond_knife", () -> new KnifeItem(Tiers.DIAMOND, 1, -2.0F, pizzaProperties().stacksTo(1)));
    public static final RegistryObject<Item> NETHERITE_KNIFE = ITEMS.register("netherite_knife", () -> new KnifeItem(Tiers.NETHERITE, 1, -2.0F, pizzaProperties().stacksTo(1)));
    public static final RegistryObject<Item> STONE_PIZZA_PEEL = ITEMS.register("stone_pizza_peel", () -> new PizzaPeelItem(Tiers.STONE,1.5F, -3.0F, pizzaProperties().stacksTo(1)));
    public static final RegistryObject<Item> GOLDEN_PIZZA_PEEL = ITEMS.register("golden_pizza_peel", () -> new PizzaPeelItem( Tiers.GOLD,1.5F, -3.0F, pizzaProperties().stacksTo(1)));
    public static final RegistryObject<Item> IRON_PIZZA_PEEL = ITEMS.register("iron_pizza_peel", () -> new PizzaPeelItem(Tiers.IRON, 1.5F, -3.0F, pizzaProperties().stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_PIZZA_PEEL = ITEMS.register("diamond_pizza_peel", () -> new PizzaPeelItem(Tiers.DIAMOND, 1.5F, -3.0F, pizzaProperties().stacksTo(1)));
    public static final RegistryObject<Item> NETHERITE_PIZZA_PEEL = ITEMS.register("netherite_pizza_peel", () -> new PizzaPeelItem(Tiers.NETHERITE, 1.5F, -3.0F, pizzaProperties().stacksTo(1)));

    //Pizza Slice
    public static final RegistryObject<Item> PIZZA_SLICE = ITEMS.register("pizza_slice", () -> new PizzaSliceItem(pizzaProperties()));

    //Jugs
    public static final RegistryObject<Item> OLIVE_OIL = ITEMS.register("olive_oil", () -> new SauceItem(pizzaProperties().stacksTo(1).food(ModFoods.OLIVE_OIL), SauceType.NONE));
    public static final RegistryObject<Item> TOMATO_SAUCE = ITEMS.register("tomato_sauce", () -> new SauceItem(pizzaProperties().stacksTo(1).food(ModFoods.TOMATO_SAUCE), SauceType.TOMATO));
    public static final RegistryObject<Item> HOT_SAUCE = ITEMS.register("hot_sauce", () -> new SauceItem(pizzaProperties().stacksTo(1).food(ModFoods.HOT_SAUCE), SauceType.HOT));

    //Trees
    public static final RegistryObject<Item> OLIVE_PLANKS = registerBlockItem("olive_planks", ModBlocks.OLIVE_PLANKS, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_SAPLING = registerBlockItem("olive_sapling", ModBlocks.OLIVE_SAPLING, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_LOG = registerBlockItem("olive_log", ModBlocks.OLIVE_LOG, pizzaProperties());
    public static final RegistryObject<Item> STRIPPED_OLIVE_LOG = registerBlockItem("stripped_olive_log", ModBlocks.STRIPPED_OLIVE_LOG, pizzaProperties());
    public static final RegistryObject<Item> STRIPPED_OLIVE_WOOD = registerBlockItem("stripped_olive_wood", ModBlocks.STRIPPED_OLIVE_WOOD, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_WOOD = registerBlockItem("olive_wood", ModBlocks.OLIVE_WOOD, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_LEAVES = registerBlockItem("olive_leaves", ModBlocks.OLIVE_LEAVES, pizzaProperties());
    public static final RegistryObject<Item> FRUIT_OLIVE_LEAVES = registerBlockItem("fruit_olive_leaves", ModBlocks.FRUIT_OLIVE_LEAVES, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_SLAB = registerBlockItem("olive_slab", ModBlocks.OLIVE_SLAB, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_PRESSURE_PLATE = registerBlockItem("olive_pressure_plate", ModBlocks.OLIVE_PRESSURE_PLATE, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_FENCE = registerBlockItem("olive_fence", ModBlocks.OLIVE_FENCE, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_TRAPDOOR = registerBlockItem("olive_trapdoor", ModBlocks.OLIVE_TRAPDOOR, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_FENCE_GATE = registerBlockItem("olive_fence_gate", ModBlocks.OLIVE_FENCE_GATE, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_BUTTON = registerBlockItem("olive_button", ModBlocks.OLIVE_BUTTON, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_STAIRS = registerBlockItem("olive_stairs", ModBlocks.OLIVE_STAIRS, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_DOOR = registerBlockItem("olive_door", ModBlocks.OLIVE_DOOR, pizzaProperties());
    public static final RegistryObject<Item> OLIVE_BOOKSHELF = registerBlockItem("olive_bookshelf", ModBlocks.OLIVE_BOOKSHELF, pizzaProperties());

    //Vegetables
    public static final RegistryObject<Item> BROCCOLI = ITEMS.register("broccoli", () -> new Item(pizzaProperties().food(ModFoods.BROCCOLI)));
    public static final RegistryObject<Item> CORN = ITEMS.register("corn", () -> new ItemNameBlockItem(ModBlocks.CORNS.get(), pizzaProperties().food(ModFoods.CORN)));
    public static final RegistryObject<Item> CUCUMBER = ITEMS.register("cucumber", () -> new Item(pizzaProperties().food(ModFoods.CUCUMBER)));
    public static final RegistryObject<Item> ONION = ITEMS.register("onion", () -> new ItemNameBlockItem(ModBlocks.ONIONS.get(), pizzaProperties().food(ModFoods.ONION)));
    public static final RegistryObject<Item> PEPPER = ITEMS.register("pepper", () -> new Item(pizzaProperties().food(ModFoods.PEPPER)));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new Item(pizzaProperties().food(ModFoods.TOMATO)));

    //Fruits
    public static final RegistryObject<Item> PINEAPPLE = ITEMS.register("pineapple", () -> new Item(pizzaProperties().food(ModFoods.PINEAPPLE)));
    public static final RegistryObject<Item> OLIVE = ITEMS.register("olive", () -> new Item(pizzaProperties().food(ModFoods.OLIVE)));

    //Slices
    public static final RegistryObject<Item> CUCUMBER_SLICE = ITEMS.register("cucumber_slice", () -> new Item(pizzaProperties().food(ModFoods.CUCUMBER_SLICE)));
    public static final RegistryObject<Item> ONION_SLICE = ITEMS.register("onion_slice", () -> new Item(pizzaProperties().food(ModFoods.ONION_SLICE)));
    public static final RegistryObject<Item> PEPPER_SLICE = ITEMS.register("pepper_slice", () -> new Item(pizzaProperties().food(ModFoods.PEPPER_SLICE)));
    public static final RegistryObject<Item> PINEAPPLE_SLICE = ITEMS.register("pineapple_slice", () -> new Item(pizzaProperties().food(ModFoods.PINEAPPLE_SLICE)));
    public static final RegistryObject<Item> TOMATO_SLICE = ITEMS.register("tomato_slice", () -> new Item(pizzaProperties().food(ModFoods.TOMATO_SLICE)));
    public static final RegistryObject<Item> MUSHROOM_SLICE = ITEMS.register("mushroom_slice", () -> new Item(pizzaProperties().food(ModFoods.MUSHROOM_SLICE)));

    //Meats
    public static final RegistryObject<Item> HAM = ITEMS.register("ham", () -> new Item(pizzaProperties().food(ModFoods.HAM)));
    public static final RegistryObject<Item> WING = ITEMS.register("wing", () -> new Item(pizzaProperties().food(ModFoods.WING)));
    public static final RegistryObject<Item> COOKED_WING = ITEMS.register("cooked_wing", () -> new Item(pizzaProperties().food(ModFoods.COOKED_WING)));
    public static final RegistryObject<Item> HOT_WING = ITEMS.register("hot_wing", () -> new Item(pizzaProperties().food(ModFoods.HOT_WING)));
    public static final RegistryObject<Item> FISH_FILLET = ITEMS.register("fish_fillet", () -> new Item(pizzaProperties().food(ModFoods.FISH_FILLET)));
    public static final RegistryObject<Item> COOKED_FISH_FILLET = ITEMS.register("cooked_fish_fillet", () -> new Item(pizzaProperties().food(ModFoods.COOKED_FISH_FILLET)));

    //Ingredients
    public static final RegistryObject<Item> FLOUR = ITEMS.register("flour", () -> new Item(pizzaProperties()));
    public static final RegistryObject<Item> CORN_FLOUR = ITEMS.register("corn_flour", () -> new Item(pizzaProperties()));
    public static final RegistryObject<Item> CHEESE_BLOCK = registerBlockItem("cheese_block", ModBlocks.CHEESE_BLOCK, pizzaProperties());
    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese", () -> new Item(pizzaProperties().food(ModFoods.CHEESE)));

    //Seeds
    public static final RegistryObject<Item> BROCCOLI_SEEDS = ITEMS.register("broccoli_seeds", () -> new ItemNameBlockItem(ModBlocks.BROCCOLI.get(), pizzaProperties()));
    public static final RegistryObject<Item> CUCUMBER_SEEDS = ITEMS.register("cucumber_seeds", () -> new ItemNameBlockItem(ModBlocks.CUCUMBERS.get(), pizzaProperties()));
    public static final RegistryObject<Item> PEPPER_SEEDS = ITEMS.register("pepper_seeds", () -> new ItemNameBlockItem(ModBlocks.PEPPERS.get(), pizzaProperties()));
    public static final RegistryObject<Item> PINEAPPLE_SEEDS = ITEMS.register("pineapple_seeds", () -> new ItemNameBlockItem(ModBlocks.PINEAPPLE.get(), pizzaProperties()));
    public static final RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds", () -> new ItemNameBlockItem(ModBlocks.TOMATOES.get(), pizzaProperties()));

    public static RegistryObject<Item> registerBlockItem(final String name, RegistryObject<Block> block, Item.Properties properties)
    {
        return ITEMS.register(name, () -> new BlockItem(block.get(), properties));
    }

    public static Item.Properties pizzaProperties()
    {
        return new Item.Properties();
    }
}