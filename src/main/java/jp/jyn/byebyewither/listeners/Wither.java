package jp.jyn.byebyewither.listeners;

import java.util.Set;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;

public class Wither implements Listener {

	// 許可されている世界
	private final Set<String> worlds;

	/**
	 * ウィザーの発生イベントを阻止
	 *
	 * @param worlds
	 *            許可されている世界のSet
	 */
	public Wither(Plugin plugin, Set<String> worlds) {
		this.worlds = worlds;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void CreatureSpawn(CreatureSpawnEvent e) {
		if (e.getEntityType() == EntityType.WITHER && // 召喚されるのがウィザーかつ
				!worlds.contains(e.getLocation().getWorld().getName().toLowerCase())) { // 許可されていない場合
			// キャンセル
			e.setCancelled(true);
			return;
		}
	}
}
