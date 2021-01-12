package Asylx.NitricLobby.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onFly implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("Nitric.rank")) {
            p.setAllowFlight(true);
            p.setFlying(true);
        } return;
    }
}
