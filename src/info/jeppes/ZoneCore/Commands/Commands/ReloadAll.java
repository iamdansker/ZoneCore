/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Commands.Commands;

import info.jeppes.ZoneCore.Commands.CommandData;
import info.jeppes.ZoneCore.ZoneCore;
import info.jeppes.ZoneCore.ZoneCorePlugin;
import info.jeppes.ZoneCore.ZonePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Jeppe
 */
public class ReloadAll extends CommandData{

    public ReloadAll(){
        super("reload",ZoneCore.getCorePlugin());
    }
    
    @Override
    public void run(ZonePlugin plugin, CommandSender cs, Command cmnd, String[] args) {
        if(plugin instanceof ZoneCorePlugin){
            ZoneCorePlugin corePlugin = (ZoneCorePlugin)plugin;
            corePlugin.reloadEveryPlugin(cs);
        }
    }

    @Override
    public String getDescrption() {
        return "Reload every plugin registred by ZoneCore";
    }

    @Override
    public String getUsage() {
        return getDefaultUsage();
    }
}
