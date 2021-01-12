package Asylx.NitricLobby;

import Asylx.NitricLobby.Events.*;
import Asylx.NitricLobby.Events.GUI.createGUI;
import Asylx.NitricLobby.Events.GUI.moveGui;
import Asylx.NitricLobby.Events.GUI.serverSelector;
import Asylx.NitricLobby.commands.auth;
import Asylx.NitricLobby.commands.spawn;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class Main extends JavaPlugin implements Listener, PluginMessageListener{

    FileConfiguration config = getConfig();
    public Chat chat = null;

    org.bukkit.scoreboard.Scoreboard board;

    @Override
    public void onEnable() {

        config.options().copyDefaults(true);
        saveConfig();

        setupCommands();
        setupEvents();

        setupChat();

        board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("Main", "dummy", ChatColor.translateAlternateColorCodes('&', "&b&lNitric"));


        setupScoreboard(obj);


        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                for (Player a : getServer().getOnlinePlayers()) {
                    obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                }
            }
        }, 1, 5);




    }

    private void setupEvents() {// SETUP EVENTS
        getServer().getPluginManager().registerEvents(new onAttack(this, chat), this);
        getServer().getPluginManager().registerEvents(new onJoin(this), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new onChat(this), this);
        getServer().getPluginManager().registerEvents(new Hunger(), this);
        getServer().getPluginManager().registerEvents(new onFly(), this);
        getServer().getPluginManager().registerEvents(new BotCaptcha(this), this);
        getServer().getPluginManager().registerEvents(new scoreboard(this), this);

        getServer().getPluginManager().registerEvents(new createGUI(this), this);
        getServer().getPluginManager().registerEvents(new moveGui(), this);
        getServer().getPluginManager().registerEvents(new serverSelector(), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);


    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p =e.getPlayer();
        p.setScoreboard(board);
    }


    private void setupCommands() { // SETUP COMMANDS
        this.getCommand("spawn").setExecutor(new spawn(this, chat));
        this.getCommand("auth").setExecutor(new auth());
        //dsds
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    public Chat getChat() {
        return chat;
    }

    private void setupScoreboard(Objective obj) {
        for (Player a : Bukkit.getOnlinePlayers()) {


            Score blank = obj.getScore(ChatColor.GRAY + " ");
            Score rank = obj.getScore(ChatColor.GRAY + "Rank: " + ChatColor.AQUA + ChatColor.translateAlternateColorCodes('&', chat.getPlayerPrefix(a)));
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

    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();

        if (subchannel.equals("PlayerCount")) {
            String server = in.readUTF();
            createGUI.count.put(server, in.readInt());

        }

    }


}
