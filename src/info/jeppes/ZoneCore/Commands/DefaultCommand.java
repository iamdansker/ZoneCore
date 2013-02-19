/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Commands;

import info.jeppes.ZoneCore.ZonePlugin;

/**
 *
 * @author Jeppe
 */
public abstract class DefaultCommand extends CommandData{

    public DefaultCommand(String commandName, ZonePlugin plugin) {
        super(commandName, plugin);
    }

    public DefaultCommand(String commandName, String commandAlias, ZonePlugin plugin) {
        super(commandName, commandAlias, plugin);
    }

    public DefaultCommand(String commandName, String[] commandAliases, ZonePlugin plugin) {
        super(commandName, commandAliases, plugin);
    }

    public DefaultCommand(String commandName, String[] commandAliases, String[] subCommandAliases, ZonePlugin plugin) {
        super(commandName, commandAliases, subCommandAliases, plugin);
    }
    
//    public abstract void initDefaultCommand();
//    @Override
//    public String getCommandName(){
//        return getDefaultCommandName();
//    }
//    public abstract String getDefaultCommandName();
//    @Override
//    public String[] getAliases() {
//        return getDefaultAliases();
//    }
//    public abstract String[] getDefaultAliases();
//
//    @Override
//    public String[] getSubAliases() {
//        return getDefaultSubAliases();
//    }
//    public abstract String[] getDefaultSubAliases();

    public abstract DefaultCommand clone(ZonePlugin plugin);
    
}
