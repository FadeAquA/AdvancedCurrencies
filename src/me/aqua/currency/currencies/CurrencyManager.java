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
        String symbol = configManager.getConfig("config").getString("Currencies." + currency + ".symbol");
        data.set("data." + player.getUniqueId() + "." + currency, getCurrency(player, currency) + amount);
        if (player.isOnline()) {
            Player onlinePlayer = (Player) player;
            configManager.getMessage("GIVE-CURRENCY").setPlaceholder("%symbol%", symbol).setPlaceholder("%currency%", currency).setPlaceholder("%value%", df.format(amount))
                    .setPlaceholder("%reason%", reason).send(onlinePlayer);
        }
        configManager.saveConfig("data");
    }

    public void removeCurrency(OfflinePlayer player, String currency , int amount, String reason) {
        data.set("data." + player.getUniqueId() + "." + currency, getCurrency(player, currency) - amount);
        String symbol = configManager.getConfig("config").getString("Currencies." + currency + ".symbol");
        if (player.isOnline()) {
            Player onlinePlayer = (Player) player;
            configManager.getMessage("TAKE-CURRENCY").setPlaceholder("%symbol%", symbol).setPlaceholder("%currency%", currency).setPlaceholder("%reason%", reason)
                    .setPlaceholder("%value%", df.format(amount)).send(onlinePlayer);
        }
        configManager.saveConfig("data");
    }


    public void setCurrency(OfflinePlayer player, String currency , int amount) {
        data.set("data." + player.getUniqueId() + "." + currency, amount);
        configManager.saveConfig("data");
    }

    public static void createCurrencyData(Player player) {
        for (String currency : configManager.getConfig("config").getConfigurationSection("Currencies").getKeys(false)) {
            ConfigurationSection path = configManager.getConfig("config").getConfigurationSection("Currencies." + currency);
            int amountOnJoin = path.getInt("join-amount");
            data.set("data." + player.getUniqueId() + "." + currency, amountOnJoin);
            configManager.saveConfig("data");
        }
    }

    public String getCurrencyPrimaryColor(String currency) {
        return ColorAPI.process(configManager.getConfig("config").getString("Currencies." + currency + ".primary"));
    }

    public String getCurrencySecondaryColor(String currency) {
        return ColorAPI.process(configManager.getConfig("config").getString("Currencies." + currency + ".secondary"));
    }
}
