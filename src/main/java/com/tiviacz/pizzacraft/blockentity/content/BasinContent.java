package com.tiviacz.pizzacraft.blockentity.content;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BasinContent
{
    public static final BasinContent AIR = BasinContentRegistry.REGISTRY.register(new BasinContent("air", BasinContentForm.EMPTY, BasinContentType.EMPTY, SauceType.NONE, ItemStack.EMPTY,  0));
    public static final BasinContent MILK = BasinContentRegistry.REGISTRY.register(new BasinContent("milk", BasinContentForm.FERMENTABLE, BasinContentType.MILK, SauceType.NONE, Items.MILK_BUCKET.getDefaultInstance(), 0));
    public static final BasinContent FERMENTING_MILK = BasinContentRegistry.REGISTRY.register(new BasinContent("fermenting_milk", BasinContentForm.FERMENTABLE, BasinContentType.FERMENTING_MILK, SauceType.NONE, Items.MILK_BUCKET.getDefaultInstance(), 0));
    public static final BasinContent CHEESE = BasinContentRegistry.REGISTRY.register(new BasinContent("cheese", BasinContentForm.BLOCK, BasinContentType.CHEESE, SauceType.NONE, ModBlocks.CHEESE_BLOCK.get().asItem().getDefaultInstance(), 0));
    public static final BasinContent TOMATO_SAUCE = BasinContentRegistry.REGISTRY.register(new BasinContent("tomato_sauce", BasinContentForm.FLUID, BasinContentType.SAUCE, SauceType.TOMATO, ModItems.TOMATO_SAUCE.get().getDefaultInstance(), 4));
    public static final BasinContent HOT_SAUCE = BasinContentRegistry.REGISTRY.register(new BasinContent("hot_sauce", BasinContentForm.FLUID, BasinContentType.SAUCE, SauceType.HOT, ModItems.HOT_SAUCE.get().getDefaultInstance(), 4));
    public static final BasinContent OLIVE_OIL = BasinContentRegistry.REGISTRY.register(new BasinContent("olive_oil", BasinContentForm.FLUID, BasinContentType.OIL, SauceType.NONE, ModItems.OLIVE_OIL.get().getDefaultInstance(), 4));

    public final String name;
    public final BasinContentForm form;
    public final BasinContentType contentType;
    public final SauceType sauceType;
    public final ItemStack extractionStack;
    public final int extractionSize;

    public BasinContent(String name, BasinContentForm form, BasinContentType contentType, SauceType sauce, ItemStack extractionStack, int extractionSize)
    {
        this.name = name;
        this.form = form;
        this.contentType = contentType;
        this.sauceType = sauce;
        this.extractionStack = extractionStack;
        this.extractionSize = extractionSize;
    }

    public static void register() {}

    public boolean isEmpty()
    {
        return this == AIR;
    }

    public SauceType getSauceType()
    {
        return this.sauceType;
    }

    public BasinContentType getContentType()
    {
        return this.contentType;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

    public String getTranslationKey()
    {
        return PizzaCraft.MODID + "." + this.name;
    }
}
