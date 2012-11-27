/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Listeners;

import com.vexsoftware.votifier.model.VotifierEvent;
import info.jeppes.ZoneCore.Users.ZoneUser;
import info.jeppes.ZoneCore.ZoneCore;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Jeppe
 */
public class PlayerListener implements Listener{
    
    @EventHandler(priority=EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        ZoneUser user = ZoneCore.getUser(event.getPlayer());
        if(user != null){
            ConfigurationSection config = user.getConfig();
            config.set("lastjoined", System.currentTimeMillis());
            config.set("playtimecheck",System.currentTimeMillis());
            user.saveConfig();
        }
    }
    
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
}
