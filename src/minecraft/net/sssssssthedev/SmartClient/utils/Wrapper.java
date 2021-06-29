package net.sssssssthedev.SmartClient.utils;

import org.lwjgl.input.Keyboard;

public class Wrapper {

    public static int getKey(String keyname) {
        return Keyboard.getKeyIndex(keyname.toUpperCase());
    }

    public static String getKeyName(int keycode) {
        return Keyboard.getKeyName(keycode);
    }


}
