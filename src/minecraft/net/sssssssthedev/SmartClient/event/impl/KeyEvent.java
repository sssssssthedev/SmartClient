package net.sssssssthedev.SmartClient.event.impl;

import net.sssssssthedev.SmartClient.event.Event;

public class KeyEvent extends Event {
    private final int key;

    public KeyEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
