package ro.sssssssthedev.AntiCheat.judgement;

import lombok.Getter;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;
import ro.sssssssthedev.AntiCheat.trait.Startable;
import ro.sssssssthedev.AntiCheat.utils.LogUtil;

import java.io.*;
import java.util.Objects;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class JudgementManager implements Startable {
    @Getter
    private final Queue<UUID> judgementQueue;

    public JudgementManager() {
        judgementQueue = new ConcurrentLinkedQueue<>();
    }

    // Add player to the judgement day queue. We're using a boolean intentionally so the staff/administration can know whether they were added or not via command
    public final boolean addToQueue(final OfflinePlayer player) {
        // We do not want to add a player more than twice
        if (!judgementQueue.contains(player.getUniqueId())) {
            // Add to queue and return true to signify that they have been added
            judgementQueue.add(player.getUniqueId());
            return true;
        }
        // They already exist, so return false
        return false;
    }

    // Remove player from judgement day queue. Same reason as above for why we're using a boolean except it's to remove, not add
    public final boolean removeFromQueue(final OfflinePlayer player) {
        // We only want to remove players that actually exist in the map, we don't want null pointers.
        if (judgementQueue.contains(player.getUniqueId())) {
            judgementQueue.remove(player.getUniqueId());
            return true;
        }
        // They never existed in the queue in the first place
        return false;
    }

    // Execute judgement day and start the banning
    public final void executeJudgement() {
        // Grab the general config from the config manager
        // TODO: Grab config

        // Run the loops in a seperate thread to prevent load in the server. 
        AntiCheatAPI.INSTANCE.getJudgementExecutor().execute(() -> judgementQueue.forEach(uuid -> {
            // We're gonna assume the player is offline for now, so lets get their offline profile by UUID even if they're online
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

            // Add player to the ban list
            Bukkit.getBanList(BanList.Type.NAME).addBan(Objects.requireNonNull(offlinePlayer.getName()), "Cheating", null, "Console");

            if (offlinePlayer.isOnline()) {
                final Player player = Bukkit.getPlayer(uuid);

                // Kicking can only be handled in the main thread, so we'll use a scheduler to handle it
                AntiCheatAPI.INSTANCE.getPlugin().getServer().getScheduler().runTask(AntiCheatAPI.INSTANCE.getPlugin(), () -> player.kickPlayer("You have been banned for cheating!"));
            }

            //TODO: Configurable broadcast message

            // Remove player from queue
            judgementQueue.remove(uuid);
        }));
    }

    // Where the judgement day queue will be saved upon server restart.
    public void saveQueue() {
        // The saved file
        final File judgementFile = new File(AntiCheatAPI.INSTANCE.getPlugin().getDataFolder() + "/query/" + "judgement.txt");

        // Making sure the file doesn't already exist for some reason
        if (!judgementFile.exists()) {
            try {
                // Create new file here
                judgementFile.createNewFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            // The FileWriter and the PrintWriter which we use to add data in the txt file
            FileWriter fileWriter = new FileWriter(judgementFile, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Add the UUID to file
            judgementQueue.forEach(printWriter::println);

            // Save and close the writer
            printWriter.close();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    @Override
    public void start() {
        LogUtil.log("Initialing Judgement Manager...");

        // Create the folder
        final File file = new File(AntiCheatAPI.INSTANCE.getPlugin().getDataFolder() + "/query");
        file.mkdirs();

        // Where the judgement queue will be updated
        final File judgementFile = new File(AntiCheatAPI.INSTANCE.getPlugin().getDataFolder() + "/query/" + "judgement.txt");

        // Making sure the file actually exists
        if (judgementFile.exists()) {
            try {
                // Getting the file reader so we can read the actual file
                final FileReader fileReader = new FileReader(judgementFile);
                final BufferedReader bufferReader = new BufferedReader(fileReader);

                // Add the lines as UUIDs to the Queue
                bufferReader.lines().forEach(line -> {
                    // Get the UUID from string using the bukkit-api
                    final UUID uuid = UUID.fromString(line);

                    // Where the UUID gets added
                    judgementQueue.add(uuid);
                });

                fileReader.close();
                bufferReader.close();

                judgementFile.delete();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
