package ro.sssssssthedev.AntiCheat.hook;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import ro.sssssssthedev.AntiCheat.check.Check;
import ro.sssssssthedev.AntiCheat.config.impl.WebConfig;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;

import java.awt.*;

public final class DiscordManager {

    public void log(final Check check) {
        final String header = AntiCheatAPI.INSTANCE.getConfigManager().getConfig(WebConfig.class).getHeader();
        final String body = AntiCheatAPI.INSTANCE.getConfigManager().getConfig(WebConfig.class).getBody().replace("%check%", check.getCheckName()).replace("%vl%", String.valueOf(check.getAlert().getViolations())).replace("%player%", check.getPlayerData().getPlayer().getName());

        final String link = AntiCheatAPI.INSTANCE.getConfigManager().getConfig(WebConfig.class).getLink();

        final WebhookClient webhookClient = WebhookClient.withUrl(link);
        final WebhookEmbed embed = new WebhookEmbedBuilder().setColor(getIntFromColor(Color.RED)).setTitle(new WebhookEmbed.EmbedTitle(header, null)).setDescription(body).build();

        AntiCheatAPI.INSTANCE.getAlertExecutor().execute(() -> {
            webhookClient.send(embed);
            webhookClient.close();
        });
    }

    private int getIntFromColor(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        red = (red << 16) & 0x00FF0000;
        green = (green << 8) & 0x0000FF00;
        blue = blue & 0x000000FF;
        return 0xFF000000 | red | green | blue;
    }
}
