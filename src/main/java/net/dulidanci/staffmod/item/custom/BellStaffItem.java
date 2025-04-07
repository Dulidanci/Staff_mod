package net.dulidanci.staffmod.item.custom;

import net.dulidanci.staffmod.util.EntityTimerManager;
import net.dulidanci.staffmod.util.MobUtilities;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class BellStaffItem extends StaffItem{
    public BellStaffItem(Settings settings, int level) {
        super(settings, level);
    }

    public static void onHit(MobEntity mobEntity, World world) {
        world.playSound(null, mobEntity.getBlockPos(), SoundEvents.BLOCK_ANVIL_PLACE,
                SoundCategory.PLAYERS, 2.0f, 1.0f);
        world.playSound(null, mobEntity.getBlockPos(), SoundEvents.BLOCK_BELL_USE,
                SoundCategory.PLAYERS, 2.0f, 1.0f);
        world.playSound(null, mobEntity.getBlockPos(), SoundEvents.BLOCK_BELL_USE,
                SoundCategory.PLAYERS, 2.0f, 1.0f);
        MobUtilities.setNoAI(mobEntity, true);
        EntityTimerManager.startEntityTimer(mobEntity, 400, 0);

    }
}
