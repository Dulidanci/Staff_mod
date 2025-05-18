package net.dulidanci.staffmod.item;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup STAFF_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(StaffMod.MOD_ID, "staff"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.staff"))
                   .icon(() -> new ItemStack(ModItems.EMPTY_STAFF)).entries((displayContext, entries) -> {
                       entries.add(ModItems.EMPTY_STAFF);
                       entries.add(ModItems.NETHERRACK_STAFF);
                       entries.add(ModItems.ENDSTONE_STAFF);
                       entries.add(ModItems.TNT_STAFF);
                       entries.add(ModItems.ANVIL_STAFF);
                       entries.add(ModItems.MAGMA_BLOCK_STAFF);

                       entries.add(ModItems.OAK_PLANKS_STAFF);
                       entries.add(ModItems.SPRUCE_PLANKS_STAFF);
                       entries.add(ModItems.BIRCH_PLANKS_STAFF);
                       entries.add(ModItems.JUNGLE_PLANKS_STAFF);
                       entries.add(ModItems.ACACIA_PLANKS_STAFF);
                       entries.add(ModItems.DARK_OAK_PLANKS_STAFF);
                       entries.add(ModItems.MANGROVE_PLANKS_STAFF);
                       entries.add(ModItems.CHERRY_PLANKS_STAFF);
                       entries.add(ModItems.BAMBOO_PLANKS_STAFF);
                       entries.add(ModItems.CRIMSON_PLANKS_STAFF);
                       entries.add(ModItems.WARPED_PLANKS_STAFF);

                       entries.add(ModItems.GLOWSTONE_STAFF);
                       entries.add(ModItems.BONE_BLOCK_STAFF);
                       entries.add(ModItems.BEEHIVE_STAFF);
                       entries.add(ModItems.LAPIS_LAZULI_STAFF);
                       entries.add(ModItems.BELL_STAFF);
                       entries.add(ModItems.TARGET_STAFF);

                       entries.add(ModBlocks.BLUEPRINT_PLANKS);
                       entries.add(ModBlocks.FADING_LIGHT_BLOCK);
                       entries.add(ModBlocks.STAFF_UPGRADE_STATION);

                   }).build());

    public static void registerItemGroups() {
        StaffMod.LOGGER.info("Registering ModItemGroups for " + StaffMod.MOD_ID);
    }
}
