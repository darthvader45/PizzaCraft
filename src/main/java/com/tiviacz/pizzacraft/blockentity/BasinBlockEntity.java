package com.tiviacz.pizzacraft.blockentity;

import com.google.common.collect.Maps;
import com.google.gson.JsonSyntaxException;
import com.tiviacz.pizzacraft.blockentity.content.*;
import com.tiviacz.pizzacraft.init.ModBlockEntityTypes;
import com.tiviacz.pizzacraft.init.ModBlocks;
import com.tiviacz.pizzacraft.init.ModItems;
import com.tiviacz.pizzacraft.recipes.crushing.CrushingRecipe;
import com.tiviacz.pizzacraft.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public class BasinBlockEntity extends BaseBlockEntity
{
    private final ItemStackHandler inventory = createHandler();
    private BasinContent content = BasinContent.AIR;
    private ItemStack squashedStack = ItemStack.EMPTY;
    private int fermentProgress = 0;
    private final int defaultFermentTime = 1200;
    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> this.inventory);

    private final String BASIN_CONTENT = "BasinContent";
    private final String SQUASHED_STACK = "SquashedStack";
    private final String FERMENT_PROGRESS = "FermentProgress";

    public BasinBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntityTypes.BASIN.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        this.content = BasinContentRegistry.REGISTRY.fromString(compound.getString(BASIN_CONTENT));
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY));
        this.squashedStack = ItemStack.of(compound.getCompound(SQUASHED_STACK));

        this.fermentProgress = compound.getInt(FERMENT_PROGRESS);
    }

    @Override
    public void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.putString(BASIN_CONTENT, content.toString());
        compound.put(INVENTORY, this.inventory.serializeNBT());

        CompoundTag squashedStack = new CompoundTag();
        this.squashedStack.save(squashedStack);
        compound.put(SQUASHED_STACK, squashedStack);

        compound.putInt(FERMENT_PROGRESS, this.fermentProgress);
    }

    public float getAmount()
    {
        return getSquashedStackCount() * 0.75F;
    }

    public BasinContent getBasinContent()
    {
        return this.content;
    }

    public void updateBasinContent()
    {
        if(getSquashedStackCount() <= 0)
        {
            this.squashedStack = ItemStack.EMPTY;
            this.content = BasinContent.AIR;
        }
        setChanged();
    }

    public void playSound(@Nullable Player player, BasinContentType type)
    {
        SoundEvent sound = null;
        if(type == BasinContentType.EMPTY) sound = SoundEvents.BUCKET_EMPTY;
        if(type == BasinContentType.MILK || type == BasinContentType.FERMENTING_MILK) sound = SoundEvents.BUCKET_FILL;
        if(type == BasinContentType.CHEESE) sound = SoundEvents.FUNGUS_PLACE;
        if(type == BasinContentType.SAUCE || type == BasinContentType.OIL) sound = SoundEvents.BOTTLE_FILL;

        if(sound != null)
        {
            level.playSound(player, getBlockPos(), sound, SoundSource.BLOCKS, 0.8F, 0.9F + level.random.nextFloat());
        }
    }

    public ItemStack extractBasinContent(ItemStack clickedStack)
    {
        if(!getBasinContent().isEmpty())
        {
            if(getBasinContent().extractionSize <= getSquashedStackCount())
            {
                ItemStack result = getBasinContent().extractionStack.copy();
                boolean hasContainer = false;

                //If item has container, we need container to extract it
                if(result.hasCraftingRemainingItem())
                {
                    hasContainer = true;
                    if(clickedStack.getItem() != result.getCraftingRemainingItem().getItem()) return ItemStack.EMPTY;
                }

                //If sauce, we need to decrease squashed stack size
                if(getBasinContent().sauceType != SauceType.NONE)
                {
                    setSquashedStackCount(getSquashedStackCount() - getBasinContent().extractionSize);
                    return result;
                }

                this.content = BasinContent.AIR;
                this.resetFermenting();
                this.setSquashedStackCount(0);
                if(hasContainer) clickedStack.shrink(1);
                this.setChanged();
                return result;
            }
        }
        return ItemStack.EMPTY;
    }

    public boolean startFermenting(@Nullable Player player, ItemStack stack)
    {
        if(getBasinContent().getContentType() == BasinContentType.MILK)
        {
            if(stack.is(ModTags.FERMENTING_ITEMS_TAG))
            {
                level.playSound(player, getBlockPos(), SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                stack.shrink(player != null && player.isCreative() ? 0 : 1);
                this.content = BasinContent.FERMENTING_MILK;
                this.setChanged();
                return true;
            }
        }
        return false;
    }
    /*#TODO
    tekstury hot sauce
    wszystkie universal layery zrobic (block i item)
    zrobic jakas mozliwosc automatyzacji basin?
    reszta tekstur od squidla
     */
    public boolean insertOrExtract(Player player, ItemStack stack)
    {
        if(getBasinContent().isEmpty() || getBasinContent().form == BasinContentForm.FLUID)
        {
            if(stack.getItem() instanceof MilkBucketItem && isEmpty() && getBasinContent().isEmpty())
            {
                playSound(player, getBasinContent().contentType);
                this.content = BasinContent.MILK;
                if(!player.isCreative())
                {
                    player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET));
                }
                return true;
            }
            if(canInsert(stack))
            {
                insertStackFromHand(player, InteractionHand.MAIN_HAND);
                return true;
            }
            if(canExtract(player, InteractionHand.MAIN_HAND))
            {
                extractStackToPlayer(player);
                return true;
            }
        }
        return false;
    }

    public InteractionResult onBlockActivated(Player player, InteractionHand hand)
    {
        ItemStack itemHeld = player.getItemInHand(hand);
        BasinContentType basinContentType = getBasinContent().getContentType();
        ItemStack stackInSlot = getInventory().getStackInSlot(0);

        //If cheese, just extract
        if(basinContentType == BasinContentType.CHEESE)
        {
            level.addFreshEntity(new ItemEntity(level, getBlockPos().getX() + 0.5D, getBlockPos().getY() + 0.5D, getBlockPos().getZ() + 0.5D, new ItemStack(ModBlocks.CHEESE_BLOCK.get())));
            this.content = BasinContent.AIR;
            this.squashedStack = ItemStack.EMPTY;
            level.playSound(player, getBlockPos(), SoundEvents.FUNGUS_PLACE, SoundSource.BLOCKS, 0.8F, 0.9F + level.random.nextFloat());
            this.setChanged();
            return InteractionResult.SUCCESS;
        }
        if(hand == InteractionHand.MAIN_HAND)
        {
            if(basinContentType == BasinContentType.MILK)
            {
                //Check if player holds acceptable fermenting item, if so start fermenting process.
                if(itemHeld.is(ModTags.FERMENTING_ITEMS_TAG))
                {
                    level.playSound(player, getBlockPos(), SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);

                    if(!player.isCreative())
                    {
                        itemHeld.shrink(1);
                    }

                    this.content = BasinContent.FERMENTING_MILK;
                    this.setChanged();

                    return InteractionResult.SUCCESS;
                }

                //Extract Milk
                else if(itemHeld.getItem() instanceof BucketItem)
                {
                    this.content = BasinContent.AIR;
                    itemHeld.shrink(1);

                    if(!player.addItem(new ItemStack(Items.MILK_BUCKET)))
                    {
                        level.addFreshEntity(new ItemEntity(player.level(), getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), new ItemStack(Items.MILK_BUCKET)));
                    }

                    level.playSound(player, getBlockPos(), SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 0.8F, 1.0F);
                    this.setChanged();
                    return InteractionResult.SUCCESS;
                }
            }

            if(basinContentType == BasinContentType.EMPTY || getBasinContent().getSauceType() != SauceType.NONE || basinContentType == BasinContentType.OIL) //#TODO IF NOT FIX OLIVE, ADD LINE HERE
            {
                if(basinContentType == BasinContentType.EMPTY)
                {
                    if(itemHeld.getItem() == ModItems.CHEESE_BLOCK.get() && stackInSlot.isEmpty())
                    {
                        this.content = BasinContent.CHEESE;
                        this.squashedStack = new ItemStack(ModItems.CHEESE.get(), getInventory().getSlotLimit(0));

                        if(!player.isCreative())
                        {
                            itemHeld.shrink(2);
                        }

                        level.playSound(player, getBlockPos(), SoundEvents.FUNGUS_PLACE, SoundSource.BLOCKS, 0.8F, 0.9F + level.random.nextFloat());
                        this.setChanged();
                        return InteractionResult.SUCCESS;
                    }

                    else if(itemHeld.getItem() instanceof MilkBucketItem && stackInSlot.isEmpty())
                    {
                        this.content = BasinContent.MILK;
                        this.squashedStack = new ItemStack(Items.MILK_BUCKET, getInventory().getSlotLimit(0));

                        if(!player.isCreative())
                        {
                            player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                        }

                        level.playSound(player, getBlockPos(), SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 0.8F, 1.0F);
                        this.setChanged();
                        return InteractionResult.SUCCESS;
                    }

                    else if(canInsert(itemHeld))
                    {
                        //insertStack(player, hand);
                        return InteractionResult.SUCCESS;
                    }
                    else if(canExtract(player, hand))
                    {
                        //extractStack(player);
                        return InteractionResult.SUCCESS;
                    }
                }
                else if(getBasinContent().getSauceType() != SauceType.NONE || basinContentType == BasinContentType.OIL) //#TODO IF NOT FIX OLIVE, ADD LINE HERE
                {
                    if(getSquashedStackCount() >= getBasinContent().extractionSize)
                    {
                        ItemStack result = getBasinContent().extractionStack.copy();

                        if(!player.getInventory().add(result))
                        {
                            level.addFreshEntity(new ItemEntity(player.level(), getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), result));
                        }
                        level.playSound(player, getBlockPos(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat());
                        setSquashedStackCount(getSquashedStackCount() - getBasinContent().extractionSize);
                        return InteractionResult.SUCCESS;
                    }

                    else if(canInsert(itemHeld))
                    {
                        //insertStack(player, hand);
                        return InteractionResult.SUCCESS;
                    }
                    else if(canExtract(player, hand))
                    {
                        //extractStack(player);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.FAIL;
    }

    // ======== CRUSHING ========

    public ItemStack getSquashedStack()
    {
        return this.squashedStack;
    }

    public int getSquashedStackCount()
    {
        return this.squashedStack.getCount();
    }

    public void setSquashedStackCount(int count)
    {
        this.squashedStack.setCount(count);
        updateBasinContent();
    }

    /**
     * Called when player jumps on basin block
     * @param player
     */
    public void crush(@Nullable Player player)
    {
        if(!isEmpty(inventory) && getSquashedStackCount() + 1 <= getInventory().getSlotLimit(0))
        {
            ItemStack stack = getInventory().getStackInSlot(0);

            Optional<CrushingRecipe> match = level.getRecipeManager().getRecipeFor(CrushingRecipe.Type.CRUSHING_RECIPE_TYPE, new RecipeWrapper(getInventory()), level);

            if(match.isPresent())
            {
                if(BasinContentRegistry.REGISTRY.fromString(match.get().contentOutput) == null)
                {
                    throw new JsonSyntaxException(String.format("Content in %s recipe does not exist", match.get()));
                }

                if(getBasinContent() == BasinContent.AIR || getBasinContent() == BasinContentRegistry.REGISTRY.fromString(match.get().contentOutput))
                {
                    if(getSquashedStack().isEmpty())
                    {
                        this.squashedStack = new ItemStack(stack.getItem(), 1);
                    }
                    else
                    {
                        setSquashedStackCount(getSquashedStackCount() + 1);
                    }

                    this.content = BasinContentRegistry.REGISTRY.fromString(match.get().contentOutput);
                    decrStackSize(inventory, 0, 1);
                    level.playSound(player, getBlockPos(), SoundEvents.SLIME_BLOCK_FALL, SoundSource.BLOCKS, 0.7F, 0.9F + (0.1F * level.random.nextFloat()));
                }
            }
        }
    }

    // ======== FERMENTING ========

    public int getFermentProgress()
    {
        return this.fermentProgress;
    }

    public int getDefaultFermentTime()
    {
        return this.defaultFermentTime;
    }

    public int getComparatorOutput()
    {
        float f = (float)this.fermentProgress / defaultFermentTime;
        return (int)(f*15);
    }

    public void finishFermenting()
    {
        if(getBasinContent().getContentType() == BasinContentType.FERMENTING_MILK)
        {
            this.fermentProgress = 0;
            this.content = BasinContent.CHEESE;
        }
        setChanged();
    }

    public void resetFermenting()
    {
        this.fermentProgress = 0;
        setChanged();;
    }

    private int tick = 0;

    public static void tick(Level level, BlockPos pos, BlockState state, BasinBlockEntity blockEntity)
    {
        if(blockEntity.getBasinContent() != null)
        {
            if(blockEntity.getBasinContent().getContentType() == BasinContentType.FERMENTING_MILK)
            {
                blockEntity.fermentProgress++;
                //blockEntity.setChanged();

                if(blockEntity.fermentProgress % 60 == 0)
                {
                    //if(tick == 59)
                    //{
                    level.playSound(null, pos, SoundEvents.FUNGUS_PLACE, SoundSource.BLOCKS, 0.8F, 0.9F + level.random.nextFloat());
                    //}
                }
            }
            if(blockEntity.fermentProgress >= blockEntity.defaultFermentTime)
            {
                blockEntity.finishFermenting();
            }

            if(blockEntity.getBasinContent().getContentType() == BasinContentType.CHEESE)
            {
                if(blockEntity.tick % 20 == 0)
                {
                    blockEntity.createCheeseParticle();
                }
                if(blockEntity.tick < 60)
                {
                    blockEntity.tick++;
                }
                else if(blockEntity.tick == 60)
                {
                    blockEntity.tick = 0;
                }
            }
        }
    }

    private void createCheeseParticle()
    {
        double x = ((double)level.random.nextInt(12) / 16);
        double z = ((double)level.random.nextInt(12) / 16);
        level.addParticle(new DustParticleOptions(new Vector3f(0.91F, 0.76F, 0.31F), 1.0F), getBlockPos().getX() + x + 0.2D, getBlockPos().getY() + 0.6D, getBlockPos().getZ() + z + 0.2D, 0.0D, 0.09D, 0.0D);
    }

    // ======== ITEMHANDLER ========

    public boolean isEmpty()
    {
        return getInventory().getStackInSlot(0).isEmpty();
    }

    public boolean canInsert(ItemStack stack)
    {
        return !stack.isEmpty() && isEmpty();
    }

    public boolean canExtract(Player player, InteractionHand handIn)
    {
        return player.getItemInHand(handIn).isEmpty() && !isEmpty();
    }

    public void insertStackFromHand(Player player, InteractionHand hand)
    {
        if(!player.isCreative())
        {
            player.setItemInHand(hand, getInventory().insertItem(0, player.getItemInHand(hand), false));
        }
        else
        {
            getInventory().insertItem(0, player.getItemInHand(hand).copy(), false);
        }
        level.playSound(player, getBlockPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
    }

    public void extractStackToPlayer(Player player)
    {
        if(!player.isCreative())
        {
            Containers.dropItemStack(level, getBlockPos().getX() + 0.5, getBlockPos().getY() + 0.8, getBlockPos().getZ() + 0.5, getInventory().extractItem(0, 64, false));
        }
        else
        {
            getInventory().extractItem(0, 64, false);
        }
        level.playSound(player, getBlockPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.7F, 0.8F + level.random.nextFloat());
    }

    public IItemHandlerModifiable getInventory()
    {
        return inventory;
    }

    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler()
        {
            @Override
            public int getSlotLimit(int slot)
            {
                return 8;
            }

            @Override
            protected void onContentsChanged(int slot)
            {
                super.onContentsChanged(slot);
                setChanged();
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side)
    {
        if(cap == ForgeCapabilities.ITEM_HANDLER)
            return inventoryCapability.cast();
        return super.getCapability(cap, side);
    }
}