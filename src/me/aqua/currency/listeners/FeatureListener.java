package me.aqua.currency.listeners;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import tk.piggyster.api.config.ConfigManager;

import java.util.concurrent.ThreadLocalRandom;

public class FeatureListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Entity entity = event.getEntity();
        ConfigManager configManager = Currency.getInstance().getConfigManager();
        final boolean enabled = configManager.getConfig("features").getBoolean("MOB-KILL.ENABLED");
        if (enabled && killer != null) {
            ConfigurationSection section = configManager.getConfig("features").getConfigurationSection("MOB-KILL.MOBS");
            for (final String mobs : section.getKeys(false)) {
                if (entity.getType() == EntityType.valueOf(mobs.toUpperCase())) {
                    final String currency = section.getString(mobs + ".currency");
                    final int amount = section.getInt(mobs + ".amount");
                    final int chance = section.getInt(mobs + ".chance");
                    final CurrencyManager currencyManager = new CurrencyManager();
                    int finalChance = ThreadLocalRandom.current().nextInt(0, 100 + 1);

                    if (finalChance <= chance) {
                        currencyManager.addCurrency(killer, currency, amount, "Mob Kill");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ConfigManager configManager = Currency.getInstance().getConfigManager();
        final boolean enabled = configManager.getConfig("features").getBoolean("BLOCK-BREAK.ENABLED");
        if (enabled) {
            ConfigurationSection section = configManager.getConfig("features").getConfigurationSection("BLOCK-BREAK.BLOCKS");
            for (final String blocks : section.getKeys(false)) {
                if (block.getType() == Material.valueOf(blocks.toUpperCase())) {
                    final String currency = section.getString(blocks + ".currency");
                    final int amount = section.getInt(blocks + ".amount");
                    final int chance = section.getInt(blocks + ".chance");
                    final CurrencyManager currencyManager = new CurrencyManager();
                    int finalChance = ThreadLocalRandom.current().nextInt(0, 100 + 1);

                    if (finalChance <= chance) {
                        currencyManager.addCurrency(player, currency, amount, "Block Break");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block  = event.getBlock();
        ConfigManager configManager = Currency.getInstance().getConfigManager();
        final boolean enabled = configManager.getConfig("features").getBoolean("BLOCK-PLACE.ENABLED");

        if (enabled) {
            ConfigurationSection section = configManager.getConfig("features").getConfigurationSection("BLOCK-PLACE.BLOCKS");
            for (final String blocks : section.getKeys(false)) {
                if (block.getType() == Material.valueOf(blocks.toUpperCase())) {
                    final String currency = section.getString(blocks + ".currency");
                    final int amount = section.getInt(blocks + ".amount");
                    final int chance = section.getInt(blocks + ".chance");
                    final CurrencyManager currencyManager = new CurrencyManager();
                    int finalChance = ThreadLocalRandom.current().nextInt(0, 100 + 1);

                    if (finalChance <= chance) {
                        currencyManager.addCurrency(player, currency, amount, "Block Place");
                    }
                }
            }
        }
    }
}
