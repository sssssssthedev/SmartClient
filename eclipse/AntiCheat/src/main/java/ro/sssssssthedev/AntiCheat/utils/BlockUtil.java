package ro.sssssssthedev.AntiCheat.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class BlockUtil {
    public static List<Material> blocked = new ArrayList<>();

    private static Set<Byte> blockSolidPassSet;
    private static Set<Byte> blockStairsSet;
    private static Set<Byte> blockLiquidsSet;
    private static Set<Byte> blockWebsSet;
    private static Set<Byte> blockIceSet;
    private static Set<Byte> blockCarpetSet;

    static {
        blockSolidPassSet = new HashSet<>();
        blockStairsSet = new HashSet<>();
        blockLiquidsSet = new HashSet<>();
        blockWebsSet = new HashSet<>();
        blockIceSet = new HashSet<>();
        blockCarpetSet = new HashSet<>();

        blockSolidPassSet.add((byte) 0);
        blockSolidPassSet.add((byte) 6);
        blockSolidPassSet.add((byte) 8);
        blockSolidPassSet.add((byte) 9);
        blockSolidPassSet.add((byte) 10);
        blockSolidPassSet.add((byte) 11);
        blockSolidPassSet.add((byte) 27);
        blockSolidPassSet.add((byte) 28);
        blockSolidPassSet.add((byte) 30);
        blockSolidPassSet.add((byte) 31);
        blockSolidPassSet.add((byte) 32);
        blockSolidPassSet.add((byte) 37);
        blockSolidPassSet.add((byte) 38);
        blockSolidPassSet.add((byte) 39);
        blockSolidPassSet.add((byte) 40);
        blockSolidPassSet.add((byte) 50);
        blockSolidPassSet.add((byte) 51);
        blockSolidPassSet.add((byte) 55);
        blockSolidPassSet.add((byte) 59);
        blockSolidPassSet.add((byte) 63);
        blockSolidPassSet.add((byte) 66);
        blockSolidPassSet.add((byte) 68);
        blockSolidPassSet.add((byte) 69);
        blockSolidPassSet.add((byte) 70);
        blockSolidPassSet.add((byte) 72);
        blockSolidPassSet.add((byte) 75);
        blockSolidPassSet.add((byte) 76);
        blockSolidPassSet.add((byte) 77);
        blockSolidPassSet.add((byte) 78);
        blockSolidPassSet.add((byte) 83);
        blockSolidPassSet.add((byte) 90);
        blockSolidPassSet.add((byte) 104);
        blockSolidPassSet.add((byte) 105);
        blockSolidPassSet.add((byte) 115);
        blockSolidPassSet.add((byte) 119);
        blockSolidPassSet.add((byte) (-124));
        blockSolidPassSet.add((byte) (-113));
        blockSolidPassSet.add((byte) (-81));

        blockStairsSet.add((byte) 53);
        blockStairsSet.add((byte) 67);
        blockStairsSet.add((byte) 108);
        blockStairsSet.add((byte) 109);
        blockStairsSet.add((byte) 114);
        blockStairsSet.add((byte) (-128));
        blockStairsSet.add((byte) (-122));
        blockStairsSet.add((byte) (-121));
        blockStairsSet.add((byte) (-120));
        blockStairsSet.add((byte) (-100));
        blockStairsSet.add((byte) (-93));
        blockStairsSet.add((byte) (-92));
        blockStairsSet.add((byte) (-76));
        blockStairsSet.add((byte) 126);
        blockStairsSet.add((byte) (-74));
        blockStairsSet.add((byte) 44);
        blockStairsSet.add((byte) 78);
        blockStairsSet.add((byte) 99);
        blockStairsSet.add((byte) (-112));
        blockStairsSet.add((byte) (-115));
        blockStairsSet.add((byte) (-116));
        blockStairsSet.add((byte) (-105));
        blockStairsSet.add((byte) (-108));
        blockStairsSet.add((byte) 100);

        blockLiquidsSet.add((byte) 8);
        blockLiquidsSet.add((byte) 9);
        blockLiquidsSet.add((byte) 10);
        blockLiquidsSet.add((byte) 11);

        blockWebsSet.add((byte) 30);

        blockIceSet.add((byte) 79);
        blockIceSet.add((byte) (-82));

        blockCarpetSet.add((byte) (-85));

        blocked.add(Material.ACTIVATOR_RAIL);
        blocked.add(Material.ANVIL);
        blocked.add(Material.BED_BLOCK);
        blocked.add(Material.POTATO);
        blocked.add(Material.POTATO_ITEM);
        blocked.add(Material.CARROT);
        blocked.add(Material.CARROT_ITEM);
        blocked.add(Material.BIRCH_WOOD_STAIRS);
        blocked.add(Material.BREWING_STAND);
        blocked.add(Material.BOAT);
        blocked.add(Material.BRICK_STAIRS);
        blocked.add(Material.BROWN_MUSHROOM);
        blocked.add(Material.CAKE_BLOCK);
        blocked.add(Material.CARPET);
        blocked.add(Material.CAULDRON);
        blocked.add(Material.COBBLESTONE_STAIRS);
        blocked.add(Material.COBBLE_WALL);
        blocked.add(Material.DARK_OAK_STAIRS);
        blocked.add(Material.DIODE);
        blocked.add(Material.DIODE_BLOCK_ON);
        blocked.add(Material.DIODE_BLOCK_OFF);
        blocked.add(Material.DEAD_BUSH);
        blocked.add(Material.DETECTOR_RAIL);
        blocked.add(Material.DOUBLE_PLANT);
        blocked.add(Material.DOUBLE_STEP);
        blocked.add(Material.DRAGON_EGG);
        blocked.add(Material.PAINTING);
        blocked.add(Material.FLOWER_POT);
        blocked.add(Material.GOLD_PLATE);
        blocked.add(Material.HOPPER);
        blocked.add(Material.STONE_PLATE);
        blocked.add(Material.IRON_PLATE);
        blocked.add(Material.HUGE_MUSHROOM_1);
        blocked.add(Material.HUGE_MUSHROOM_2);
        blocked.add(Material.IRON_DOOR_BLOCK);
        blocked.add(Material.IRON_DOOR);
        blocked.add(Material.FENCE);
        blocked.add(Material.IRON_FENCE);
        blocked.add(Material.IRON_PLATE);
        blocked.add(Material.ITEM_FRAME);
        blocked.add(Material.JUKEBOX);
        blocked.add(Material.JUNGLE_WOOD_STAIRS);
        blocked.add(Material.LADDER);
        blocked.add(Material.LEVER);
        blocked.add(Material.LONG_GRASS);
        blocked.add(Material.NETHER_FENCE);
        blocked.add(Material.NETHER_STALK);
        blocked.add(Material.NETHER_WARTS);
        blocked.add(Material.MELON_STEM);
        blocked.add(Material.PUMPKIN_STEM);
        blocked.add(Material.QUARTZ_STAIRS);
        blocked.add(Material.RAILS);
        blocked.add(Material.RED_MUSHROOM);
        blocked.add(Material.RED_ROSE);
        blocked.add(Material.SAPLING);
        blocked.add(Material.SEEDS);
        blocked.add(Material.SIGN);
        blocked.add(Material.SIGN_POST);
        blocked.add(Material.SKULL);
        blocked.add(Material.SMOOTH_STAIRS);
        blocked.add(Material.NETHER_BRICK_STAIRS);
        blocked.add(Material.SPRUCE_WOOD_STAIRS);
        blocked.add(Material.STAINED_GLASS_PANE);
        blocked.add(Material.REDSTONE_COMPARATOR);
        blocked.add(Material.REDSTONE_COMPARATOR_OFF);
        blocked.add(Material.REDSTONE_COMPARATOR_ON);
        blocked.add(Material.REDSTONE_LAMP_OFF);
        blocked.add(Material.REDSTONE_LAMP_ON);
        blocked.add(Material.REDSTONE_TORCH_OFF);
        blocked.add(Material.REDSTONE_TORCH_ON);
        blocked.add(Material.REDSTONE_WIRE);
        blocked.add(Material.SANDSTONE_STAIRS);
        blocked.add(Material.STEP);
        blocked.add(Material.ACACIA_STAIRS);
        blocked.add(Material.SUGAR_CANE);
        blocked.add(Material.SUGAR_CANE_BLOCK);
        blocked.add(Material.ENCHANTMENT_TABLE);
        blocked.add(Material.SOUL_SAND);
        blocked.add(Material.TORCH);
        blocked.add(Material.TRAP_DOOR);
        blocked.add(Material.TRIPWIRE);
        blocked.add(Material.TRIPWIRE_HOOK);
        blocked.add(Material.WALL_SIGN);
        blocked.add(Material.VINE);
        blocked.add(Material.WATER_LILY);
        blocked.add(Material.WEB);
        blocked.add(Material.WOOD_DOOR);
        blocked.add(Material.WOOD_DOUBLE_STEP);
        blocked.add(Material.WOOD_PLATE);
        blocked.add(Material.WOOD_STAIRS);
        blocked.add(Material.WOOD_STEP);
        blocked.add(Material.HOPPER);
        blocked.add(Material.WOODEN_DOOR);
        blocked.add(Material.YELLOW_FLOWER);
        blocked.add(Material.LAVA);
        blocked.add(Material.WATER);
        blocked.add(Material.STATIONARY_WATER);
        blocked.add(Material.STATIONARY_LAVA);
        blocked.add(Material.CACTUS);
        blocked.add(Material.CHEST);
        blocked.add(Material.PISTON_BASE);
        blocked.add(Material.PISTON_MOVING_PIECE);
        blocked.add(Material.PISTON_EXTENSION);
        blocked.add(Material.PISTON_STICKY_BASE);
        blocked.add(Material.TRAPPED_CHEST);
        blocked.add(Material.SNOW);
        blocked.add(Material.ENDER_CHEST);
        blocked.add(Material.THIN_GLASS);
        blocked.add(Material.ENDER_PORTAL_FRAME);
    }

    public static boolean isOnStairs(final Location location, final int down) {
        return isUnderBlock(location, blockStairsSet, down);
    }

    public static boolean isOnLiquid(final Location location, final int down) {
        return isUnderBlock(location, blockLiquidsSet, down);
    }

    public static boolean isOnWeb(final Location location, final int down) {
        return isUnderBlock(location, blockWebsSet, down);
    }

    public static boolean isOnIce(final Location location, final int down) {
        return isUnderBlock(location, blockIceSet, down);
    }

    public static boolean isOnCarpet(final Location location, final int down) {
        return isUnderBlock(location, blockCarpetSet, down);
    }

    public static boolean isSlab(final Player player) {
        return blocked.contains(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType());
    }

    public static boolean isBlockFaceAir(final Player player) {
        final Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);

        return block.getType().equals(Material.AIR) && block.getRelative(BlockFace.WEST).getType().equals(Material
                .AIR) && block.getRelative(BlockFace.NORTH).getType().equals(Material.AIR) && block.getRelative
                (BlockFace.EAST).getType().equals(Material.AIR) && block.getRelative(BlockFace.SOUTH).getType()
                .equals(Material.AIR);
    }

    private static boolean isUnderBlock(final Location location, final Set<Byte> itemIDs, final int down) {
        final double posX = location.getX();
        final double posZ = location.getZ();

        final double fracX = (posX % 1.0 > 0.0) ? Math.abs(posX % 1.0) : (1.0 - Math.abs(posX % 1.0));
        final double fracZ = (posZ % 1.0 > 0.0) ? Math.abs(posZ % 1.0) : (1.0 - Math.abs(posZ % 1.0));

        final int blockX = location.getBlockX();
        final int blockY = location.getBlockY() - down;
        final int blockZ = location.getBlockZ();

        final World world = location.getWorld();

        if (itemIDs.contains((byte) world.getBlockAt(blockX, blockY, blockZ).getTypeId())) {
            return true;
        }

        if (fracX < 0.3) {
            if (itemIDs.contains((byte) world.getBlockAt(blockX - 1, blockY, blockZ).getTypeId())) {
                return true;
            }

            if (fracZ < 0.3) {
                if (itemIDs.contains((byte) world.getBlockAt(blockX - 1, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }

                if (itemIDs.contains((byte) world.getBlockAt(blockX, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }

                return itemIDs.contains((byte) world.getBlockAt(blockX + 1, blockY, blockZ - 1).getTypeId());
            } else if (fracZ > 0.7) {
                if (itemIDs.contains((byte) world.getBlockAt(blockX - 1, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }

                if (itemIDs.contains((byte) world.getBlockAt(blockX, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }

                return itemIDs.contains((byte) world.getBlockAt(blockX + 1, blockY, blockZ + 1).getTypeId());
            }
        } else if (fracX > 0.7) {
            if (itemIDs.contains((byte) world.getBlockAt(blockX + 1, blockY, blockZ).getTypeId())) {
                return true;
            }

            if (fracZ < 0.3) {
                if (itemIDs.contains((byte) world.getBlockAt(blockX - 1, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }

                if (itemIDs.contains((byte) world.getBlockAt(blockX, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }

                return itemIDs.contains((byte) world.getBlockAt(blockX + 1, blockY, blockZ - 1).getTypeId());
            } else if (fracZ > 0.7) {
                if (itemIDs.contains((byte) world.getBlockAt(blockX - 1, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }

                if (itemIDs.contains((byte) world.getBlockAt(blockX, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }

                return itemIDs.contains((byte) world.getBlockAt(blockX + 1, blockY, blockZ + 1).getTypeId());
            }
        } else if (fracZ < 0.3) {
            return itemIDs.contains((byte) world.getBlockAt(blockX, blockY, blockZ - 1).getTypeId());
        } else return fracZ > 0.7 && itemIDs.contains((byte) world.getBlockAt(blockX, blockY, blockZ + 1).getTypeId());

        return false;
    }

    public static boolean isOnGround(final Location location, final int down) {
        final double posX = location.getX();
        final double posZ = location.getZ();

        final double fracX = (posX % 1.0 > 0.0) ? Math.abs(posX % 1.0) : (1.0 - Math.abs(posX % 1.0));
        final double fracZ = (posZ % 1.0 > 0.0) ? Math.abs(posZ % 1.0) : (1.0 - Math.abs(posZ % 1.0));

        final int blockX = location.getBlockX();
        final int blockY = location.getBlockY() - down;
        final int blockZ = location.getBlockZ();

        final World world = location.getWorld();

        if (!blockSolidPassSet.contains((byte) world.getBlockAt(blockX, blockY, blockZ).getTypeId())) {
            return true;
        }

        if (fracX < 0.3) {
            if (!blockSolidPassSet.contains((byte) world.getBlockAt(blockX - 1, blockY, blockZ).getTypeId())) {
                return true;
            }

            if (fracZ < 0.3) {
                if (!blockSolidPassSet.contains((byte) world.getBlockAt(blockX - 1, blockY, blockZ - 1)
                        .getTypeId())) {
                    return true;
                }

                if (!blockSolidPassSet.contains((byte) world.getBlockAt(blockX, blockY, blockZ - 1)
                        .getTypeId())) {
                    return true;
                }

                return !blockSolidPassSet.contains((byte) world.getBlockAt(blockX + 1, blockY, blockZ - 1).getTypeId());
            } else if (fracZ > 0.7) {
                if (!blockSolidPassSet.contains((byte) world.getBlockAt(blockX - 1, blockY, blockZ + 1)
                        .getTypeId())) {
                    return true;
                }

                if (!blockSolidPassSet.contains((byte) world.getBlockAt(blockX, blockY, blockZ + 1)
                        .getTypeId())) {
                    return true;
                }

                return !blockSolidPassSet.contains((byte) world.getBlockAt(blockX + 1, blockY, blockZ + 1).getTypeId());
            }
        } else if (fracX > 0.7) {
            if (!blockSolidPassSet.contains((byte) world.getBlockAt(blockX + 1, blockY, blockZ).getTypeId()
            )) {
                return true;
            }

            if (fracZ < 0.3) {
                if (!blockSolidPassSet.contains((byte) world.getBlockAt(blockX - 1, blockY, blockZ - 1)
                        .getTypeId())) {
                    return true;
                }

                if (!blockSolidPassSet.contains((byte) world.getBlockAt(blockX, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }

                return !blockSolidPassSet.contains((byte) world.getBlockAt(blockX + 1, blockY, blockZ - 1).getTypeId());
            } else if (fracZ > 0.7) {
                if (!blockSolidPassSet.contains((byte) world.getBlockAt(blockX - 1, blockY, blockZ + 1)
                        .getTypeId())) {
                    return true;
                }

                if (!blockSolidPassSet.contains((byte) world.getBlockAt(blockX, blockY, blockZ + 1)
                        .getTypeId())) {
                    return true;
                }

                return !blockSolidPassSet.contains((byte) world.getBlockAt(blockX + 1, blockY, blockZ + 1).getTypeId());
            }
        } else if (fracZ < 0.3) {
            return !blockSolidPassSet.contains((byte) world.getBlockAt(blockX, blockY, blockZ - 1).getTypeId());
        } else return fracZ > 0.7 && !blockSolidPassSet.contains((byte) world.getBlockAt(blockX, blockY, blockZ + 1).getTypeId());

        return false;
    }
}

