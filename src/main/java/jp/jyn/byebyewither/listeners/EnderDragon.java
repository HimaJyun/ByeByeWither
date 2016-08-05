package jp.jyn.byebyewither.listeners;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class EnderDragon implements Listener {

	// 許可されている世界
	private final Set<String> worlds;

	/**
	 * エンダークリスタル設置イベント阻止
	 *
	 * @param worlds
	 *            許可されている世界のSet
	 */
	public EnderDragon(Plugin plugin, Set<String> worlds) {
		this.worlds = worlds;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void BlockPlace(PlayerInteractEvent e) {
		// ブロックを右クリック以外なら終了
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		Player player = e.getPlayer();
		World world = player.getWorld();
		if (world.getEnvironment() == Environment.THE_END && // 世界がThe Endかつ
				e.getMaterial() == Material.END_CRYSTAL && // アイテムがエンドクリスタルかつ
				e.getClickedBlock().getType() == Material.BEDROCK && // クリックしたのが岩盤で
				!worlds.contains(world.getName().toLowerCase()) && // 許可されておらず
				!player.hasPermission("byebyewither.allowdragon") // 権限を持たない
		) { // 場合に
			// キャンセル
			e.setCancelled(true);
			return;
		}
	}
}
