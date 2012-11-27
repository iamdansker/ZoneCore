/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Users;

import com.vexsoftware.votifier.model.Vote;
import info.jeppes.ZoneCore.Exceptions.AlreadyVotedException;
import info.jeppes.ZoneCore.Exceptions.RecommendCooldownException;
import info.jeppes.ZoneCore.Exceptions.RecommendStaffException;
import info.jeppes.ZoneCore.Exceptions.StaffTempFixException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author Jeppe
 */
public class RecommendationsHolder {
    
    private int recommendations = 0;
    private int webVotes = 0;
    private long lastVote = 0;
    private long lastWebVote = 0;
    private float recommendationsPerWebvote = 0.15f;
    private HashMap<String,Integer> recommendationsHistory;
    private HashMap<String,Integer> webVotesHistory ;
    private final ZoneUser user;
    private final ConfigurationSection userConfig;
    private ArrayList<String> recommendedBy;
    
    public RecommendationsHolder(ZoneUser user, ConfigurationSection userConfig){
        this.user = user;
        this.userConfig = userConfig;
        load();
    }
    
    private void load(){
        recommendations = userConfig.getInt(RecommendationsManager.getVoteseason()+".recommendations");
        lastVote = userConfig.getInt(RecommendationsManager.getVoteseason()+".lastvote");
        lastWebVote = userConfig.getInt(RecommendationsManager.getVoteseason()+".lastwebvote");
        recommendedBy = (ArrayList<String>) userConfig.getList(RecommendationsManager.getVoteseason()+".recommendedBy");
        if(recommendedBy == null){
            recommendedBy = new ArrayList<>();
        }
        loadPreviousseasons();
        loadWebVotes();
    }
    
    private void loadPreviousseasons(){
        recommendationsHistory = new HashMap<>();
        webVotesHistory = new HashMap<>();
        ConfigurationSection votePastseasons = userConfig.getConfigurationSection("previusseasons");
        if(votePastseasons == null){
            return;
        }
        Set<String> keys = votePastseasons.getKeys(false);
        for(String key : keys){
            recommendationsHistory.put(key, votePastseasons.getInt(key+".recommendations"));
            webVotesHistory.put(key, votePastseasons.getInt(key+".webVotes"));
        }
    }
    private void loadWebVotes(){
        webVotes = getWebVotesSession(RecommendationsManager.getVoteseason());
    }
    public int getWebVotesSession(String season){
        ConfigurationSection configurationSection = userConfig.getConfigurationSection(season+".webVotes");
        if(configurationSection != null){
        int tempWebVotes = 0;
            Set<String> keys = configurationSection.getKeys(true);
            for(String key : keys){
                int votes = configurationSection.getInt(key);
                tempWebVotes += votes;
            }
            return tempWebVotes;
        } else if(webVotesHistory.containsKey(season)) {
            return webVotesHistory.get(season);
        }
        return 0;
    }
    
    public int getRecommendations(){
        return getRawRecommendations() + (int)((double)getWebVotes()*recommendationsPerWebvote);
    }
    public int getRawRecommendations(){
        return recommendations;
    }
    public long getLastVote(){
        return lastVote;
    }
    public int getWebVotes(){
        return webVotes;
    }
    public long getLastWebVote(){
        return lastWebVote;
    }
    
    public void canVoteFor(ZoneUser otherUser) throws AlreadyVotedException,RecommendCooldownException,RecommendStaffException{
        if(recommendedBy.contains(otherUser.getName())){
            throw new AlreadyVotedException();
        } else {
            long timeLeft = otherUser.getRecommendationsHolder().getLastVote() + RecommendationsManager.getVoteCooldown() - System.currentTimeMillis();
            if(timeLeft > 0){
                throw new RecommendCooldownException(timeLeft);
            } else {
                if(otherUser.getServerGroup().isStaff()){
                    if(user.getServerGroup().isStaff()){
                        throw new RecommendStaffException();
                    }
                }
            }
        }
    }
    
    public void voteFor(ZoneUser otherUser) throws RecommendCooldownException, AlreadyVotedException, RecommendStaffException, StaffTempFixException{
        //Will throw an exception if the other user cannot vote for this user
        canVoteFor(otherUser); 
        int votesPerVote = RecommendationsManager.getVotesPerVote(otherUser);
        user.getRecommendationsHolder().addRecommendations(otherUser,votesPerVote);
        //save last vote time;
        lastVote = System.currentTimeMillis();
        userConfig.set(RecommendationsManager.getVoteseason()+".lastvote", lastVote);
    }
    private void addRecommendations(ZoneUser otherUser, int votesPerVote) {
        recommendations += votesPerVote;
        recommendedBy.add(otherUser.getName());
        this.userConfig.set(RecommendationsManager.getVoteseason()+".recommendations", recommendations);
        this.userConfig.set(RecommendationsManager.getVoteseason()+".recommendedBy", recommendedBy);
        save();
    }
    
    public void addWebVote(Vote vote) {
        webVotes++;
        String[] serviceNames = vote.getServiceName().split("\\.");
        String serviceName = vote.getServiceName();
        if(serviceNames.length != 0){
            serviceName = serviceNames[0];
        }
        this.userConfig.set(RecommendationsManager.getVoteseason()+".webVotes."+serviceName, getWebVotes(serviceName) + 1);
        this.userConfig.set("webVotes."+serviceName+".lastvotetime", System.currentTimeMillis());
        save();
    }
    public int getWebVotes(String serviceName){
        return userConfig.getInt(RecommendationsManager.getVoteseason()+".webVotes."+serviceName);
    }
    
    public long getTimeToNextVote(String service){
        return getTimeToNextVote(getLastWebVoteTime(service));
    }
    public long getTimeToNextVote(long lastVote){
        return lastVote + 86400000 - System.currentTimeMillis();
    }
    public long getLastWebVoteTime(String service){
        return userConfig.getLong("webVotes."+service+".lastvotetime");
    }
    public boolean canWebVote(String service){
        return getTimeToNextVote(service) <= 0;
    }
    public boolean canWebVote(String service, long lastVote){
        return getTimeToNextVote(lastVote) <= 0;
    }
    
    public int getRecommendationsAllTime(){
        return (int) (getRawRecommendationsAllTime() + (getWebVotesAllTime() * recommendationsPerWebvote));
    }
    public int getRawRecommendationsAllTime(){
        int recommendationsTotal = getRawRecommendations();
        for(int recommendations : recommendationsHistory.values()){
            recommendationsTotal += recommendations;
        }
        return recommendationsTotal;
    }
    public int getWebVotesAllTime(){
        int webVotesTotal = getWebVotes();
        for(int webVotes : webVotesHistory.values()){
            webVotesTotal += webVotes;
        }
        return webVotesTotal;
    }

    public ArrayList<String> getRecommandBy() {
        return recommendedBy;
    }
    
    public void save(){
        this.user.getUsersConfig().save();
    }

    public void newseason(String newseason, String oldseason) {
        this.userConfig.set("previusseasons."+oldseason+".recommendations", recommendations);
        this.userConfig.set("previusseasons."+oldseason+".webVotes", webVotes);
        this.userConfig.set(oldseason, null);
        recommendationsHistory.put(oldseason, recommendations);
        webVotesHistory.put(oldseason, webVotes);
        recommendations = 0;
        webVotes = 0;
        recommendedBy.clear();
    }
}
