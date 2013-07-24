/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Users;

import info.jeppes.ZoneCore.Events.NewZoneUserEvent;
import info.jeppes.ZoneCore.ZoneConfig;
import info.jeppes.ZoneCore.ZoneCore;
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

/**
 *
 * @author Jeppe
 */
public class ZoneUserManager implements Listener{
    private static HashMap<String,ZoneUser> users = new HashMap<>();
    private static ZoneConfig usersConfig;
    private static int saveInterval = 24000; //ticks
    
    public ZoneUserManager(ZoneConfig usersConfig){
        ZoneUserManager.usersConfig = usersConfig;
        RecommendationsManager recommendationsManager = new RecommendationsManager();
        loadUsers(usersConfig);
        if(recommendationsManager.seasonChangeWaiting()){
            recommendationsManager.newVoteseason();
        }
        
        Bukkit.getPluginManager().registerEvents(this, ZoneCore.getCorePlugin());
        
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(ZoneCore.getCorePlugin(), new Runnable(){
            @Override
            public void run() {
                getUsersConfig().schedualSave();
            }
        }, saveInterval, saveInterval);
    }
    
    public static HashMap<String,ZoneUser> getUsers(){
        return users;
    }
    public static ZoneUser getUser(String userName){
        return getUsers().get(userName.toLowerCase());
    }
    public static ZoneUser getUser(Player player){
        return getUsers().get(player.getName().toLowerCase());
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event){
        if(newUser(event.getPlayer())){
            
        } else {
            
        }
        ZoneUser user = ZoneCore.getUser(event.getPlayer());
        user.onPlayerJoin(event);
    }
    
    @EventHandler(priority=EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event){
        ZoneUser user = ZoneCore.getUser(event.getPlayer());
        if(user != null){
            user.saveConfig();
        }
    }
    public static ZoneConfig getUsersConfig(){
        return usersConfig;
    }
    
    public final void loadUsers(ZoneConfig usersConfig){
        if(ZoneCore.getCorePlugin().inDebugMode()) {
            ZoneCore.getCorePlugin().getLogger().log(Level.INFO,"Loading users...");
        }
        Set<String> keys = usersConfig.getKeys(false);
        for(String userName : keys){
            ConfigurationSection configurationSection = usersConfig.getConfigurationSection(userName);
            ZoneUserData user = new ZoneUserData(userName,configurationSection);
            users.put(userName.toLowerCase(), user);
        }
        if(ZoneCore.getCorePlugin().inDebugMode()) {
            ZoneCore.getCorePlugin().getLogger().log(Level.INFO,"Loaded users");
        }
    }
    
    public boolean newUser(Player player) {
        if(!users.containsKey(player.getName().toLowerCase())){
            ZoneUserData newUser = new ZoneUserData(player,null);
            users.put(newUser.getName().toLowerCase(), newUser);
            getUsersConfig().schedualSave();
            
            NewZoneUserEvent newZoneUserEvent = new NewZoneUserEvent(this,newUser);
            Bukkit.getPluginManager().callEvent(newZoneUserEvent);
            
            return true;
        }
        return false;
    }
}
