package net.dulidanci.staffmod.item.custom;

import net.dulidanci.staffmod.block.ModBlocks;
import net.dulidanci.staffmod.util.ManaSupplier;
import net.dulidanci.staffmod.util.ModTags;
import net.minecraft.block.LightBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GlowstoneStaffItem extends StaffItem{
    public static final int mana = 2;

    public GlowstoneStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }

        if (ManaSupplier.manaCheck(player, mana)) {
            BlockPos playerPosition = player.getBlockPos();
            if (world.getBlockState(playerPosition).isIn(ModTags.REPLACEABLE_BY_LIGHT)) {
                world.setBlockState(playerPosition, ModBlocks.FADING_LIGHT_BLOCK.getDefaultState().with(LightBlock.LEVEL_15, 15));
                world.scheduleBlockTick(playerPosition, ModBlocks.FADING_LIGHT_BLOCK, 800);
                return TypedActionResult.success(player.getStackInHand(hand));

            } else if (world.getBlockState(playerPosition.add(0, 1, 0)).isIn(ModTags.REPLACEABLE_BY_LIGHT)) {
                world.setBlockState(playerPosition.add(0, 1, 0), ModBlocks.FADING_LIGHT_BLOCK.getDefaultState().with(LightBlock.LEVEL_15, 15));
                world.scheduleBlockTick(playerPosition.add(0, 1, 0), ModBlocks.FADING_LIGHT_BLOCK, 800);
                return TypedActionResult.success(player.getStackInHand(hand));
            }
            return TypedActionResult.pass(player.getStackInHand(hand));
        }
        return TypedActionResult.fail(player.getStackInHand(hand));
    }

}
