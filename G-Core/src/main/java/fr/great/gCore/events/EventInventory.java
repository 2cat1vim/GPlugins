package fr.great.gCore.events;

import fr.great.gCore.database.DataInventory;
import fr.great.gCore.di.Context;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static fr.great.gCore.utils.Logger.*;

public class EventInventory implements Listener {

    private DataInventory di = Context.getInstance().getDataInventory();

    @EventHandler
    public void onBasicInventoryClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        ClickType action = e.getClick();
        String title = e.getView().getTitle(), keyTitle = "";
        ItemStack is = e.getCurrentItem();
        if (is == null)
            return ;
        int current_page = 0, hasKey = 0;
        for (String key : di.getInventoryList().keySet()) {
            if (title.contains(key)) {
                keyTitle = key;
                hasKey = 1;
            }
        }
        if (hasKey == 0) {
            return ;
        }
        try {
            current_page = Integer.parseInt(title.substring((title.length() - 1)));
        }
        catch (NumberFormatException ex) {
            sendError("NumberFormatException > Contact dev", p);
        }
        e.setCancelled(true);
        if (action.equals(ClickType.LEFT)) {
            if (di.getInventoryMaterials(keyTitle).contains(is.getType())) {
                if (di.executeActionAndExit(keyTitle, is)) {
                    p.closeInventory();
                    return ;
                }
                p.openInventory(di.getGameruleInventory().get(current_page));
                return ;
            }
            if (is.getItemMeta().getDisplayName().equals("§cPrevious Page")) {
                p.openInventory(di.getGameruleInventory().get(current_page - 1));
            }
            if (is.getItemMeta().getDisplayName().equals("§aNext Page")) {
                p.openInventory(di.getGameruleInventory().get(current_page + 1));
                sendSuccess("Previous: " + current_page + ", New: " + (current_page + 1), p);
            }
        }
    }
}
