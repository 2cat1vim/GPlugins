package fr.great.gCore.events;

import fr.great.gCore.database.DataInventory;
import fr.great.gCore.di.Context;
import org.bukkit.Sound;
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

    public String getKeyTitle(String title) {
        int isValid = 0;
        String keyTitle = "";
        for (String key : di.getInventoryList().keySet()) {
            if (title.contains(key)) {
                keyTitle = key;
                isValid = 1;
            }
        }
        if (isValid == 0) {
            return null;
        }
        return keyTitle;
    }

    @EventHandler
    public void onBasicInventoryClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        ClickType action = e.getClick();
        String title = e.getView().getTitle();
        ItemStack is = e.getCurrentItem();
        int currentPage = 0;

        if (is == null)
            return ;

        String keyTitle = getKeyTitle(title);
        if (keyTitle == null) {
            return ;
        }

        try {
            currentPage = Integer.parseInt(title.substring((title.length() - 1)));
        }
        catch (NumberFormatException ex) {
            sendError("NumberFormatException > Contact dev", p);
        }

        e.setCancelled(true);

        if (action.equals(ClickType.LEFT)) {
            if (di.getInventoryMaterials(keyTitle).contains(is.getType())) {
                di.getBridgeInventory().executeActionAndExit(keyTitle, is, currentPage, p);
            }
            if (is.getItemMeta().getDisplayName().equals("§cPrevious Page")) {
                di.getBridgeInventory().switchPage(keyTitle, currentPage - 1, p);
            }
            if (is.getItemMeta().getDisplayName().equals("§aNext Page")) {
                di.getBridgeInventory().switchPage(keyTitle, currentPage + 1, p);
            }
        }
    }
}
