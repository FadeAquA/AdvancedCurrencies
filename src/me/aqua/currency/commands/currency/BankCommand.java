package me.aqua.currency.commands.currency;

import me.aqua.currency.Currency;
import me.aqua.currency.commands.currency.subcommands.BankGiveCommand;
import me.aqua.currency.commands.currency.subcommands.BankSetCommand;
import me.aqua.currency.commands.currency.subcommands.BankTakeCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import tk.piggyster.api.color.ColorAPI;
import tk.piggyster.api.command.Command;
import tk.piggyster.api.config.ConfigManager;

public class BankCommand extends Command {

    public BankCommand(JavaPlugin plugin) {
        super(plugin, "bank");
        addSubCommand(new BankGiveCommand());
        addSubCommand(new BankTakeCommand());
        addSubCommand(new BankSetCommand());
    }

    @Override
    public void run(CommandSender commandSender, String[] strings) {
        if (commandSender.hasPermission("currency.admin")) {
            if (strings.length < 4) {
                ConfigManager configManager = Currency.getInstance().getConfigManager();
                for (String messages : configManager.getConfig("messages").getConfigurationSection("messages").getStringList("BANK-HELP")) {
                    commandSender.sendMessage(ColorAPI.process(messages));
                }
            }
        } else {
            ConfigManager configManager = Currency.getInstance().getConfigManager();
            configManager.getMessage("NO-PERMISSION").send(commandSender);
        }
    }
}
