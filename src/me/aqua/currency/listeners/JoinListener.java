package me.aqua.currency.listeners;

import me.aqua.currency.currencies.CurrencyManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.net.http.WebSocket;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            CurrencyManager.createCurrencyData(event.getPlayer());
        }
    }
}
