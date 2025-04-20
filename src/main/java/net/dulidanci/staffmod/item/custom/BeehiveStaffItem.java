package net.dulidanci.staffmod.item.custom;

import net.dulidanci.staffmod.entity.ModEntities;
import net.dulidanci.staffmod.entity.custom.LoyalBeeEntity;
import net.dulidanci.staffmod.util.ManaSupplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BeehiveStaffItem extends StaffItem{
    public static final int mana = 1;

    public BeehiveStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient()) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }

        if (ManaSupplier.manaCheck(player, mana)) {
            LoyalBeeEntity bee = new LoyalBeeEntity(ModEntities.LOYAL_BEE, world);
            bee.setOwner(player);
            bee.refreshPositionAndAngles(player.getBlockPos(), player.getYaw(), 0);
            world.spawnEntity(bee);

            return TypedActionResult.success(player.getStackInHand(hand));
        }
        return TypedActionResult.fail(player.getStackInHand(hand));
    }
}
