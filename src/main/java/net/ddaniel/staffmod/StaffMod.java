package net.ddaniel.staffmod;

import net.ddaniel.staffmod.block.ModBlocks;
import net.ddaniel.staffmod.entity.custom.TrackedAnvilEntity;
import net.ddaniel.staffmod.item.ModItemGroups;
import net.ddaniel.staffmod.item.ModItems;
import net.ddaniel.staffmod.util.PlayerItemTracker;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaffMod implements ModInitializer {
	public static final String MOD_ID = "staffmod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		PlayerItemTracker.register();

		// Event register for attacking
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			// Your custom logic here
			if (!world.isClient) {
				TrackedAnvilEntity.setTargetForAnvils(entity);

				if (player.getMainHandStack().isOf(ModItems.MAGMA_STAFF)) {
					entity.setOnFireFor(8);
				}
			}
			return ActionResult.PASS; // Or CONSUME to cancel further processing
		});
	}
}