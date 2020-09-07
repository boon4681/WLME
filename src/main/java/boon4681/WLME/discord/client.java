package boon4681.WLME.discord;


import boon4681.WLME.core;
import boon4681.WLME.utils.Bstring;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class client {
    protected static JDA bot;
    public static int run(){
        try {
            bot = JDABuilder.createDefault(core.config.getData().getString("token")).addEventListeners(new listener()).build();
            bot.awaitReady();
            return 0;
        } catch (InterruptedException | LoginException e) {
            Bstring.error(e);
            return -1;
        }
    }
    public static void shutdown(){
        bot.removeEventListener(new listener());
        bot.shutdownNow();
    }
    public static boolean WorkingStatus(){
        return bot.getStatus() == JDA.Status.SHUTTING_DOWN || bot.getStatus() == JDA.Status.SHUTDOWN;
    }
}
