/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Users;

import info.jeppes.ZoneCore.ZoneConfig;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Jeppe
 */
public interface ZoneUser extends Player{
    public void newUser();
    public ConfigurationSection getConfig();
    public ZoneConfig getUsersConfig();
    public ZoneUserData.ServerGroup getServerGroup();
    
    public HashMap<String,Object> getTempData();
    public Object getTempData(String key);
    public void addTempData(String key, Object obj);
    public void removeTempData(String key);
    public void removeTempData(Object obj);
    public void setTempData(HashMap<String,Object> objList);
    public void clearTempData();

    public RecommendationsHolder getRecommendationsHolder();
    public void setPlayer(Player player);
    
    public void onPlayerJoin(PlayerJoinEvent event);
    public boolean sendMesssagesWhenOnline();
    public boolean giveItemsWhenOnline();
    public boolean giveLevelsWhenOnline();
    
    public void sendMesssageWhenOnline(String message);
    public void giveItemWhenOnline(ItemStack itemStack);
    public void giveLevelsWhenOnline(int i);
    public void saveConfig();

    public void updatePlayTime();
    public long getPlayTime();
}
