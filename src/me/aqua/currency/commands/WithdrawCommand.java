package me.aqua.currency.commands;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import me.aqua.currency.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.piggyster.api.color.ColorAPI;
import tk.piggyster.api.config.ConfigManager;

public class WithdrawCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        ConfigManager configManager = Currency.getInstance().getConfigManager();
        ConfigurationSection messages = configManager.getConfig("messages").getConfigurationSection("messages");
        ConfigurationSection currencies = Currency.getInstance().getConfig().getConfigurationSection("Currencies");
        CurrencyManager manager = new CurrencyManager();
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (player.hasPermission("currency.withdraw")) {
                if (args.length < 2) {
                    player.sendMessage(ColorAPI.process(messages.getString("WITHDRAW-USAGE")));
                }
                if (args.length > 2) {
                    player.sendMessage(ColorAPI.process(messages.getString("WITHDRAW-USAGE")));
                }
                if (args.length == 2) {
                    String currency = args[0];
                    int value = Integer.parseInt(args[1]);
                    if (currencies.contains(currency)) {
                        if (manager.getCurrency(player, currency) >= value) {
                            if (value > 0) {
                                Utils.currencyNote(player, value, player.getName(), currency, 1);
                                manager.removeCurrency(player, currency, value, "Withdraw");
                            } else {
                                player.sendMessage(ColorAPI.process(messages.getString("INVALID-AMOUNT")));
                            }
                        } else {
                            player.sendMessage(ColorAPI.process(messages.getString("INSUFFICIANT-CURRENCY").replace("%currency%", currency)));
                        }
                    } else {
                        player.sendMessage(ColorAPI.process(messages.getString("INVALID-CURRENCY")));
                    }

                }
            }
        } else {
            sender.sendMessage(ColorAPI.process(messages.getString("PLAYER-COMMAND")));
        }
        return false;
    }
}
