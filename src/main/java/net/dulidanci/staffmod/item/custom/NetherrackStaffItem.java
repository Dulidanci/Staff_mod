package net.dulidanci.staffmod.item.custom;

import net.dulidanci.staffmod.util.ManaSupplier;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetherrackStaffItem extends StaffItem {
    public static final int mana = 0;

    public NetherrackStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (ManaSupplier.manaCheck(context.getPlayer(), mana)) {
            World world = context.getWorld();
            if (!world.isClient) {
                BlockPos positionClicked = context.getBlockPos();

                world.setBlockState(positionClicked, Blocks.NETHERRACK.getDefaultState());
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }
}
