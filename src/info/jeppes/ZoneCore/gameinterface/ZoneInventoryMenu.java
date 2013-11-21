/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.jeppes.ZoneCore.gameinterface;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 *
 * @author jeppe
 */
public abstract class ZoneInventoryMenu extends ZoneInventory{

    private HashMap<Integer,MenuListener> listeners = new HashMap();
    
    public ZoneInventoryMenu(String title, int rows) {
        super(Bukkit.createInventory(null, rows, title));
        this.setCanEdit(false);
    }

    @Override
    public void onInventoryClickImpl(InventoryClickEvent event) {
        if(listeners.containsKey(event.getRawSlot())){
            MenuListener listener = listeners.get(event.getRawSlot());
            listener.onMenuItemClick();
            if(listener.isCancelled()){
                return;
            }
        }
        super.onInventoryClickImpl(event);
    }
    
    public void addMenuItemListener(int rawSlot, MenuListener listener){
        listeners.put(rawSlot, listener);
    }
    public void removeMenuItemListener(int rawSlot){
        listeners.remove(rawSlot);
    }

    @Override
    public abstract void onInventoryClick(InventoryClickEvent event);
    
}
