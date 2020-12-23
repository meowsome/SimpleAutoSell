package me.meowso.simpleautosell;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CommandHandler implements CommandExecutor {
    private final FileHandler fileHandler;
    private final FileConfiguration config;

    public CommandHandler(SimpleAutoSell simpleAutoSell) {
        fileHandler = new FileHandler(simpleAutoSell);
        config = simpleAutoSell.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        if (!sender.hasPermission("sas.use")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("chatPrefix") + " " + config.getString("noPermissionMessage")));
            return true;
        }

        if (args.length == 0) {
            String status = config.getString((fileHandler.hasAutoSellEnabled(sender.getName()) ? "enabled" : "disabled"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("chatPrefix") + " " + config.getString("statusMessage").replace("%s", status)));
            return true;
        }

        switch (args[0]) {
            case "on":
            case "enable":
                try {
                    fileHandler.addPlayer(sender.getName());
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("chatPrefix") + " " + config.getString("enabledMessage")));
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("chatPrefix") + " " + config.getString("errorEnablingMessage")));
                    return true;
                }
            case "off":
            case "disable":
                try {
                    fileHandler.removePlayer(sender.getName());
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("chatPrefix") + " " + config.getString("disabledMessage")));
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("chatPrefix") + " " + config.getString("errorDisablingMessage")));
                    return true;
                }
            default:
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("chatPrefix") + " " + config.getString("invalidArgumentMessage")));
                return true;
        }
    }
}
