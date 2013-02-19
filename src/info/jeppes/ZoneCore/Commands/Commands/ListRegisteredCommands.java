package info.jeppes.ZoneCore.Commands.Commands;

import info.jeppes.ZoneCore.Commands.CommandData;
import info.jeppes.ZoneCore.Commands.SubCommandManager;
import info.jeppes.ZoneCore.Commands.ZoneCommand;
import info.jeppes.ZoneCore.ZoneCore;
import info.jeppes.ZoneCore.ZonePlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/*
 * Author: Jeppe Boysen Vennekilde
 *
 * This document is Copyright ©() and is the intellectual property of the author.
 *
 * TERMS AND CONDITIONS
 * 0. USED TERMS
 * OWNER - The original author(s) of the program
 * USER - End user of the program, person installing/using the program.
 *
 * 1. LIABILITY
 * THIS PROGRAM IS PROVIDED 'AS IS' WITH NO WARRANTIES, IMPLIED OR OTHERWISE.
 * THE OWNER OF THIS PROGRAM TAKES NO RESPONSIBILITY FOR ANY DAMAGES INCURRED
 * FROM THE USE OF THIS PROGRAM.
 *
 * 2. REDISTRIBUTION
 * This program may only be distributed where uploaded, mirrored, or otherwise
 * linked to by the OWNER solely. All mirrors of this program must have advance
 * written permission from the OWNER. ANY attempts to make money off of this
 * program (selling, selling modified versions, adfly, sharecash, etc.) are
 * STRICTLY FORBIDDEN, and the OWNER may claim damages or take other action to
 * rectify the situation.
 *
 * 3. DERIVATIVE WORKS/MODIFICATION
 * This program is provided freely and may be decompiled and modified for
 * private use, either with a decompiler or a bytecode editor. Public
 * distribution of modified versions of this program require advance written
 * permission of the OWNER and may be subject to certain terms.
 */

public class ListRegisteredCommands extends CommandData{

    public ListRegisteredCommands(){
        super("listRegisteredCommands",ZoneCore.getCorePlugin());
    }
    
    @Override
    public void run(ZonePlugin plugin, CommandSender cs, Command cmnd, String[] args) {
        String zonePluginName = "";
        if(args.length <= 2){
            zonePluginName = "ZoneCore";
        } else {
            zonePluginName = args[2];
        }
        if(ZoneCore.isZonePluginRunning(zonePluginName)){
            ZonePlugin zonePlugin = ZoneCore.getZonePluginsMap().get(zonePluginName.toLowerCase());
            HashMap<String, SubCommandManager> subCommandManagers = zonePlugin.getCommandManager().getSubCommandManagers();
            for(String subCommandManagerAlias : subCommandManagers.keySet()){
                sendMessage(cs,"Alias - "+subCommandManagerAlias);
                SubCommandManager subCommandManager = subCommandManagers.get(subCommandManagerAlias);
                ArrayList<ZoneCommand> commands = subCommandManager.getCommands();
                for(ZoneCommand command : commands){
                    for(String subAlias : command.getSubAliases()){
                        this.sendMessage(cs, "/"+subCommandManagerAlias+" "+subAlias);
                    }
                }
            }
        } else {
            this.sendErrorMessage(cs,zonePluginName+" is not running");
        }
    }

    @Override
    public String getDescrption() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getUsage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
