package me.meowso.simpleautosell;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileHandler {
    private final String databasePath;
    private static final String enabledPlayerArray = "playersWithAutoSellTurnedOn";

    public FileHandler(SimpleAutoSell simpleAutoSell) {
        databasePath = simpleAutoSell.getDataFolder() + "/database.yml";
    }

    public boolean hasAutoSellEnabled(String name) {
        File file = new File(databasePath);
        YamlConfiguration database = YamlConfiguration.loadConfiguration(file);

        return database.getStringList(enabledPlayerArray).contains(name);
    }

    public void addPlayer(String name) throws IOException {
        File file = new File(databasePath);
        YamlConfiguration database = YamlConfiguration.loadConfiguration(file);

        List<String> players = database.getStringList(enabledPlayerArray);

        if (!players.contains(name)) players.add(name);

        database.set(enabledPlayerArray, players);
        database.save(file);
    }

    public void removePlayer(String name) throws IOException {
        File file = new File(databasePath);
        YamlConfiguration database = YamlConfiguration.loadConfiguration(file);

        List<String> players = database.getStringList(enabledPlayerArray);

        players.remove(name);

        database.set("playersWithSellAllTurnedOn", players);
        database.save(file);
    }
}
