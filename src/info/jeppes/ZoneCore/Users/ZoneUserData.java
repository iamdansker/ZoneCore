/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Users;

import info.jeppes.ZoneCore.ZoneConfig;
import info.jeppes.ZoneCore.ZoneCore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Jeppe
 */
public class ZoneUserData extends BukkitPlayerWrapper{
    
    private ConfigurationSection config;
    private HashMap<String, Object> tempData = new HashMap<>();
    
    public ZoneUserData(Player player, ConfigurationSection configurationSection){
        this(player.getName(),configurationSection);
    }
    public ZoneUserData(String userName, ConfigurationSection configurationSection) {
        super(Bukkit.getPlayer(userName),userName);
        this.config = configurationSection;
    }
    

    @Override
    public ConfigurationSection getConfig() {
        return config;
    }
    
    @Override
    public void setAndSave(String key, Object obj){
        getConfig().set(key, obj);
        saveConfig();
    }

    @Override
    public ZoneConfig getUsersConfig(){
        return (ZoneConfig)config.getRoot();
    }
    
    @Override
    public HashMap<String, Object> getTempData() {
        return tempData;
    }

    @Override
    public Object getTempData(String key) {
        return getTempData().get(key);
    }

    @Override
    public void addTempData(String key, Object obj) {
        getTempData().put(key, obj);
    }

    @Override
    public void removeTempData(String key) {
        getTempData().remove(key);
    }

    @Override
    public void removeTempData(Object obj) {
        getTempData().values().remove(obj);
    }

    @Override
    public void setTempData(HashMap<String, Object> objList) {
        this.tempData = objList;
    }

    @Override
    public void clearTempData() {
        getTempData().clear();
    }

    @Override
    public boolean sendMesssagesWhenOnline() {
        if(isOnline() && config.contains("onjoin.sendmessage")){
            Bukkit.getScheduler().scheduleSyncDelayedTask(ZoneCore.getCorePlugin(), new Runnable(){
                @Override
                public void run() {
                    String key = "onjoin.sendmessage";
                    ArrayList<String> messages = (ArrayList<String>) getConfig().getList(key);
                    if(messages == null){
                        return;
                    }
                    for(String message : messages){
                        getPlayer().sendMessage(message);
                    }
                    setAndSave(key, null);
                }
            },20);
            return true;
        }
        return false;
    }

    @Override
    public boolean giveItemsWhenOnline() {
        if(isOnline() && config.contains("onjoin.giveitems")){
            ConfigurationSection configurationSection = getConfig().getConfigurationSection("onjoin.giveitems");
            if(configurationSection == null){
                return true;
            }
            Set<String> keys = configurationSection.getKeys(false);
            for(String key : keys){
                int amount = configurationSection.getInt(key);
                Material material = Material.valueOf(key.toUpperCase());
                if(material != null && amount > 0){
                    ItemStack itemStack = new ItemStack(material);
                    itemStack.setAmount(amount);
                    getInventory().addItem(itemStack);
                }
            }
            setAndSave("onjoin.giveitems", null);
            return true;
        }
        return false;
    }

    @Override
    public boolean giveLevelsWhenOnline() {
        if(isOnline() && config.contains("onjoin.givelevels")){
            int levels = getConfig().getInt("onjoin.givelevels");
            setAndSave("onjoin.givelevels", null);
            this.setLevel(getLevel()+levels);
            return true;
        }
        return false;
    }
    
    @Override
    public void sendMesssageWhenOnline(String message){
        if(isOnline()){
            sendMessage(message);
        } else {
            String key = "onjoin.sendmessage";
            ArrayList<String> messages = (ArrayList<String>) getConfig().getList(key);
            if(messages == null){
                messages = new ArrayList<>();
            }
            messages.add(message);
            setAndSave(key, messages);
        }
    }
    
    @Override
    public void giveItemWhenOnline(ItemStack itemStack) {
        if(isOnline()){
            getInventory().addItem(itemStack);
        } else {
            String key = "onjoin.giveitems."+itemStack.getType().name();
            int itemAmmount = getConfig().getInt(key);
            itemAmmount += itemStack.getAmount();
            setAndSave(key, itemAmmount);
        }
    }

    @Override
    public void giveLevelsWhenOnline(int i) {
        if(isOnline()){
            setLevel(getLevel()+1);
        } else {
            setAndSave("onjoin.givelevels", getConfig().getInt("onjoin.givelevels")+1);
        }
    }
    
    @Override
    public void saveConfig(){
        this.getUsersConfig().schedualSave();
    }
}
