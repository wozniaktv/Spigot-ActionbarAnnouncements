package wozniaktv.announcements;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Reload implements CommandExecutor {

    private Announcements main;
    public Reload() { main = JavaPlugin.getPlugin(Announcements.class); }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("announcements.reload")){

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&bAnnouncements>&7 Sorry, you do not have the permission to execute this command"));
            return true;
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&bAnnouncements>&7 I'm reloading the configuration file..."));

        main.reloadConfig();
        main.Update_Messages();
        main.Update_Timers();

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&bAnnouncements>&2 Everything reloaded successfully!"));


        return true;

    }
}
