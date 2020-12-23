package me.meowso.simpleautosell;

import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleAutoSell extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Listeners(this), this);
        getCommand("autosell").setExecutor(new CommandHandler(this));
        getCommand("autosell").setTabCompleter(new TabCompletion());
        saveDefaultConfig();
    }
}
