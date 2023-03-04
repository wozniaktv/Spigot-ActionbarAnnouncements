package wozniaktv.announcements;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public final class Announcements extends JavaPlugin {



    public int actual_index = 0;

    public List<String> announcements_messages;

    public int timer = 0;

    public int needed_seconds = 120;



    @Override
    public void onEnable() {
        // Plugin startup logic

        getCommand("acreload").setExecutor(new Reload());

        getLogger().info("Setting up the engine...");

        Update_Timers();
        Update_Messages();
        start_timer();

        getLogger().info("");
        getLogger().info("Engine started!");
        getLogger().info("Everything looks working just fine, if you face any issue join the TG channel down here to get support!");
        getLogger().info("Join the TG Channel | https://t.me/wozDevPlugins");
        getLogger().info("");


    }

    public void start_timer(){

        new BukkitRunnable() {

            @Override
            public void run() {

                timer++;

                if(timer>=needed_seconds){

                    timer = 0;
                    if(actual_index>= announcements_messages.size()) actual_index = 0;
                    for(Player P :getServer().getOnlinePlayers()){
                        new BukkitRunnable(){

                            int hasBeenDisplayedFor = 0;

                            final int displayIndex = actual_index;
                            final int needsToBeDisplayedFor = getPlugin(Announcements.class).getConfig().getInt("remainTimeAnnouncements");

                            @Override
                            public void run() {

                                P.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&',announcements_messages.get(displayIndex))));
                                hasBeenDisplayedFor++;
                                if(hasBeenDisplayedFor>needsToBeDisplayedFor) {
                                    P.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
                                    this.cancel();
                                }
                            }

                        }.runTaskTimer(getPlugin(Announcements.class),0,20);

                    }
                    actual_index++;
                    timer = 0;

                }


            }

        }.runTaskTimer(this,0,20);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void Update_Timers(){

        actual_index = 0;
        timer = 0;

        saveDefaultConfig();
        getConfig();


        needed_seconds = getConfig().getInt("delay_announcements");

    }

    public void Update_Messages(){

        saveDefaultConfig();
        getConfig();

        announcements_messages = getConfig().getStringList("announcements");

    }

}
