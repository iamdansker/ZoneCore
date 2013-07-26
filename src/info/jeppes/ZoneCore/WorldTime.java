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
                return 16000;
            case NIGHT: 
                return 0;
            case SUNRISE:
                return 12000;
            case SUNSET: 
                return 220000;
            default:
                return 0;
        }
    }
}