package Asylx.NitricLobby.Events.GUI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class serverSelector implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.getInventory().clear();

        ItemStack serverItem = new ItemStack(Material.COMPASS);
        ItemMeta servermeta = serverItem.getItemMeta();
        servermeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3Server Selector"));
        serverItem.setItemMeta(servermeta);
        p.getInventory().setItem(4, serverItem);

    }
}
