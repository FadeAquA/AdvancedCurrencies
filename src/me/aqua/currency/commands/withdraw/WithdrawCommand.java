package me.aqua.currency.commands.withdraw;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import me.aqua.currency.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tk.piggyster.api.command.Command;
import tk.piggyster.api.config.ConfigManager;

public class WithdrawCommand extends Command{

    public WithdrawCommand(JavaPlugin plugin) {
        super(plugin, "withdraw");
    }

    @Override
    public void run(CommandSender commandSender, String[] strings) {
        ConfigManager configManager = Currency.getInstance().getConfigManager();
        ConfigurationSection currencies = Currency.getInstance().getConfig().getConfigurationSection("Currencies");
        CurrencyManager manager = new CurrencyManager();
        if (commandSender instanceof Player) {
            final Player player = (Player) commandSender;
            if (player.hasPermission("currency.withdraw")) {
                if (strings.length < 2) {
                    configManager.getMessage("WITHDRAW-USAGE").send(player);
                }
                if (strings.length > 2) {
                    configManager.getMessage("WITHDRAW-USAGE").send(player);
                }
                if (strings.length == 2) {
                    String currency = strings[0].toUpperCase();
                    int value = Integer.parseInt(strings[1]);
                    if (currencies.contains(currency)) {
                        if (manager.getCurrency(player, currency) >= value) {
                            if (value > 0) {
                                Utils.currencyNote(player, value, player.getName(), currency, 1);
                                manager.removeCurrency(player, currency, value, "Withdraw");
                            } else {
                                configManager.getMessage("INVALID-AMOUNT").send(player);
                            }
                        } else {
                            configManager.getMessage("INSUFFICIANT-BALANCE").setPlaceholder("%currency%", currency).send(player);
                        }
                    } else {
                        configManager.getMessage("INVALID-CURRENCY").send(player);
                    }

                }
            }
        } else {
            configManager.getMessage("PLAYER-COMMAND").send(commandSender);
        }
    }
}
