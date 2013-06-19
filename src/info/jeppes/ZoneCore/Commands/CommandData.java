/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Commands;

import info.jeppes.ZoneCore.Exceptions.NotEnoughArguementsException;
import info.jeppes.ZoneCore.ZoneCore;
import info.jeppes.ZoneCore.ZonePlugin;
import java.util.HashMap;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Jeppe
 */
public abstract class CommandData implements ZoneCommand{
    private final String commandName;
    private boolean showInHelp = true;
    private boolean playerOnlineCommand = false;
    private final String[] commandAliases;
    private final String[] subCommandAliases;
    private final ZonePlugin plugin;
    private HashMap<String,Boolean> isPrimaryCommand = new HashMap<>();
    private HashMap<String,Boolean> isHelpCommand = new HashMap<>();
    private int minArgs = 0;
    private String[] permissionNodes;
    
    public CommandData(String commandName, ZonePlugin plugin){
        this(commandName,plugin.getCommandAliases(),plugin);
//        this.commandName = commandName;
//        this.commandAliases = plugin.getCommandAliases();
//        this.subCommandAliases = new String[]{commandName};
//        this.permissionNodes = new String[]{plugin.getName()};
//        this.plugin = plugin;
    }
    public CommandData(String commandName, String commandAlias, ZonePlugin plugin){
        this(commandName,new String[]{commandAlias},plugin);
//        this.commandName = commandName;
//        this.commandAliases = new String[]{commandAlias};
//        this.subCommandAliases = new String[]{commandName};
//        this.permissionNodes = new String[]{plugin.getName()};
//        this.plugin = plugin;
    }
    public CommandData(String commandName, String[] commandAliases, ZonePlugin plugin){
        this(commandName,commandAliases,new String[]{commandName},plugin);
//        this.commandName = commandName;
//        this.commandAliases = commandAliases;
//        this.subCommandAliases = new String[]{commandName};
//        this.permissionNodes = new String[]{plugin.getName()};
//        this.plugin = plugin;
    }
    public CommandData(String commandName, String[] commandAliases, String[] subCommandAliases, ZonePlugin plugin){
        this.commandName = commandName;
        this.commandAliases = commandAliases;
        this.subCommandAliases = subCommandAliases;
        this.permissionNodes = new String[]{plugin.getName()};
        this.plugin = plugin;
    }
    
    public ZonePlugin getPlugin(){
        return plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender cs, org.bukkit.command.Command cmnd, String string, String[] args) {
        try {
            if(!canRun(cs,args)){
                logCommand(args, cs, false);
                noPermissions(cs);
                return false;
            } else {
                logCommand(args, cs, true);
                run(plugin,cs,cmnd,args);
                //The command will display it's own error message if needed so it just need to return true
                return true;
            }
        } catch (NotEnoughArguementsException ex) {
            this.missingArguments(cs);
        }
        return false;
    }
    
//    @Override
//    @Deprecated
//    public boolean canRun(CommandSender cs){
//        return ZoneCore.hasPermission(cs , plugin.getName() + "." + commandName);
//    }
    
    @Override
    public void setPermissionNodes(String[] permissionNodes) {
        this.permissionNodes = permissionNodes;
    }

    @Override
    public String[] getPermissionNodes() {
        return permissionNodes;
    }
    
    @Override
    public boolean canRun(CommandSender cs, String[] args) throws NotEnoughArguementsException{
        if(this.isPlayerOnlyCommand()){
            if(!(cs instanceof Player)){
                return false;
            }
        }
        if(args.length < this.getMinimumArguments()){
            throw new NotEnoughArguementsException(args, args.length, getMinimumArguments());
        }
        return hasPermission(cs,args);
    }
    @Override
    public boolean hasSimplePermission(CommandSender cs){
        for(String permissionNode : getPermissionNodes()){
            if(ZoneCore.hasPermission(cs , permissionNode + "." + commandName)){
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean hasPermission(CommandSender cs, String[] args){
        return hasSimplePermission(cs);
    }

    @Override
    public boolean isPlayerOnlyCommand() {
        return playerOnlineCommand;
    }
    @Override
    public void setPlayerOnlyCommand(boolean playerOnlineCommand) {
        this.playerOnlineCommand = playerOnlineCommand;
    }
    
    @Override
    public Player toPlayerObject(CommandSender cs){
        if(this.isPlayerOnlyCommand()){
            return (Player)cs;
        }
        if(cs instanceof Player){
            return (Player)cs;
        }
        return null;
    }
    
    @Override
    public void setMinimumArguements(int minArgs){
        this.minArgs = minArgs;
    }
    @Override
    public int getMinimumArguments(){
        return minArgs;
    }
    
    @Override
    public void setShowInHelp(boolean show){
        showInHelp = show;
    }
    @Override
    public boolean showInHelp(){
        return showInHelp;
    }

    @Override
    public boolean isPrimaryCommand(String alias){
        return isPrimaryCommand.containsKey(alias) ? isPrimaryCommand.get(alias) : false ;
    }
    @Override
    public void setIsPrimaryCommand(boolean isPrimaryCommand){
        for(String alias : this.getAliases()){
            setIsPrimaryCommand(isPrimaryCommand, alias);
        }
    }
    @Override
    public void setIsPrimaryCommand(boolean isPrimaryCommand, String alias){
        this.isPrimaryCommand.put(alias, isPrimaryCommand);
    }
    
    @Override
    public boolean isHelpCommand(String alias) {
        return isHelpCommand.containsKey(alias) ? isHelpCommand.get(alias) : false ;
    }

    @Override
    public void setIsHelpCommand(boolean isHelpCommand) {
        for(String alias : this.getAliases()){
            setIsHelpCommand(isHelpCommand, alias);
        }
    }

    @Override
    public void setIsHelpCommand(boolean isHelpCommand, String alias) {
        this.isHelpCommand.put(alias, isHelpCommand);
    }
    
    @Override
    public String[] getAliases() {
        return commandAliases;
    }

    @Override
    public String[] getSubAliases() {
        return subCommandAliases;
    }
    
    @Override
    public String getDefaultUsage() {
        return "/" + (getPlugin().getCommandAliases() != null ? getPlugin().getCommandAliases()[0] : getPlugin().getName())+ " "+getCommandName();
    }
//    public static Command getCommand(ZoneManager.Manager manager, String command){
//        String commandClassDirectory = manager.getPackageDirectory()+"."+TextToCommandFormat(command);
//        return findCommand(commandClassDirectory);
//    }
    public static String TextToCommandFormat(String commandString){
        return commandString.substring(0,1).toUpperCase()+commandString.substring(1).toLowerCase();
    }
    public static ZoneCommand findCommand(String commandClassPath){
        try {
            Class<?> commandClass = Class.forName(commandClassPath);
            try {
                Object newInstance = commandClass.newInstance();
                if(newInstance instanceof ZoneCommand){
                    ZoneCommand command = (ZoneCommand)newInstance;
                    return command;
                }
            } catch (InstantiationException | IllegalAccessException ex) {
            }
        } catch (ClassNotFoundException ex) {
        }
        return null;

    }
    
    @Override
    public String getCommandName(){
        return commandName;
    }
    @Override
    public String getName(){
        return getCommandName();
    }
    
    ////////////
    //messages//
    ////////////
    
    @Override
    public void sendMessage(CommandSender cs, String message){
        getPlugin().getZoneCore().sendMessage(cs, message);
    }
    @Override
    public void sendErrorMessage(CommandSender cs, String message){
        getPlugin().getZoneCore().sendErrorMessage(cs, message);
    }
    
    //////////////
    //exceptions//
    //////////////
    
    @Override
    public void playerOnlyCommandException(CommandSender cs){
        sendErrorMessage(cs,"This command can only be used by players");
    }
    
    @Override
    public void noPermissions(CommandSender cs){
        sendErrorMessage(cs,"You do not have permission to use command "+getCommandName());
    }
    @Override
    public void missingArguments(CommandSender cs){
        sendErrorMessage(cs, "Missing arguments");
        sendMessage(cs,getUsage());
        sendMessage(cs,getDescrption());
    }
    @Override
    public void missingItemException(CommandSender cs,String item, String itemName){
        sendErrorMessage(cs,item + ": " + itemName + " is missing or does not exist");
    }
    
    @Override
    public void logCommand(String[] args, CommandSender cs, boolean allow){
        String commandLine = "";
        for(int i = 0; i < args.length - 1; i++){
            commandLine += args[i] + " ";
        }
        commandLine += args[args.length-1];
        logCommand(commandLine,cs, allow);
    }
    @Override
    public void logCommand(String commandLine, CommandSender cs, boolean allow){
        String senderName = cs.getName();
        String tag;
        if(cs instanceof Player){
            tag = "[PLAYER_COMMAND] ";
        } else {
            tag = "[SERVER_COMMAND] ";
        }
        if(allow){
            Bukkit.getLogger().log(Level.INFO, (tag + senderName + ": "+commandLine));
        } else {
            Bukkit.getLogger().log(Level.INFO, (tag + senderName + " Was denied access to command "+commandLine));
        }
    }
}
