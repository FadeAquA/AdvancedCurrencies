package me.aqua.currency.commands.pouch;

import me.aqua.currency.Currency;
import me.aqua.currency.commands.pouch.subcommands.PouchGiveCommand;
import me.aqua.currency.currencies.CurrencyManager;
import me.aqua.currency.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tk.piggyster.api.color.ColorAPI;
import tk.piggyster.api.command.Command;
import tk.piggyster.api.config.ConfigManager;

public class PouchCommand extends Command {

    public PouchCommand(JavaPlugin plugin) {
        super(plugin, "pouch");
        addSubCommand(new PouchGiveCommand());
    }

    @Override
    public void run(CommandSender commandSender, String[] strings) {
        if (commandSender.hasPermission("currency.admin")) {
            if (strings.length < 6) {
                ConfigManager configManager = Currency.getInstance().getConfigManager();
                for (String message : configManager.getConfig("messages").getConfigurationSection("messages").getStringList("ITEM-HELP")) {
                    commandSender.sendMessage(ColorAPI.process(message));
                }
            }
        }
    }
}
