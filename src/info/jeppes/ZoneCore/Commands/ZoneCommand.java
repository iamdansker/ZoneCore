/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Commands;

import info.jeppes.ZoneCore.Exceptions.NotEnoughArguementsException;
import info.jeppes.ZoneCore.ZonePlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Jeppe
 */
public interface ZoneCommand extends CommandExecutor{
    public void run(ZonePlugin plugin, CommandSender cs, org.bukkit.command.Command cmnd, String[] args);
    public String getCommandName();
    public String getName();
    public String getDescrption();
    public String getUsage();
    public String getDefaultUsage();
//    @Deprecated
//    public boolean canRun(CommandSender cs);
    public void setPermissionNodes(String[] permissionNodes);
    public String[] getPermissionNodes();
    public boolean canRun(CommandSender cs, String[] args) throws NotEnoughArguementsException;
    public boolean hasSimplePermission(CommandSender cs);
    public boolean hasPermission(CommandSender cs, String[] args);
    public void setShowInHelp(boolean show);
    public boolean showInHelp();
    
    public boolean isPlayerOnlyCommand();
    public void setPlayerOnlyCommand(boolean playerOnlyCommand);
    public void setMinimumArguements(int minArgs);
    public int getMinimumArguments();
    
    public boolean isPrimaryCommand(String alias);
    public void setIsPrimaryCommand(boolean isPrimaryCommand);
    public void setIsPrimaryCommand(boolean isPrimaryCommand, String alias);
    
    public boolean isHelpCommand(String alias);
    public void setIsHelpCommand(boolean isPrimaryCommand);
    public void setIsHelpCommand(boolean isPrimaryCommand, String alias);
    
    public Player toPlayerObject(CommandSender cs);
    
    public void sendMessage(CommandSender cs, String message);
    public void sendErrorMessage(CommandSender cs, String message);
    
    public void playerOnlyCommandException(CommandSender cs);
    public void noPermissions(CommandSender cs);
    public void missingArguments(CommandSender cs);
    public void missingItemException(CommandSender cs,String item, String itemName);
    
    public void logCommand(String[] args, CommandSender cs, boolean allow);
    public void logCommand(String commandLine, CommandSender cs, boolean allow);

    public String[] getAliases();
    public String[] getSubAliases();
}
