package boon4681.WLME;

import boon4681.WLME.minecraft.command.CommandManager;
import boon4681.WLME.config.ConfigManager;
import boon4681.WLME.discord.client;
import boon4681.WLME.minecraft.event.join;
import boon4681.WLME.utils.Bfile;
import boon4681.WLME.utils.Bstring;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class core extends JavaPlugin {
    public static ConsoleCommandSender logger;
    public static JavaPlugin This;
    public static Bfile whitelist;
    public static Bfile config;
    public static Runnable task;
    public static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    public static ScheduledFuture<?> scheduledFuture;
    public static void runBot(){
        int initSuccess = client.run();
        if(initSuccess == 0) {
            logger.sendMessage(Bstring.merger(ChatColor.GREEN,"[Success] ",ChatColor.WHITE,"Discord client was already"));
        }else{
            logger.sendMessage(Bstring.merger(ChatColor.RED,"[ERROR] ",ChatColor.WHITE,"Discord Client failed to initialize"));
        }
    }
    @Override
    public void onEnable() {
        This = this;
        logger = This.getServer().getConsoleSender();
        config = new Bfile(This.getDataFolder(),"config.yml");
        whitelist = new Bfile(This.getDataFolder(),"whitelist.yml");
        if(config.load()!=null){
            ConfigManager configManager = new ConfigManager(config);
            configManager.makeExists();
            CommandManager.registry();
            whitelist.load();
            core.whitelist.getData().getKeys(false).forEach(e ->{
                Bstring.print(core.whitelist.getData().get(e));
            });
            Bukkit.getServer().getPluginManager().registerEvents(new join(),this);
            runBot();
        }
    }
    @Override
    public void onDisable() {
        client.shutdown();
        boolean WorkingStatus = boon4681.WLME.discord.client.WorkingStatus();
        if(!WorkingStatus){
            client.shutdown();
        }
    }
}