package info.jeppes.ZoneCore.TriggerBoxes;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class PrecisePoint extends Point2D implements Cloneable, Serializable{
    public double x;
    public double y;
    
    public PrecisePoint(){
        this.setLocation(0,0);
    }
    public PrecisePoint(double x, double y){
        this.setLocation(x, y);
    }
    
    public final void setX(double x) {
        this.x = x;
    }

    public final void setY(double y) {
        this.y = y;
    }
    @Override
    public final double getX() {
        return x;
    }

    @Override
    public final double getY() {
        return y;
    }

    @Override
    public final void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double angleFrom(Point2D location){
        return Math.atan2(y - location.getY(), location.getX() - x);
    }
    public double angleFrom(Point location){
        return Math.atan2(y - location.getY(), location.getX() - x);
    }
    public static double angleFrom(Point2D location1, Point2D location2){
        return Math.atan2(location2.getY() - location1.getY(), location1.getX() - location2.getX());
    }
    public static double angleFrom(Point location1, Point location2){
        return Math.atan2(location2.getY() - location1.getY(), location1.getX() - location2.getX());
    }
    
    public final Dimension toDimension(){
        return new Dimension((int)x,(int)y);
    }
    
    public final Point toPoint(){
        Point simpleLocation = new Point((int)x,(int)y);
        return simpleLocation;
    }

    @Override
    public String toString(){
        return this.getClass().getName()+"[x="+x+",y="+y+"]";
    }
    
    @Override
    public PrecisePoint clone(){
        return new PrecisePoint(x,y);
    }
    
    public String toSaveString(){
        return toSaveString(this);
    }
    
    public static String toSaveString(Point point){
        return toSaveString(point.getX(),point.getY());
    }
    public static String toSaveString(Point2D point){
        return toSaveString(point.getX(),point.getY());
    }
    public static String toSaveString(double x, double y){
        return x+","+y;
    }
    
    public static PrecisePoint toPrecisePoint(String saveString){
        String[] split = saveString.split(",");
        return new PrecisePoint(java.lang.Double.parseDouble(split[0]),java.lang.Double.parseDouble(split[1]));
    }
}
