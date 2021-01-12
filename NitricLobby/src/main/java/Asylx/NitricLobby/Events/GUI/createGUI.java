package Asylx.NitricLobby.Events.GUI;

import Asylx.NitricLobby.Events.BotCaptcha;
import Asylx.NitricLobby.Main;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class createGUI implements Listener {

    Main plugin;

    public createGUI(Main main) {
        this.plugin = main;
    }

    public static HashMap<String, Integer> count = new HashMap<String, Integer>();

    Material Event = Material.NETHER_STAR;
    Material SMP = Material.GRASS_BLOCK;

    @EventHandler
    public void onInventory(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);

        Inventory serverInv = Bukkit.createInventory(p, 27, ChatColor.translateAlternateColorCodes('&', "&8Gamemode Selector"));
        ItemStack EventItem = new ItemStack(Event);
        ItemStack SMPItem = new ItemStack(SMP);

        ItemMeta EventMeta = EventItem.getItemMeta();
        ItemMeta SMPMeta = SMPItem.getItemMeta();

        EventMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lEvent"));
        SMPMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lSMP"));

        int EventPlayers = 0;
        int SMPPlayers = getServerCount(p, "Survival");

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);



        ArrayList EventLore = new ArrayList<String>();
        EventLore.add(ChatColor.translateAlternateColorCodes('&', "&7 "));
        EventLore.add(ChatColor.translateAlternateColorCodes('&', "&bEvents - Custom events "));
        EventLore.add(ChatColor.translateAlternateColorCodes('&', "&bhosted by our Staff. "));
        EventLore.add(ChatColor.translateAlternateColorCodes('&', "&7 "));
        EventLore.add(ChatColor.translateAlternateColorCodes('&', "&a&lONLINE&a: "+EventPlayers+"&8/&b200"));

        ArrayList SMPLore = new ArrayList<String>();
        SMPLore.add(ChatColor.translateAlternateColorCodes('&', "&7 "));
        SMPLore.add(ChatColor.translateAlternateColorCodes('&', "&bSMP - Fun, multiplayer "));
        SMPLore.add(ChatColor.translateAlternateColorCodes('&', "&bsurvival! "));
        SMPLore.add(ChatColor.translateAlternateColorCodes('&', "&7 "));
        SMPLore.add(ChatColor.translateAlternateColorCodes('&', "&a&lONLINE&a: "+SMPPlayers+"&8/&b200"));

        EventMeta.setLore(EventLore);
        SMPMeta.setLore(SMPLore);



        EventItem.setItemMeta(EventMeta);
        SMPItem.setItemMeta(SMPMeta);

        serverInv.setItem(11, SMPItem);
        serverInv.setItem(15, EventItem);

        if (p.getItemInHand() != null && p.getItemInHand().getType() == Material.COMPASS) {
            p.openInventory(serverInv);
        }
    }

    @EventHandler
    public void onInventoryGUIMove(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        if (inv.contains(Event) && inv.contains(SMP)) {
            if (e.getCurrentItem().getType().equals(Event)) {
                p.sendMessage("WORKING IN PROGRESS.");
                p.closeInventory();
                try {
                    out.writeUTF("Connect");
                    out.writeUTF("Event");
                } catch (IOException eee) {
                    Bukkit.getLogger().info("REPORT TO THE DEVELOPERS.");
                }
                Bukkit.getPlayer(p.getUniqueId()).sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
            } else if (e.getCurrentItem().getType().equals(SMP)) {
                p.closeInventory();
                try {
                    out.writeUTF("Connect");
                    out.writeUTF("Survival");
                } catch (IOException eee) {
                    Bukkit.getLogger().info("REPORT TO THE DEVELOPERS.");
                }
                Bukkit.getPlayer(p.getUniqueId()).sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
            }
        }

    }


    public void getCount(Player player, String server) {

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    public int getServerCount(Player p, String server) {
        getCount(p, server);
        if (count.get(server) != null) {
            return count.get(server);
        }
        return 0;
    }

}
