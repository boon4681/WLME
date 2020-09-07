package boon4681.WLME.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.URL;

public class Bnetwork {
    public static JsonObject loadJSON(String url){
        URL go = null;
        try {
            go = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(go.openStream()));
            String out = "";
            String line;
            while ((line = in.readLine()) != null)
                out += line;
            in.close();
            if(out.equals("")) return null;
            JsonObject jsonElement = new JsonParser().parse(out).getAsJsonObject();
            return jsonElement;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
