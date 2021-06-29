package net.sssssssthedev.SmartClient.utils.mcping;

public class MinecraftPingReply {
    private final String ip;

    private final int port;

    private final String motd;

    private final String protocolVersion;

    private final String version;

    private final int maxPlayers;

    private final int onlinePlayers;

    protected MinecraftPingReply(String ip, int port, String motd, int onlinePlayers, int maxPlayers) {
        this(ip, port, motd, "Pre-47", "Pre-12w42b", onlinePlayers, maxPlayers);
    }

    protected MinecraftPingReply(String ip, int port, String motd, String protocolVersion, String version, int onlinePlayers, int maxPlayers) {
        this.ip = ip;
        this.port = port;
        this.motd = motd;
        this.protocolVersion = protocolVersion;
        this.version = version;
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
    }

    public String getIp() {
        return this.ip;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public String getMotd() {
        return this.motd;
    }

    public int getOnlinePlayers() {
        return this.onlinePlayers;
    }

    public int getPort() {
        return this.port;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }

    public String getVersion() {
        return this.version;
    }

    public String toString() {
        return String.format("{\"ip\":\"%s\",\"port\":%s,\"maxPlayers\":%s,\"onlinePlayers\":%s,\"motd\":\"%s\",\"protocolVersion\":\"%s\",\"serverVersion\":\"%s\"}", getIp(), getPort(), getMaxPlayers(), getOnlinePlayers(), getMotd(), getProtocolVersion(), getVersion());
    }
}
