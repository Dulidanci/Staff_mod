package net.dulidanci.staffmod.util;

import net.dulidanci.staffmod.StaffMod;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static final TagKey<Block> CAN_TELEPORT_INTO =
            TagKey.of(RegistryKeys.BLOCK, new Identifier(StaffMod.MOD_ID, "can_teleport_into"));

    public static final TagKey<Block> REPLACEABLE_BY_BUILDING_PREVIEW =
            TagKey.of(RegistryKeys.BLOCK, new Identifier(StaffMod.MOD_ID, "replaceable_by_building_preview"));

    public static final TagKey<Block> REPLACEABLE_BY_LIGHT =
            TagKey.of(RegistryKeys.BLOCK, new Identifier(StaffMod.MOD_ID, "replaceable_by_light"));

    public static final TagKey<EntityType<?>> GREEN_GLOW =
            TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(StaffMod.MOD_ID, "green_glow"));

    public static final TagKey<EntityType<?>> RED_GLOW =
            TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(StaffMod.MOD_ID, "red_glow"));
}
