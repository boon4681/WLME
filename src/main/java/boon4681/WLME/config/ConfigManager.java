package boon4681.WLME.config;

import boon4681.WLME.utils.Bfile;

import java.util.Arrays;

public class ConfigManager {
    private Bfile config;
    private enum dconfig{
        token("string","[YOUR_BOT_TOKEN]"),
        command_prefix("string","!"),
        allow_user_role("list", ""),
        allow_guilds("list", ""),
        allow_channels("list", ""),
        discord__command_name__me__description_message("string", ""),
        discord__command_name__me__not_found_data_message("string", ""),
        ;
        private class ifig{
            private String type;
            private Object Default;
            private String path;
            public ifig(String type,Object Default,String path){
                this.type = type;
                this.Default = Default;
                this.path = path;
            }
        }
        private ifig Ifig;
        dconfig(String type,Object Default) {
            this.Ifig = new ifig(type, Default,name().replace("__","."));
        }
        public static void makeExists(Bfile bfile){
            for (dconfig s : values()){
                if(!bfile.getData().contains(s.Ifig.path)){
                    bfile.set(s.Ifig.path,s.Ifig.Default);
                }
            }
        }
    }
    public ConfigManager(Bfile defaultConfig){
        this.config = defaultConfig;
    }
    public void makeExists(){
        dconfig.makeExists(this.config);
    }
}