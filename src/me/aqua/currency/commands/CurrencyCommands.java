package me.aqua.currency.commands;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.text.WordUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.piggyster.api.color.ColorAPI;
import tk.piggyster.api.config.ConfigManager;

import java.text.DecimalFormat;

public class CurrencyCommands implements CommandExecutor {

    ConfigManager configManager = Currency.getInstance().getConfigManager();
    ConfigurationSection currencySection  = Currency.getInstance().getConfig().getConfigurationSection("Currencies");
    ConfigurationSection messages = configManager.getConfig("messages").getConfigurationSection("messages");
    DecimalFormat df = new DecimalFormat("#,###.##");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("currency.admin")) {
            if (args.length < 4) {
                for (String messages : messages.getStringList("BANK-HELP")) {
                    sender.sendMessage(ColorAPI.process(messages));
                }
            }
            if (args.length == 4) {
                Player target = Bukkit.getPlayer(args[1]);
                int intamount = Integer.parseInt(args[3]);
                String currency = args[2];
                boolean decimal = Currency.getInstance().getConfig().getBoolean("Currencies." + currency + ".decimal");
                CurrencyManager manager = new CurrencyManager();
                if (target != null) {
                    if (!decimal) {
                        if (currencySection.getKeys(false).contains(currency)) {
                            if (args[0].equalsIgnoreCase("give")) {
                                if (intamount > 0) {
                                    manager.addCurrency(target, currency, intamount, "Admin");
                                    sender.sendMessage(ColorAPI.process(messages.getString("ADMIN-GIVE-CURRENCY").replace("%currency%", WordUtils.capitalize(currency)).replace("%amount%", df.format(intamount) + "").replace("%player%", target.getName())
                                            .replace("&p", manager.getCurrencyPrimaryColor(currency)).replace("&s", manager.getCurrencySecondaryColor(currency))));
                                } else {
                                    sender.sendMessage(ColorAPI.process(messages.getString("INVALID-AMOUNT")));
                                }
                            }
                            if (args[0].equalsIgnoreCase("take")) {
                                if (manager.getCurrency(target, currency) > intamount) {
                                    manager.removeCurrency(target, currency, intamount, "Admin");
                                    sender.sendMessage(ColorAPI.process(messages.getString("ADMIN-TAKE-CURRENCY").replace("%currency%", WordUtils.capitalize(currency)).replace("%amount%", df.format(intamount) + "").replace("%player%", target.getName())
                                            .replace("&p", manager.getCurrencyPrimaryColor(currency)).replace("&s", manager.getCurrencySecondaryColor(currency))));
                                } else {
                                    sender.sendMessage(ColorAPI.process(messages.getString("INSUFFICIANT-CURRENCY").replace("%currency%", WordUtils.capitalize(currency))));
                                }
                            }
                            if (args[0].equalsIgnoreCase("set")) {
                                manager.setCurrency(target, currency, intamount);
                                sender.sendMessage(ColorAPI.process(messages.getString("ADMIN-SET-CURRENCY").replace("%currency%", WordUtils.capitalize(currency)).replace("%amount%", df.format(intamount) + "").replace("%player%", target.getName())
                                        .replace("&p", manager.getCurrencyPrimaryColor(currency)).replace("&s", manager.getCurrencySecondaryColor(currency))));
                            }
                        } else {
                            sender.sendMessage(ColorAPI.process(messages.getString("INVALID-CURRENCY")));
                        }
                    } else {
                        double doubleAmount = Double.parseDouble(args[3]);
                        if (currencySection.getKeys(false).contains(currency)) {
                            if (args[0].equalsIgnoreCase("give")) {
                                if (doubleAmount > 0) {
                                    manager.addCurrency(target, currency, (int) doubleAmount, "Admin");
                                    sender.sendMessage(ColorAPI.process(messages.getString("ADMIN-GIVE-CURRENCY").replace("%currency%", WordUtils.capitalize(currency)).replace("%amount%", df.format(doubleAmount) + "").replace("%player%", target.getName())
                                            .replace("&p", manager.getCurrencyPrimaryColor(currency)).replace("&s", manager.getCurrencySecondaryColor(currency))));
                                } else {
                                    sender.sendMessage(ColorAPI.process(messages.getString("INVALID-AMOUNT")));
                                }
                            }
                            if (args[0].equalsIgnoreCase("take")) {
                                if (manager.getCurrency(target, currency) > doubleAmount) {
                                    manager.removeCurrency(target, currency, (int) doubleAmount, "Admin");
                                    sender.sendMessage(ColorAPI.process(messages.getString("ADMIN-TAKE-CURRENCY").replace("%currency%", WordUtils.capitalize(currency)).replace("%amount%", df.format(doubleAmount) + "").replace("%player%", target.getName())
                                            .replace("&p", manager.getCurrencyPrimaryColor(currency)).replace("&s", manager.getCurrencySecondaryColor(currency))));
                                } else {
                                    sender.sendMessage(ColorAPI.process(messages.getString("INSUFFICIANT-CURRENCY").replace("%currency%", WordUtils.capitalize(currency))));
                                }
                            }
                            if (args[0].equalsIgnoreCase("set")) {
                                manager.setCurrency(target, currency, (int) doubleAmount);
                                sender.sendMessage(ColorAPI.process(messages.getString("ADMIN-SET-CURRENCY").replace("%currency%", WordUtils.capitalize(currency)).replace("%amount%", df.format(doubleAmount) + "").replace("%player%", target.getName())
                                        .replace("&p", manager.getCurrencyPrimaryColor(currency)).replace("&s", manager.getCurrencySecondaryColor(currency))));
                            }
                        } else {
                            sender.sendMessage(ColorAPI.process(messages.getString("INVALID-CURRENCY")));
                        }
                    }
                } else {
                    sender.sendMessage(ColorAPI.process(messages.getString("PLAYER-OFFLINE").replace("%player%", args[1])));
                }
            }
        } else {
            sender.sendMessage(ColorAPI.process(messages.getString("NO-PERMISSION")));
        }
        return true;
    }
}
