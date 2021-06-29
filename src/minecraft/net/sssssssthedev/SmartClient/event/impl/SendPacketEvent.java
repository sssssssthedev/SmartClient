package net.sssssssthedev.SmartClient.event.impl;

import net.minecraft.network.Packet;
import net.sssssssthedev.SmartClient.event.Event;

public class SendPacketEvent extends Event {
    private Packet packet;

    public SendPacketEvent(Packet packet) {
        packet = null;
        setPacket(packet);
    }


    public Packet getPacket() {
        return packet;
    }
    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
