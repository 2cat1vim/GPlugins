package fr.great.gCore.events;

import fr.great.gCore.Utils.Definitions.WorldDef;
import fr.great.gCore.Utils.Functions.Global.Delay;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static fr.great.gCore.Utils.Definitions.BasicDef.*;

public class EventDeath implements Listener {
    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        Delay.Set(()-> {
        p.teleport(WorldDef.LOCATION);
        p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_DEATH, 1.0f, 1.0f);
        p.sendTitle(MSGDEATHTITLE, MSGDEATHSUBTITLE);
        }, 5L);
    }
}
