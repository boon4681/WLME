package boon4681.WLME.minecraft.command;
import boon4681.WLME.minecraft.command.discord.client.botrun;
import boon4681.WLME.minecraft.command.discord.client.botstop;
import boon4681.WLME.core;
import boon4681.WLME.utils.Bstring;
import org.bukkit.command.CommandExecutor;

public enum CommandManager {
    run(new botrun()),
    stop(new botstop()),
    ;
    private class icd{
        private CommandExecutor executor;
        private String name;
        public icd(CommandExecutor executor,String name){
            this.executor = executor;
            this.name = name;
        }
    }
    private icd Icd;
    CommandManager(CommandExecutor executor) {
        this.Icd = new icd(executor,"wlme"+name());
    }
    public static void registry(){
        for (CommandManager command : values()) {
            Bstring.print(Bstring.merger("Registry command \"",command.Icd.name,"\""));
            core.This.getCommand(command.Icd.name).setExecutor(command.Icd.executor);
        }
    }
}