package com.tiviacz.pizzacraft.recipes.chopping;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.tiviacz.pizzacraft.PizzaCraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class ChoppingRecipeSerializer implements RecipeSerializer<ChoppingRecipe>
{
    public ChoppingRecipeSerializer() {}

    public static final ChoppingRecipeSerializer INSTANCE = new ChoppingRecipeSerializer();
    public static final ResourceLocation ID = new ResourceLocation(PizzaCraft.MODID, "chopping_recipe");

    @Override
    public ChoppingRecipe fromJson(ResourceLocation id, JsonObject json)
    {
        Ingredient tool = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "tool"));
        Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));

        if(tool.isEmpty() || input.isEmpty())
        {
            throw new JsonSyntaxException("Missing Attributes in Chopping Recipe!");
        }

        if(!json.has("result"))
        {
            throw new JsonSyntaxException("Missing result, expected to find a string or object");
        }

        ItemStack result;
        if(json.get("result").isJsonObject())
        {
            result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
        }
        else
        {
            String s1 = GsonHelper.getAsString(json, "result");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            result = new ItemStack(BuiltInRegistries.ITEM.getOptional(resourcelocation).orElseThrow(() ->
                    new IllegalStateException("Item: " + s1 + " does not exist")));
        }

        return new ChoppingRecipe(tool, input, result, id);
    }

    @Override
    public ChoppingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf)
    {
        Ingredient tool = Ingredient.fromNetwork(buf);
        Ingredient input = Ingredient.fromNetwork(buf);
        ItemStack output = buf.readItem();

        return new ChoppingRecipe(tool, input, output, id);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, ChoppingRecipe recipe)
    {
        recipe.tool.toNetwork(buf);
        recipe.input.toNetwork(buf);
        buf.writeItem(recipe.output);
    }
}