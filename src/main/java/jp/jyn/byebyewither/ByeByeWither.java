package jp.jyn.byebyewither;

import jp.jyn.byebyewither.listeners.EnderDragon;
import jp.jyn.byebyewither.listeners.Wither;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public class ByeByeWither extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        FileConfiguration conf = getConfig();

        if (conf.getBoolean("Wither.Enable", false)) {
            getServer().getPluginManager().registerEvents(new Wither(new HashSet<>(conf.getStringList("Wither.Allow"))), this);
        }

        if (conf.getBoolean("EnderDragon.Enable", false)) {
            getServer().getPluginManager().registerEvents(new EnderDragon(new HashSet<>(conf.getStringList("EnderDragon.Allow"))), this);
        }
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(ChatColor.GREEN + "=========" + ChatColor.WHITE + " BeyBeyWither " + ChatColor.GREEN + "=========");
            sender.sendMessage("Developer: HimaJyun( https://jyn.jp/ )");
            sender.sendMessage("/byebyewither reload - Reload config.");
            return true;
        }

        if (!sender.hasPermission("byebyewither.reload")) {
            sender.sendMessage("[ByeByeWither] " + ChatColor.RED + "You don't have permission.");
            return true;
        }

        onDisable();
        onEnable();
        sender.sendMessage("[ByeByeWither] Config reloaded.");
        return true;
    }
}
