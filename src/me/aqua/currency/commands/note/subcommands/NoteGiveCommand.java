package me.aqua.currency.commands.note.subcommands;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import me.aqua.currency.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import tk.piggyster.api.command.SubCommand;
import tk.piggyster.api.config.ConfigManager;

public class NoteGiveCommand extends SubCommand {

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public void run(CommandSender commandSender, String[] strings) {
        if (strings.length == 4) {
            ConfigManager configManager = Currency.getInstance().getConfigManager();
            CurrencyManager currencyManager = new CurrencyManager();
            ConfigurationSection currencies = configManager.getConfig("config").getConfigurationSection("Currencies");
            Player target = Bukkit.getPlayer(strings[0]);
            int value = Integer.parseInt(strings[2]);
            int amount = Integer.parseInt(strings[3]);
            String currency = strings[1].toUpperCase();
            if (target != null) {
                if (value > 0) {
                    if (currencies.contains(currency)) {
                        if (amount > 0) {

                            Utils.currencyNote(target, value, configManager.getConfig("config").getString("Items.note.SERVER-SIGNER"), currency, amount);
                            configManager.getMessage("ADMIN-GIVE-NOTE").setPlaceholder("%player%", target.getName()).setPlaceholder("%value%", value)
                                    .setPlaceholder("%amount%", amount).setPlaceholder("%currency%", currency)
                                    .setPlaceholder("&p", currencyManager.getCurrencyPrimaryColor(currency))
                                    .setPlaceholder("&s", currencyManager.getCurrencySecondaryColor(currency)).send(commandSender);

                        } else {
                            configManager.getMessage("INVALID-AMOUNT").send(commandSender);
                        }
                    } else {
                        configManager.getMessage("INVALID-CURRENCY").send(commandSender);
                    }
                } else {
                    configManager.getMessage("INVALID-AMOUNT").send(commandSender);
                }
            } else {
                configManager.getMessage("PLAYER-OFFLINE").setPlaceholder("%player%", strings[0]).send(commandSender);
            }
        }
    }
}
