package net.dulidanci.staffmod.item.custom;

import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LapisLazuliStaffItem extends StaffItem{
    public LapisLazuliStaffItem(Settings settings, int level) {
        super(settings, level);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient()) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }

        player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 60, 7, false, true));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200, 0, false, true));


        return TypedActionResult.success(player.getStackInHand(hand));
    }

    public static void levitating(PlayerEntity player) {
        BlockPos position = player.getBlockPos();
        World world = player.getWorld();
        if (world.isClient()) {
            return;
        }
        if (!player.hasStatusEffect(StatusEffects.LEVITATION)) {
            if (world.getBlockState(position.add(0, -1, 0)) != Blocks.AIR.getDefaultState() ||
                    world.getBlockState(position.add(0, -2, 0)) != Blocks.AIR.getDefaultState()) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 5, 2, true, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 40, 0, true, false));
            }
        }
    }
}
