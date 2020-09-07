package boon4681.WLME.minecraft.event;

import boon4681.WLME.core;
import boon4681.WLME.utils.Bstring;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class join implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        Boolean canJoin = false;
        for (String s : core.whitelist.getData().getKeys(false)){
            if(core.whitelist.getData().get(s).equals(p.getUniqueId().toString().replace("-",""))){
                canJoin = true;
                break;
            }
        }
        if(!canJoin){
            p.kickPlayer("You not have a whitelist");
        }
    }
}