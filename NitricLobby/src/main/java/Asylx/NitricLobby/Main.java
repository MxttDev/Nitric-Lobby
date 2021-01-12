package Asylx.NitricLobby;

import Asylx.NitricLobby.Events.*;
import Asylx.NitricLobby.commands.auth;
import Asylx.NitricLobby.commands.spawn;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();
    public Chat chat = null;

    @Override
    public void onEnable() {

        config.options().copyDefaults(true);
        saveConfig();

        setupCommands();
        setupEvents();

        setupChat();




    }

    private void setupEvents() {// SETUP EVENTS
        getServer().getPluginManager().registerEvents(new onAttack(this, chat), this);
        getServer().getPluginManager().registerEvents(new onJoin(this), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new onChat(this), this);
        getServer().getPluginManager().registerEvents(new Hunger(), this);
        getServer().getPluginManager().registerEvents(new onFly(), this);
        getServer().getPluginManager().registerEvents(new BotCaptcha(this), this);

    }



    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        getLogger().info(chat.getPlayerPrefix(e.getPlayer()));
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


}
