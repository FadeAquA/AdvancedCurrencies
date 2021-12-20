package me.aqua.currency.listeners;

import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.piggyster.api.color.ColorAPI;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            CurrencyManager.createCurrencyData(event.getPlayer());
            System.out.println(ColorAPI.process("&aCreating default values for &3" + event.getPlayer().getName()));
        }
    }
}
