package boon4681.WLME.discord;

import boon4681.WLME.core;
import boon4681.WLME.utils.Bnetwork;
import boon4681.WLME.utils.Bstring;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class listener extends ListenerAdapter {
    private void checkCanUse(MessageReceivedEvent event,TextChannel channel){
        Boolean canUse = false;
        if(core.config.getData().get("allow_user_role").equals(null)){
            canUse = true;
        }
        if(core.config.getData().get("allow_user_role").equals("")){
            canUse = true;
        }
        if(!canUse){
            for (Role role : event.getGuild().getMember(event.getAuthor()).getRoles()){
                if(core.config.getData().getList("allow_user_role").contains(role.getName())){
                    canUse = true;
                    break;
                }
            }
        }
        if(canUse==false) {
            channel.sendMessage(":fire: sorry you cannot use this command").queue();
            return;
        }
    }
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        event.getJDA().getGuilds().forEach(e->{
            for (String s : core.whitelist.getData().getKeys(false)){
                for (Role role : e.getMember(User.fromId(s)).getRoles()){
                    if(core.config.getData().getList("allow_user_role").contains(role.getName())){
                        core.whitelist.remove(s);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.isFromType(ChannelType.TEXT)){
            if(event.getAuthor().isBot()){
                return;
            }
            String content = event.getMessage().getContentDisplay();
            String command_prefix = core.config.getData().get("command_prefix").toString();
            TextChannel channel = event.getTextChannel();
            if(content.equals(command_prefix+"me")){
                core.config.load();
                core.whitelist.load();
                if(core.whitelist.getData().get(event.getAuthor().getId())==null){
                    if(core.config.getData().get("discord.command_name.me.not_found_data_message").equals("") || core.config.getData().get("discord.command_name.me.not_found_data_message").equals(null)){
                        channel.sendMessage(":fire: sorry not found you data").queue();
                    }else{
                        channel.sendMessage(core.config.getData().getString("discord.command_name.me.not_found_data_message")).queue();
                    }
                }else {
                    EmbedBuilder profile = new EmbedBuilder();
                    profile.setThumbnail(Bstring.merger("https://crafatar.com/avatars/",core.whitelist.getData().get(event.getAuthor().getId()).toString(),"?size=256&overlay"));
                    profile.setTitle(Bnetwork.loadJSON(Bstring.merger("https://sessionserver.mojang.com/session/minecraft/profile/",core.whitelist.getData().get(event.getAuthor().getId()).toString())).get("name").getAsString());
                    if(!(core.config.getData().get("discord.command_name.me.description_message").equals("")) || !(core.config.getData().get("discord.command_name.me.description_message").equals(null)))
                    profile.setDescription(core.config.getData().getString("discord.command_name.me.description_message"));
                    profile.setFooter("bot created by boon4681", "https://boon4681.github.io/assets/image/logo.png");
                    channel.sendMessage(profile.build()).queue();
                }
            }
            if(content.startsWith(command_prefix+"verify")){
                String name = content.replace(command_prefix+"verify","");
                JsonObject element = Bnetwork.loadJSON(Bstring.merger("https://api.mojang.com/users/profiles/minecraft/",name.replace(" ","")));
                if(!(element==null)){
                    core.whitelist.set(event.getAuthor().getId(),element.get("id").getAsString());
                    channel.sendMessage(":watermelon: verified").queue();
                }else{
                    channel.sendMessage(":fire: sorry not found you minecraft id").queue();
                }
            }
        }
    }
}
