package net.sssssssthedev.SmartClient.module.player;

import com.google.common.collect.Multimap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.sssssssthedev.SmartClient.Main;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.PreMotionUpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import net.sssssssthedev.SmartClient.settings.Setting;
import net.sssssssthedev.SmartClient.utils.TimeHelper;
import org.lwjgl.input.Keyboard;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class InvCleaner extends Module {
    public TimeHelper timer = new TimeHelper();

    public InvCleaner() {
        super("InvCleaner", Keyboard.KEY_0, Category.PLAYER);
    }

    public void setup() {
        Main.instance.settingsManager.rSetting(new Setting("OpenInv", this, true));
    }

    @EventTarget
    public void onPre(PreMotionUpdateEvent event) {
        if (this.mc.thePlayer.isUsingItem())
            return;
        if (!Main.instance.settingsManager.getSettingByName("InvOpenInventory").getValBoolean() || (this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory && Main.instance.settingsManager.getSettingByName("InvOpenInventory").getValBoolean()))
            if (this.timer.hasTimePassed(135L)) {
                CopyOnWriteArrayList<Integer> uselessItems = new CopyOnWriteArrayList<>();
                for (int o = 0; o < 45; o++) {
                    if (this.mc.thePlayer.inventoryContainer.getSlot(o).getHasStack()) {
                        ItemStack item = this.mc.thePlayer.inventoryContainer.getSlot(o).getStack();
                        if (this.mc.thePlayer.inventory.armorItemInSlot(0) != item && this.mc.thePlayer.inventory
                                .armorItemInSlot(1) != item && this.mc.thePlayer.inventory
                                .armorItemInSlot(2) != item && this.mc.thePlayer.inventory
                                .armorItemInSlot(3) != item)
                            if (item != null && item.getItem() != null && Item.getIdFromItem(item.getItem()) != 0 &&
                                    !stackIsUseful(o))
                                uselessItems.add(o);
                    }
                }
                if (!uselessItems.isEmpty()) {
                    this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, uselessItems
                            .get(0), 1, 4, this.mc.thePlayer);
                    uselessItems.remove(0);
                    this.timer.reset();
                }
            }
    }

    private boolean stackIsUseful(int i) {
        ItemStack itemStack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
        boolean hasAlreadyOrBetter = false;
        if (itemStack.getItem() instanceof net.minecraft.item.ItemSword || itemStack.getItem() instanceof net.minecraft.item.ItemPickaxe || itemStack
                .getItem() instanceof net.minecraft.item.ItemAxe) {
            for (int j = 0; j < 45; j++) {
                if (j != i)
                    if (this.mc.thePlayer.inventoryContainer.getSlot(j).getHasStack()) {
                        ItemStack item = this.mc.thePlayer.inventoryContainer.getSlot(j).getStack();
                        if ((item != null && item.getItem() instanceof net.minecraft.item.ItemSword) || Objects.requireNonNull(item).getItem() instanceof net.minecraft.item.ItemAxe || item
                                .getItem() instanceof net.minecraft.item.ItemPickaxe) {
                            float damageFound = getItemDamage(itemStack);
                            // getModifierForCreature
                            damageFound += EnchantmentHelper.func_152377_a(itemStack, EnumCreatureAttribute.UNDEFINED);
                            float damageCurrent = getItemDamage(item);
                            // getModifierForCreature
                            damageCurrent += EnchantmentHelper.func_152377_a(item, EnumCreatureAttribute.UNDEFINED);
                            if (damageCurrent > damageFound) {
                                hasAlreadyOrBetter = true;
                                break;
                            }
                        }
                    }
            }
        } else if (itemStack.getItem() instanceof net.minecraft.item.ItemArmor) {
            for (int j = 0; j < 45; j++) {
                if (i != j)
                    if (this.mc.thePlayer.inventoryContainer.getSlot(j).getHasStack()) {
                        ItemStack item = this.mc.thePlayer.inventoryContainer.getSlot(j).getStack();
                        if (item != null && item.getItem() instanceof net.minecraft.item.ItemArmor) {
                            List<Integer> helmet = Arrays.asList(298, 314, 302, 306, 310);
                            List<Integer> chestplate = Arrays.asList(299, 315, 303, 307, 311);
                            List<Integer> leggings = Arrays.asList(300, 316, 304, 308, 312);
                            List<Integer> boots = Arrays.asList(301, 317, 305, 309, 313);
                            if (helmet.contains(Item.getIdFromItem(item.getItem())) && helmet
                                    .contains(Item.getIdFromItem(itemStack.getItem()))) {
                                if (helmet.indexOf(Item.getIdFromItem(itemStack.getItem())) < helmet
                                        .indexOf(Item.getIdFromItem(item.getItem()))) {
                                    hasAlreadyOrBetter = true;
                                    break;
                                }
                            } else if (chestplate.contains(Item.getIdFromItem(item.getItem())) && chestplate
                                    .contains(Item.getIdFromItem(itemStack.getItem()))) {
                                if (chestplate.indexOf(Item.getIdFromItem(itemStack.getItem())) < chestplate
                                        .indexOf(Item.getIdFromItem(item.getItem()))) {
                                    hasAlreadyOrBetter = true;
                                    break;
                                }
                            } else if (leggings.contains(Item.getIdFromItem(item.getItem())) && leggings
                                    .contains(Item.getIdFromItem(itemStack.getItem()))) {
                                if (leggings.indexOf(Item.getIdFromItem(itemStack.getItem())) < leggings
                                        .indexOf(Item.getIdFromItem(item.getItem()))) {
                                    hasAlreadyOrBetter = true;
                                    break;
                                }
                            } else if (boots.contains(Item.getIdFromItem(item.getItem())) && boots
                                    .contains(Item.getIdFromItem(itemStack.getItem())) &&
                                    boots.indexOf(Item.getIdFromItem(itemStack.getItem())) < boots
                                            .indexOf(Item.getIdFromItem(item.getItem()))) {
                                hasAlreadyOrBetter = true;
                                break;
                            }
                        }
                    }
            }
        }
        for (int o = 0; o < 45; o++) {
            if (i != o)
                if (this.mc.thePlayer.inventoryContainer.getSlot(o).getHasStack()) {
                    ItemStack item = this.mc.thePlayer.inventoryContainer.getSlot(o).getStack();
                    if (item != null && (item.getItem() instanceof net.minecraft.item.ItemSword || item.getItem() instanceof net.minecraft.item.ItemAxe || item
                            .getItem() instanceof net.minecraft.item.ItemBow || item.getItem() instanceof net.minecraft.item.ItemFishingRod || item
                            .getItem() instanceof net.minecraft.item.ItemArmor || item.getItem() instanceof net.minecraft.item.ItemAxe || item
                            .getItem() instanceof net.minecraft.item.ItemPickaxe || Item.getIdFromItem(item.getItem()) == 346)) {
                        if (Item.getIdFromItem(itemStack.getItem()) == Item.getIdFromItem(item.getItem())) {
                            hasAlreadyOrBetter = true;
                            break;
                        }
                    }
                }
        }
        if (Item.getIdFromItem(itemStack.getItem()) == 367)
            return false;
        if (Item.getIdFromItem(itemStack.getItem()) == 30)
            return true;
        if (Item.getIdFromItem(itemStack.getItem()) == 259)
            return true;
        if (Item.getIdFromItem(itemStack.getItem()) == 262)
            return true;
        if (Item.getIdFromItem(itemStack.getItem()) == 264)
            return true;
        if (Item.getIdFromItem(itemStack.getItem()) == 265)
            return true;
        if (Item.getIdFromItem(itemStack.getItem()) == 346)
            return true;
        if (Item.getIdFromItem(itemStack.getItem()) == 384)
            return true;
        if (Item.getIdFromItem(itemStack.getItem()) == 345)
            return true;
        if (Item.getIdFromItem(itemStack.getItem()) == 296)
            return true;
        if (Item.getIdFromItem(itemStack.getItem()) == 336)
            return true;
        if (Item.getIdFromItem(itemStack.getItem()) == 266)
            return true;
        if (Item.getIdFromItem(itemStack.getItem()) == 280)
            return true;
        if (itemStack.hasDisplayName())
            return true;
        if (hasAlreadyOrBetter)
            return false;
        if (itemStack.getItem() instanceof net.minecraft.item.ItemArmor)
            return true;
        if (itemStack.getItem() instanceof net.minecraft.item.ItemAxe)
            return true;
        if (itemStack.getItem() instanceof net.minecraft.item.ItemBow)
            return true;
        if (itemStack.getItem() instanceof net.minecraft.item.ItemSword)
            return true;
        if (itemStack.getItem() instanceof net.minecraft.item.ItemPotion)
            return true;
        if (itemStack.getItem() instanceof net.minecraft.item.ItemFlintAndSteel)
            return true;
        if (itemStack.getItem() instanceof net.minecraft.item.ItemEnderPearl)
            return true;
        if (itemStack.getItem() instanceof net.minecraft.item.ItemBlock)
            return true;
        if (itemStack.getItem() instanceof net.minecraft.item.ItemFood)
            return true;
        return itemStack.getItem() instanceof net.minecraft.item.ItemPickaxe;
    }

    private float getItemDamage(ItemStack itemStack) {
        Multimap<String, AttributeModifier> multimap = itemStack.getAttributeModifiers();
        if (!multimap.isEmpty()) {
            Iterator<Map.Entry<String, AttributeModifier>> iterator = multimap.entries().iterator();
            if (iterator.hasNext()) {
                double damage;
                Map.Entry<String, AttributeModifier> entry = iterator.next();
                AttributeModifier attributeModifier = entry.getValue();
                if (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) {
                    damage = attributeModifier.getAmount();
                } else {
                    damage = attributeModifier.getAmount() * 100.0D;
                }
                if (attributeModifier.getAmount() > 1.0D)
                    return 1.0F + (float)damage;
                return 1.0F;
            }
        }
        return 1.0F;
    }

}
