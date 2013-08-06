/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.Users;

import org.bukkit.World;

/**
 *
 * @author jeppe
 */
public interface ZoneCoreUser extends ZoneUser{
    
    public ZoneCoreUserData.ServerGroup getServerGroup(World world);
    public ZoneCoreUserData.ServerGroup getServerGroup();
    
    public void updatePlayTime();
    public long getPlayTime();
    public long getPlayTime(boolean update);
}
