package net.ddaniel.staffmod.item.custom;

import net.ddaniel.staffmod.block.ModBlocks;
import net.ddaniel.staffmod.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class PlanksStaffItem extends StaffItem{
    private static BlockPos previousPos;
    private static Direction previousDir;
    private static Direction previousMode;
    private static final Set<BlockPos> previousPlaces = new HashSet<>();
    private final Block blockType;

    public PlanksStaffItem(Settings settings, int level, Block block) {
        super(settings, level);
        this.blockType = block;
    }

    public static void generatePreview(PlayerEntity player) {
        World world = player.getWorld();
        if (!world.isClient) {
            BlockPos newPos = player.getBlockPos();
            Direction newDir = player.getHorizontalFacing();
            Direction newMode = getMode(player.getPitch());
            if (newPos != previousPos || newDir != previousDir || newMode != previousMode) {
                BlockPos playerPosition = player.getBlockPos();
                Set<BlockPos> newPlaces = new HashSet<>();
                Vec3i directionMultiplier = newDir.getVector();
                if (newMode == Direction.DOWN) {                // platform
                    if (directionMultiplier.getX() == 0) {          // Z axis
                        for (int i = -2; i < 3; i++) {
                            for (int j = 0; j < 5; j++) {
                                BlockState original = world.getBlockState(playerPosition.add(i, -1, j * directionMultiplier.getZ()));
                                if (original.isIn(ModTags.REPLACEABLE_BY_BUILDING_PREVIEW)) {
                                    newPlaces.add(playerPosition.add(i, -1, j * directionMultiplier.getZ()));
                                }
                            }
                        }
                    } else {                                        // X axis
                        for (int i = -2; i < 3; i++) {
                            for (int j = 0; j < 5; j++) {
                                BlockState original = world.getBlockState(playerPosition.add(j * directionMultiplier.getX(), -1, i));
                                if (original.isIn(ModTags.REPLACEABLE_BY_BUILDING_PREVIEW)) {
                                    newPlaces.add(playerPosition.add(j * directionMultiplier.getX(), -1, i));
                                }
                            }
                        }
                    }
                } else if (newMode == Direction.NORTH) {        // ramp
                    if (directionMultiplier.getX() == 0) {          // Z axis
                        for (int i = -2; i < 3; i++) {
                            for (int j = 0; j < 5; j++) {
                                BlockState original = world.getBlockState(playerPosition.add(i, j - 1, j * directionMultiplier.getZ()));
                                if (original.isIn(ModTags.REPLACEABLE_BY_BUILDING_PREVIEW)) {
                                    newPlaces.add(playerPosition.add(i, j - 1, j * directionMultiplier.getZ()));
                                }
                            }
                        }
                    } else {                                        // X axis
                        for (int i = -2; i < 3; i++) {
                            for (int j = 0; j < 5; j++) {
                                BlockState original = world.getBlockState(playerPosition.add(j * directionMultiplier.getX(), j - 1, i));
                                if (original.isIn(ModTags.REPLACEABLE_BY_BUILDING_PREVIEW)) {
                                    newPlaces.add(playerPosition.add(j * directionMultiplier.getX(), j - 1, i));
                                }
                            }
                        }
                    }
                } else {                                        // wall
                    if (directionMultiplier.getX() == 0) {          // Z axis
                        for (int i = -2; i < 3; i++) {
                            for (int j = 0; j < 4; j++) {
                                BlockState original = world.getBlockState(playerPosition.add(i, j, 2 * directionMultiplier.getZ()));
                                if (original.isIn(ModTags.REPLACEABLE_BY_BUILDING_PREVIEW)) {
                                    newPlaces.add(playerPosition.add(i, j, 2 * directionMultiplier.getZ()));
                                }
                            }
                        }
                    } else {                                        // X axis
                        for (int i = -2; i < 3; i++) {
                            for (int j = 0; j < 4; j++) {
                                BlockState original = world.getBlockState(playerPosition.add(2 * directionMultiplier.getX(), j, i));
                                if (original.isIn(ModTags.REPLACEABLE_BY_BUILDING_PREVIEW)) {
                                    newPlaces.add(playerPosition.add(2 * directionMultiplier.getX(), j, i));
                                }
                            }
                        }
                    }
                }
                for (BlockPos setToDefault : previousPlaces) {
                    world.setBlockState(setToDefault, Blocks.AIR.getDefaultState());
                }
                previousPlaces.clear();
                for (BlockPos setToPreviewMode : newPlaces) {
                    world.setBlockState(setToPreviewMode, ModBlocks.BLUEPRINT_PLANKS.getDefaultState());
                    previousPlaces.add(setToPreviewMode);
                }
                previousPos = playerPosition;
                previousDir = newDir;
                previousMode = newMode;
            }
        }
    }

    private static Direction getMode(float pitch) {
        if (pitch > 30) return Direction.DOWN;
        if (pitch >= -30) return Direction.NORTH;
        return Direction.UP;
    }

    public static void removePreview(PlayerEntity player) {
        World world = player.getWorld();
        for (BlockPos setToDefault : previousPlaces) {
            world.setBlockState(setToDefault, Blocks.AIR.getDefaultState());
        }
        previousPlaces.clear();
        previousPos = new BlockPos(0, -100, 0);
        previousDir = Direction.UP;
        previousMode = Direction.SOUTH;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }
        int blocksNeeded = previousPlaces.size();
        if (hasEnoughItems(player, this.blockType.asItem(), blocksNeeded)) {
            for (BlockPos setToPlanks : previousPlaces) {
                world.setBlockState(setToPlanks, this.blockType.getDefaultState());
            }
            previousPlaces.clear();
            removeItems(player, this.blockType.asItem(), blocksNeeded);
            return TypedActionResult.success(player.getStackInHand(hand));
        }
        player.sendMessage(Text.literal("Not enough planks to build!"));
        return TypedActionResult.fail(player.getStackInHand(hand));
    }

    private boolean hasEnoughItems(PlayerEntity player, Item item, int requiredAmount) {
        int count = 0;
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.getItem() == item) {
                count += stack.getCount();
                if (count >= requiredAmount) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeItems(PlayerEntity player, Item item, int amountToRemove) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.getItem() == item) {
                int stackSize = stack.getCount();

                if (stackSize > amountToRemove) {
                    stack.decrement(amountToRemove); // Remove partial stack
                    return;
                } else {
                    amountToRemove -= stackSize; // Remove entire stack
                    player.getInventory().setStack(i, ItemStack.EMPTY);
                }

                if (amountToRemove <= 0) {
                    return; // Finished removing items
                }
            }
        }
    }
}