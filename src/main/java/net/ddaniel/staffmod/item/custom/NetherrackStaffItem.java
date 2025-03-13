package net.ddaniel.staffmod.item.custom;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetherrackStaffItem extends StaffItem {
    public NetherrackStaffItem(Settings settings, int level) {
        super(settings, level);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            BlockPos positionClicked = context.getBlockPos();

            world.setBlockState(positionClicked, Blocks.NETHERRACK.getDefaultState());
        }
        return ActionResult.SUCCESS;
    }
}
