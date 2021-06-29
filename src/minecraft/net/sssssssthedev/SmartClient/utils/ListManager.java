package net.sssssssthedev.SmartClient.utils;

import java.util.List;

public abstract class ListManager<T> {
    protected List<T> contents;

    public ListManager(List<T> contents) {
        this.contents = contents;
    }

    public List<T> getContents() {
        return contents;
    }

    public void setup() {
    }
}
