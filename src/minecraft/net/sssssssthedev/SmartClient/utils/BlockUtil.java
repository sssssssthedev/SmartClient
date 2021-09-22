package net.sssssssthedev.SmartClient.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockUtil {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
        EntityEgg var4 = new EntityEgg(mc.theWorld);
        var4.posX = (double) var0 + 0.5D;
        var4.posY = (double) var1 + 0.5D;
        var4.posZ = (double) var2 + 0.5D;
        var4.posX += (double) var3.getDirectionVec().getX() * 0.25D;
        var4.posY += (double) var3.getDirectionVec().getY() * 0.25D;
        var4.posZ += (double) var3.getDirectionVec().getZ() * 0.25D;
        return getDirectionToEntity(var4);
    }

    private static float[] getDirectionToEntity(Entity var0) {
        return new float[]{getYaw(var0) + mc.thePlayer.rotationYaw, getPitch(var0) + mc.thePlayer.rotationPitch};
    }

    public static float[] getRotationNeededForBlock(EntityPlayer paramEntityPlayer, BlockPos pos) {
        double d1 = pos.getX() - paramEntityPlayer.posX;
        double d2 = pos.getY() + 0.5 - (paramEntityPlayer.posY + paramEntityPlayer.getEyeHeight());
        double d3 = pos.getZ() - paramEntityPlayer.posZ;
        double d4 = Math.sqrt(d1 * d1 + d3 * d3);
        float f1 = (float) (Math.atan2(d3, d1) * 180.0D / Math.PI) - 90.0F;
        float f2 = (float) -(Math.atan2(d2, d4) * 180.0D / Math.PI);
        return new float[]{f1, f2};
    }

    public static float getYaw(Entity var0) {
        double var1 = var0.posX - mc.thePlayer.posX;
        double var3 = var0.posZ - mc.thePlayer.posZ;
        double var5;

        if (var3 < 0.0D && var1 < 0.0D) {
            var5 = 90.0D + Math.toDegrees(Math.atan(var3 / var1));
        } else if (var3 < 0.0D && var1 > 0.0D) {
            var5 = -90.0D + Math.toDegrees(Math.atan(var3 / var1));
        } else {
            var5 = Math.toDegrees(-Math.atan(var1 / var3));
        }

        return MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float) var5));
    }

    public static float getPitch(Entity var0) {
        double var1 = var0.posX - mc.thePlayer.posX;
        double var3 = var0.posZ - mc.thePlayer.posZ;
        double var5 = var0.posY - 1.6D + (double) var0.getEyeHeight() - mc.thePlayer.posY;
        double var7 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
        double var9 = -Math.toDegrees(Math.atan(var5 / var7));
        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float) var9);
    }

    public static BlockPos[] getBlocksAround(EntityPlayer pl, int r, boolean mbv) {
        final long px = (long)pl.posX;
        final long py = (long)pl.posY;
        final long pz = (long)pl.posZ;
        final long ex = px + r;
        final long ey = py + r;
        final long ez = pz + r;
        final long sx = px - r;
        final long sy = py - r;
        final long sz = pz - r;
        List<BlockPos> b = new ArrayList<>();
        for(long x = sx; x < ex; x++)
            for(long y = sy; y < ey; y++)
                for(long z = sz; z < ez; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if(!mc.theWorld.isAirBlock(pos) && (!mbv || isBlockVisible(pos)))
                        b.add(pos);
                }
        return b.toArray(new BlockPos[0]);
    }
    static boolean isBlockVisible(BlockPos pos) {
        World w = mc.theWorld;
        EntityPlayerSP p = mc.thePlayer;
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        double d = p.posX;
        double e = p.posY + p.getEyeHeight();
        double f = p.posZ;
        boolean b = w.isAirBlock(new BlockPos(i - 1, j, k));
        boolean c = w.isAirBlock(new BlockPos(i + 1, j, k));
        boolean g = w.isAirBlock(new BlockPos(i, j, k - 1));
        boolean h = w.isAirBlock(new BlockPos(i, j, k + 1));
        boolean l = w.isAirBlock(new BlockPos(i, j + 1, k));
        boolean m = w.isAirBlock(new BlockPos(i, j - 1, k));
        return (l && e > j) || (m && e < j) || (b && d < i) || (c && d > i) || (g && f < k) || (h && f > k);
    }

    public static void breakBlock(BlockPos block) {
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, block, EnumFacing.UP));
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, block, EnumFacing.UP));
    }

    static void applyRotations(float[] rotations) {
        mc.thePlayer.rotationYaw = rotations[0];
        mc.thePlayer.rotationPitch = rotations[1] + 8.1f;
    }

    static float[] getRotationsNeeded(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        double x = Random.randDouble(minX, maxX) - mc.thePlayer.posX;
        double y = Random.randDouble(minY, maxY) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double z = Random.randDouble(minZ, maxZ) - mc.thePlayer.posZ;
        return new float[] {mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(((float)(Math.atan2(z, x) * 180 / Math.PI) - 90) - mc.thePlayer.rotationYaw),
                mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(((float)-(Math.atan2(y, sqrt((float) (x * x + z * z))) * 180 / Math.PI)) - mc.thePlayer.rotationPitch)};
    }

    public static void faceCoords(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        applyRotations(getRotationsNeeded(minX, minY, minZ, maxX, maxY, maxZ));
    }

    public static void faceBlock(BlockPos p) {
        faceCoords(p.getX(), p.getY(), p.getZ(), p.getX() + 1, p.getY() + 1, p.getZ() + 1);
    }

    // Custom sqrt method
    public static float sqrt(float value)
    {
        return (float)Math.sqrt(value);
    }



}
