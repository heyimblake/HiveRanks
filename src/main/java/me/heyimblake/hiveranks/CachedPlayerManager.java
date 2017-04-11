package me.heyimblake.hiveranks;

import com.google.common.io.ByteStreams;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import me.heyimblake.hiveranks.util.CachedPlayer;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/17/2017
 */
public class CachedPlayerManager {
    private static CachedPlayerManager ourInstance = new CachedPlayerManager();
    private HashMap<UUID, CachedPlayer> uuidCachedPlayerHashMap;
    private final String FILE_SUFFIX = "_cache.json";

    private CachedPlayerManager() {
        uuidCachedPlayerHashMap = new HashMap<>();
    }

    public static CachedPlayerManager getInstance() {
        return ourInstance;
    }

    /**
     * Creates the directory for the cache files.
     */
    protected void initializeDirectory() {
        File dataFolder = HiveRanks.getInstance().getDataFolder();
        if (!dataFolder.exists())
            HiveRanks.getInstance().getDataFolder().mkdir();
    }

    /**
     * Initializes or creates the cache file for a UUID
     *
     * @param uuid the uuid of the player
     */
    public void initializeFile(UUID uuid) {
        File cacheFile = new File(HiveRanks.getInstance().getDataFolder().getPath(), uuid.toString() + FILE_SUFFIX);

        if (!cacheFile.exists()) {
            try {
                cacheFile.createNewFile();
                try (InputStream is = HiveRanks.getInstance().getResource("cache.json");
                     OutputStream os = new FileOutputStream(cacheFile)) {
                    ByteStreams.copy(is, os);
                    os.close();
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            Gson gson = new Gson();
            CachedPlayer cachedPlayer;
            Type type = new TypeToken<CachedPlayer>() {
            }.getType();
            cachedPlayer = gson.fromJson(new FileReader(cacheFile), type);
            uuidCachedPlayerHashMap.put(cachedPlayer.getUuid(), cachedPlayer);
        } catch (FileNotFoundException e) {
            //Not really possible as it's created/verified above, but oh well, here's a catch block!
        }
    }

    /**
     * Gets the CachedPlayer from a given UUID. Null if invalid.
     *
     * @param uuid the uuid of the cachedplayer
     * @return the cachedplayer of the uuid, null if none exists
     */
    public CachedPlayer getCachedPlayer(UUID uuid) {
        return uuidCachedPlayerHashMap.getOrDefault(uuid, null);
    }

    /**
     * Sees if a UUID is cached or not.
     *
     * @param uuid the uuid of the player
     * @return true if cached, false otherwise
     */
    public boolean isCached(UUID uuid) {
        return uuidCachedPlayerHashMap.containsKey(uuid);
    }

    /**
     * Gets the HashMap of UUIDs and their CachedPlayer objects.
     *
     * @return hashmap of uuids and cachedplayer objects
     */
    public HashMap<UUID, CachedPlayer> getUuidCachedPlayerHashMap() {
        return uuidCachedPlayerHashMap;
    }

    /**
     * Gets the cache file of a UUID.
     *
     * @param uuid uuid of the player
     * @return cache file of the supplied uuid
     */
    public File getCacheFile(UUID uuid) {
        return new File(HiveRanks.getInstance().getDataFolder().getPath(), uuid.toString() + FILE_SUFFIX);
    }

    /**
     * Sees if a cache file for a UUID exists or not.
     *
     * @param uuid the uuid to look for
     * @return true if exists, false otherwise
     */
    public boolean fileExists(UUID uuid) {
        return new File(HiveRanks.getInstance().getDataFolder().getPath(), uuid.toString() + FILE_SUFFIX).exists();
    }
}
