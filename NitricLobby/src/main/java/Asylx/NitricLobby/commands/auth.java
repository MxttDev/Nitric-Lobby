package Asylx.NitricLobby.commands;

import Asylx.NitricLobby.Events.BotCaptcha;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class auth implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Player only command");
        }

        if (args.length == 1) {
            if (args[0].equals(BotCaptcha.random)) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bYou can now talk."));
                BotCaptcha.onChatBo.put(p.getName(), true);
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect code."));
            }
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect usage: /auth (code)"));
        }

        return false;
    }
}
