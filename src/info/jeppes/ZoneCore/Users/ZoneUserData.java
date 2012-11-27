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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 *
 * @author Jeppe
 */
public class ZoneUserData extends BukkitUser{


    public enum ServerGroup {
        Owner,
        PvPAdmin,
        Admin,
        SuperMod,
        Sponsor,
        SponsorPlus,
        ModP,
        Mod,
        Donator,
        VIP,
        MovieMaker,
        Regular,
        Basic;
        
        public boolean isStaff(){
            switch(this){
                case Owner: return true;
                case Admin: return true;
                case SuperMod: return true;
                case Mod: return true;
                case ModP: return true;
                case PvPAdmin: return true;
                default: return false;
            }
        }
    }
    
    private ConfigurationSection config;
    private HashMap<String, Object> tempData = new HashMap<>();
    private RecommendationsHolder recommendationsHolder;
    public ZoneUserData(Player player, ConfigurationSection config){
        super(player,player.getName());
        this.config = config;
        if(config == null){
            newUser();
        }
        recommendationsHolder = new RecommendationsHolder(this,this.config);
    }

    public ZoneUserData(String userName, ConfigurationSection configurationSection) {
        super(Bukkit.getPlayer(userName),userName);
        this.config = configurationSection;
        recommendationsHolder = new RecommendationsHolder(this,this.config);
    }

    @Override
    public ServerGroup getServerGroup(){
        try{
            PermissionGroup group = PermissionsEx.getUser(this.getName()).getGroups()[0];
            return ServerGroup.valueOf(group.getName());
        } catch(Exception e){}
        return null;
    }
    
    @Override
    public final void newUser() {
        getUsersConfig().set(getName()+".lastjoined", System.currentTimeMillis());
        config = getUsersConfig().getConfigurationSection(getName());
        getConfig().set("playtime",0);
        getConfig().set("playtimecheck",System.currentTimeMillis());
        saveConfig();
    }

    @Override
    public ConfigurationSection getConfig() {
        return config;
    }
    
    public void setAndSave(String key, Object obj){
        getConfig().set(key, obj);
        saveConfig();
    }

    @Override
    public ZoneConfig getUsersConfig() {
        return ZoneUserManager.getUsersConfig();
    }

    @Override
    public RecommendationsHolder getRecommendationsHolder() {
        return recommendationsHolder;
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
        if(isOnline()){
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
        if(isOnline()){
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
        if(isOnline()){
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
    
    //Event methods
    
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.setPlayer(event.getPlayer());
        this.sendMesssagesWhenOnline();
        this.giveItemsWhenOnline();
        this.giveLevelsWhenOnline();
    }
    @Override
    public void saveConfig(){
        this.getUsersConfig().save();
    }
    
    @Override
    public void updatePlayTime() {
        if(getConfig().contains("playtimecheck")){
            long lastPlayTimeChecked = getConfig().getLong("playtimecheck");
            getConfig().set("playtime", getPlayTime() + (System.currentTimeMillis() - lastPlayTimeChecked));
            getConfig().set("playtimecheck", System.currentTimeMillis());
        }
        saveConfig();
    }
    @Override
    public long getPlayTime() {
        return getConfig().getLong("playtime");
    }
}
