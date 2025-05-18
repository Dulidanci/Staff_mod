package net.dulidanci.staffmod.block;

import net.dulidanci.staffmod.StaffMod;
import net.dulidanci.staffmod.block.entity.StaffUpgradeStationBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<StaffUpgradeStationBlockEntity> STAFF_UPGRADE_STATION_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(StaffMod.MOD_ID, "staff_upgrade_station_be"),
                    FabricBlockEntityTypeBuilder.create(StaffUpgradeStationBlockEntity::new,
                            ModBlocks.STAFF_UPGRADE_STATION).build());

    public static void registeringBlockEntities() {
        StaffMod.LOGGER.info("Registering Block Entities for " + StaffMod.MOD_ID);
    }
}
