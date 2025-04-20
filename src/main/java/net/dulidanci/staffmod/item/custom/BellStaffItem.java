package net.dulidanci.staffmod.item.custom;

import net.dulidanci.staffmod.util.EntityTimerManager;
import net.dulidanci.staffmod.util.ManaSupplier;
import net.dulidanci.staffmod.util.MobUtilities;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class BellStaffItem extends StaffItem{
    public static final int mana = 5;

    public BellStaffItem(Settings settings) {
        super(settings);
    }

    public static void onHit(MobEntity mobEntity, World world, PlayerEntity player) {
        if (ManaSupplier.manaCheck(player, mana)) {
            world.playSound(null, mobEntity.getBlockPos(), SoundEvents.BLOCK_ANVIL_PLACE,
                    SoundCategory.PLAYERS, 2.0f, 1.0f);
            world.playSound(null, mobEntity.getBlockPos(), SoundEvents.BLOCK_BELL_USE,
                    SoundCategory.PLAYERS, 2.0f, 1.0f);
            MobUtilities.setNoAI(mobEntity, true);
            EntityTimerManager.startEntityTimer(mobEntity, 400, 0);
        }
    }
}
