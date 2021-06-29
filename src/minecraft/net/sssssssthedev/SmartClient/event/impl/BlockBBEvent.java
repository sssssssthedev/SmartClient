package net.sssssssthedev.SmartClient.event.impl;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.sssssssthedev.SmartClient.event.Event;

import java.util.ArrayList;
import java.util.List;

public class BlockBBEvent extends Event  {
    private BlockPos pos;
    private Block block;
    private AxisAlignedBB aabb;
    private final List<AxisAlignedBB> axisAlignedBBList = new ArrayList<>();

    public BlockBBEvent(BlockPos pos, Block block, AxisAlignedBB aabb) {
        this.pos = pos;
        this.block = block;
        this.aabb = aabb;
    }

    public BlockPos getPos() {
        return pos;
    }
    public void setPos(BlockPos pos) {
        this.pos = pos;
    }
    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
    public AxisAlignedBB getAabb() {
        return aabb;
    }
    public void setAabb(AxisAlignedBB aabb) {
        this.aabb = aabb;
    }
    public List<AxisAlignedBB> getAxisAlignedBBList() {
        return axisAlignedBBList;
    }
}
