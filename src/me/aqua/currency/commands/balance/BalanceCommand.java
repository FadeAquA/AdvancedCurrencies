package me.aqua.currency.commands.balance;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tk.piggyster.api.color.ColorAPI;
import tk.piggyster.api.command.Command;
import tk.piggyster.api.config.ConfigManager;

import java.text.DecimalFormat;

public class BalanceCommand extends Command {

    public BalanceCommand(JavaPlugin plugin) {
        super(plugin, "balance");
    }

    @Override
    public void run(CommandSender commandSender, String[] strings) {
        ConfigManager configManager = Currency.getInstance().getConfigManager();
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            CurrencyManager currencyManager = new CurrencyManager();
            DecimalFormat df = new DecimalFormat("#,###.##");
            if (strings.length == 0) {
                for (String s : configManager.getConfig("messages").getConfigurationSection("messages").getStringList("PLAYER-BALANCE")) {
                    if (s.equals("%balances%")) {
                        for (String currency : configManager.getConfig("config").getConfigurationSection("Currencies").getKeys(false)) {
                            player.sendMessage(ColorAPI.process(configManager.getConfig("messages").getConfigurationSection("messages").getString("PLAYER-BALANCE-ELEMENT")
                                    .replace("&p", currencyManager.getCurrencyPrimaryColor(currency)).replace("&s", currencyManager.getCurrencySecondaryColor(currency))
                                    .replace("%currency%", currency).replace("%balance%", df.format(currencyManager.getCurrency(player, currency)) + "")));
                        }
                    } else {
                        player.sendMessage(ColorAPI.process(s));
                    }
                }
            }
            if (strings.length == 1) {
                Player target = Bukkit.getPlayer(strings[0]);
                for (String s : configManager.getConfig("messages").getConfigurationSection("messages").getStringList("OTHER-PLAYER-BALANCE")) {
                    if (s.equals("%balances%")) {
                        for (String currency : configManager.getConfig("config").getConfigurationSection("Currencies").getKeys(false)) {
                            player.sendMessage(ColorAPI.process(configManager.getConfig("messages").getConfigurationSection("messages").getString("PLAYER-BALANCE-ELEMENT")
                                    .replace("&p", currencyManager.getCurrencyPrimaryColor(currency)).replace("&s", currencyManager.getCurrencySecondaryColor(currency))
                                    .replace("%currency%", currency).replace("%balance%", df.format(currencyManager.getCurrency(target, currency)) + "")));
                        }
                    } else {
                        player.sendMessage(ColorAPI.process(s).replace("%player%", target.getName()));
                    }
                }
            }
        }
    }
}
