package Asylx.NitricLobby.Events;

import Asylx.NitricLobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onChat implements Listener {

    Main plugin;
    BotCaptcha onChatBo;

    public onChat(Main main) {
        this.plugin = main;
    }

    String msg;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        msg = e.getMessage();
        p.setFoodLevel(20);

        boolean checkChat = BotCaptcha.onChatBo.get(p.getName());


        if (!(checkChat)) {
            e.setCancelled(true);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need to verify yourself before you can talk!"));
        }

        if (p.hasPermission("Nitric.rank")) {
            String format = plugin.getConfig().getString("format.chat.donor").replace("<prefix>", plugin.chat.getPlayerPrefix(p)).replace("<player>", p.getDisplayName()).replace("<message>", msg);
            e.setFormat(ChatColor.translateAlternateColorCodes('&', format));
        } else {
            e.setFormat(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("format.chat.default").replace("<player>", p.getDisplayName()).replace("<message>", msg)));
        }


        for (Player player : Bukkit.getOnlinePlayers() ) {
            if (e.getMessage().contains(player.getDisplayName())) {
                msg = e.getMessage().replace(player.getDisplayName(), ChatColor.translateAlternateColorCodes('&', "&6@"+player.getDisplayName()));

            }
        }

    }
}
