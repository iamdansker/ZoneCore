/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore;

import info.jeppes.ZoneWorld.ZoneWorld;
import info.jeppes.ZoneWorld.ZoneWorldAPI;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.util.Vector;

/**
 *
 * @author Jeppe
 */
public class ZoneLocation extends Location{
    private String worldName;
    
    public ZoneLocation(String worldName, double x, double y, double z){
        super(null,x,y,z);
        this.worldName = worldName;
    }
    public ZoneLocation(World world, double x, double y, double z){
        super(world,x,y,z);
        this.worldName = world.getName();
    }
    public ZoneLocation(Location location){
        super(location.getWorld(),location.getX(),location.getY(),location.getZ(),location.getYaw(),location.getPitch());
        this.worldName = location.getWorld().getName();
    }
    
    public String getWorldName(){
        return worldName;
    }
    
    @Override
    public World getWorld(){
        World world = super.getWorld();
        if(world == null){
            ZoneWorld zoneWorld = getZoneWorld();
            if(zoneWorld != null){
                if(!zoneWorld.isLoaded()){
                    zoneWorld.load();
                }
                CraftWorld craftWorld = zoneWorld.getCraftWorld();
                setWorld(world);
                return craftWorld;
            }
            return null;
        }
        return world;
    }
    
    public ZoneWorld getZoneWorld(){
        if(ZoneCore.isZoneWorldRunning()){
            return ZoneWorldAPI.getWorld(worldName);
        }
        return null;
    }

    @Override
    public ZoneLocation add(Location vec) {
        super.add(vec);
        return this;
    }

    @Override
    public ZoneLocation add(Vector vec) {
        super.add(vec);
        return this;
    }

    @Override
    public ZoneLocation add(double x, double y, double z) {
        super.add(x, y, z);
        return this;
    }

    @Override
    public ZoneLocation multiply(double m) {
        super.multiply(m);
        return this;
    }

    @Override
    public ZoneLocation subtract(Location vec) {
        super.subtract(vec);
        return this;
    }

    @Override
    public ZoneLocation subtract(Vector vec) {
        super.subtract(vec);
        return this;
    }

    @Override
    public ZoneLocation subtract(double x, double y, double z) {
        super.subtract(x, y, z);
        return this;
    }
    
    
    
    public String getSimpleInfo() {
        return  getWorldName() +
                "{"+getX()+
                ","+getY()+
                ","+getZ()+
                "} Direction: "+ZoneTools.yawToDirection(this.getYaw());
    }
}