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

public class NoteListener implements Listener {

    @EventHandler
    public void onRedeem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem.hasKey("note")) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                CurrencyManager manager = new CurrencyManager();
                String currency = nbtItem.getString("note");
                int value = nbtItem.getInteger("value");

                manager.addCurrency(player, currency, value, "Deposit");

                item.setAmount(item.getAmount() - 1);
            }
        }
    }
}
