package Asylx.NitricLobby.Events;

import Asylx.NitricLobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class onAttack implements Listener {

    Main plugin;
    private Chat chat = null;

    public onAttack(Main main, Chat chat) {
        this.plugin = main;
        this.chat = chat;
    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void voidDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            if (entity instanceof Player) {
                Bukkit.getServer().dispatchCommand(entity, "spawn");
            }
        }
        if (entity instanceof Player) {
            e.setCancelled(true);
        }

    }
}
