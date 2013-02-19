package info.jeppes.ZoneCore.Commands.DefaultCommands;

import info.jeppes.ZoneCore.Commands.CommandData;
import info.jeppes.ZoneCore.Commands.DefaultCommand;
import info.jeppes.ZoneCore.Commands.ZoneCommand;
import info.jeppes.ZoneCore.ZonePlugin;
import info.jeppes.ZoneCore.ZoneTools;
import info.jeppes.ZoneWorld.ZoneWorldAPI;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
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
public class Help extends DefaultCommand{
    
    public Help(ZonePlugin plugin){
        super("help",plugin);
        this.setIsHelpCommand(true);
    }

    @Override
    public void run(ZonePlugin plugin, CommandSender cs, org.bukkit.command.Command cmnd, String[] args) {
        List<ZoneCommand> commands = getPlugin().getCommandManager().getAllCommands();
        
        int page = 1;
        int objectsPerPage = 7;
        if(args.length >= 3){
            try{
                page = Integer.parseInt(args[2]);
            }catch(Exception e){
                sendErrorMessage(cs,"The page number you entered is not a number");
            }
        }
        int lastPageNumber = ((int)Math.ceil((double) commands.size() / objectsPerPage));
        sendMessage(cs,ChatColor.LIGHT_PURPLE+"[help list] page "+page+" of "+lastPageNumber);
        
        if(page > lastPageNumber){
            sendMessage(cs,"There is no page "+page);
            return;
        }
        
        Object[] commandsOnPageObj = ZoneTools.getObjectsOnPage(commands, page, objectsPerPage);
        for(int i = 0; i <  commandsOnPageObj.length; i++){
            ZoneCommand command = (ZoneCommand)commandsOnPageObj[i];
            if(command.showInHelp() && command.hasSimplePermission(cs)){
                sendMessage(cs,command.getUsage());
            }
        }
    }

    @Override
    public String getDescrption() {
        return "Gives you a list over all the "+this.getPlugin().getName()+" commands";
    }

    @Override
    public String getUsage() {
        return this.getDefaultUsage()+" {[page]}";
    }

    @Override
    public DefaultCommand clone(ZonePlugin plugin) {
        return new Help(plugin);
    }
}