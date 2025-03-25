package net.dulidanci.staffmod.util;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.item.custom.MagmaStaffItem;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.server.MinecraftServer;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class EntityTimerManager {
    private static final Map<UUID, Pair<Integer, Integer>> entityTimers = new HashMap<>();
    /**
     * UUID - Ticking entity's Id
     * <p>
     * First Integer - Ticks left
     * <p>
     * Second Integer - Type of action to be executed
     */

    public static void register() {
        StaffMod.LOGGER.info("Registering EntityTimerManager for " + StaffMod.MOD_ID);
        ServerTickEvents.END_SERVER_TICK.register(EntityTimerManager::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        Iterator<Map.Entry<UUID, Pair<Integer, Integer>>> iterator = entityTimers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, Pair<Integer, Integer>> entry = iterator.next();
            int ticksLeft = entry.getValue().getA() - 1;

            if (ticksLeft <= 0) {
                UUID entityId = entry.getKey();
                Entity entity = findEntityByUUID(server, entityId);
                if (entity != null) {
                    executeTimedAction(entity, entry.getValue().getB());
                }
                iterator.remove();
            } else {
                entry.setValue(new Pair<>(ticksLeft, entry.getValue().getB()));
            }
        }
    }

    public static void startEntityTimer(Entity entity, int ticks, int type) {
        entityTimers.put(entity.getUuid(), new Pair<>(ticks, type));
    }

    /**
     * @param type Stores which action has to be executed when the timer hits zero
     * @value 0
     * <p>
     *     NoAI turning off; called from BellStaffItem
     * </p>
     * @value 1
     * <p>
     *     Removing fireballs; called from MagmaStaffItem
     * </p>
     */
    private static void executeTimedAction(Entity entity, int type) {
        if (type == 0) {
            if (entity instanceof LivingEntity livingEntity) {
                MobUtilities.setNoAI(livingEntity, false);
            }
        } else if (type == 1) {
            if (entity instanceof FireballEntity fireball) {
                MagmaStaffItem.removeFireball(fireball);
            }
        }
    }

    private static Entity findEntityByUUID(MinecraftServer server, UUID entityId) {
        for (var world : server.getWorlds()) {
            Entity entity = world.getEntity(entityId);
            if (entity != null) {
                return entity;
            }
        }
        return null;
    }


}
