/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Users;

import info.jeppes.ZoneCore.Users.ZoneCoreUserData.ServerGroup;
import info.jeppes.ZoneCore.ZoneCore;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Bukkit;

/**
 *
 * @author Jeppe
 */
public class RecommendationsManager {
    
    private static String voteseason = "";
    private static long voteCooldown = 86400000;
    private static boolean seasonChangeWaiting = false;
    
    public RecommendationsManager(){
        voteseason = calcNewVoteseason();
        String lastKnowVoteseason = ZoneCore.getCorePlugin().getConfig().getString("lastKnownVoteseason");
        if(lastKnowVoteseason != null){
            if(!voteseason.equalsIgnoreCase(lastKnowVoteseason)){
                voteseason = lastKnowVoteseason;
                seasonChangeWaiting = true;
            }
        }
        
        
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(ZoneCore.getCorePlugin(), new Runnable(){
            @Override
            public void run() {
                if(checkseason()){
                    newVoteseason();
                }
            }
        },72000,72000);
    }
    public static boolean seasonChangeWaiting() {
        return seasonChangeWaiting;
    }
    
    public static boolean checkseason(){
        return !voteseason.equalsIgnoreCase(calcNewVoteseason());
    }
    
    public static String getVoteseason(){
        return voteseason;
    }
    
    public static String calcNewVoteseason(){
        DateFormat dateFormat = new SimpleDateFormat("MMyyyy");
        Date date = new Date();
        return "season_"+dateFormat.format(date);
    }
    
    
    public void newVoteseason(){
        seasonChangeWaiting = false;
        String oldseason = voteseason;
        voteseason = calcNewVoteseason();
        ZoneCore.getCorePlugin().getConfig().set("lastKnownVoteseason",voteseason);
        ZoneCore.getCorePlugin().getConfig().save();
        for(ZoneUser user : ZoneCore.getUsers().values()){
            ZoneCoreUser coreUser = (ZoneCoreUser)user;
            coreUser.getRecommendationsHolder().newseason(voteseason, oldseason);
        }
        ZoneCore.getUserManager().getUsersConfig().schedualSave();
    }
    
    public static long getVoteCooldown(){
        return voteCooldown;
    }
    
    public static int getVotesPerVote(ZoneCoreUser user){
        ServerGroup serverGroup = user.getServerGroup();
        if(serverGroup == null){
            return 1;
        }
        switch(serverGroup){
            case Basic:
                return 0;
            case Regular:
                return 1;
            case VIP:
                return 2;
            case Donator:
                return 3;
            case Mod:
                return 5;
            case SuperModArcane:
                return 6;
            case SuperModPremium:
                return 5;
            case SuperModArchitect:
                return 5;
            case SuperModElite:
                return 5;
            case SuperModSponsor:
                return 5;
            case Sponsor:
                return 4;
            case Elite:
                return 4;
            case Architect:
                return 5;
            case Premium:
                return 5;
            case Arcane:
                return 6;
            case Admin:
                return 6;
            case Owner:
                return 10;
            default:
                return 0;
        }
    }

}
