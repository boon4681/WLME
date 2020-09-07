package boon4681.WLME.utils;

import boon4681.WLME.core;
import org.bukkit.ChatColor;

public class Bstring {
    public static String merger(Object... string){
        String result = "";
        try{
            for (Object s : string) {
                if (s instanceof String) result += s;
                else if(s instanceof ChatColor) result += s;
            }
            return result;
        }catch (Exception e) {
            error(e);
            return null;
        }
    }
    public static void print(Object string){
        core.logger.sendMessage(merger(ChatColor.GOLD,"[WLME] ",string.toString()));
    }
    public static void error(Exception string){
        try{
            core.logger.sendMessage(merger(ChatColor.GOLD,"[WLME]"));
            string.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
