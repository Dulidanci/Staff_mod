package net.dulidanci.staffmod.util;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.*;

public class StaffPairingTable {
    private static final Map<Item, Item> table = new HashMap<>(Map.ofEntries(
            Map.entry(Items.AIR, ModItems.EMPTY_STAFF),
            Map.entry(Items.NETHERRACK, ModItems.NETHERRACK_STAFF),
            Map.entry(Items.END_STONE, ModItems.ENDSTONE_STAFF),
            Map.entry(Items.TNT, ModItems.TNT_STAFF),
            Map.entry(Items.ANVIL, ModItems.ANVIL_STAFF),
            Map.entry(Items.MAGMA_BLOCK, ModItems.MAGMA_BLOCK_STAFF),

            Map.entry(Items.OAK_PLANKS, ModItems.OAK_PLANKS_STAFF),
            Map.entry(Items.SPRUCE_PLANKS, ModItems.SPRUCE_PLANKS_STAFF),
            Map.entry(Items.BIRCH_PLANKS, ModItems.BIRCH_PLANKS_STAFF),
            Map.entry(Items.JUNGLE_PLANKS, ModItems.JUNGLE_PLANKS_STAFF),
            Map.entry(Items.ACACIA_PLANKS, ModItems.ACACIA_PLANKS_STAFF),
            Map.entry(Items.DARK_OAK_PLANKS, ModItems.DARK_OAK_PLANKS_STAFF),
            Map.entry(Items.MANGROVE_PLANKS, ModItems.MANGROVE_PLANKS_STAFF),
            Map.entry(Items.CHERRY_PLANKS, ModItems.CHERRY_PLANKS_STAFF),
            Map.entry(Items.BAMBOO_PLANKS, ModItems.BAMBOO_PLANKS_STAFF),
            Map.entry(Items.CRIMSON_PLANKS, ModItems.CRIMSON_PLANKS_STAFF),
            Map.entry(Items.WARPED_PLANKS, ModItems.WARPED_PLANKS_STAFF),

            Map.entry(Items.GLOWSTONE, ModItems.GLOWSTONE_STAFF),
            Map.entry(Items.BONE_BLOCK, ModItems.BONE_BLOCK_STAFF),
            Map.entry(Items.BEEHIVE, ModItems.BEEHIVE_STAFF),
            Map.entry(Items.LAPIS_BLOCK, ModItems.LAPIS_LAZULI_STAFF),
            Map.entry(Items.BELL, ModItems.BELL_STAFF),
            Map.entry(Items.TARGET, ModItems.TARGET_STAFF)
    ));

    public static void register() {
        StaffMod.LOGGER.info("Registering StaffPairingTable for " + StaffMod.MOD_ID);
    }

    public static Item getStaffCorrespondingToBlock(Item core) {
        for (Map.Entry<Item, Item> entry : table.entrySet()) {
            if (entry.getKey() == core) {
                return entry.getValue();
            }
        }
        return ModItems.EMPTY_STAFF;
    }

    public static ItemStack getCoreCorrespondingToStaff(Item staff) {
        for (Map.Entry<Item, Item> entry : table.entrySet()) {
            if (entry.getValue() == staff) {
                return new ItemStack(entry.getKey());
            }
        }
        return ItemStack.EMPTY;
    }
}
