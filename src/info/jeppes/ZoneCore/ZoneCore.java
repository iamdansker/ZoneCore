/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore;

import info.jeppes.ZoneCore.Users.ZoneUser;
import info.jeppes.ZoneCore.Users.ZoneUserManager;
import info.jeppes.ZoneGate.ZoneGatePlugin;
import info.jeppes.ZoneWarp.ZoneWarpPlugin;
import info.jeppes.ZoneWorld.ZoneWorldPlugin;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Jeppe
 */
public class ZoneCore extends ZoneAPI{
    private static HashMap<String,ZonePlugin> zonePlugins = new HashMap<>();
    private static Server server = null;
    private static String mainDirectory = "plugins"+File.separator+"ZoneCore";
    private static String zoneGatePluginName = "ZoneGate";
    private static String zoneWorldPluginName = "ZoneWorld";
    private static String zoneWarpPluginName = "ZoneWarp";
    private static String defaultCommandsPackageDirectory = "info.jeppes.ZoneCore.Commands.DefaultCommands";
    private static ZoneCorePlugin corePlugin = null;
    
    
    private final ZonePlugin plugin;
    private String pluginDirectory;
    
    public ZoneCore(ZonePlugin zonePlugin){
        plugin = zonePlugin;
        server = zonePlugin.getServer();
        
        pluginDirectory = mainDirectory + File.separator + zonePlugin.getName();
        File pluginDir = new File(pluginDirectory);
        pluginDir.mkdirs();
    }
    
    public void setCore(ZoneCorePlugin core){
        corePlugin = core;
    }
    public static ZoneCorePlugin getCorePlugin(){
        return corePlugin;
    }
    
    public ZonePlugin getPlugin(){
        return plugin;
    }
    
    public static ZoneUserManager getUserManager(){
        return getCorePlugin().getUserManager();
    }
    public static Map<String, ZoneUser> getUsers() {
        return ZoneUserManager.getUsers();
    }
    public static ZoneUser getUser(String userName){
        return ZoneUserManager.getUser(userName);
    }
    public static ZoneUser getUser(Player player){
        return ZoneUserManager.getUser(player);
    }
    
    public String getPluginDirectory(){
        return pluginDirectory;
    }
    public static String getMainPluginDirectory(){
        return mainDirectory;
    }
    public static String getDefaultCommandsPackageDirectory() {
        return defaultCommandsPackageDirectory;
    }
    
    public static boolean hasPermission(CommandSender cs, String permission){
        if(cs.isOp()){
            return true;
        }
        return cs.hasPermission(permission);
    }
    public static boolean hasPermission(Player player, String permission){

        if(player.isOp()){
            return true;
        }
        return player.hasPermission(permission);
    }
    
    public void sendMessage(CommandSender cs, String message){
        cs.sendMessage(ChatColor.BLUE + getPlugin().getName() + ": "+ChatColor.GREEN+message);
    }
    public void sendErrorMessage(CommandSender cs, String message){
        cs.sendMessage(ChatColor.LIGHT_PURPLE + getPlugin().getName() + " Error: "+ChatColor.RED+message);
    }
    
    public static ZoneGatePlugin getZoneGate(){
        Plugin plugin = server.getPluginManager().getPlugin(zoneGatePluginName);
        if(plugin == null){
            return null;
        }
        if(plugin instanceof ZoneGatePlugin){
            ZoneGatePlugin zoneGatePlugin = (ZoneGatePlugin)plugin;
            return zoneGatePlugin;
        }
        return null;
    }
    public static ZoneWorldPlugin getZoneWorld(){
        Plugin plugin = server.getPluginManager().getPlugin(zoneWorldPluginName);
        if(plugin == null){
            return null;
        }
        if(plugin instanceof ZoneWorldPlugin){
            ZoneWorldPlugin zoneWorldPlugin = (ZoneWorldPlugin)plugin;
            return zoneWorldPlugin;
        }
        return null;
    }
    public static ZoneWarpPlugin getZoneWarp(){
        Plugin plugin = server.getPluginManager().getPlugin(zoneWarpPluginName);
        if(plugin == null){
            return null;
        }
        if(plugin instanceof ZoneWarpPlugin){
            ZoneWarpPlugin zoneWarpPlugin = (ZoneWarpPlugin)plugin;
            return zoneWarpPlugin;
        }
        return null;
    }
    
    public static void addZonePlugin(ZonePlugin plugin){
        zonePlugins.put(plugin.getName().toLowerCase(), plugin);
    }
    public static void removeZonePlugin(ZonePlugin plugin){
        zonePlugins.remove(plugin.getName().toLowerCase());
    }
    public static boolean isZoneGateRunning() {
        return isZonePluginRunning(zoneGatePluginName);
    }
    public static boolean isZoneWorldRunning() {
        return isZonePluginRunning(zoneWorldPluginName);
    }
    public static boolean isZoneWarpRunning() {
        return isZonePluginRunning(zoneWarpPluginName);
    }
    public static boolean isZonePluginRunning(String pluginName){
        return getZonePluginsMap().containsKey(pluginName.toLowerCase());
    }
    
    public static HashMap<String,ZonePlugin> getZonePluginsMap(){
        return zonePlugins;
    }
    public static List<ZonePlugin> getZonePluginsList(){
        return new ArrayList(getZonePluginsMap().values());
    }
}
