package ro.sssssssthedev.AntiCheat.command.impl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.AntiCheatPlugin;
import ro.sssssssthedev.AntiCheat.check.Check;
import ro.sssssssthedev.AntiCheat.command.type.AbstractCommand;
import ro.sssssssthedev.AntiCheat.command.type.annotation.Command;
import ro.sssssssthedev.AntiCheat.command.type.arguments.Arguments;
import ro.sssssssthedev.AntiCheat.command.type.sender.Sender;
import ro.sssssssthedev.AntiCheat.data.PlayerData;

import java.util.Collection;

@Command(name = "overflow", aliases = { "ov", "ac", "anticheat" }, canBeUsedBy = Player.class)
public final class OverflowCommand extends AbstractCommand {
	public OverflowCommand(final Plugin plugin) {
		super(plugin);
	}

	@Override
	public void execute(final Sender sender, final Arguments arguments) {
		if (!AntiCheatAPI.INSTANCE.disabled) {
			if (!sender.hasPermission("overflow.command")) {
				return;
			}

			if (arguments.length < 1) {
				final String[] format = new String[] {
						format("&cOverFlow AntiCheat - "
								+ AntiCheatAPI.INSTANCE.getPlugin().getDescription().getVersion()),
						"", format("&c/overflow alerts &7- Toggle your alerts on/off"),
						format("&c/overflow logs <player> &7- View a player's violations"),
						format("&c/overflow disable &7- Disabled the anticheat") };

				sender.sendMessage(format);
			}

			if (arguments.hasNext()) {
				final String first = arguments.next();

				if (first.equalsIgnoreCase("alerts")) {
					final Player player = sender.castPlayer();

					if (player != null) {
						final PlayerData playerData = AntiCheatAPI.INSTANCE.getPlayerDataManager().getData(player);

						final boolean alerts = playerData.getAlerts().get();

						if (alerts) {
							final String format = format("&7[&cOverFlow&7] &cYour alerts have been toggled &4off");

							sender.sendMessage(format);
						} else {
							final String format = format("&7[&cOverFlow&7] &cYour alerts have been toggled &aon");

							sender.sendMessage(format);
						}

						playerData.getAlerts().set(!alerts);
					}
				} else if (first.equalsIgnoreCase("logs")) {
					final boolean next = arguments.hasNext();

					if (next) {
						final String second = arguments.next();

						final Player player = Bukkit.getPlayer(second);

						if (player != null) {
							final String format = format(
									"&7[&cOverFlow&7] &cTrying to find violations for the player. \n &7Violations:");
							final PlayerData targetData = AntiCheatAPI.INSTANCE.getPlayerDataManager().getData(player);

							final Collection<Check> checks = targetData.getCheckManager().getChecks();

							player.sendMessage(format);

							checks.forEach(check -> {
								final String checkFormat = format(
										"&8* &c" + check.getCheckName() + " &7 VL:" + check.getAlert().getViolations());

								sender.sendMessage(checkFormat);
							});

						} else {
							final String format = format("&7[&cOverFlow&7] &cPlayer not found.");

							sender.sendMessage(format);
						}
					}

				} else if (first.equalsIgnoreCase("disable")) {
					final AntiCheatPlugin plugin = AntiCheatAPI.INSTANCE.getPlugin();
					final String format = format("&7[&cOverFlow&7] &cDisabling the AntiCheat...");

					sender.sendMessage(format);
					AntiCheatAPI.INSTANCE.disabled = true;
					Bukkit.getPluginManager().disablePlugin(plugin);

				}
			}
		}
	}

	private String format(final String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
}
