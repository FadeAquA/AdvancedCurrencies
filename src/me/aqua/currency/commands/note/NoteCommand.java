package me.aqua.currency.commands.note;

import me.aqua.currency.Currency;
import me.aqua.currency.commands.note.subcommands.NoteGiveCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import tk.piggyster.api.color.ColorAPI;
import tk.piggyster.api.command.Command;
import tk.piggyster.api.config.ConfigManager;

public class NoteCommand extends Command {

    public NoteCommand(JavaPlugin plugin) {
        super(plugin, "note");
        addSubCommand(new NoteGiveCommand());
    }

    @Override
    public void run(CommandSender commandSender, String[] strings) {
        if (commandSender.hasPermission("currency.admin")) {
            if (strings.length < 5) {
                ConfigManager configManager = Currency.getInstance().getConfigManager();
                for (String message : configManager.getConfig("messages").getConfigurationSection("messages").getStringList("ITEM-HELP")) {
                    commandSender.sendMessage(ColorAPI.process(message));
                }
            }
        }
    }
}
