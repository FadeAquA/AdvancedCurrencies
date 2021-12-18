package me.aqua.currency.commands;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.piggyster.api.color.ColorAPI;
import tk.piggyster.api.config.ConfigManager;

import java.text.DecimalFormat;

public class BalanceCommand implements CommandExecutor {


    ConfigManager manager = Currency.getInstance().getConfigManager();
    CurrencyManager currencyManager = new CurrencyManager();
    DecimalFormat df = new DecimalFormat("#,###.##");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                for (String s : manager.getConfig("messages").getConfigurationSection("messages").getStringList("PLAYER-BALANCE")) {
                    if (s.equals("%balances%")) {
                        for (String currency : Currency.getInstance().getConfig().getConfigurationSection("Currencies").getKeys(false)) {
                            sender.sendMessage(ColorAPI.process(manager.getConfig("messages").getConfigurationSection("messages").getString("PLAYER-BALANCE-ELEMENT")
                                    .replace("&p", currencyManager.getCurrencyPrimaryColor(currency)).replace("&s", currencyManager.getCurrencySecondaryColor(currency))
                                    .replace("%currency%", currency).replace("%balance%", df.format(currencyManager.getCurrency((Player) sender, currency)) + "")));
                        }
                    } else {
                        sender.sendMessage(ColorAPI.process(s));
                    }
                }
            }
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                for (String s : manager.getConfig("messages").getConfigurationSection("messages").getStringList("OTHER-PLAYER-BALANCE")) {
                    if (s.equals("%balances%")) {
                        for (String currency : Currency.getInstance().getConfig().getConfigurationSection("Currencies").getKeys(false)) {
                            sender.sendMessage(ColorAPI.process(manager.getConfig("messages").getConfigurationSection("messages").getString("PLAYER-BALANCE-ELEMENT")
                                    .replace("&p", currencyManager.getCurrencyPrimaryColor(currency)).replace("&s", currencyManager.getCurrencySecondaryColor(currency))
                                    .replace("%currency%", currency).replace("%balance%", df.format(currencyManager.getCurrency(target, currency)) + "")));
                        }
                    } else {
                        sender.sendMessage(ColorAPI.process(s).replace("%player%", target.getName()));
                    }
                }
            }
        } else {
            sender.sendMessage(ColorAPI.process(manager.getConfig("messages").getConfigurationSection("messages").getString("ONLY-PLAYER-COMMAND")));
        }

        return true;
    }
}
