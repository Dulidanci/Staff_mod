package net.dulidanci.staffmod.item.custom;

import net.dulidanci.staffmod.entity.custom.TrackedAnvilEntity;

import net.dulidanci.staffmod.util.ManaSupplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AnvilStaffItem extends StaffItem {
    public static final int mana = 8;

    public AnvilStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }

        if (ManaSupplier.manaCheck(player, mana)) {
            ServerWorld serverWorld = (ServerWorld) world;
            for (int i = -1; i < 2; i += 2) {
                for (int j = -1; j < 2; j += 2) {
                    Vec3d velocity = new Vec3d(i * 0.2, 1.5, j * 0.2);
                    TrackedAnvilEntity.spawnAnvil(serverWorld, player, velocity);
                }
            }
            return TypedActionResult.success(player.getStackInHand(hand));
        }
        return TypedActionResult.fail(player.getStackInHand(hand));
    }
}
