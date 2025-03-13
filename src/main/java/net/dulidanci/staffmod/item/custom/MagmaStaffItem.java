package net.dulidanci.staffmod.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagmaStaffItem extends StaffItem{
    public MagmaStaffItem(Settings settings, int level) {
        super(settings, level);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }
        Vec3d facing = player.getRotationVector();

        FireballEntity fireball = new FireballEntity(world, player, facing.x, facing.y, facing.z, 1);
        fireball.setPos(fireball.getX(), fireball.getY() + 1, fireball.getZ());
        world.spawnEntity(fireball);

        return TypedActionResult.success(player.getStackInHand(hand));
    }
}
