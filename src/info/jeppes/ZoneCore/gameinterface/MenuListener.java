/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.jeppes.ZoneCore.gameinterface;

import org.bukkit.event.Cancellable;

/**
 *
 * @author jeppe
 */
public abstract class MenuListener implements Cancellable{
    private boolean cancelled = false;
    
    public abstract void onMenuItemClick();

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean bln) {
        this.cancelled = bln;
    }
}
