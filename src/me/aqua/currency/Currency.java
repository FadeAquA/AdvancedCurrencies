package me.aqua.currency;

import me.aqua.currency.commands.balance.BalanceCommand;
import me.aqua.currency.commands.currency.BankCommand;
import me.aqua.currency.commands.note.NoteCommand;
import me.aqua.currency.commands.pouch.PouchCommand;
import me.aqua.currency.commands.withdraw.WithdrawCommand;
import me.aqua.currency.currencies.CurrencyManager;
import me.aqua.currency.listeners.FeatureListener;
import me.aqua.currency.listeners.JoinListener;
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
        configManager.createConfig("config");
        configManager.createConfig("features");
        configManager.setMessageConfig("messages");
        configManager.setMessageSection("messages");
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
        new PouchCommand(this);
        new BalanceCommand(this);
        new BankCommand(this);
        new NoteCommand(this);
        new WithdrawCommand(this);
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PouchListener(), this);
        getServer().getPluginManager().registerEvents(new NoteListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new FeatureListener(), this);
    }

    public static Currency getInstance() {
        return plugin;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
