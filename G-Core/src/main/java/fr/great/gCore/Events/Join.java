package fr.great.gCore.Events;
import fr.great.gCore.Database.Manager;
import fr.great.gCore.Utils.Definitions.WorldDef;
import fr.great.gCore.Utils.Functions.Global.Delay;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import fr.great.gCore.Utils.Definitions.BasicDef;
import fr.great.gCore.Utils.Functions.Global.Error;

import java.sql.SQLException;

public class Join implements Listener {
    private final Manager manager;

    public Join(Manager manager) {
        this.manager = manager;
    }

    public void addPlayerToSQL(Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(BasicDef.PLUGIN, () -> {
            try {
                manager.addPlayer(p);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void spawnPlayer(Player p) {
        p.setGameMode(GameMode.ADVENTURE);
        p.teleport(WorldDef.LOCATION);
        Delay.Set(() ->
                p.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_0, 1.0f, 1.5f)
        ,20L);
        if (BasicDef.WSTART == null) {
            Error.sendError("welcomeMessageStart is empty in config", p);
            return ;
        }
        if (BasicDef.WEND == null) {
            Error.sendError("welcomeMessageEnd is empty in config", p);
            return ;
        }
        String welcome = BasicDef.PREFIX + ChatColor.GREEN + BasicDef.WSTART + p.getName() + BasicDef.WEND;
        Bukkit.getServer().sendPlainMessage(welcome);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.joinMessage(null);
        Player p = e.getPlayer();
        addPlayerToSQL(p);
        spawnPlayer(p);
    }
}
