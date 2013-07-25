package info.jeppes.ZoneCore.TriggerBoxes;

import info.jeppes.ZoneCore.Events.TriggerBoxEnterEvent;
import info.jeppes.ZoneCore.Events.TriggerBoxLeaveEvent;
import info.jeppes.ZoneCore.ZoneCore;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public abstract class TriggerBox implements Listener{
    private boolean triggerByEveryone = true;
    private boolean useEvents = false;
    private ArrayList<Entity> triggerEntities = new ArrayList();
    private ArrayList<Entity> isInside = new ArrayList();
    private String worldName;
    private String name;
    private TriggerBoxEventHandler eventHandler = null;
    
    public TriggerBox(String name){
        this(name,null,false,true);
    }
    public TriggerBox(String name, String world){
        this(name,world,false,true);
    }
    public TriggerBox(String name, String world, boolean useEvents){
        this(name,world,useEvents,true);
    }
    public TriggerBox(String name, String world, boolean useEvents, boolean triggerByEveryone){
        this.name = name;
        this.worldName = world;
        this.useEvents = useEvents;
        this.triggerByEveryone = triggerByEveryone;
        Bukkit.getPluginManager().registerEvents(this, ZoneCore.getCorePlugin());
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent event){
        updateEntity(event.getPlayer(),event.getTo());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerTeleport(PlayerTeleportEvent event){
        updateEntity(event.getPlayer(),event.getTo());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLogin(PlayerLoginEvent event){
        updateEntity(event.getPlayer(),event.getPlayer().getLocation());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event){
        updateEntity(event.getPlayer(), null);
    }
    
    public void updateEntity(Entity entity, Location newLocation){
        //Is null if the entity left the world or server
        if(newLocation == null){
            if(isInside.contains(entity)){
                isInside.remove(entity);
                left(entity);
                if(useEvents){
                    TriggerBoxLeaveEvent triggerBoxEnterEvent = new TriggerBoxLeaveEvent(this,entity);
                    Bukkit.getPluginManager().callEvent(triggerBoxEnterEvent);
                }
            }
        }
        //check to see if the entity entered or left the box
        if(triggerByEveryone || triggerEntities.contains(entity)){
            if(isInside(entity.getLocation())){
                //An entity entered the trigger box
                //make sure the event is only called once
                if(!isInside.contains(entity)){
                    isInside.add(entity);
                    entered(entity);
                    if(useEvents){
                        TriggerBoxEnterEvent triggerBoxEnterEvent = new TriggerBoxEnterEvent(this,entity);
                        Bukkit.getPluginManager().callEvent(triggerBoxEnterEvent);
                    }
                }
            } else {
                //An entity left the trigger box
                //make sure the event is only called once
                if(isInside.contains(entity)){
                    isInside.remove(entity);
                    left(entity);
                    if(useEvents){
                        TriggerBoxLeaveEvent triggerBoxEnterEvent = new TriggerBoxLeaveEvent(this,entity);
                        Bukkit.getPluginManager().callEvent(triggerBoxEnterEvent);
                    }
                }
            }
        }
    }
    public abstract boolean isInside(Location location);
    public abstract boolean isInside(Point2D point, double y, String worldName);
    public abstract boolean isInside(Point3D point, String worldName);
    public abstract Location getRandomLocationInsideBox();
    
    public void entered(Entity entity){
        if(getEventHandler() == null){
            return;
        }
        getEventHandler().entered(this,entity);
    }
    public void left(Entity entity){
        if(getEventHandler() == null){
            return;
        }
        getEventHandler().left(this,entity);
    }

    public TriggerBoxEventHandler getEventHandler(){
        return eventHandler;
    }
    public void setEventHandler(TriggerBoxEventHandler eventHandler){
        this.eventHandler = eventHandler;
    }
    
    public boolean isTriggerByEveryone() {
        return triggerByEveryone;
    }
    public void setTriggerByEveryone(boolean triggerByEveryone) {
        this.triggerByEveryone = triggerByEveryone;
    }

    public ArrayList<Entity> getTriggerEntities() {
        return triggerEntities;
    }
    /**
     * set which entities that will trigger the box
     * NOTE that only players are supported at this time
     * @param entities
     */
    public void setTriggerEntities(ArrayList<Entity> entities) {
        this.triggerEntities = entities;
    }
    /**
     * Add an entity to that will trigger the box
     * NOTE that only players are supported at this time
     * @param entity
     */
    public void addTriggerEntity(Entity entity){
        triggerEntities.add(entity);
    }
    
    public void removeTriggerEntity(Entity entity){
        triggerEntities.remove(entity);
    }
    
    public void messagePlayersInside(String message){
        for(Entity entity : isInside){
            if(entity instanceof Player){
                Player player = (Player)entity;
                player.sendMessage(message);
            }
        }
    }

    public String getName() {
        return name;
    }

    public boolean isUsingEvents() {
        return useEvents;
    }
    public void setUseEvents(boolean useEvents) {
        this.useEvents = useEvents;
    }
    
    public ArrayList<Entity> getEntitiesInside() {
        return isInside;
    }

    public String getWorldName() {
        return worldName;
    }
    public World getWorld() {
        return Bukkit.getWorld(getWorldName());
    }
    public void setWorld(World world) {
        setWorld(world.getName());
    }
    public void setWorld(String world) {
        this.worldName = world;
    }
    
    public void delete(){
        HandlerList.unregisterAll(this);
    }
    public String toSaveString(){
        return name+","+worldName+","+triggerByEveryone+","+useEvents;
    }
}
