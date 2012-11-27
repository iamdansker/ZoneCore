/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Commands;

import info.jeppes.ZoneCore.ZonePlugin;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
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
        commands.add(command);
        String[] aliases = command.getAliases();
        for(String alias : aliases){
            alias = alias.toLowerCase();
            SubCommandManager aliasManager = subCommandManagers.get(alias);
            if(aliasManager == null){
                aliasManager = addSubCommandManager(alias);
            }
            aliasManager.addCommand(command);
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
    public boolean registreBukkitCommand(String alias, Command command){
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
         return true;
    }
    public List<ZoneCommand> getAllCommands(){
        return commands;
    }
    
}
