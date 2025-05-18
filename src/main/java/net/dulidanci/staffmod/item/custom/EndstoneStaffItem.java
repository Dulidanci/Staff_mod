package net.dulidanci.staffmod.item.custom;

import net.dulidanci.staffmod.util.ManaSupplier;
import net.dulidanci.staffmod.util.ModTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EndstoneStaffItem extends EmptyStaffItem {
    public static final int mana = 4;

    public EndstoneStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }

        if (ManaSupplier.manaCheck(player, mana)) {
            float yaw = player.getYaw();
            double checkingDistanceX = 4 * MathHelper.sin((yaw / 180 + 1) * (float) Math.PI);
            double checkingDistanceZ = -4 * MathHelper.cos((yaw / 180 + 1) * (float) Math.PI);
            Vec3d destination = new Vec3d(player.getX() + checkingDistanceX, player.getY() + 1.0, player.getZ() + checkingDistanceZ);
            BlockPos destinationBlockPos;
            int i = 0;

            do {
                destination = new Vec3d(destination.x + checkingDistanceX, destination.y, destination.z + checkingDistanceZ);
                destinationBlockPos = new BlockPos((int) Math.round(destination.x), (int) Math.round(destination.y), (int) Math.round(destination.z));
                i++;
            } while (!(world.getBlockState(destinationBlockPos).isIn(ModTags.CAN_TELEPORT_INTO)) && i <= 50);

            player.teleport(destination.x, destination.y, destination.z);

            return TypedActionResult.success(player.getStackInHand(hand));
        }
        return TypedActionResult.fail(player.getStackInHand(hand));
    }
}
