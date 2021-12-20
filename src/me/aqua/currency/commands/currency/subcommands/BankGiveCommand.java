package me.aqua.currency.commands.currency.subcommands;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import tk.piggyster.api.command.SubCommand;
import tk.piggyster.api.config.ConfigManager;

public class BankGiveCommand extends SubCommand {

    @Override
    public String getName() {
        return "give";
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
                    currencyManager.addCurrency(target, currency, value, "Admin");
                    configManager.getMessage("ADMIN-GIVE-CURRENCY").setPlaceholder("%currency%", currency).setPlaceholder("%value%", value).setPlaceholder("&p", currencyManager.getCurrencyPrimaryColor(currency))
                            .setPlaceholder("&s", currencyManager.getCurrencySecondaryColor(currency)).setPlaceholder("%player%", target.getName());

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
