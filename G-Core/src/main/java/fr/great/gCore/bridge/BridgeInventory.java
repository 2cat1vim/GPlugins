package fr.great.gCore.bridge;

import fr.great.gCore.database.DataInventory;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class BridgeInventory {

    private final DataInventory di;

    private List<Function<Player, List<Inventory>>> supplierInventoryGet;
    private List<Supplier<String>> supplierInventoryGetName;


    public void loadSupplierInventoryGet() {
        supplierInventoryGet = new ArrayList<>();
        supplierInventoryGet.add(this::getGameruleInventory);
    }

    public void loadSupplierInventoryGetName() {
        supplierInventoryGetName = new ArrayList<>();
        supplierInventoryGetName.add(di::getGameruleSettingsTitle);
    }

    public BridgeInventory(DataInventory dataInventory) {
        this.di = dataInventory;
        loadSupplierInventoryGet();
        loadSupplierInventoryGetName();
    }

    public void switchPage(String title, int newPage, Player p) {
        for (int i = 0; i < supplierInventoryGetName.size(); i++) {
            if (supplierInventoryGetName.get(i).get().equals(title)) {
                p.openInventory(supplierInventoryGet.get(i).apply(p).get(newPage));
                break ;
            }
        }
        p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
    }

    public void executeActionAndExit(String title, ItemStack itemStack, int currentPage, Player p) {
        if (title.equals(di.getGameruleSettingsTitle())) {
            World world = p.getWorld();
            String itemName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
            GameRule<Boolean> gameRule = GameRule.getByName(itemName);
            boolean newValue = !(world.getGameRuleValue(gameRule));
            world.setGameRule(gameRule, newValue);
            p.openInventory(getGameruleInventory(p).get(currentPage));
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
            return ;
        }
        p.closeInventory();
    }

    public List<Inventory> getGameruleInventory(Player p) {
        List<ItemStack> itemStackList = new ArrayList<>();

        for (GameRule<?> rule : Registry.GAME_RULE) {
            if (rule.getType() == Boolean.class) {
                @SuppressWarnings("unchecked")
                GameRule<Boolean> boolRule = (GameRule<Boolean>) rule;
                World world = p.getWorld();
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
        return di.createInventory(itemStackList, di.getGameruleSettingsTitle());
    }
}
