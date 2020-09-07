package boon4681.WLME.discord;

import boon4681.WLME.core;
import boon4681.WLME.utils.Bnetwork;
import boon4681.WLME.utils.Bstring;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class listener extends ListenerAdapter {
    private boolean checkCanUse(MessageReceivedEvent event,TextChannel channel){
        Boolean canUse = false;
        if(core.config.getData().get("allow_user_role").equals(null)){
            canUse = true;
        }
        if(core.config.getData().get("allow_user_role").equals("")){
            canUse = true;
        }
        if(!canUse){
            for (Role role : event.getGuild().getMember(event.getAuthor()).getRoles()){
                for (Object o : core.config.getData().getList("allow_user_role")){
                    if(o.toString().equals(role.getId().toLowerCase())){
                        canUse = true;
                        break;
                    }
                }
            }
        }
        if(core.config.getData().get("allow_channels").equals("")){
            canUse = true;
        }
        if(!core.config.getData().get("allow_channels").equals(channel.getId())){
            canUse = false;
        }
        if(canUse==false) {
            channel.sendMessage(":fire: sorry you cannot use this command").queue();
        }
        return canUse;
    }
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        core.task = ()->{
            core.config.load();
            core.whitelist.load();
            event.getJDA().getGuilds().forEach(e->{
                if(core.config.getData().getString("allow_guilds").equals(null)){ }
                else if(core.config.getData().getString("allow_guilds").equals("")) { }
                else if(core.config.getData().getString("allow_guilds").equals(e.getId().toString())){
                    for (Member m : e.getMembers()){
                        for (String s : core.whitelist.getData().getKeys(false)){
                            if(m.getId().equals(s)){
                                Boolean Found_role=false;
                                for (Role r: m.getRoles()){
                                    for (Object o : core.config.getData().getList("allow_user_role")){
                                        if(o.toString().equals(r.getId())){
                                            Found_role=true;
                                        }
                                    }
                                }
                                if(!Found_role) core.whitelist.remove(s);
                            }
                        }
                    }
                }
            });
        };
        core.scheduledFuture = core.executor.scheduleWithFixedDelay(core.task, 0, 5, TimeUnit.SECONDS);
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
            String[] command_list = {
                    Bstring.merger(command_prefix,"me"),
                    Bstring.merger(command_prefix,"verify")
            };
            if(content.equals(command_prefix+"me") && checkCanUse(event,channel)){
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
            if(content.startsWith(command_prefix+"verify") && checkCanUse(event,channel)){
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
