/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Events;

import info.jeppes.ZoneCore.Users.ZoneUser;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author jeppe
 */
public class NewZoneUserEvent extends Event{
    private static final HandlerList handlers = new HandlerList();
    private final ZoneUser newUser;

    /**
     *
     * @param newUser
     */
    public NewZoneUserEvent(ZoneUser newUser){
        this.newUser = newUser;
    }
    
    public ZoneUser getZoneUser() {
        return newUser;
    }
 
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
