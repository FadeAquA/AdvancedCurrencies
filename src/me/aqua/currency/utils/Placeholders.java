package me.aqua.currency.utils;

import java.text.DecimalFormat;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.Bukkit;

public class Placeholders extends PlaceholderExpansion {
    private static Currency plugin;

    public Placeholders(final Currency mainClass) {
    }

    public String getAuthor() {
        return "AquA_iReapzZ";
    }

    public String getIdentifier() {
        return "bank";
    }

    public String getVersion() {
        return "1.0.0";
    }

    public boolean canRegister() {
        return true;
    }

    public String onRequest(final OfflinePlayer player, final String params) {
        for (String currency : Currency.getInstance().getConfig().getConfigurationSection("Currencies").getKeys(false)) {
            if (params.equalsIgnoreCase(currency)) {
                CurrencyManager manager = new CurrencyManager();
                return Formatter.formatter(manager.getCurrency(player, currency));
            }
        }
        return null;
    }
}

