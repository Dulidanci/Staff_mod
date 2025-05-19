package net.dulidanci.staffmod.util;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.item.ModItems;
import net.dulidanci.staffmod.item.custom.LapisLazuliStaffItem;
import net.dulidanci.staffmod.item.custom.PlanksStaffItem;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Pair;

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
    private static final Map<UUID, Pair<Item, Item>> heldItems = new HashMap<>();

    public static void register() {
        StaffMod.LOGGER.info("Registering PlayerItemTracker for " + StaffMod.MOD_ID);
        ServerTickEvents.END_SERVER_TICK.register(minecraftServer -> {
            for (ServerPlayerEntity player : minecraftServer.getPlayerManager().getPlayerList()) {
                heldItems.putIfAbsent(player.getUuid(), new Pair<>(player.getMainHandStack().getItem(), player.getOffHandStack().getItem()));
                Pair<Item, Item> previouslyHeld = heldItems.get(player.getUuid());
                Pair<Item, Item> playerHands = new Pair<>(player.getMainHandStack().getItem(), player.getOffHandStack().getItem());

                if (isCheckedItem(playerHands.getLeft()) >= 0 && previouslyHeld.getLeft() != playerHands.getLeft()) {
                    onItemEquipped(player, playerHands.getLeft());
                }
                if (isCheckedItem(previouslyHeld.getLeft()) >= 0 && previouslyHeld.getLeft() == playerHands.getLeft()) {
                    whileHoldingItem(player, playerHands.getLeft());
                }
                if (isCheckedItem(previouslyHeld.getLeft()) >= 0 && previouslyHeld.getLeft() != playerHands.getLeft()) {
                    onItemRemoved(player, previouslyHeld.getLeft());
                }

                if (isCheckedItem(playerHands.getRight()) >= 0 && previouslyHeld.getRight() != playerHands.getRight()) {
                    onItemEquipped(player, playerHands.getRight());
                }
                if (isCheckedItem(previouslyHeld.getRight()) >= 0 && previouslyHeld.getRight() == playerHands.getRight()) {
                    whileHoldingItem(player, playerHands.getRight());
                }
                if (isCheckedItem(previouslyHeld.getRight()) >= 0 && previouslyHeld.getRight() != playerHands.getRight()) {
                    onItemRemoved(player, previouslyHeld.getRight());
                }

                heldItems.put(player.getUuid(), playerHands);
            }
        });
    }

    private static int isCheckedItem(Item item) {
        for (int i = 0; i < ITEMS_TO_TRACK.length; i++) {
            Item staff = ITEMS_TO_TRACK[i];
            if (item == staff) {
                return i;
            }
        }
        return -1;
    }

    private static void onItemEquipped(ServerPlayerEntity player, Item item) {
        if (item instanceof PlanksStaffItem planksStaffItem) {
            planksStaffItem.generatePreview(player);
        } else {
            LapisLazuliStaffItem.levitating(player);
        }
    }

    private static void whileHoldingItem(ServerPlayerEntity player, Item item) {
        if (item instanceof PlanksStaffItem planksStaffItem) {
            planksStaffItem.generatePreview(player);
        } else {
            LapisLazuliStaffItem.levitating(player);
        }
    }

    private static void onItemRemoved(ServerPlayerEntity player, Item item) {
        if (item instanceof PlanksStaffItem planksStaffItem) {
            planksStaffItem.removePreview(player);
        }
    }
}
