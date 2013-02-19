/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Commands;

import info.jeppes.ZoneCore.ZoneConfig;
import info.jeppes.ZoneCore.ZoneCore;
import info.jeppes.ZoneCore.ZonePlugin;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.SimplePluginManager;

/**
 *
 * @author Jeppe
 */
public class ZoneCommandManager {
    private HashMap<String,SubCommandManager> subCommandManagers = new HashMap<>();
    private List<ZoneCommand> commands = new ArrayList<>();
    private final ZonePlugin plugin;
    
    public ZoneCommandManager(ZonePlugin plugin){
        this.plugin = plugin;
    }
    
    public void registreCommand(ZoneCommand command){
        String[] aliases = command.getAliases();
        for(String alias : aliases){
            alias = alias.toLowerCase();
            SubCommandManager aliasManager = subCommandManagers.get(alias);
            if(aliasManager == null){
                aliasManager = addSubCommandManager(alias);
            }
            boolean addCommand = aliasManager.addCommand(command);
            if(addCommand){
                if(!commands.contains(command)){
                    commands.add(command);
                }
            } else {
                plugin.getLogger().log(Level.INFO, "Could not register command: "+command.getCommandName());
            }
        }
        
    }
    public void unregistreCommand(ZoneCommand command){
        commands.remove(command);
        for(String commandAlias : command.getAliases()){
            SubCommandManager subCommandManager = subCommandManagers.get(commandAlias);
            if(subCommandManager != null){
                subCommandManager.removeCommand(command);
            }
        }
    }
    
    public ZoneCommand getCommand(String commandAlias, String subCommandAlias){
        SubCommandManager subCommandManager = subCommandManagers.get(commandAlias.toLowerCase());
        if(subCommandManager != null){
            return subCommandManager.getCommand(subCommandAlias);
        }
        return null;
    }
    
    public SubCommandManager addSubCommandManager(String commandAlias){
        commandAlias = commandAlias.toLowerCase();
        if(!subCommandManagers.containsKey(commandAlias)){
            SubCommandManager subCommandManager = new SubCommandManager(commandAlias);
            subCommandManagers.put(commandAlias, subCommandManager);
            registreBukkitCommand(commandAlias,subCommandManager);
        }
        return subCommandManagers.get(commandAlias);
    }
    public HashMap<String, SubCommandManager> getSubCommandManagers(){
        return subCommandManagers;
    }
    public boolean registreBukkitCommand(String alias, SubCommandManager command){
        PluginCommand commandFromBukkit = plugin.getCommand(alias);
        if(commandFromBukkit == null || command == null){
            plugin.getLogger().log(Level.SEVERE, "Could not load command "+ alias);
            if(ZoneCore.getCorePlugin().inDebugMode() || plugin.inDebugMode()) {
                plugin.getLogger().log(Level.SEVERE, "Debug info: ");
                plugin.getLogger().log(Level.SEVERE, "CommandFromBukkit: "+commandFromBukkit);
                plugin.getLogger().log(Level.SEVERE, "ZoneCommand: "+command);
            }
            return false;
        }
        if(ZoneCore.getCorePlugin().inDebugMode() || plugin.inDebugMode()){
            plugin.getLogger().log(Level.INFO, "loaded command "+ alias);
        }
        boolean injectCommands = false;
        ZoneConfig config = ZoneCore.getCorePlugin().getConfig();
        if(config.contains("injectCommands")){
            injectCommands = config.getBoolean("injectCommands");
        }   
        if(injectCommands){
            CommandMap commandMap = null;

            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
               try {
                   final Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                   f.setAccessible(true);
                   commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
               } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                   Logger.getLogger(ZoneCommandManager.class.getName()).log(Level.SEVERE, null, ex);
                   return false;
               }
            }
            commandMap.register(alias, command);
        } else { 
            commandFromBukkit.setExecutor(command);
        }
         return true;
    }
    public List<ZoneCommand> getAllCommands(){
        return commands;
    }
    
}
