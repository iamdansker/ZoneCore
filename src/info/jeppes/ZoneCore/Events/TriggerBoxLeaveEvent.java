package info.jeppes.ZoneCore.Events;

import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TriggerBoxLeaveEvent extends Event{

    private static final HandlerList handlers = new HandlerList();
    private final TriggerBox triggerBox;
    private final Entity entity;
 
    public TriggerBoxLeaveEvent(TriggerBox triggerBox, Entity entity) {
        this.triggerBox = triggerBox;
        this.entity = entity;
    }

    public TriggerBox getTriggerBox() {
        return triggerBox;
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
