/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Users;

import info.jeppes.ZoneCore.Events.NewZoneUserEvent;
import info.jeppes.ZoneCore.ZoneConfig;
import info.jeppes.ZoneCore.ZoneCore;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author jeppe
 */
public class ZoneCoreUserManager extends ZoneUserManager{
    public ZoneCoreUserManager(ZoneConfig usersConfig) {
        super(usersConfig);
    }
    
    @Override
    @EventHandler(priority=EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event){
        Location location = event.getPlayer().getLocation();
        ZoneUser user = ZoneCore.getUser(event.getPlayer());
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
    
    public void onNewZoneUser(NewZoneUserEvent event){
        if(event.getZoneUserManager() == this){
            ZoneUser zoneUser = event.getZoneUser();
            zoneUser.getConfig().set(zoneUser.getName()+".lastjoined", System.currentTimeMillis());
            zoneUser.getConfig().set("playtime",0);
            zoneUser.getConfig().set("playtimecheck",System.currentTimeMillis());
            zoneUser.saveConfig();
            
            if(ZoneCore.getCorePlugin().inDebugMode()) {
                System.out.println("New ZoneUser: "+zoneUser.getName());
            }
        }
    }
}
