package me.aqua.currency.commands;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import me.aqua.currency.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.text.WordUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.piggyster.api.color.ColorAPI;
import tk.piggyster.api.config.ConfigManager;

import java.text.DecimalFormat;

public class NoteCommands implements CommandExecutor {

    CurrencyManager manager = new CurrencyManager();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("currency.admin")) {
            ConfigManager configManager = Currency.getInstance().getConfigManager();
            DecimalFormat df = new DecimalFormat("#,###.##");
            FileConfiguration config = Currency.getInstance().getConfig();
            ConfigurationSection messages = configManager.getConfig("messages").getConfigurationSection("messages");
            if (args.length < 5) {
                for (String message : messages.getStringList("ITEM-HELP")) {
                    sender.sendMessage(ColorAPI.process(message));
                }
            }
            if (args.length == 5) {
                Player target = Bukkit.getPlayer(args[1]);
                int value = Integer.parseInt(args[3]);
                int amount = Integer.parseInt(args[4]);
                String currency = args[2];
                if (target != null) {
                    if (args[0].equalsIgnoreCase("give")) {
                        if (config.getConfigurationSection("Currencies").getKeys(false).contains(currency)) {
                            if (value > 0) {
                                if (amount > 0) {
                                    Utils.currencyNote(target, value, config.getString("Items.note.SERVER-SIGNER"), currency, amount);
                                    sender.sendMessage(ColorAPI.process(messages.getString("ADMIN-GIVE-NOTE").replace("%player%", target.getName())
                                            .replace("%value%", value + "").replace("%currency%", WordUtils.capitalize(currency))
                                            .replace("%amount%", amount + "").replace("&p", manager.getCurrencyPrimaryColor(currency))
                                            .replace("&s", manager.getCurrencySecondaryColor(currency))));
                                } else {
                                    sender.sendMessage(ColorAPI.process(messages.getString("INVALID-AMOUNT")));
                                }
                            } else {
                                sender.sendMessage(ColorAPI.process(messages.getString("INVALID-AMOUNT")));
                            }
                        } else {
                            sender.sendMessage(ColorAPI.process(messages.getString("INVALID-CURRENCY").replace("%currency%", currency)));
                        }
                    }
                } else {
                    sender.sendMessage(ColorAPI.process(messages.getString("PLAYER-OFFLINE").replace("%player%", args[1])));
                }
            }
        }

        return true;
    }
}
