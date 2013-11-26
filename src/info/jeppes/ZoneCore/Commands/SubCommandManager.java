/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Commands;

import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Jeppe
 */
public class SubCommandManager extends Command implements CommandExecutor{

    private ArrayList<ZoneCommand> commands = new ArrayList<>();
    private ZoneCommand primaryCommand = null;
    private ZoneCommand helpCommand = null;
    
    public SubCommandManager(String alias){
        super(alias);
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String commandName, String[] args) {
        execute(cs,commandName,args);
        return true;
    }
    
    @Override
    public boolean execute(CommandSender cs, String commandName, String[] args) {
        try{
            String[] commandArgs = new String[args.length+1];
            commandArgs[0] = commandName;
            System.arraycopy(args, 0, commandArgs, 1, args.length);
            if(commandArgs.length > 1){
                ZoneCommand zoneCommand = getCommand(commandArgs[1]);
                if(zoneCommand != null){
                    zoneCommand.onCommand(cs, this, commandName, commandArgs);
                } else {
                    if(primaryCommand != null){
                        primaryCommand.onCommand(cs, this, commandName, commandArgs);
                    } else if(helpCommand != null){
                        helpCommand.onCommand(cs, this, commandName, commandArgs);
                    }
                }
            } else {
                for(ZoneCommand command : commands){
                    if(command.getSubAliases() == null){
                        command.onCommand(cs, this, commandName, commandArgs);
                    }
                }
                if(primaryCommand != null){
                    primaryCommand.onCommand(cs, this, commandName, commandArgs);
                } else if(helpCommand != null){
                    helpCommand.onCommand(cs, this, commandName, commandArgs);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            return false;
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
    
    public ArrayList<ZoneCommand> getCommands(){
        return commands;
    }
    
    public boolean containsCommandClass(Class<ZoneCommand> commandClass){
        for(ZoneCommand command : commands){
            if(command.getClass() == commandClass){
                return true;
            }
        }
        return false;
    }
    
    public boolean addCommand(ZoneCommand command) {
        if(commands.contains(command) || containsCommandClass((Class<ZoneCommand>)command.getClass())){
            return false;
        }
        
        commands.add(command);
        if(command.isPrimaryCommand(this.getName())){
            primaryCommand = command;
        } else if(command.isHelpCommand(this.getName())){
            helpCommand = command;
        }
        return true;
    }
    public void removeCommand(ZoneCommand command) {
        commands.remove(command);
        if(primaryCommand.equals(command)) {
            primaryCommand = null;
        }
    }
}
