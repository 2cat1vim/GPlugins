package fr.great.gCore.database;

import fr.great.gCore.bridge.BridgeInventory;
import org.bukkit.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataInventory {
    private BridgeInventory bridgeInventory;

    private final String gameruleSettingsTitle = "§0Gamerule Settings";

    private HashMap<String, List<Material>> inventoryList;

    public void loadHashMap() {
        inventoryList = new HashMap<>();
        inventoryList.put(gameruleSettingsTitle, List.of(Material.MOJANG_BANNER_PATTERN));
    }

    public DataInventory() {
        loadHashMap();
        bridgeInventory = new BridgeInventory(this);
    }

    public BridgeInventory getBridgeInventory() { return bridgeInventory; }
    public List<Material> getInventoryMaterials(String title) { return inventoryList.get(title); }
    public HashMap<String, List<Material>> getInventoryList() { return inventoryList; }
    public String getGameruleSettingsTitle() { return gameruleSettingsTitle; }

    public ItemStack getPreviousPageItem() {
        ItemStack is = new ItemStack(Material.RED_DYE, 1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§cPrevious Page");
        is.setItemMeta(im);
        return is;
    }

    public ItemStack getNextPageItem() {
        ItemStack is = new ItemStack(Material.GREEN_DYE, 1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§aNext Page");
        is.setItemMeta(im);
        return is;
    }

    public List<Inventory> createInventory(List<ItemStack> itemStackList, String title) {
        List<Inventory> invList = new ArrayList<>();
        int nPage = 0, currLocation = 0;
        final int nItem = itemStackList.size();
        final int defaultSize = 27;
        final int slotLimit = defaultSize - 9;

        invList.add(Bukkit.createInventory(null, defaultSize, title + " " + nPage));
        for (int i = 0, j = 0; i < nItem; i++) {
            if (i == slotLimit) {
                currLocation += slotLimit;
                if (currLocation < nItem) {
                    invList.get(nPage).setItem(slotLimit + 5, getNextPageItem());
                }
                if (nPage > 0) {
                    invList.get(nPage).setItem(slotLimit + 3, getPreviousPageItem());
                }
                nPage++;
                invList.add(Bukkit.createInventory(null, defaultSize, title + " " + nPage));
                i = -1;
                continue;
            }
            if (j == nItem) {
                if (nPage > 0) {
                    invList.get(nPage).setItem(slotLimit + 3, getPreviousPageItem());
                }
                break;
            }
            invList.get(nPage).setItem(i, itemStackList.get(j));
            j++;
        }
        return invList;
    }
}
