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
    Sunrise,
    Day,
    Sunset,
    Night;

    public long getTime(){
        switch(this){
            case Day: 
                return 16000;
            case Night: 
                return 0;
            case Sunrise:
                return 12000;
            case Sunset: 
                return 220000;
            default:
                return 0;
        }
    }
}