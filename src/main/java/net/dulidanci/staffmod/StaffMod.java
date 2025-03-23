package net.dulidanci.staffmod;

import net.dulidanci.staffmod.block.ModBlocks;
import net.dulidanci.staffmod.entity.ModEntities;
import net.dulidanci.staffmod.entity.custom.TrackedAnvilEntity;
import net.dulidanci.staffmod.item.ModItemGroups;
import net.dulidanci.staffmod.item.ModItems;
import net.dulidanci.staffmod.util.PlayerItemTracker;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
		ModEntities.registerModEntities();

		PlayerItemTracker.register();

		// Event register for attacking
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			// Your custom logic here
			if (!world.isClient) {
				TrackedAnvilEntity.setTargetForAnvils(entity);

				if (player.getMainHandStack().isOf(ModItems.MAGMA_STAFF)) {
					entity.setOnFireFor(8);
				}
				if (player.getMainHandStack().isOf(ModItems.LAPIS_LAZULI_STAFF)) {
					if (entity instanceof LivingEntity livingEntity) {
						livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 600, 0));
					}
				}
			}
			return ActionResult.PASS; // Or CONSUME to cancel further processing
		});
	}
}