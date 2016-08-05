package jp.jyn.byebyewither;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import jp.jyn.byebyewither.listeners.EnderDragon;
import jp.jyn.byebyewither.listeners.Wither;

public class ByeByeWither extends JavaPlugin {

	// 設定ロード済みか
	private boolean isLoaded = false;

	@Override
	public void onEnable() {
		// デフォルト設定を書き出し
		saveDefaultConfig();

		if (isLoaded) { // もし起動済みなら
			// リロード
			reloadConfig();
		}
		// 設定を取得
		FileConfiguration conf = getConfig();

		if (conf.getBoolean("Wither.Enable", false)) {
			new Wither(this, loadWorld(conf.getStringList("Wither.Allow"), "Wither"));
		}

		if (conf.getBoolean("EnderDragon.Enable", false)) {
			new EnderDragon(this, loadWorld(conf.getStringList("EnderDragon.Allow"), "EnderDragon"));
		}
		isLoaded = true;
	}

	@Override
	public void onDisable() {
		// イベントを全解除
		HandlerList.unregisterAll((Plugin) this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length != 0 && // 引数があり
				args[0].equalsIgnoreCase("reload")) { // reloadなら
			if (!sender.hasPermission("byebyewither.reload")) { // 権限がなければ
				// エラー
				sender.sendMessage("[" + ChatColor.RED + "ByeByeWither" + ChatColor.RESET + "] " + ChatColor.RED
						+ "You don't have permission.");
			} else {
				// リロード(単にDisableとEnableを呼ぶだけでよし)
				onDisable();
				onEnable();
			}
		} else { // 引数がない
			// 宣伝しちゃう
			sender.sendMessage(new String[] {
					ChatColor.GREEN + "=========" + ChatColor.WHITE + " BeyBeyWither " + ChatColor.GREEN + "=========",
					"Author: @HimaJyun( https://jyn.jp/ )", "/byebyewither reload - Reload config." });
		}
		return true;
	}

	/**
	 * 世界の一覧をSetにします。
	 *
	 * @param worlds
	 *            世界の名前を含むList
	 * @param logInfo
	 *            どの設定に対するロードか
	 * @return 変換後のSet、各Stringは小文字に変換されます。
	 */
	Set<String> loadWorld(List<String> worlds, String section) {
		Set<String> result = new HashSet<>();
		section += "->";
		for (String world : worlds) {
			result.add(world.toLowerCase());
			getLogger().info(section + world);
		}
		return result;
	}
}
