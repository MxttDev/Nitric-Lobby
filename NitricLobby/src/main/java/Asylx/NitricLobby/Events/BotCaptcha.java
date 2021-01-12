package Asylx.NitricLobby.Events;

import Asylx.NitricLobby.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;

public class BotCaptcha implements Listener {

    Main plugin;

    public BotCaptcha(Main main) {
        this.plugin = main;
    }

    static String getRandomString(int n) {
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString = new String(array, Charset.forName("UTF-8"));
        StringBuffer r = new StringBuffer();
        String  AlphaNumericString
                = randomString
                .replaceAll("[^A-Za-z0-9]", "");
        for (int k = 0; k < AlphaNumericString.length(); k++) {

            if (Character.isLetter(AlphaNumericString.charAt(k))
                    && (n > 0)
                    || Character.isDigit(AlphaNumericString.charAt(k))
                    && (n > 0)) {

                r.append(AlphaNumericString.charAt(k));
                n--;
            }
        }
        return r.toString();
    }

    public static HashMap<String, Boolean> onChatBo = new HashMap<String, Boolean>();

    public static int randomNumberAuth = 5; // SIZE OF RANDOM GENERATED STRING
    public static String random = getRandomString(randomNumberAuth);

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();


        String msg1 = "&c ";
        String msg2 = "&7Welcome back, &b"+p.getDisplayName();
        String msg3 = "&d ";
        String msg4 = "&7Please type &b/auth "+random+"&7 or move to verify that you are human.";
        String msg5 = "&8 ";

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg1));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg2));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg3));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg4));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg5));
        onChatBo.put(p.getName(), false);

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        boolean canChat = onChatBo.get(p.getName());
        if (!(canChat)) {
            onChatBo.put(p.getName(), true);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bYou can now talk."));
        }
    }
}
