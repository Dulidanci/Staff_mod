package net.dulidanci.staffmod.item.custom;

import net.dulidanci.staffmod.util.ManaSupplier;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class LapisLazuliStaffItem extends StaffItem{
    public static final int mana = 3;
    public static final int manaOnHit = 2;

    public LapisLazuliStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient()) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }

        if (ManaSupplier.manaCheck(player, mana)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 60, 7, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200, 0, false, true));

            return TypedActionResult.success(player.getStackInHand(hand));
        }
        return TypedActionResult.fail(player.getStackInHand(hand));
    }

    public static void levitating(PlayerEntity player) {
        BlockPos position = player.getBlockPos();
        World world = player.getWorld();
        if (world.isClient()) {
            return;
        }
        ArrayList<BlockPos> posList = new ArrayList<>();
        for (int i = -2; i < 0; i++) {
                    posList.add(position.add(0, i, 0));
        }
        for (BlockPos current : posList) {
            if (!player.hasStatusEffect(StatusEffects.LEVITATION)) {
                if (world.getBlockState(current) != Blocks.AIR.getDefaultState()) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 5, 2, true, false));
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 40, 0, true, false));
                }
            }
        }
    }
}