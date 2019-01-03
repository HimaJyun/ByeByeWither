package jp.jyn.byebyewither.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Set;

@SuppressWarnings("unused")
public class Wither implements Listener {

    // allowed worlds
    private final Set<String> worlds;

    public Wither(Set<String> worlds) {
        this.worlds = worlds;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.getEntityType() != EntityType.WITHER) {
            return;
        }
        if (worlds.contains(e.getLocation().getWorld().getName())) {
            return;
        }
        e.setCancelled(true);
    }
}
