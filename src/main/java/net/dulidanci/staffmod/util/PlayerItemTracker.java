package net.dulidanci.staffmod.util;

import net.dulidanci.staffmod.item.ModItems;
import net.dulidanci.staffmod.item.custom.LapisLazuliStaffItem;
import net.dulidanci.staffmod.item.custom.PlanksStaffItem;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerItemTracker {
    private static final Item[] ITEMS_TO_TRACK = {
            ModItems.OAK_PLANKS_STAFF,          // 0
            ModItems.SPRUCE_PLANKS_STAFF,       // 1
            ModItems.BIRCH_PLANKS_STAFF,        // 2
            ModItems.JUNGLE_PLANKS_STAFF,       // 3
            ModItems.ACACIA_PLANKS_STAFF,       // 4
            ModItems.DARK_OAK_PLANKS_STAFF,     // 5
            ModItems.MANGROVE_PLANKS_STAFF,     // 6
            ModItems.CHERRY_PLANKS_STAFF,       // 7
            ModItems.BAMBOO_PLANKS_STAFF,       // 8
            ModItems.CRIMSON_PLANKS_STAFF,      // 9
            ModItems.WARPED_PLANKS_STAFF,       // 10
            ModItems.LAPIS_LAZULI_STAFF         // 11
    };
    private static final Map<UUID, Boolean[]> table = new HashMap<>();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(minecraftServer -> {
            for (ServerPlayerEntity player : minecraftServer.getPlayerManager().getPlayerList()) {
                table.putIfAbsent(player.getUuid(), initializeBooleanArray());
                Boolean[] areStaffsHeld = table.get(player.getUuid());
                for (int i = 0; i < ITEMS_TO_TRACK.length; i++) {
                    Item currentItem = ITEMS_TO_TRACK[i];
                    if (isPlayerHoldingItem(player, currentItem) && areStaffsHeld[i]) {
                        whileHoldingItem(player, i);
                    } else if (isPlayerHoldingItem(player, currentItem) && !areStaffsHeld[i]) {
                        onItemEquipped(player, i);
                        areStaffsHeld[i] = true;
                    } else if (!isPlayerHoldingItem(player, currentItem) && areStaffsHeld[i]) {
                        onItemRemoved(player, i);
                        areStaffsHeld[i] = false;
                    }
                }
            }
        });
    }

    private static Boolean[] initializeBooleanArray() {
        Boolean[] array = new Boolean[ITEMS_TO_TRACK.length];
        for (int i = 0; i < ITEMS_TO_TRACK.length; i++) {
            array[i] = false;
        }
        return array;
    }

    private static boolean isPlayerHoldingItem(ServerPlayerEntity player, Item item) {
        return player.getMainHandStack().getItem() == item || player.getOffHandStack().getItem() == item;
    }

    private static void onItemEquipped(ServerPlayerEntity player, int whichStaff) {
        if (whichStaff < 11) {
            PlanksStaffItem.generatePreview(player);
        } else {
            LapisLazuliStaffItem.levitating(player);
        }
    }

    private static void whileHoldingItem(ServerPlayerEntity player, int whichStaff) {
        if (whichStaff < 11) {
            PlanksStaffItem.generatePreview(player);
        } else {
            LapisLazuliStaffItem.levitating(player);
        }
    }

    private static void onItemRemoved(ServerPlayerEntity player, int whichStaff) {
        if (whichStaff < 11) {
            PlanksStaffItem.removePreview(player);
        }
    }
}
