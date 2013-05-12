package info.jeppes.ZoneCore.Events;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TriggerBoxLeaveEvent extends Event{

    private static final HandlerList handlers = new HandlerList();
    private final Entity entity;
 
    public TriggerBoxLeaveEvent(Entity entity) {
        this.entity = entity;
    }
 
    public Entity getEntity() {
        return entity;
    }
 
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
