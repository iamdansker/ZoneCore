/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Exceptions;

/**
 *
 * @author Jeppe
 */
public class RecommendCooldownException extends Exception{
    private long timeLeft = 0;
    
    public RecommendCooldownException(long timeLeft){
        this.timeLeft = timeLeft;
    }
    
    public long getTimeLeft(){
        return timeLeft;
    }
}
