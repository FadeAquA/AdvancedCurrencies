package me.aqua.currency.utils;

import de.tr7zw.nbtapi.NBTItem;
import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.text.WordUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.piggyster.api.color.ColorAPI;
import tk.piggyster.api.menu.item.SimpleItem;

import java.text.DecimalFormat;

public class Utils {

    //private static ConfigurationSection path = Currency.getInstance().getConfigManager().getConfig("config").getConfigurationSection("Items");
    private static FileConfiguration config = Currency.getInstance().getConfig();
    private static DecimalFormat df = new DecimalFormat("#,###.##");
    private static CurrencyManager manager = new CurrencyManager();

    public static ItemStack currencyNote(Player player, int value, String owner, String currency, int amount) {
        ConfigurationSection path = Currency.getInstance().getConfigManager().getConfig("config").getConfigurationSection("Items");
        ItemStack noteItem = new ItemBuilder(Material.valueOf(path.getString("note.material")))
                .setLore(ColorAPI.process(path.getStringList("note.lore")))
                .setName(ColorAPI.process(path.getString("note.name")))
                .setPlaceholder("%value%", df.format(value) + "").setPlaceholder("%signer%", owner).setPlaceholder("%currency%", WordUtils.capitalizeFully(currency)).setPlaceholder("&p", manager.getCurrencyPrimaryColor(currency)).setPlaceholder("&s", manager.getCurrencySecondaryColor(currency))
                .setEnchanted(path.getBoolean("note.glowing"))
                .build();

        NBTItem item = new NBTItem(noteItem);
        item.setInteger("value", value);
        item.setString("note", currency);
        noteItem = item.getItem();

        noteItem.setAmount(amount);

        player.getInventory().addItem(noteItem);
        return noteItem;
    }

    public static ItemStack currencyPouch(Player player, int min, int max, String currency, int amount) {
        ConfigurationSection path = Currency.getInstance().getConfigManager().getConfig("config").getConfigurationSection("Items");
        ItemStack pouch = new ItemBuilder(Material.valueOf(path.getString("pouch.material")))
                .setName(ColorAPI.process(path.getString("pouch.name")))
                .setEnchanted(path.getBoolean("pouch.enchanted"))
                .setLore(ColorAPI.process(path.getStringList("pouch.lore")))
                .setPlaceholder("%min%", df.format(min) + "").setPlaceholder("%max%", df.format(max) + "").setPlaceholder("%currency%", WordUtils.capitalizeFully(currency)).setPlaceholder("&p", manager.getCurrencyPrimaryColor(currency)).setPlaceholder("&s", manager.getCurrencySecondaryColor(currency))
                .build();

        NBTItem item = new NBTItem(pouch);
        item.setInteger("pouchmin", min);
        item.setInteger("pouchmax", max);
        item.setString("pouchcurrency", currency);
        pouch = item.getItem();

        pouch.setAmount(amount);
        player.getInventory().addItem(pouch);

        return pouch;
    }
}
