/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Users;

import info.jeppes.ZoneCore.Events.NewZoneUserEvent;
import info.jeppes.ZoneCore.ZoneConfig;
import info.jeppes.ZoneCore.ZoneCore;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author jeppe
 */
public class ZoneCoreUserManager extends ZoneUserManager{
    public ZoneCoreUserManager(ZoneConfig usersConfig) {
        super(ZoneCore.getCorePlugin(),usersConfig);
    }
    
    public ZoneCoreUser getZoneCoreUser(Player player) {
        return (ZoneCoreUser)getUser(player);
    }
    public ZoneCoreUser getZoneCoreUser(String playerName) {
        return (ZoneCoreUser)getUser(playerName);
    }
    @Override
    public void onPlayerQuit(PlayerQuitEvent event){
        Location location = event.getPlayer().getLocation();
        ZoneCoreUser user = getZoneCoreUser(event.getPlayer());
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
