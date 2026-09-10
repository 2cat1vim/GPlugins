package fr.great.gCore.database;

import org.bukkit.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataInventory {
    private final int defaultSize = 27;
    private final int slotLimit = defaultSize - 9;

    private final String gameruleSettingsTitle = "§0Gamerule Settings";
    private final String worldSelectorTitle = "§0World Selector";

    private final HashMap<String, List<Material>> inventoryList;

    public DataInventory() {
        inventoryList = new HashMap<>();
        inventoryList.put(gameruleSettingsTitle, List.of(Material.MOJANG_BANNER_PATTERN));
        inventoryList.put(worldSelectorTitle, List.of(Material.GRASS_BLOCK));
    }

    public List<Material> getInventoryMaterials(String title) {
        return inventoryList.get(title);
    }

    public HashMap<String, List<Material>> getInventoryList() { return inventoryList; }
    public String getGameruleSettingsTitle() { return gameruleSettingsTitle; }
    public String getWorldSelectorTitle() { return worldSelectorTitle; }

    public boolean executeActionAndExit(String title, ItemStack itemStack) {
        if (title.equals(getGameruleSettingsTitle())) {
            World world = Bukkit.getWorld("World");
            String itemName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
            GameRule<Boolean> gameRule = GameRule.getByName(itemName);
            boolean newValue = !(world.getGameRuleValue(gameRule));
            world.setGameRule(gameRule, newValue);
            return false;
        }
        return true;
    }

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
        int nItem = itemStackList.size();

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

    public List<Inventory> getGameruleInventory() {
        List<ItemStack> itemStackList = new ArrayList<>();

        for (GameRule<?> rule : Registry.GAME_RULE) {
            if (rule.getType() == Boolean.class) {
                @SuppressWarnings("unchecked")
                GameRule<Boolean> boolRule = (GameRule<Boolean>) rule;
                World world = Bukkit.getWorld("World");
                boolean value = world.getGameRuleValue(boolRule);

                ItemStack is = new ItemStack(Material.MOJANG_BANNER_PATTERN, 1);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName("§e" + boolRule.getName());

                String boolColored = value ? "§atrue" : "§cfalse";
                String boolOppositeColored = value ? "§cfalse" : "§atrue";

                im.setLore(List.of("§7Value is currently: " + boolColored,
                        "§7Press LEFT-CLICK = " + boolOppositeColored));
                is.setItemMeta(im);
                itemStackList.add(is);
            }
        }
        return createInventory(itemStackList, "§0Gamerule Settings");
    }
}
