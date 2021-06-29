package net.sssssssthedev.SmartClient.settings;

import net.sssssssthedev.SmartClient.module.Module;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SettingsManager {
    private final ArrayList<Setting> settings;
    public static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    public static Date date = new Date(System.currentTimeMillis());

    public SettingsManager(){
        this.settings = new ArrayList<>();
    }

    public void rSetting(Setting in){
        this.settings.add(in);
    }

    public ArrayList<Setting> getSettings(){
        return this.settings;
    }

    public ArrayList<Setting> getSettingsByMod(Module mod){
        ArrayList<Setting> out = new ArrayList<>();
        for(Setting s : getSettings()){
            if(s.getParentMod().equals(mod)){
                out.add(s);
            }
        }
        if(out.isEmpty()){
            return null;
        }
        return out;
    }

    public Setting getSettingByName(String name){
        for(Setting set : getSettings()){
            if(set.getName().equalsIgnoreCase(name)){
                return set;
            }
        }
        System.err.println("[" + formatter.format(date) + "] " + "[Smart thread/ERROR]:" + "Error Setting NOT found: '" + name +"'!");
        return null;
    }
}
