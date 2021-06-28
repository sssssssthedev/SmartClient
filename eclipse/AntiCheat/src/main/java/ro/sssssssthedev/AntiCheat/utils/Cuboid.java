package ro.sssssssthedev.AntiCheat.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public final class Cuboid {

    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private double z1;
    private double z2;

    public Cuboid(Location playerLocation) {
        this(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());
    }

    public Cuboid(double n, double n2, double n3) {
        this(n, n, n2, n2, n3, n3);
    }

    public Cuboid add(Cuboid cuboid) {
        this.x1 = this.x1 + cuboid.x1;
        this.x2 = this.x2 + cuboid.x2;
        this.y1 = this.y1 + cuboid.y1;
        this.y2 = this.y2 + cuboid.y2;
        this.z1 = this.z1 + cuboid.z1;
        this.z2 = this.z2 + cuboid.z2;
        return this;
    }

    public Cuboid move(double n, double n2, double n3) {
        this.x1 = this.x1 + n;
        this.x2 = this.x2 + n;
        this.y1 = this.y1 + n2;
        this.y2 = this.y2 + n2;
        this.z1 = this.z1 + n3;
        this.z2 = this.z2 + n3;
        return this;
    }

    public Cuboid shrink(double n, double n2, double n3) {
        this.x1 = this.x1 + n;
        this.x2 = this.x2 - n;
        this.y1 = this.y1 + n2;
        this.y2 = this.y2 - n2;
        this.z1 = this.z1 + n3;
        this.z2 = this.z2 - n3;
        return this;
    }

    public Cuboid expand(double n, double n2, double n3) {
        this.x1 = this.x1 - n;
        this.x2 = this.x2 + n;
        this.y1 = this.y1 - n2;
        this.y2 += n2;
        this.z1 = this.z1 - n3;
        this.z2 = this.z2 + n3;
        return this;
    }

    public List<Block> getBlocks(World world) {
        int n = (int)Math.floor(this.x1);
        int n2 = (int)Math.ceil(this.x2);
        int n3 = (int)Math.floor(this.y1);
        int n4 = (int)Math.ceil(this.y2);
        int n5 = (int)Math.floor(this.z1);
        int n6 = (int)Math.ceil(this.z2);

        ArrayList<Block> list = new ArrayList<>();
        list.add(world.getBlockAt(n, n3, n5));

        for (int i = n; i < n2; ++i) {
            for (int j = n3; j < n4; ++j) {
                for (int k = n5; k < n6; ++k) {
                    list.add(world.getBlockAt(i, j, k));
                }
            }
        }
        return list;
    }

    public boolean phase(Cuboid cuboid) {
        return (this.x1 <= cuboid.x1 && this.x2 >= cuboid.x2) || (this.z1 <= cuboid.z1 && this.z2 >= cuboid.z2);
    }

    public boolean contains(Location playerLocation) {
        return this.x1 <= playerLocation.getX() && this.x2 >= playerLocation.getX() && this.y1 <= playerLocation.getY() && this.y2 >= playerLocation.getY() && this.z1 <= playerLocation.getZ() && this.z2 >= playerLocation.getZ();
    }

    public boolean containsXZ(double n, double n2) {
        return this.x1 <= n && this.x2 >= n && this.z1 <= n2 && this.z2 >= n2;
    }

    public Cuboid combine(Cuboid cuboid) {
        return new Cuboid(Math.min(this.x1, cuboid.x1), Math.max(this.x2, cuboid.x2), Math.min(this.y1, cuboid.y1), Math.max(this.y2, cuboid.y2), Math.min(this.z1, cuboid.z1), Math.max(this.z2, cuboid.z2));
    }

    public static boolean checkBlocks(Collection<Block> collection, Predicate<Material> predicate) {
        return collection.stream().allMatch(block -> predicate.test(block.getType()));
    }

    public boolean checkBlocks(World world, Predicate<Material> predicate) {
        return checkBlocks(this.getBlocks(world), predicate);
    }

    public int count(final World world, Material material) {
        return (int) this.getBlocks(world).stream().filter(mat -> mat.getType() == material).count();
    }

    public double cX() {
        return (this.x1 + this.x2) * 0.5;
    }

    public double cY() {
        return (this.y1 + this.y2) * 0.5;
    }

    public double cZ() {
        return (this.z1 + this.z2) * 0.5;
    }

    public Cuboid(double x1, double x2, double y1, double y2, double z1, double z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
    }

    public double getX1() {
        return this.x1;
    }

    public double getX2() {
        return this.x2;
    }

    public double getY1() {
        return this.y1;
    }

    public double getY2() {
        return this.y2;
    }

    public double getZ1() {
        return this.z1;
    }

    public double getZ2() {
        return this.z2;
    }

    public void setX1(double n) {
        this.x1 = n;
    }

    public void setX2(double n) {
        this.x2 = n;
    }

    public void setY1(double n) {
        this.y1 = n;
    }

    public void setY2(double n) {
        this.y2 = n;
    }

    public void setZ1(double n) {
        this.z1 = n;
    }

    public void setZ2(double n) {
        this.z2 = n;
    }
}
