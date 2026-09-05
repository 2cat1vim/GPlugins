package fr.great.gCore.events;
import fr.great.gCore.database.DataManager;
import fr.great.gCore.database.DataRole;
import fr.great.gCore.Utils.Definitions.WorldDef;
import fr.great.gCore.Utils.Functions.Global.Delay;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import fr.great.gCore.Utils.Definitions.BasicDef;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class EventLog implements Listener {
    private final DataManager manager;
    private final DataRole role;

    public EventLog(DataManager manager, DataRole role) {
        this.manager = manager;
        this.role = role;
    }

    public void addPlayerToSQL(Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(BasicDef.PLUGIN, () -> {
            try {
                manager.addPlayer(p);
                ChatColor color = role.getNameTagColor(p);
                role.setNameTagColor(p, color);
            } catch (SQLException e) {
                p.sendMessage("SQL Error, contact staff -> Log[addPlayerToSQL]");
            }
        });
    }

    public void spawnPlayer(Player p) {
        p.setGameMode(GameMode.ADVENTURE);
        p.teleport(WorldDef.LOCATION);
        p.setHealth(p.getAttribute(Attribute.MAX_HEALTH).getValue());
        p.setFoodLevel((int)p.getAttribute(Attribute.MAX_HEALTH).getValue());
        Delay.Set(() ->
                p.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_0, 1.0f, 1.5f)
        ,20L);

        Bukkit.getServer().sendPlainMessage(BasicDef.MSGWELCOME.replace("<player>", p.getName()));
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
