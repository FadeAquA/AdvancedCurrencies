package me.aqua.currency.commands.currency.subcommands;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import tk.piggyster.api.command.SubCommand;
import tk.piggyster.api.config.ConfigManager;

public class BankTakeCommand extends SubCommand {

    @Override
    public String getName() {
        return "take";
    }

    @Override
    public void run(CommandSender commandSender, String[] strings) {
        ConfigManager configManager = Currency.getInstance().getConfigManager();
        CurrencyManager currencyManager = new CurrencyManager();
        ConfigurationSection currencies = configManager.getConfig("config").getConfigurationSection("Currencies");
        Player target = Bukkit.getPlayer(strings[0]);
        String currency = strings[1].toUpperCase();
        int value = Integer.parseInt(strings[2]);

        if (target != null) {
            if (currencies.contains(currency)) {
                if (value > 0) {
                    if (currencyManager.getCurrency(target, currency) >= value) {

                        currencyManager.removeCurrency(target, currency, value, "Admin");
                        configManager.getMessage("ADMIN-TAKE-CURRENCY").setPlaceholder("%player%", target.getName())
                                .setPlaceholder("%currency%", currency).setPlaceholder("%value%", value)
                                .setPlaceholder("&p", currencyManager.getCurrencyPrimaryColor(currency))
                                .setPlaceholder("&s", currencyManager.getCurrencySecondaryColor(currency)).send(commandSender);

                    } else {
                        configManager.getMessage("INSUFFICIANT-CURRENCY").setPlaceholder("%currency%", currency).send(commandSender);
                    }
                } else {
                    configManager.getMessage("INVALID-AMOUNT").send(commandSender);
                }
            } else {
                configManager.getMessage("INVALID-CURRENCY").send(commandSender);
            }
        } else {
            configManager.getMessage("PLAYER-OFFLINE").setPlaceholder("%player%", strings[0]).send(commandSender);
        }
    }
}
