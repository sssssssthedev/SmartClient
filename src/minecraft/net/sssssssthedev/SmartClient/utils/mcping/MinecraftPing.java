package net.sssssssthedev.SmartClient.utils.mcping;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MinecraftPing {
    public MinecraftPingReply getPing(String hostname) throws IOException {
        return getPing(hostname, 25565);
    }

    public MinecraftPingReply getPing(String hostname, int port) throws IOException {
        validate(hostname, "Hostname cannot be null.");
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(hostname, port), 3000);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.write(254);
        out.write(1);
        out.write(250);
        out.writeShort(11);
        out.writeChars("MC|PingHost");
        out.writeShort(7 + 2 * hostname.length());
        out.writeByte(73);
        out.writeShort(hostname.length());
        out.writeChars(hostname);
        out.writeInt(port);
        out.flush();
        if (in.read() != 255)
            throw new IOException("Bad message: An incorrect packet was received.");
        short bit = in.readShort();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bit; i++)
            sb.append(in.readChar());
        out.close();
        String[] bits = sb.toString().split("\000");
        if (bits.length != 6)
            return getPing(sb.toString(), hostname, port);
        return new MinecraftPingReply(hostname, port, bits[3], bits[1], bits[2], Integer.parseInt(bits[4]), Integer.parseInt(bits[5]));
    }

    @Deprecated
    public MinecraftPingReply getPing(String response, String hostname, int port) throws IOException {
        validate(response, "Response cannot be null. Try calling MinecraftPing.getPing().");
        validate(hostname, "Hostname cannot be null.");
        String[] bits = response.split("§");
        if (bits.length != 3)
            throw new IOException("Bad message: Failed to parse pre-12w42b ping message, check to see if it's correct?");
        return new MinecraftPingReply(hostname, port, bits[0], Integer.parseInt(bits[2]), Integer.parseInt(bits[1]));
    }

    private void validate(Object o, String m) {
        if (o == null)
            throw new RuntimeException(m);
    }
}

