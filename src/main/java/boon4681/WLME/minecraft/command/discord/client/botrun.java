package boon4681.WLME.minecraft.command.discord.client;

import boon4681.WLME.core;
import boon4681.WLME.utils.Bstring;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import boon4681.WLME.discord.client;

public class botrun implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(client.WorkingStatus()) core.runBot();
        else core.logger.sendMessage(Bstring.merger(ChatColor.RED,"[ERROR] ",ChatColor.WHITE,"Bot have already"));
        return true;
    }
}
