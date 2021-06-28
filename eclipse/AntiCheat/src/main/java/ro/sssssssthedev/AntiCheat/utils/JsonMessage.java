package ro.sssssssthedev.AntiCheat.utils;

import org.bukkit.entity.*;
import org.bukkit.*;
import java.lang.reflect.*;
import java.util.*;
import org.bukkit.inventory.*;

public final class JsonMessage
{
    private List<AMText> Text;

    public JsonMessage() {
        this.Text = new ArrayList<AMText>();
    }

    public AMText addText(final String Message) {
        final AMText Text = new AMText(Message);
        this.Text.add(Text);
        return Text;
    }

    public String getFormattedMessage() {
        String Chat = "[\"\",";
        for (final AMText Text : this.Text) {
            Chat = Chat + Text.getFormattedMessage() + ",";
        }
        Chat = Chat.substring(0, Chat.length() - 1);
        Chat += "]";
        return Chat;
    }

    public void sendToPlayer(final Player Player) {
        try {
            final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            Object base = null;
            final Constructor<?> titleConstructor = this.getNMSClass("PacketPlayOutChat").getConstructor(this.getNMSClass("IChatBaseComponent"));
            if (version.contains("1_7") || version.contains("1_8_R1")) {
                base = this.getNMSClass("ChatSerializer").getMethod("a", String.class).invoke(null, this.getFormattedMessage());
            }
            else {
                base = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, this.getFormattedMessage());
            }
            final Object packet = titleConstructor.newInstance(base);
            this.sendPacket(Player, packet);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void sendPacket(final Player player, final Object packet) {
        try {
            final Object handle = player.getClass().getMethod("getHandle", (Class<?>[])new Class[0]).invoke(player, new Object[0]);
            final Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", this.getNMSClass("Packet")).invoke(playerConnection, packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Class<?> getNMSClass(final String name) {
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Class<?> getCBClass(final String name) {
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public enum ClickableType
    {
        RunCommand("run_command"),
        SuggestCommand("suggest_command"),
        OpenURL("open_url");

        public String Action;

        private ClickableType(final String Action) {
            this.Action = Action;
        }
    }

    public class AMText
    {
        private String Message;
        private Map<String, Map.Entry<String, String>> Modifiers;

        public AMText(final String Text) {
            this.Message = "";
            this.Modifiers = new HashMap<String, Map.Entry<String, String>>();
            this.Message = Text;
        }

        public String getMessage() {
            return this.Message;
        }

        public String getFormattedMessage() {
            String Chat = "{\"text\":\"" + this.Message + "\"";
            for (final String Event : this.Modifiers.keySet()) {
                final Map.Entry<String, String> Modifier = this.Modifiers.get(Event);
                Chat = Chat + ",\"" + Event + "\":{\"action\":\"" + Modifier.getKey() + "\",\"value\":" + Modifier.getValue() + "}";
            }
            Chat += "}";
            return Chat;
        }

        public AMText addHoverText(final String... Text) {
            final String Event = "hoverEvent";
            final String Key = "show_text";
            String Value = "";
            if (Text.length == 1) {
                Value = "{\"text\":\"" + Text[0] + "\"}";
            }
            else {
                Value = "{\"text\":\"\",\"extra\":[";
                for (final String Message : Text) {
                    Value = Value + "{\"text\":\"" + Message + "\"},";
                }
                Value = Value.substring(0, Value.length() - 1);
                Value += "]}";
            }
            final Map.Entry<String, String> Values = new AbstractMap.SimpleEntry<String, String>(Key, Value);
            this.Modifiers.put(Event, Values);
            return this;
        }

        public AMText addHoverItem(final ItemStack Item) {
            try {
                final String Event = "hoverEvent";
                final String Key = "show_item";
                final Object craftItemStack = JsonMessage.this.getCBClass("CraftItemStack");
                final Class<?> items = Class.forName("org.bukkit.inventory.ItemStack");
                final Object NMS = craftItemStack.getClass().getMethod("asNMSCopy", items).invoke(Item, new Object[0]);
                final String Value = NMS.getClass().getMethod("getTag", (Class<?>[])new Class[0]).toString();
                final Map.Entry<String, String> Values = new AbstractMap.SimpleEntry<String, String>(Key, Value);
                this.Modifiers.put(Event, Values);
                return this;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public AMText setClickEvent(final ClickableType Type, final String Value) {
            final String Event = "clickEvent";
            final String Key = Type.Action;
            final Map.Entry<String, String> Values = new AbstractMap.SimpleEntry<String, String>(Key, "\"" + Value + "\"");
            this.Modifiers.put(Event, Values);
            return this;
        }
    }
}
