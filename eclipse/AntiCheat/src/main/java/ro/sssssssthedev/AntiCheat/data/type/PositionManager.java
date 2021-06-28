package ro.sssssssthedev.AntiCheat.data.type;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.*;
import ro.sssssssthedev.AntiCheat.data.Observable;
import ro.sssssssthedev.AntiCheat.utils.BlockUtil;
import ro.sssssssthedev.AntiCheat.utils.Cuboid;

@Getter
public final class PositionManager {
    // We're using observables for better performance and also for thread control
    private Observable<Boolean> touchingAir = new Observable<>(false);
    private Observable<Boolean> touchingClimbable = new Observable<>(false);
    private Observable<Boolean> touchingLiquid = new Observable<>(false);
    private Observable<Boolean> touchingHalfBlocks = new Observable<>(false);
    private Observable<Boolean> touchingIllegalBlocks = new Observable<>(false);
    private Observable<Boolean> touchingFence = new Observable<>(false);
    private Observable<Boolean> touchingDoor = new Observable<>(false);
    private Observable<Boolean> belowBlocks = new Observable<>(false);
    private Observable<Boolean> touchingGround = new Observable<>(false);
    private Observable<Boolean> touchingSlab = new Observable<>(false);
    private Observable<Boolean> touchingSlime = new Observable<>(false);
    private Observable<Boolean> touchingStair = new Observable<>(false);
    private Observable<Boolean> touchingIce = new Observable<>(false);
    private Observable<Boolean> touchingWeb = new Observable<>(false);
    private Observable<Boolean> touchingClimableWaterChest = new Observable<>(false);

    public void updatePositionFlags(final Location to) {
        final Cuboid cuboid = new Cuboid(to).expand(0.5, 0.07, 0.5).move(0.0, -0.55, 0.0);
        final Cuboid oldCuboid = new Cuboid(to).expand(0.07, 0.07, 0.07).move(0.0, -0.55, 0.0);

        this.touchingAir.set(false);
        this.touchingClimbable.set(false);
        this.touchingLiquid.set(false);
        this.touchingHalfBlocks.set(false);
        this.touchingIllegalBlocks.set(false);
        this.touchingFence.set(false);
        this.touchingDoor.set(false);
        this.touchingGround.set(false);

        // Running on a diff thread to minimize load
        final boolean touchingAir = cuboid.checkBlocks(to.getWorld(), material -> material == Material.AIR);
        final boolean touchingClimbable = cuboid.checkBlocks(to.getWorld(), material -> material == Material.VINE || material == Material.LADDER);
        final boolean touchingLiquid = cuboid.checkBlocks(to.getWorld(), material -> material == Material.LAVA || material == Material.WATER || material == Material.STATIONARY_LAVA || material == Material.STATIONARY_WATER);
        final boolean touchingHalfBlocks = oldCuboid.checkBlocks(to.getWorld(), material -> material.getData() == Stairs.class || material.getData() == Step.class || material.getData() == WoodenStep.class);
        final boolean touchingIllegalBlocks = cuboid.checkBlocks(to.getWorld(), material -> material == Material.DAYLIGHT_DETECTOR || material == Material.DAYLIGHT_DETECTOR_INVERTED || material.getData() == Skull.class || material.getData() == FlowerPot.class || material == Material.CARPET || material == Material.CAULDRON || material == Material.WATER_LILY || material == Material.WEB || material.name().equals("SLIME_BLOCK"));
        final boolean touchingFence = cuboid.checkBlocks(to.getWorld(), material -> material == Material.FENCE || material == Material.FENCE_GATE);
        final boolean touchingDoor = cuboid.checkBlocks(to.getWorld(), material -> material.getData() == Gate.class);
        final boolean belowBlocks = BlockUtil.isOnGround(to, -2);
        final boolean touchingGround = BlockUtil.isOnGround(to, 1) || BlockUtil.isOnGround(to, 2);
        final boolean touchingSlab = oldCuboid.checkBlocks(to.getWorld(), material -> material == Material.DOUBLE_STONE_SLAB2 || material == Material.STONE_SLAB2);
        final boolean touchingSlime = oldCuboid.checkBlocks(to.getWorld(), material -> material == Material.SLIME_BLOCK);
        final boolean touchingStair = oldCuboid.checkBlocks(to.getWorld(), material -> material.getData() == Stairs.class);
        final boolean touchingIce = oldCuboid.checkBlocks(to.getWorld(), material -> material == Material.ICE || material == Material.PACKED_ICE);
        final boolean touchingWeb = cuboid.checkBlocks(to.getWorld(), material -> material == Material.WEB);

        this.touchingWeb.set(touchingWeb);
        this.touchingIce.set(touchingIce);
        this.touchingStair.set(touchingStair);
        this.touchingSlime.set(touchingSlime);
        this.touchingSlab.set(touchingSlab);
        this.touchingGround.set(touchingGround);
        this.touchingAir.set(touchingAir);
        this.touchingLiquid.set(touchingLiquid);
        this.touchingClimbable.set(touchingClimbable);
        this.touchingHalfBlocks.set(touchingHalfBlocks);
        this.touchingIllegalBlocks.set(touchingIllegalBlocks);
        this.touchingFence.set(touchingFence);
        this.touchingDoor.set(touchingDoor);
        this.belowBlocks.set(belowBlocks);
    }
}

