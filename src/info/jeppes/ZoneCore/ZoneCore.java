/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore;

import info.jeppes.ZoneCore.Commands.DefaultCommand;
import info.jeppes.ZoneCore.Users.ZoneCoreUser;
import info.jeppes.ZoneCore.Users.ZoneCoreUserManager;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Jeppe
 */
public class ZoneCore extends ZoneAPI{
    private static HashMap<String,ZonePlugin> zonePlugins = new HashMap<>();
    private static String mainDirectory = "plugins"+File.separator+"ZoneCore";
    private static String defaultCommandsPackageDirectory = "info.jeppes.ZoneCore.Commands.DefaultCommands";
    private static ZoneCorePlugin corePlugin = null;
    private static ArrayList<String> noneDefaultOpPermissions = new ArrayList();
    private static ArrayList<YamlConfiguration> configDefaults = new ArrayList();
    private static ArrayList<DefaultCommand> defaultCommands = new ArrayList();
    
    
    private final ZonePlugin plugin;
    private String pluginDirectory;
    
    public ZoneCore(ZonePlugin zonePlugin){
        plugin = zonePlugin;
        
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
    
    public static ZoneCoreUserManager<ZoneCoreUser> getUserManager(){
        return getCorePlugin().getUserManager();
    }
    public static Map<String, WeakReference<ZoneCoreUser>> getUsers() {
        return getUserManager().getUsers();
    }
    public static ZoneCoreUser getUser(String userName){
        return getUserManager().getUser(userName);
    }
    public static ZoneCoreUser getUser(Player player){
        return getUserManager().getUser(player);
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
    
    public static void addConfigDefault(YamlConfiguration config){
        configDefaults.add(config);
    }
    public static void removeConfigDefault(YamlConfiguration config){
        configDefaults.remove(config);
    }
    public static ArrayList<YamlConfiguration> getConfigDefaults(){
        return configDefaults;
    }
    
    public static void addDefaultCommand(DefaultCommand command){
        defaultCommands.add(command);
    }
    public static void removeDefaultCommand(DefaultCommand command){
        defaultCommands.remove(command);
    }
    public static ArrayList<DefaultCommand> getDefaultCommands(){
        return defaultCommands;
    }
    
    public static boolean hasPermission(CommandSender cs, String permission){
        if(cs.isOp() && isDefaultOpPermission(permission)){
            return true;
        }
        if(cs.hasPermission(permission + ".^")){
            return false;
        }
        boolean hasPermission = cs.hasPermission(permission);
        if(!hasPermission){
            String[] permissionsSplit = permission.split(".");
            StringBuilder tempPermission = new StringBuilder();
            boolean first = true;
            for(String permissionPart : permissionsSplit){
                if(!first){
                    tempPermission.append(".");
                } else {
                    first = false;
                }
                tempPermission.append(permissionPart);
                System.out.println(tempPermission.toString() + ".*");
                hasPermission = cs.hasPermission(tempPermission.toString() + ".*");
                if(hasPermission){
                    return true;
                }
            }
        }
        return hasPermission;
    }
    public static boolean hasPermission(Player player, String permission){
        return ZoneCore.hasPermission((CommandSender) player, permission);
    }
    
    public static void addDefaultOpPermission(String permission){
        noneDefaultOpPermissions.remove(permission);
    } 
    public static void removeDefaultOpPermission(String permission){
        noneDefaultOpPermissions.add(permission);
    } 
    public static boolean isDefaultOpPermission(String permission){
        return !noneDefaultOpPermissions.contains(permission);
    }
    
    public void sendMessage(CommandSender cs, String message){
        cs.sendMessage(ChatColor.BLUE + getPlugin().getName() + ": "+ChatColor.GREEN+message);
    }
    public void sendErrorMessage(CommandSender cs, String message){
        cs.sendMessage(ChatColor.LIGHT_PURPLE + getPlugin().getName() + " Error: "+ChatColor.RED+message);
    }
    public void sendMessage(CommandSender cs, String message, String tag){
        cs.sendMessage(ChatColor.BLUE + tag + ": "+ChatColor.GREEN+message);
    }
    public void sendErrorMessage(CommandSender cs, String message, String tag){
        cs.sendMessage(ChatColor.LIGHT_PURPLE + tag + " Error: "+ChatColor.RED+message);
    }
    
    public static void addZonePlugin(ZonePlugin plugin){
        zonePlugins.put(plugin.getName().toLowerCase(), plugin);
    }
    public static void removeZonePlugin(ZonePlugin plugin){
        zonePlugins.remove(plugin.getName().toLowerCase());
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
