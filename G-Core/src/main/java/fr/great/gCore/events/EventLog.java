package fr.great.gCore.events;
import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.database.DataManager;
import fr.great.gCore.database.DataRole;
import fr.great.gCore.di.Context;
import fr.great.gCore.utils.Delay;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class EventLog implements Listener {
    private final DataManager dm = Context.getInstance().getDataManager();
    private final DataRole dr = Context.getInstance().getDataRole();
    private final ConfigManager cm = Context.getInstance().getConfigManager();

    public void addPlayerToSQL(Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(cm.getPlugin(), () -> {
            try {
                dm.addPlayer(p);
                int r = dr.getRole(p);
                ChatColor color = dr.getNameTagColor(p, r);
                dr.setNameTagColor(p, color, dr.getRole(p));
            } catch (SQLException e) {
                p.sendMessage("SQL Error, contact staff -> Log[addPlayerToSQL]");
            }
        });
    }

    public void spawnPlayer(Player p) {
        p.setGameMode(GameMode.ADVENTURE);
        p.teleport(cm.getSpawnLocation());
        p.setHealth(p.getAttribute(Attribute.MAX_HEALTH).getValue());
        p.setFoodLevel((int)p.getAttribute(Attribute.MAX_HEALTH).getValue());
        Delay.Set(() ->
                p.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_0, 1.0f, 1.5f)
        ,20L);
        Bukkit.getServer().sendPlainMessage(cm.getWelcomeMessage().replace("<player>", p.getName()));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.joinMessage(null);
        Player p = e.getPlayer();
        addPlayerToSQL(p);
        spawnPlayer(p);
    }

    @EventHandler
    public void OnPlayerQuit(PlayerQuitEvent e) {
        e.quitMessage(null);
    }
}
