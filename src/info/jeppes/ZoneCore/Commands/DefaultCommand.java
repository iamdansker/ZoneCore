/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Commands;

import info.jeppes.ZoneCore.ZonePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Jeppe
 */
public abstract class DefaultCommand extends CommandData{

    public DefaultCommand(ZonePlugin instance){
        super(null,instance);
        initDefaultCommand();
    }
    
    public abstract void initDefaultCommand();
    @Override
    public String getCommandName(){
        return getDefaultCommandName();
    }
    public abstract String getDefaultCommandName();
    @Override
    public String[] getAliases() {
        return getDefaultAliases();
    }
    public abstract String[] getDefaultAliases();

    @Override
    public String[] getSubAliases() {
        return getDefaultSubAliases();
    }
    public abstract String[] getDefaultSubAliases();

    
}
