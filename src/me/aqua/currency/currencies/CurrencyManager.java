package me.aqua.currency.currencies;

import me.aqua.currency.Currency;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import tk.piggyster.api.color.ColorAPI;
import tk.piggyster.api.config.ConfigManager;

import java.text.DecimalFormat;

public class CurrencyManager {

    ConfigurationSection path = Currency.getInstance().getConfig().getConfigurationSection("Currencies");
    private static FileConfiguration data = Currency.getInstance().getConfigManager().getConfig("data");
    private static ConfigManager configManager = Currency.getInstance().getConfigManager();
    ConfigurationSection messages = configManager.getConfig("messages").getConfigurationSection("messages");
    DecimalFormat df = new DecimalFormat("#,###.##");


    public int getCurrency(OfflinePlayer player, String currency) {
        return data.getInt("data." + player.getUniqueId() + "." + currency);
    }

    public void addCurrency(OfflinePlayer player, String currency , int amount, String reason) {
        String symbol = Currency.getInstance().getConfig().getString("Currencies." + currency + ".symbol");
        data.set("data." + player.getUniqueId() + "." + currency, getCurrency(player, currency) + amount);
        if (player.isOnline()) {
            Player onlinePlayer = (Player) player;
            onlinePlayer.sendMessage(ColorAPI.process(messages.getString("GIVE-CURRENCY").replace("%currency%", currency).replace("%symbol%", symbol).replace("%amount%", df.format(amount) + "").replace("%reason%", reason)));
        }
        configManager.saveConfig("data");
    }

    public void removeCurrency(OfflinePlayer player, String currency , int amount, String reason) {
        data.set("data." + player.getUniqueId() + "." + currency, getCurrency(player, currency) - amount);
        String symbol = Currency.getInstance().getConfig().getString("Currencies." + currency + ".symbol");
        if (player.isOnline()) {
            Player onlinePlayer = (Player) player;
            onlinePlayer.sendMessage(ColorAPI.process(messages.getString("TAKE-CURRENCY").replace("%currency%", currency).replace("%symbol%", symbol).replace("%amount%", df.format(amount) + "").replace("%reason%", reason)));
        }
        configManager.saveConfig("data");
    }


    public void setCurrency(OfflinePlayer player, String currency , int amount) {
        data.set("data." + player.getUniqueId() + "." + currency, amount);
        configManager.saveConfig("data");
    }

    public static void createCurrencyData(Player player) {
        for (String currency : Currency.getInstance().getConfig().getConfigurationSection("Currencies").getKeys(false)) {
            ConfigurationSection path = Currency.getInstance().getConfig().getConfigurationSection("Currencies." + currency);
            int amountOnJoin = path.getInt("join-amount");
            data.set("data." + player.getUniqueId() + "." + currency, amountOnJoin);
            configManager.saveConfig("data");
        }
    }

    public String getCurrencyPrimaryColor(String currency) {
        return ColorAPI.process(Currency.getInstance().getConfig().getString("Currencies." + currency + ".primary"));
    }

    public String getCurrencySecondaryColor(String currency) {
        return ColorAPI.process(Currency.getInstance().getConfig().getString("Currencies." + currency + ".secondary"));
    }
}
