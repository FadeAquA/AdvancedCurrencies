package me.aqua.currency.commands.pouch.subcommands;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import me.aqua.currency.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import tk.piggyster.api.command.SubCommand;
import tk.piggyster.api.config.ConfigManager;

import java.text.DecimalFormat;

public class PouchGiveCommand extends SubCommand {

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public void run(CommandSender commandSender, String[] strings) {
        if (strings.length == 5) {
            ConfigManager configManager = Currency.getInstance().getConfigManager();
            CurrencyManager currencyManager = new CurrencyManager();
            ConfigurationSection currencies = configManager.getConfig("config").getConfigurationSection("Currencies");
            Player target = Bukkit.getPlayer(strings[0]);
            DecimalFormat df = new DecimalFormat("#,###.##");
            String currency = strings[1].toUpperCase();
            int min = Integer.parseInt(strings[2]);
            int max = Integer.parseInt(strings[3]);
            int amount = Integer.parseInt(strings[4]);

            if (target != null) {
                if (currencies.contains(currency)) {
                    if (min > 0 && min < max) {

                        Utils.currencyPouch(target, min, max, currency, amount);
                        configManager.getMessage("ADMIN-GIVE-POUCH").setPlaceholder("%min%", df.format(min)).setPlaceholder("%max%", df.format(max)).setPlaceholder("%player%", target.getName()).setPlaceholder("%currency%", currency)
                                .setPlaceholder("%amount%", amount).setPlaceholder("&p", currencyManager.getCurrencyPrimaryColor(currency)).setPlaceholder("&s", currencyManager.getCurrencySecondaryColor(currency)).send(commandSender);

                    } else {
                        configManager.getMessage("INVALID-POUCH-AMOUNT").send(commandSender);
                    }
                } else {
                    configManager.getMessage("INVALID-CURRENCY").send(commandSender);
                }
            } else {
                configManager.getMessage("PLAYER-OFFLINE").setPlaceholder("%player%", strings[0]).send(commandSender);
            }
        }
    }
}
