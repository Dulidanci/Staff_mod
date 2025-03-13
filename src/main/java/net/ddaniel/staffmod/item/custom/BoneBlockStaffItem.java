package net.ddaniel.staffmod.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BoneBlockStaffItem extends StaffItem{
    public BoneBlockStaffItem(Settings settings, int level) {
        super(settings, level);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient()) {
            return ActionResult.PASS;
        }

        BlockPos positionClicked = context.getBlockPos();
        BlockState targetBlockState = world.getBlockState(positionClicked);

        if (targetBlockState.getBlock() instanceof Fertilizable fertilizable) {
            if (fertilizable.isFertilizable(world, positionClicked, targetBlockState)) {
                fertilizable.grow((ServerWorld) world, Random.create(), positionClicked, targetBlockState);
                ((ServerWorld) world).spawnParticles(ParticleTypes.HAPPY_VILLAGER, positionClicked.getX() + 0.5, positionClicked.getY() + 0.5,
                        positionClicked.getZ() + 0.5, 4, 0.5, 0.5, 0.5, 0.1);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }
}

// world.addParticle(ParticleTypes.HAPPY_VILLAGER, positionClicked.getX(), positionClicked.getY(),
//                            positionClicked.getZ(), 0, 0.1, 0);