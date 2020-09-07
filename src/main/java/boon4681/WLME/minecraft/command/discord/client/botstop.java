package boon4681.WLME.minecraft.command.discord.client;

import boon4681.WLME.core;
import boon4681.WLME.discord.client;
import boon4681.WLME.utils.Bstring;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class botstop implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        client.shutdown();
        boolean WorkingStatus = boon4681.WLME.discord.client.WorkingStatus();
        if(!WorkingStatus){
            core.logger.sendMessage(Bstring.merger(ChatColor.RED,"[ERROR] ",ChatColor.WHITE,"Failed to trigger shutdown on client"));
            return false;
        }
        core.logger.sendMessage(Bstring.merger(ChatColor.GREEN,"[COMPLETE] ",ChatColor.WHITE,"Triggered shutdown on client"));
        return true;
    }
}
