package com.tiviacz.pizzacraft.compat.jei;

import com.tiviacz.pizzacraft.PizzaCraft;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.recipes.crushing.CrushingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CrushingRecipeCategory implements IRecipeCategory<CrushingRecipe>
{
    public static final RecipeType<CrushingRecipe> CRUSHING =
            RecipeType.create(PizzaCraft.MODID, "crushing", CrushingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final Component title;

    public CrushingRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createDrawable(new ResourceLocation(PizzaCraft.MODID, "textures/gui/crushing_recipe.png"), -4, -4, 93, 39);
        icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.GRANITE_BASIN.get()));
        title = Component.translatable("recipecategory." + PizzaCraft.MODID + ".crushing");
    }

    @Override
    public RecipeType<CrushingRecipe> getRecipeType()
    {
        return CRUSHING;
    }

    @Override
    public Component getTitle()
    {
        return title;
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrushingRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 5, 11).addItemStacks(Arrays.asList(makeListWithCount(recipe.input.getItems(), recipe.inputCount)));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 68, 11).addItemStack(recipe.stackOutput);
    }

    public ItemStack[] makeListWithCount(ItemStack[] itemstacks, int count)
    {
        ItemStack[] modifiedStacks = itemstacks.clone();

        for(int i = 0; i < modifiedStacks.length; i++)
        {
            modifiedStacks[i].setCount(count);
        }
        return modifiedStacks;
    }
}
