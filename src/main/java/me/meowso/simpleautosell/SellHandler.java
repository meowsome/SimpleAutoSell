package me.meowso.simpleautosell;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SellHandler {
    private final FileHandler fileHandler;
    private static final ArrayList<String> cooldowns = new ArrayList<>();
    private final FileConfiguration config;

    public SellHandler(SimpleAutoSell simpleAutoSell) {
        fileHandler = new FileHandler(simpleAutoSell);
        config = simpleAutoSell.getConfig();
    }

    public void handleItemPickup(Player player) throws IOException {
        // If auto sell enabled, inventory is full, has permission, AND is not in cooldown
        if (isInventoryFull(player) && isAutoSellEnabled(player.getName()) && isNotInCooldown(player.getName())) {
            if (hasPermission(player)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("chatPrefix") + " " + config.getString("sellingMessage")));
                player.performCommand("sellall");
                beginCooldown(player.getName());
            } else {
                fileHandler.removePlayer(player.getName());
            }
        }
    }

    public void beginCooldown(String name) {
        if (!cooldowns.contains(name)) cooldowns.add(name);

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                cooldowns.remove(name);
            }
        }, config.getInt("cooldown"));
    }

    public boolean isAutoSellEnabled(String name) {
        return fileHandler.hasAutoSellEnabled(name);
    }

    public boolean isInventoryFull(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }

    public boolean isNotInCooldown(String name) {
        return !cooldowns.contains(name);
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission("sas.use");
    }
}
