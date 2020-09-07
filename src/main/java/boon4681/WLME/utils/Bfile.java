package boon4681.WLME.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class Bfile {
    private File main;
    private FileConfiguration data = new YamlConfiguration();
    public Bfile(@NotNull File file, String name){
        Bstring.print(file);
        file.mkdirs();
        this.main = new File(file,name);
    }
    public FileConfiguration getData(){
        return data;
    }
    public FileConfiguration load(){
        try {
            if(!checkExist()){
                genFile();
            }
            this.data = new YamlConfiguration();
            this.data.load(this.main);
            return this.data;
        } catch (InvalidConfigurationException | IOException e) {
            Bstring.error(e);
            return null;
        }
    }
    public void set(String k,Object v){
        this.data.set(k,v);
        save();
    }
    public void remove(String k){
        this.data.set(k,null);
        save();
    }
    private boolean contain(String k){
        return this.data.contains(k);
    }
    private void save(){
        try {
            this.data.save(this.main.getPath());
        } catch (IOException e) {
            Bstring.error(e);
        }
    }
    private void genFile(){
        if(!checkExist()){
            try {
                this.main.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean checkExist(){
        if(this.main.exists()) return true;
        else return false;
    }
}