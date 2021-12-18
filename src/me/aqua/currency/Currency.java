package me.aqua.currency;

import me.aqua.currency.commands.*;
import me.aqua.currency.listeners.NoteListener;
import me.aqua.currency.listeners.PouchListener;
import me.aqua.currency.utils.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tk.piggyster.api.config.ConfigManager;


public class Currency extends JavaPlugin {

    private static Currency plugin;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        plugin = this;
        configManager = new ConfigManager(this);
        configManager.createConfig("data");
        configManager.createConfig("messages");
        saveDefaultConfig();
        registerCommands();
        registerEvents();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders(this).register();
        }
    }

    @Override
    public void onDisable() {

    }



    public void registerCommands() {
        getCommand("pouch").setExecutor(new PouchCommands());
        getCommand("bank").setExecutor(new CurrencyCommands());
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("note").setExecutor(new NoteCommands());
        getCommand("withdraw").setExecutor(new WithdrawCommand());
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PouchListener(), this);
        getServer().getPluginManager().registerEvents(new NoteListener(), this);
    }

    public static Currency getInstance() {
        return plugin;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
