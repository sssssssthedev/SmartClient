package net.sssssssthedev.SmartClient.event.impl;

import net.minecraft.network.Packet;
import net.sssssssthedev.SmartClient.event.Event;

public class ReceivePacketEvent extends Event {
    private Packet packet;

    public ReceivePacketEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
