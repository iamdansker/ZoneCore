/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.TriggerBoxes;

import org.bukkit.entity.Entity;

/**
 *
 * @author jeppe
 */
public abstract class TriggerBoxEventHandler {
    public abstract void entered(Entity entity);
    public abstract void left(Entity entity);
}
