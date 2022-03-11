package com.tiviacz.pizzacraft.blocks;

import com.tiviacz.pizzacraft.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class OliveLeavesBlock extends LeavesBlock implements IGrowable
{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public OliveLeavesBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(DISTANCE, 7).setValue(PERSISTENT, Boolean.FALSE).setValue(AGE, 3));
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if(state.getValue(AGE) == getMaxAge())
        {
            ItemStack stack = new ItemStack(ModItems.OLIVE.get(), worldIn.random.nextInt(2) + 1);

            if(!player.addItem(stack))
            {
                ItemEntity entity = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5, stack);
                worldIn.addFreshEntity(entity);
            }

            worldIn.setBlockAndUpdate(pos, state.setValue(AGE, 0));

            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random)
    {
        if(!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light

        if(worldIn.getRawBrightness(pos, 0) >= 9 && random.nextInt(5) == 0)
        {
            int i = this.getAge(state);

            if(i < this.getMaxAge())
            {
               // float f = getGrowthChance(this, worldIn, pos);

               // if(ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int)(25.0F / f) + 1) == 0))
               // {
                    worldIn.setBlock(pos, this.withAge(state, i + 1), 2);
                //    ForgeHooks.onCropsGrowPost(worldIn, pos, state);
               // }
            }
        }

        if(!state.getValue(PERSISTENT) && state.getValue(DISTANCE) == 7)
        {
            dropResources(state, worldIn, pos);
            worldIn.removeBlock(pos, false);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return super.isRandomlyTicking(state) || this.getAge(state) < this.getMaxAge();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(AGE);
        super.createBlockStateDefinition(builder);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return super.getStateForPlacement(context).setValue(AGE, 3);
    }

    public int getAge(BlockState state)
    {
        return state.getValue(AGE);
    }

    public int getMaxAge()
    {
        return 3;
    }

    public BlockState withAge(BlockState state, int age)
    {
        return state.setValue(AGE, age);
    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient)
    {
        return state.getValue(AGE) < getMaxAge();
    }

    @Override
    public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state)
    {
        return state.getValue(AGE) < getMaxAge();
    }

    @Override
    public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state)
    {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        worldIn.setBlock(pos, this.withAge(state, i), 2);
    }

    protected int getBonemealAgeIncrease(World worldIn)
    {
        return MathHelper.nextInt(worldIn.random, 1, 2);
    }
}