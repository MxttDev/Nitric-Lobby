package Asylx.NitricLobby.Events;

import Asylx.NitricLobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class scoreboard implements Listener {

    Main plugin;



    public scoreboard(Main main) {
        this.plugin = main;
    }

    org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    Objective obj = board.registerNewObjective("Main", "dummy", ChatColor.translateAlternateColorCodes('&', "&b&lNitric"));


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();


        p.setScoreboard(board);

    }

    private void setupScoreboard(Objective obj) {
        for (Player a : Bukkit.getOnlinePlayers()) {
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score blank = obj.getScore(ChatColor.GRAY + " ");
            Score rank = obj.getScore(ChatColor.GRAY + "Rank: " + ChatColor.AQUA + ChatColor.translateAlternateColorCodes('&', plugin.chat.getPlayerPrefix(a)));
            Score ONLINE = obj.getScore(ChatColor.GRAY+"Players: "+ChatColor.AQUA+Bukkit.getOnlinePlayers().size());
            Score blank1 = obj.getScore(ChatColor.GRAY + "  ");
            Score server = obj.getScore(ChatColor.AQUA + "play.nitric.net");
            blank.setScore(5);
            rank.setScore(4);
            ONLINE.setScore(3);
            blank1.setScore(2);
            server.setScore(1);
        }
    }
}
