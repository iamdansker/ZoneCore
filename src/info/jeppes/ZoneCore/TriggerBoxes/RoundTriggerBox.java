/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore.TriggerBoxes;

import org.bukkit.Location;
import org.bukkit.World;

/**
 *
 * @author jeppe
 */
public abstract class RoundTriggerBox extends TriggerBox{

    private Location center;
    private double radius;
    
    private double startHeight;
    private double endHeight;
    
    public RoundTriggerBox(Location center, double radius, String name) {
        super(center.getWorld(), name);
    }

    @Override
    public boolean isInside(Location location) {
        if(location.getY() >= getCircleBottomY() && location.getY() <= getCircleTopY()){
            double distance = PrecisePoint.distance(
                    location.getX(), location.getZ(), 
                    getCenter().getX(), getCenter().getZ()
                );
            if(distance <= getRadius()){
                return true;
            }
        }
        return false;
    }

    @Override
    public Location getRandomLocationInsideBox() {
        double radius = Math.random() * getRadius();
        double theta = Math.random() * Math.toRadians(360);
        double x = Math.cos(theta) * radius;
        double z = Math.sin(theta) * radius;
        double y = getCircleBottomY() + (Math.random() * (getCircleTopY() - getCircleBottomY()));
        return new Location(getWorld(),x,y,z);
    }

    @Override
    public World getWorld(){
        return getCenter().getWorld();
    }
    
    public Location getCenter() {
        return center;
    }

    public void setCenter(Location center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getCircleBottomY() {
        return startHeight;
    }

    public void setCircleBottomY(double startHeight) {
        this.startHeight = Math.min(startHeight, getCircleTopY());
    }

    public double getCircleTopY() {
        return endHeight;
    }

    public void setCircleTopY(double endHeight) {
        this.startHeight = Math.min(endHeight, getCircleBottomY());
    }
    
    
    
}
