package net.sssssssthedev.SmartClient.discord;

import net.sssssssthedev.SmartClient.gui.SplashProgress;

public class Client {

    private static final Client INSTANCE = new Client();
    public static Client getInstance() { return INSTANCE; }

    private final DiscordMYRPC discordMYRPC = new DiscordMYRPC();


    public void init() {
        SplashProgress.setProgress(1, "Client - Initializing Discord RPC");
        discordMYRPC.start();
    }

    public void start() {
    }

    public void shutdown() {
        discordMYRPC.shutdown();
    }

    public DiscordMYRPC getDiscordMYRPC() {
        return discordMYRPC;
    }

}
