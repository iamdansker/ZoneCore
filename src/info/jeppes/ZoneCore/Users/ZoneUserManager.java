/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Users;

import info.jeppes.ZoneCore.ZoneConfig;
import info.jeppes.ZoneCore.ZoneCore;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
                getUsersConfig().save();
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
            
        }
        ZoneUser user = ZoneCore.getCorePlugin().getAPI().getUser(event.getPlayer());
        user.onPlayerJoin(event);
    }
    public static ZoneConfig getUsersConfig(){
        return usersConfig;
    }
    
    public final void loadUsers(ZoneConfig usersConfig){
        ZoneCore.getCorePlugin().getLogger().log(Level.INFO,"Loading gates...");
        Set<String> keys = usersConfig.getKeys(false);
        for(String userName : keys){
            ConfigurationSection configurationSection = usersConfig.getConfigurationSection(userName);
            ZoneUserData user = new ZoneUserData(userName,configurationSection);
            users.put(userName.toLowerCase(), user);
        }
        ZoneCore.getCorePlugin().getLogger().log(Level.INFO,"Loaded users");
    }
    
    public static boolean newUser(Player player) {
        if(!users.containsKey(player.getName().toLowerCase())){
            ConfigurationSection configurationSection = usersConfig.getConfigurationSection(player.getName());
            ZoneUserData newUser = new ZoneUserData(player,configurationSection);
            users.put(newUser.getName().toLowerCase(), newUser);
            getUsersConfig().save();
            System.out.println("New ZoneUser: "+player.getName());
            return true;
        }
        return false;
    }
}
