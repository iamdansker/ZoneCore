/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore;

/**
 *
 * @author Jeppe
 */
public enum WorldTime {
    SUNRISE,
    DAY,
    SUNSET,
    NIGHT;

    public long getTime(){
        switch(this){
            case DAY: 
                return 7000;
            case NIGHT: 
                return 18000;
            case SUNRISE:
                return 230000;
            case SUNSET: 
                return 12000;
            default:
                return 0;
        }
    }
    
    public static WorldTime getWorldTime(long time){
        long relativeTime = time % 24000;
        if (relativeTime < 12000) {
            return WorldTime.DAY;
        } else if (relativeTime < 14000) {
            return WorldTime.SUNSET;
        } else if (relativeTime < 220000) {
            return WorldTime.NIGHT;
        } else if (relativeTime < 24000) {
            return WorldTime.SUNRISE;
        }
        return WorldTime.DAY;
    }
}