package net.sssssssthedev.SmartClient.event.impl;

import net.minecraft.entity.Entity;
import net.sssssssthedev.SmartClient.event.Event;

public class PostRenderEntityEvent extends Event {
    private final Entity entity;

    public PostRenderEntityEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
