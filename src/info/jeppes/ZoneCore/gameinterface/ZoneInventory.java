/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.gameinterface;

import info.jeppes.ZoneCore.ZoneCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author jeppe
 */
public abstract class ZoneInventory implements ZoneInventoryInterface, Listener{
    
    private Inventory inventory = null;
    private boolean canEdit = true;
    
    public ZoneInventory(Inventory inventory){
        this.inventory = inventory;
        Bukkit.getPluginManager().registerEvents(this, ZoneCore.getCorePlugin());
    }
    
    public Inventory getInventory(){
        return inventory;
    }
    
    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClickImpl(InventoryClickEvent event){
        if(getInventory().getViewers().contains(event.getWhoClicked())){
            event.setCancelled(true);
            onInventoryClick(event);
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryCloseImpl(InventoryCloseEvent event){
        if(getInventory().getViewers().contains(event.getPlayer())){
            close(event.getPlayer());
        }
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
    
    public Inventory open(HumanEntity player){
        player.openInventory(this.getInventory());
        getInventory().getViewers().add(player);
        return getInventory();
    }
    public void close(HumanEntity player) {
        HandlerList.unregisterAll(this);
        this.getInventory().getViewers().remove(player);
    }
    
    public abstract void onInventoryClick(InventoryClickEvent event);
}
