package net.sssssssthedev.SmartClient.event.impl;

import net.minecraft.entity.Entity;
import net.sssssssthedev.SmartClient.event.Event;

public class RenderEntityEvent extends Event {
    private final Entity entity;

    public RenderEntityEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
