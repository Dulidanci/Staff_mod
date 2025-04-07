package net.dulidanci.staffmod.item.custom;

import net.dulidanci.staffmod.util.EntityTimerManager;
import net.dulidanci.staffmod.util.ModTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TargetStaffItem extends StaffItem{
    public TargetStaffItem(Settings settings, int level) {
        super(settings, level);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }
        ServerWorld serverWorld = (ServerWorld) world;
        for (Entity entity : world.getEntitiesByType(
                TypeFilter.instanceOf(LivingEntity.class),
                new Box(new Vec3d(player.getX() - 32, player.getY() - 32, player.getZ() - 32),
                        new Vec3d(player.getX() + 32, player.getY() + 32, player.getZ() + 32)),
                entity -> entity.getBlockPos().isWithinDistance(player.getBlockPos(), 32)
        )) {
            if (entity instanceof LivingEntity living) {
                Scoreboard scoreboard = serverWorld.getScoreboard();
                Team originalTeam = scoreboard.getTeam(living.getNameForScoreboard());

                if (originalTeam == null) {
                    if (living.getType().isIn(ModTags.RED_GLOW)) {
                        String teamName = "red_glow";
                        Team team = scoreboard.getTeam(teamName);

                        if (team == null) {
                            team = scoreboard.addTeam(teamName);
                            team.setColor(Formatting.DARK_RED);
                        }
                        scoreboard.addScoreHolderToTeam(living.getNameForScoreboard(), team);

                    } else if (living.getType().isIn(ModTags.GREEN_GLOW)) {
                        String teamName = "green_glow";
                        Team team = scoreboard.getTeam(teamName);

                        if (team == null) {
                            team = scoreboard.addTeam(teamName);
                            team.setColor(Formatting.DARK_GREEN);
                        }
                        scoreboard.addScoreHolderToTeam(living.getNameForScoreboard(), team);
                    }
                    EntityTimerManager.startEntityTimer(living, 310, 2);
                }
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 300, 0, false, true));
            }
        }
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.PLAYERS, 1.0f, 1.0f);

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    public static void resetTeam(LivingEntity entity, World world) {
        Scoreboard scoreboard = ((ServerWorld) world).getScoreboard();
        if (scoreboard != null && entity.getScoreboardTeam() != null) {
            scoreboard.removeScoreHolderFromTeam(entity.getNameForScoreboard(), entity.getScoreboardTeam());
        }
    }
}
