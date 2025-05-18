package net.dulidanci.staffmod.item.custom;

import net.dulidanci.staffmod.util.ManaSupplier;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TntStaffItem extends EmptyStaffItem {
    public static final int mana = 1;

    public TntStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            if (ManaSupplier.manaCheck(player, mana)) {
                ServerWorld serverWorld = (ServerWorld) world;
                spawnTnt(serverWorld, player);
                return TypedActionResult.success(player.getStackInHand(hand));
            }
        }
        return TypedActionResult.pass(player.getStackInHand(hand));
    }

    public void spawnTnt(ServerWorld world, PlayerEntity player) {
        TntEntity spawnedTnt = EntityType.TNT.create(world);

        BlockPos position = player.getBlockPos().add(0, 1, 0);

        float yaw = player.getYaw();
        float pitch = player.getPitch();
        //        MathHelper.sin((yaw / 180 + 1) * (float) Math.PI)
        float elevation = MathHelper.sin((pitch / 180) * (float) Math.PI);
        Vec3d velocity = new Vec3d(Math.sqrt(1 - elevation * elevation) * MathHelper.sin((yaw / 180 + 1) * (float) Math.PI),
                -elevation,
                Math.sqrt(1 - elevation * elevation) * -MathHelper.cos((yaw / 180 + 1) * (float) Math.PI));
        if (spawnedTnt != null) {
            spawnedTnt.refreshPositionAndAngles(position, 0, 0);
            spawnedTnt.setVelocity(velocity);
            world.spawnEntity(spawnedTnt);
        }
    }
}
