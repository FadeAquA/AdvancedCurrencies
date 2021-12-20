package me.aqua.currency.listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.aqua.currency.Currency;
import me.aqua.currency.currencies.CurrencyManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class PouchListener implements Listener {

    @EventHandler
    public void onRedeem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack pouch = event.getPlayer().getInventory().getItemInMainHand();
        if (pouch == null || pouch.getType() == Material.AIR) return;
        NBTItem item = new NBTItem(pouch);
        CurrencyManager manager = new CurrencyManager();

        if (item.hasKey("pouchcurrency")) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                String currency = item.getString("pouchcurrency");
                int min = item.getInteger("pouchmin");
                int max = item.getInteger("pouchmax");

                int number = ThreadLocalRandom.current().nextInt(min, max + 1);
                manager.addCurrency(player, currency, number, "Pouch");

                pouch.setAmount(pouch.getAmount() - 1);
            }
        }
    }
}
