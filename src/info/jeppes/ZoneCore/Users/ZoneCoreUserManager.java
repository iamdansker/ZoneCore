/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Users;

import info.jeppes.ZoneCore.Events.NewZoneUserEvent;
import info.jeppes.ZoneCore.ZoneConfig;
import info.jeppes.ZoneCore.ZoneCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author jeppe
 */
public class ZoneCoreUserManager <E extends ZoneCoreUser> extends ZoneUserManager<E> implements Listener{
    public ZoneCoreUserManager(ZoneConfig usersConfig) {
        super(ZoneCore.getCorePlugin(),usersConfig);
    }

    @Override
    public E loadUser(String userName, ConfigurationSection config) {
        return (E) new ZoneCoreUserData(userName,config);
    }
    
    @Override
    public void onPlayerQuit(PlayerQuitEvent event){
        Location location = event.getPlayer().getLocation();
        ZoneCoreUser user = getUser(event.getPlayer());
        if(user != null){
            ConfigurationSection config = user.getConfig();
            config.set("lastleft", System.currentTimeMillis());
            config.set("lastlocation.world", location.getWorld().getName());
            config.set("lastlocation.x", location.getX());
            config.set("lastlocation.y", location.getY());
            config.set("lastlocation.z", location.getZ());
            user.updatePlayTime();
            user.saveConfig();
        }
    }
    
    @Override
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        super.onPlayerJoin(event);
        ZoneUser user = getUser(event.getPlayer());
        user.getConfig().set("lastjoined", System.currentTimeMillis());
        user.getConfig().set("playtimecheck",System.currentTimeMillis());
        getUsersConfig().schedualSave();
    }
    @EventHandler
    public void onNewZoneUser(NewZoneUserEvent event){
        if(event.getZoneUserManager() == this){
            ZoneUser zoneUser = event.getZoneUser();
            zoneUser.getConfig().set("lastjoined", System.currentTimeMillis());
            zoneUser.getConfig().set("playtime",0);
            zoneUser.getConfig().set("playtimecheck",System.currentTimeMillis());
            zoneUser.saveConfig();
            
            if(ZoneCore.getCorePlugin().inDebugMode()) {
                System.out.println("New ZoneUser: "+zoneUser.getName());
            }
        }
    }
}
