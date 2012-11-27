/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Commands;

import info.jeppes.ZoneCore.ZoneCore;
import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Jeppe
 */
public class SubCommandManager extends Command{

    private ArrayList<ZoneCommand> commands = new ArrayList<>();
    private ZoneCommand helpCommand = null;
    
    public SubCommandManager(String alias){
        super(alias);
        
    }
    
    @Override
    public boolean execute(CommandSender cs, String commandName, String[] args) {
        String[] commandArgs = new String[args.length+1];
        commandArgs[0] = commandName;
        System.arraycopy(args, 0, commandArgs, 1, args.length);
        if(args.length >= 1){
            ZoneCommand zoneCommand = getCommand(args[0]);
            if(zoneCommand != null){
                zoneCommand.onCommand(cs, this, commandName, commandArgs);
            } else {
                if(helpCommand != null){
                    helpCommand.onCommand(cs, this, commandName, commandArgs);
                }
            }
        } else {
            for(ZoneCommand command : commands){
                if(command.getSubAliases() == null){
                    command.onCommand(cs, this, commandName, commandArgs);
                }
            }
            if(helpCommand != null){
                helpCommand.onCommand(cs, this, commandName, commandArgs);
            }
        }
        return false;
    }

    public ZoneCommand getCommand(String subAlias){
        ZoneCommand alternativeCommand = null;
        for(ZoneCommand command : commands){
            if(command.getSubAliases() == null){
                alternativeCommand = command;
            } else {
                for(String subCommandAlias : command.getSubAliases()){
                    if(subCommandAlias.equalsIgnoreCase(subAlias)){
                        return command;
                    }
                }
            }
        }
        return alternativeCommand;
    }
    
    public void addCommand(ZoneCommand command) {
        if(commands.contains(command)){
            return;
        }
        commands.add(command);
        if(command.isHelpCommand(this.getName())){
            helpCommand = command;
        }
    }
    public void removeCommand(ZoneCommand command) {
        commands.remove(command);
        if(helpCommand.equals(command)) {
            helpCommand = null;
        }
    }
}
