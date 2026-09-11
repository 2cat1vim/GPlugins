package fr.great.gFFA.events;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.di.API;
import fr.great.gCore.utils.Delay;
import fr.great.gFFA.di.FFAContext;
import fr.great.gFFA.manager.ManagerDuel;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EventDuelDeath implements Listener {

    private ManagerDuel md = FFAContext.getInstance().getManagerDuel();
    private ConfigManager cm = API.get().configManager();

    public void eraseDuelAndGreatWinner(Player p, Player k) {
        md.eraseDuel(p, k);
        Bukkit.getServer().sendPlainMessage(k.getName() + " killed " + p.getName() + " in a private duel");
        k.sendTitle("§aYou won", "§7You are just better");
        k.playSound(k.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 2.0f);
        p.getWorld().strikeLightningEffect(p.getLocation());
        Delay.Set(() -> k.teleport(this.cm.getSpawnLocation()), 20L);
    }

    public void terminateDuel(Player p, Player k) {
        if (k == null) {
            k = md.getOpponent(p);
            if (k != null) {
                eraseDuelAndGreatWinner(p, k);
            }
        }
        if (md.hasDuel(p, k)) {
            eraseDuelAndGreatWinner(p, k);
        }
    }

    @EventHandler
    public void onPlayerDeathInDuel(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        terminateDuel(p, p.getKiller());
    }
}
