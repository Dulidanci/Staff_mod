package net.dulidanci.staffmod.item;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item REGULAR_STAFF = registerItem("regular_staff",
            new StaffItem(new FabricItemSettings().maxCount(1), 0));

    public static final Item NETHERRACK_STAFF = registerItem("netherrack_staff",
            new NetherrackStaffItem(new FabricItemSettings().maxCount(1), 0));

    public static final Item ENDSTONE_STAFF = registerItem("endstone_staff",
            new EndstoneStaffItem(new FabricItemSettings().maxCount(1), 0));

    public static final Item TNT_STAFF = registerItem("tnt_staff",
            new TntStaffItem(new FabricItemSettings().maxCount(1), 0));

    public static final Item ANVIL_STAFF = registerItem("anvil_staff",
            new AnvilStaffItem(new FabricItemSettings().maxCount(1), 0));

    public static final Item MAGMA_STAFF = registerItem("magma_staff",
            new MagmaStaffItem(new FabricItemSettings().maxCount(1), 0));

    public static final Item ACACIA_PLANKS_STAFF = registerItem("acacia_planks_staff",
            new PlanksStaffItem(new FabricItemSettings().maxCount(1), 0, Blocks.ACACIA_PLANKS));

    public static final Item BAMBOO_PLANKS_STAFF = registerItem("bamboo_planks_staff",
            new PlanksStaffItem(new FabricItemSettings().maxCount(1), 0, Blocks.BAMBOO_PLANKS));

    public static final Item BIRCH_PLANKS_STAFF = registerItem("birch_planks_staff",
            new PlanksStaffItem(new FabricItemSettings().maxCount(1), 0, Blocks.BIRCH_PLANKS));

    public static final Item CHERRY_PLANKS_STAFF = registerItem("cherry_planks_staff",
            new PlanksStaffItem(new FabricItemSettings().maxCount(1), 0, Blocks.CHERRY_PLANKS));

    public static final Item CRIMSON_PLANKS_STAFF = registerItem("crimson_planks_staff",
            new PlanksStaffItem(new FabricItemSettings().maxCount(1), 0, Blocks.CRIMSON_PLANKS));

    public static final Item DARK_OAK_PLANKS_STAFF = registerItem("dark_oak_planks_staff",
            new PlanksStaffItem(new FabricItemSettings().maxCount(1), 0, Blocks.DARK_OAK_PLANKS));

    public static final Item JUNGLE_PLANKS_STAFF = registerItem("jungle_planks_staff",
            new PlanksStaffItem(new FabricItemSettings().maxCount(1), 0, Blocks.JUNGLE_PLANKS));

    public static final Item MANGROVE_PLANKS_STAFF = registerItem("mangrove_planks_staff",
            new PlanksStaffItem(new FabricItemSettings().maxCount(1), 0, Blocks.MANGROVE_PLANKS));

    public static final Item OAK_PLANKS_STAFF = registerItem("oak_planks_staff",
            new PlanksStaffItem(new FabricItemSettings().maxCount(1), 0, Blocks.OAK_PLANKS));

    public static final Item SPRUCE_PLANKS_STAFF = registerItem("spruce_planks_staff",
            new PlanksStaffItem(new FabricItemSettings().maxCount(1), 0, Blocks.SPRUCE_PLANKS));

    public static final Item WARPED_PLANKS_STAFF = registerItem("warped_planks_staff",
            new PlanksStaffItem(new FabricItemSettings().maxCount(1), 0, Blocks.WARPED_PLANKS));

    public static final Item GLOWSTONE_STAFF = registerItem("glowstone_staff",
            new GlowstoneStaffItem(new FabricItemSettings().maxCount(1), 0));

    public static final Item LAPIS_LAZULI_STAFF = registerItem("lapis_lazuli_staff",
            new LapisLazuliStaffItem(new FabricItemSettings().maxCount(1), 0));

    public static final Item BONE_BLOCK_STAFF = registerItem("bone_block_staff",
            new BoneBlockStaffItem(new FabricItemSettings().maxCount(1), 0));

    public static final Item BEEHIVE_STAFF = registerItem("beehive_staff",
            new BeehiveStaffItem(new FabricItemSettings().maxCount(1), 0));

    public static final Item BELL_STAFF = registerItem("bell_staff",
            new BellStaffItem(new FabricItemSettings().maxCount(1).maxDamage(6), 0));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(StaffMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        StaffMod.LOGGER.info("Registering ModItems for " + StaffMod.MOD_ID);
    }
}
