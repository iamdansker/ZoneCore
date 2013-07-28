/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Users;

import info.jeppes.ZoneCore.Events.NewZoneUserEvent;
import info.jeppes.ZoneCore.ZoneConfig;
import info.jeppes.ZoneCore.ZoneCore;
import info.jeppes.ZoneCore.ZonePlugin;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Jeppe
 */
public class ZoneUserManager implements Listener{
    private HashMap<String,ZoneUser> users = new HashMap<>();
    private final Plugin plugin;
    private boolean isZonePlugin;
    private ZoneConfig usersConfig;
    private int saveInterval = 24000; //ticks
    
    public ZoneUserManager(Plugin plugin, ZoneConfig usersConfig){
        this.usersConfig = usersConfig;
        this.plugin = plugin;
        if(plugin instanceof  ZonePlugin){
            this.isZonePlugin = true;
        } else {
            this.isZonePlugin = false;
        }
        
        RecommendationsManager recommendationsManager = new RecommendationsManager();
        loadUsers(usersConfig);
        
        if(recommendationsManager.seasonChangeWaiting()){
            recommendationsManager.newVoteseason();
        }
        
        Bukkit.getPluginManager().registerEvents(this, plugin);
        
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable(){
            @Override
            public void run() {
                getUsersConfig().schedualSave();
            }
        }, saveInterval, saveInterval);
    }
    
    public HashMap<String,ZoneUser> getUsers(){
        return users;
    }
    public ZoneUser getUser(String userName){
        return getUsers().get(userName.toLowerCase());
    }
    public ZoneUser getUser(Player player){
        return getUsers().get(player.getName().toLowerCase());
    }
    public boolean containsPlayer(Player player){
        return containsPlayer(player);
    }
    public boolean containsPlayer(String playerName){
        return getUsers().containsKey(playerName.toLowerCase());
    }
    
    public ZoneConfig getUsersConfig(){
        return usersConfig;
    }
    
    public void loadUsers(ZoneConfig usersConfig){
        if(isZonePlugin){
            if(((ZonePlugin)plugin).inDebugMode()) {
                ((ZonePlugin)plugin).getLogger().log(Level.INFO,"Loading users...");
            }
        }
        Set<String> keys = usersConfig.getKeys(false);
        for(String userName : keys){
            ConfigurationSection configurationSection = usersConfig.getConfigurationSection(userName);
            ZoneUserData user = new ZoneUserData(userName,configurationSection);
            getUsers().put(userName.toLowerCase(), user);
        }
        if(isZonePlugin){
            if(((ZonePlugin)plugin).inDebugMode()) {
                ((ZonePlugin)plugin).getLogger().log(Level.INFO,"Loaded users");
            }
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event){
        checkNewUser(event.getPlayer());
        final ZoneUser user = getUser(event.getPlayer());
        user.setPlayer(event.getPlayer());
        
        Bukkit.getScheduler().runTaskLater(ZoneCore.getCorePlugin(), new Runnable(){
            @Override
            public void run() {
                user.sendMesssagesWhenOnline();
                user.giveItemsWhenOnline();
                user.giveLevelsWhenOnline();
            }
        }, 20);
    }
    
    public boolean checkNewUser(Player player) {
        if(!getUsers().containsKey(player.getName().toLowerCase())){
            ZoneUserData newUser = new ZoneUserData(player, getUsersConfig().createSection(player.getName()));
            getUsers().put(newUser.getName().toLowerCase(), newUser);
            getUsersConfig().schedualSave();
            
            NewZoneUserEvent newZoneUserEvent = new NewZoneUserEvent(this,newUser);
            Bukkit.getPluginManager().callEvent(newZoneUserEvent);
            
            return true;
        }
        return false;
    }
    
    @EventHandler(priority=EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event){
        ZoneUser user = getUser(event.getPlayer());
        if(user != null){
            user.saveConfig();
        }
    }
}
