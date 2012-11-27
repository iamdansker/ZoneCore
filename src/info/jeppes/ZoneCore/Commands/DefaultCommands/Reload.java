/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Commands.DefaultCommands;

import info.jeppes.ZoneCore.Commands.DefaultCommand;
import info.jeppes.ZoneCore.ZonePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Jeppe
 */
public class Reload extends DefaultCommand{

    private final ZonePlugin plugin;
    
    public Reload(ZonePlugin plugin){
        super(plugin);
        this.plugin = plugin;
    }
    
    @Override
    public void initDefaultCommand() {
    }

    @Override
    public String getDefaultCommandName() {
        return "reload";
    }

    @Override
    public String[] getDefaultAliases() {
        return plugin.getCommandAliases();
    }

    @Override
    public String[] getDefaultSubAliases() {
        return new String[]{"reload"};
    }
    
    @Override
    public void run(ZonePlugin plugin, CommandSender cs, Command cmnd, String[] args) {
        plugin.reloadPlugin(cs);
    }

    @Override
    public String getDescrption() {
        return "Reload "+getPlugin().getName();
    }

    @Override
    public String getUsage() {
        return getDefaultUsage();
    }
}
