package net.sssssssthedev.SmartClient.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordMYRPC {

    private boolean running = true;
    private long created = 0;

    public void start() {

        this.created = System.currentTimeMillis();

        DiscordEventHandlers  handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(user -> {
            System.out.println("[DiscordRPC] Welcome " + user.username + "#" + user.discriminator + ".");
            update("Booting up...", "");
        }).build();

        DiscordRPC.discordInitialize("772208571550662677", handlers, true);

        new Thread("Discord RPC Callback") {

            @Override
            public void run() {

                while(running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();

    }

    public void shutdown() {
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String firstLine, String secondLine) {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
        b.setBigImage("large", "");
        b.setDetails(firstLine);
        b.setStartTimestamps(created);

        DiscordRPC.discordUpdatePresence(b.build());
    }
}
