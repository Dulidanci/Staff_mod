package net.ddaniel.staffmod.entity.custom;

import net.ddaniel.staffmod.util.AnvilTracker;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.lang.reflect.Field;

public class TrackedAnvilEntity extends FallingBlockEntity {
    private Runnable onLanding;

    private TrackedAnvilEntity(ServerWorld world, double x, double y, double z, BlockState blockState){
        super(EntityType.FALLING_BLOCK, world);
        this.setPos(x, y, z);
        try {
            Field blockField = FallingBlockEntity.class.getDeclaredField("block");
            blockField.setAccessible(true);
            blockField.set(this, blockState);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void spawnAnvil(ServerWorld serverWorld, PlayerEntity player, Vec3d velocity) {
        Vec3d spawnPos = player.getPos().add(0, 1, 0);
        TrackedAnvilEntity trackedAnvil = new TrackedAnvilEntity(serverWorld, spawnPos.x, spawnPos.y, spawnPos.z, Blocks.ANVIL.getDefaultState());

        trackedAnvil.setVelocity(velocity);
        trackedAnvil.setHurtEntities(2.0f, 20);
        trackedAnvil.setDestroyedOnLanding();
        trackedAnvil.dropItem = false;
        AnvilTracker.add(trackedAnvil);
        trackedAnvil.setOnLanding(() -> {
            AnvilTracker.remove(trackedAnvil);
            if (!trackedAnvil.getWorld().isClient) {
                World world = trackedAnvil.getWorld();
                double x = trackedAnvil.getX();
                double y = trackedAnvil.getY();
                double z = trackedAnvil.getZ();

                world.createExplosion(trackedAnvil, x, y, z, 2.0F, false, World.ExplosionSourceType.BLOW);
            }
        });
        serverWorld.spawnEntity(trackedAnvil);
    }

    public void setOnLanding(Runnable onLanding) {
        this.onLanding = onLanding;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isOnGround() && this.onLanding != null) {
            this.onLanding.run();
            this.onLanding = null;
        }
    }

    public static void setTargetForAnvils(Entity entity) {
        for (Entity anvil : AnvilTracker.getAnvilsSpawnedFromStaff()) {
//            anvil.setNoGravity(true);
            Vec3d startingPoint = anvil.getPos();
            Vec3d destination = entity.getPos();
            double dx = destination.x - startingPoint.x, dy = destination.y - startingPoint.y, dz = destination.z - startingPoint.z;
            double movement = Math.sqrt(dx * dx + dy * dy + dz * dz);
            anvil.setVelocity(dx / movement * 2, dy / movement * 2, dz / movement * 2);

        }
    }
}
