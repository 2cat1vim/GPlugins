package fr.great.gFFA.manager;

import fr.great.gCore.utils.Delay;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ManagerDuelRequest {
    private final long MINUTE = 1200L;
    private final HashMap<Player, Player> duelRequestMap;

    public ManagerDuelRequest() {
        this.duelRequestMap = new HashMap<>();
    }

    public void addPendingRequest(Player key, Player value) {
        duelRequestMap.putIfAbsent(key, value);
    }

    public void removePendingRequest(Player key, Player value) {
        duelRequestMap.remove(key, value);
    }

    public boolean hasPendingRequest(Player key, Player value) {
        if (duelRequestMap.containsKey(key)) {
            for (Map.Entry<Player, Player> entry : duelRequestMap.entrySet()) {
                if (entry.getValue() == value && entry.getKey() == key) {
                    return true;
                }
            }
        }
        return false;
    }

    public void timeoutRequest(Player key, Player value) {
        Delay.Set(() -> duelRequestMap.remove(key, value), MINUTE);
    }
}
