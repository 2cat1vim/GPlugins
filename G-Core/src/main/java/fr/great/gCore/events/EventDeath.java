package fr.great.gCore.events;

import fr.great.gCore.di.Context;
import fr.great.gCore.utils.Delay;
import fr.great.gCore.config.ConfigManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EventDeath implements Listener {

    private final ConfigManager cm = Context.getInstance().getConfigManager();

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        Delay.Set(()-> {
        p.teleport(cm.getSpawnLocation());
        p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_DEATH, 1.0f, 1.0f);
        p.sendTitle(cm.getDeathTitle(), cm.getDeathSubTitle());
        }, 5L);
    }
}
