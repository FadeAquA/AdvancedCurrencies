package me.aqua.currency.commands;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import me.aqua.currency.utils.Utils;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.piggyster.api.color.ColorAPI;
import tk.piggyster.api.config.ConfigManager;

import java.text.DecimalFormat;

public class PouchCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        ConfigManager configManager = Currency.getInstance().getConfigManager();
        DecimalFormat df = new DecimalFormat("#,###.##");
        FileConfiguration config = Currency.getInstance().getConfig();
        ConfigurationSection messages = configManager.getConfig("messages").getConfigurationSection("messages");
        if (sender.hasPermission("currency.admin")) {
            if (args.length > 6) {
                for (String message : messages.getStringList("ITEM-HELP")) {
                    sender.sendMessage(ColorAPI.process(message));
                }
            }
            else if (args.length == 6) {
                if (args[0].equalsIgnoreCase("give")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    int min = Integer.parseInt(args[3]);
                    int max = Integer.parseInt(args[4]);
                    int amount = Integer.parseInt(args[5]);
                    CurrencyManager manager = new CurrencyManager();
                    String currency = args[2];

                    if (target != null) {
                        if (amount > 0 && min > 0 && max > 0 && max > min) {
                            if (config.getConfigurationSection("Currencies").getKeys(false).contains(currency)) {
                                Utils.currencyPouch(target, min, max, currency, amount);
                                sender.sendMessage(ColorAPI.process(messages.getString("ADMIN-GIVE-POUCH").replace("%player%", target.getName())
                                        .replace("%currency%", currency).replace("%min%", df.format(min) + "").replace("%max%", df.format(max) + "")
                                        .replace("&p", manager.getCurrencyPrimaryColor(currency)).replace("&s", manager.getCurrencySecondaryColor(currency))));
                            }
                        } else {
                            sender.sendMessage(ColorTranslator.translateColorCodes(messages.getString("INVALID-CURRENCY")));
                        }
                    } else {
                        sender.sendMessage(ColorTranslator.translateColorCodes(messages.getString("INVALID-POUCH-AMOUNT")));
                    }
                } else {
                    sender.sendMessage(ColorTranslator.translateColorCodes(messages.getString("PLAYER-OFFLINE")));
                }
            }
        }
        return false;
    }
}
