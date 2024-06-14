package com.tiviacz.pizzacraft.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tiviacz.pizzacraft.config.PizzaCraftConfig;
import com.tiviacz.pizzacraft.init.ModItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SeedsLootModifier extends LootModifier
{
    public static final Codec<SeedsLootModifier> CODEC = RecordCodecBuilder.create(instance -> LootModifier.codecStart(instance).apply(instance, SeedsLootModifier::new));

    public SeedsLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        if(PizzaCraftConfig.SERVER.dropOliveFromJungleLeaves.get() && context.getParamOrNull(LootContextParams.BLOCK_STATE).getBlock() == Blocks.JUNGLE_LEAVES)
        {
            generatedLoot.add(ModItems.OLIVE.get().getDefaultInstance());
        }

        if(context.getParamOrNull(LootContextParams.BLOCK_STATE).getBlock() == Blocks.TALL_GRASS)
        {
            generatedLoot.add(selectRandomSeed(context.getRandom()));
        }
        return generatedLoot;
    }

    public ItemStack selectRandomSeed(RandomSource random)
    {
        List<ItemStack> pool = new ArrayList<>();

        if(PizzaCraftConfig.SERVER.broccoliSeedDrops.get())
        {
            pool.add(ModItems.BROCCOLI_SEEDS.get().getDefaultInstance());
        }
        if(PizzaCraftConfig.SERVER.cucumberSeedDrops.get())
        {
            pool.add(ModItems.CUCUMBER_SEEDS.get().getDefaultInstance());
        }
        if(PizzaCraftConfig.SERVER.pepperSeedDrops.get())
        {
            pool.add(ModItems.PEPPER_SEEDS.get().getDefaultInstance());
        }
        if(PizzaCraftConfig.SERVER.pineappleSeedDrops.get())
        {
            pool.add(ModItems.PINEAPPLE_SEEDS.get().getDefaultInstance());
        }
        if(PizzaCraftConfig.SERVER.tomatoSeedDrops.get())
        {
            pool.add(ModItems.TOMATO_SEEDS.get().getDefaultInstance());
        }
        if(PizzaCraftConfig.SERVER.cornDrops.get())
        {
            pool.add(ModItems.CORN.get().getDefaultInstance());
        }
        if(PizzaCraftConfig.SERVER.onionDrops.get())
        {
            pool.add(ModItems.ONION.get().getDefaultInstance());
        }
        if(pool.isEmpty())
        {
            return ItemStack.EMPTY;
        }

        return pool.get(random.nextInt(pool.size()));
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec()
    {
        return CODEC;
    }
}