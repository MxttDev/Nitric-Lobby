package Asylx.NitricLobby.Events;

import Asylx.NitricLobby.Main;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class onJoin implements Listener {

    Main plugin;

    public onJoin(Main main) {
        this.plugin = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.setGameMode(GameMode.ADVENTURE);

        p.setPlayerListName(ChatColor.translateAlternateColorCodes('&', plugin.chat.getPlayerPrefix(p)+" &7"+p.getName()));
        p.setCollidable(false);



        if (p.hasPermission("Nitric.rank")) {
            String format = plugin.getConfig().getString("format.join").replace("<prefix>", plugin.chat.getPlayerPrefix(p)).replace("<player>", p.getDisplayName());
            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', format));
        } else {
            String format2 = plugin.getConfig().getString("format.joindefault").replace("<player>", p.getDisplayName());
            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', format2));
        }

        if (!(p.hasPlayedBefore())) {
            int unique = plugin.getConfig().getInt("FirstJoins.total");
            unique++;
            plugin.getConfig().set("FirstJoins.total", unique);
            String uniquestring = String.valueOf(unique);
            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("format.welcome").replace("<player>", p.getName()).replace("<unique>", uniquestring)));
        }

        double x = plugin.getConfig().getDouble("Spawn.X");
        double y = plugin.getConfig().getDouble("Spawn.Y");
        double z = plugin.getConfig().getDouble("Spawn.Z");
        float yaw = 0;
        float pitch = 0;

        p.teleport(new Location(p.getWorld(), x,y,z,yaw,pitch));


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }
}
