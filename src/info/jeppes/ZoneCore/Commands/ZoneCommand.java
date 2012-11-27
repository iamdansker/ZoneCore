/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Commands;

import info.jeppes.ZoneCore.ZonePlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Jeppe
 */
public interface ZoneCommand extends CommandExecutor{
//    public void runCommand(ZoneGate plugin, Player player, String[] args);
    public void run(ZonePlugin plugin, CommandSender cs, org.bukkit.command.Command cmnd, String[] args);
    public String getDescrption();
    public String getUsage();
    public String getDefaultUsage();
    public boolean canRun(CommandSender cs);
    public void setShowInHelp(boolean show);
    public boolean showInHelp();
    public boolean isHelpCommand(String alias);
    public void setIsHelpCommand(boolean isHelpCommand);
    public void setIsHelpCommand(boolean isHelpCommand, String alias);
    
    public void sendMessage(CommandSender cs, String message);
    public void sendErrorMessage(CommandSender cs, String message);
    
    public void noPermissions(CommandSender cs);
    public void missingArguments(CommandSender cs);
    public void missingItemException(CommandSender cs,String item, String itemName);
    
    public void logCommand(String[] args, CommandSender cs, boolean allow);
    public void logCommand(String commandLine, CommandSender cs, boolean allow);

    public String[] getAliases();
    public String[] getSubAliases();
}
