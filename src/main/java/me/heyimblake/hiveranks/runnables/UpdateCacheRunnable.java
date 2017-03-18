package me.heyimblake.hiveranks.runnables;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.heyimblake.hiveranks.CachedPlayerManager;
import me.heyimblake.hiveranks.util.CachedPlayer;
import me.heyimblake.hiveranks.util.HiveRank;
import org.bukkit.scheduler.BukkitRunnable;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/17/2017
 */
public class UpdateCacheRunnable extends BukkitRunnable {
    private UUID uuid;
    private HiveRank[] updatedRanks;
    private int displayRank;

    public UpdateCacheRunnable(UUID uuid) {
        this.uuid = uuid;
        this.displayRank = -1;
        updatedRanks = new HiveRank[2]; // 0: Display, 1: Hive
    }

    public UpdateCacheRunnable(UUID uuid, int displayRank) {
        this.uuid = uuid;
        this.displayRank = displayRank;
        updatedRanks = new HiveRank[2]; // 0: Display, 1: Hive
    }

    @Override
    public void run() {
        CachedPlayerManager.getInstance().initializeFile(uuid);
        try {
            getUpdatedRanks();
            CachedPlayerManager manager = CachedPlayerManager.getInstance();
            if (manager.isCached(uuid)) {
                manager.getCachedPlayer(uuid).update(updatedRanks[0].getId(), updatedRanks[1].getId());
                return;
            }
            new CachedPlayer(uuid, updatedRanks[0].getId(), updatedRanks[1].getId(), System.currentTimeMillis()).update(updatedRanks[0].getId(), updatedRanks[1].getId());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getUpdatedRanks() throws IOException {
        URL url = new URL("https://api.hivemc.com/v1/player/" + uuid.toString().replaceAll("-", ""));
        HttpsURLConnection request = (HttpsURLConnection) url.openConnection();
        request.setRequestProperty("Content-Type", "application/json");
        request.addRequestProperty("User-Agent", "Mozilla/5.0");
        request.connect();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject object = element.getAsJsonObject();

        updatedRanks[1] = HiveRank.getHiveRankFromName(object.get("rankName").getAsString());
        if (displayRank == -1) {
            updatedRanks[0] = HiveRank.getHiveRankFromName(object.get("rankName").getAsString());
            request.disconnect();
            return;
        }
        updatedRanks[0] = HiveRank.getHiveRankFromID(displayRank);
        request.disconnect();

    }
}
