/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/**
 *
 * @author Jeppe
 */
public class ZoneTools {
    
    public static boolean getBoolean(String arg) throws Exception{
        arg = arg.toLowerCase();
        switch(arg){
            case "true":
                return true;
            case "t":
                return true;
            case "allow":
                return true;
            case "false":
                return false;
            case "f":
                return false;
            case "deny":
                return false;
        }
        throw new Exception("Could not find any boolean");
    }
    
    public static WorldTime getWorldTime(long time){
        long relativeTime = time % 24000;
        if (relativeTime > 12000) {
            return WorldTime.Day;
        } else if ((relativeTime > 22200 || relativeTime < 13700)) {
            return WorldTime.Night;
        } else if ((relativeTime < 12000 || relativeTime > 13700)) {
            return WorldTime.Sunset;
        } else if (relativeTime < 22000) {
            return WorldTime.Sunrise;
        }
        return WorldTime.Day;
    }
    
    public static byte yawToDirection(float yaw){
        yaw += 45;
        if(yaw < 0){
            yaw += 360;
        }
        
        if(0 <= yaw && yaw < 90){
            return 0;
        } else if(90 <= yaw && yaw < 180){
            return 1;
        } else if(180 <= yaw && yaw < 270){
            return 2;
        } else if(270 <= yaw && yaw < 360){
            return 3; 
        }
        return 2;
    }
    
    public static float directionToYaw(byte direction){
      
        switch(direction){
            case 0: 
                return 0;
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return 270;
        }
        return 180;
    }
    public static Object[] getObjectsOnPage(List<?> objList, int page, int objectsPerPage){
        int to = page * objectsPerPage;
        int from = to - objectsPerPage;
        int arraySize = objList.size() - from;
        if(arraySize > objectsPerPage){
            arraySize = objectsPerPage;
        }
        Object[] objectsOnPage = new Object[arraySize];
        int arrayPos = 0;
        for(int i = from; i < to;i++){
            if(objList.size() > i){
                objectsOnPage[arrayPos] = objList.get(i);
                arrayPos++;
            }
        }
        return objectsOnPage;
    }
    public static List<Object> getListSorted(Object[] array){
        List<Object> list = new ArrayList<Object>();
        if(array.length > 0){
            for(Object nameObj : array){
                String name = nameObj.toString();
                boolean found = false;
                for(int i2 = 0; i2 < list.size(); i2++){
                    String listName = list.get(i2).toString();
                    int size = name.compareTo(listName);
                    if(size > 0){
                        continue;
                    }else if(size <= 0) {
                        list.add(i2,nameObj);
                        found = true;
                        break;
                    }
                }
                if(!found){
                    list.add(nameObj);
                }
            }
        }
        return list;
    }
    
    public static Material getMaterialFromString(String arg){
        try{
            int id = Integer.parseInt(arg);
            return Material.getMaterial(id);
        } catch(Exception e){
            arg = arg.toUpperCase();
            if(arg.contains("COBBLE") && !arg.contains("COBBLESTONE")){
                arg = arg.replace("COBBLE", "COBBLESTONE");
            }
            if(arg.contains("PLATE") && !arg.contains("CHESTPLATE")){
                arg = arg.replace("PLATE", "CHESTPLATE");
            }
            if(arg.contains("RAIL") && !arg.contains("RAILS")){
                arg = arg.replace("RAIL", "RAILS");
            }
            if(arg.contains("BREWINGSTAND") && !arg.contains("BREWINGSTANDITEM")){
                arg = arg.replace("BREWINGSTAND", "BREWING_STAND_ITEM");
            }
            if(arg.contains("SPRUCE") && !arg.contains("SPRUCE_WOOD_")){
                arg = arg.replace("SPRUCE", "SPRUCE_WOOD_");
            }
            if(arg.contains("BIRCH") && !arg.contains("BIRCH_WOOD_")){
                arg = arg.replace("BIRCH", "BIRCH_WOOD_");
            }
            if(arg.contains("JUNGLE") && !arg.contains("JUNGLE_WOOD_")){
                arg = arg.replace("JUNGLE", "JUNGLE_WOOD_");
            }
            arg = arg.replace("WOODEN", "WOOD");
            arg = arg.replace("SHOVEL", "SPADE");
            arg = arg.replace("GUNPOWDER", "SULPHUR");
            arg = arg.replace("REDSTONELAMP", "REDSTONE_LAMP_OFF");
            arg = arg.replace("REDSTONETORCH", "REDSTONE_TORCH_ON");
            arg = arg.replace("PANTS", "LEGGINGS");
            arg = arg.replace("PLANK", "WOOD");
            arg = arg.replace("melonslice", "MELON_ITEM");
            arg = arg.replace("NETHERSTAIRS", "NETHER_BRICK_STAIRS");
            arg = arg.replace("STONEBRICK", "SMOOTH_BRICK");
            arg = arg.replace("STICKYPISTON", "PISTON_STICKY_BASE");
            arg = arg.replace("GLASSPANE", "THIN_GLASS");
            arg = arg.replace("IRONBARS", "IRON_FENCE");
            arg = arg.replace("STONESTAIRS", "SMOOTH_STAIRS");
            arg = arg.replace("BRICKBLOCK", "BRICK");
            
            
            
            
            Material material = Material.getMaterial(arg);
            if(material == null){
                material = Material.getMaterial(arg.replaceAll(" ", "_"));
                if(material == null){
                    Material[] materials = Material.values();
                    for(Material mat : materials){
                        if(mat.name().replaceAll("_", "").equalsIgnoreCase(arg)){
                            material = mat;
                            break;
                        }
                    }
                }
            }
            return material;
        }
    }
    
    public static int blockFaceToDirection(BlockFace facing){
        switch(facing){
            case NORTH:
                return 3;
            case SOUTH:
                return 1;
            case EAST:
                return 0;
            case WEST:
                return 2;
        }
        return 0;
    }
    
    public boolean isPlayer(CommandSender cs){
        return (cs instanceof Player);
    }
    
    public static int[] getTimeDDHHMMSS(long time) {     
        int seconds = (int) (time / 1000) % 60;
        int minutes = (int) ((time / 60000) % 60);
        int hours   = (int) ((time / 3600000) % 24);
        int days    = (int) ((time / 86400000) % 60);
        return new int[]{days,hours,minutes,seconds};
    }
    
    public static String getTimeDDHHMMSSString(long time){
        int[] timeDDHHMMSS = getTimeDDHHMMSS(time);
        boolean useDays = timeDDHHMMSS[0] != 0;
        boolean useHours = timeDDHHMMSS[1] != 0;
        boolean useMinuts = timeDDHHMMSS[2] != 0;
        boolean useSeconds = timeDDHHMMSS[3] != 0;
        return  (useDays ? timeDDHHMMSS[0] + " day"+ (timeDDHHMMSS[0] == 1 ? " " : "s "): "") + 
                (useHours ? (timeDDHHMMSS[1] + " hour"+ (timeDDHHMMSS[1] == 1 ? " " : "s ")): "") + 
                (useMinuts ? (timeDDHHMMSS[2] + " minute"+ (timeDDHHMMSS[2] == 1 ? " " : "s ")): "") + 
                ((useHours || useMinuts) && useSeconds ? "and " : "")+
                (useSeconds ? (timeDDHHMMSS[3] +" second" + (timeDDHHMMSS[3] == 1 ? "" : "s")) : "");
    }
    public static String getTimeDDHHMMSSStringShort(long time){
        int[] timeDDHHMMSS = getTimeDDHHMMSS(time);
        return 
                (timeDDHHMMSS[0] != 0 ? (timeDDHHMMSS[0] + " day"+ (timeDDHHMMSS[0] == 1 ? " " : "s ")) : "") + 
                (timeDDHHMMSS[0] != 0 && timeDDHHMMSS[1] != 0 ? (timeDDHHMMSS[1] + " hour"+ (timeDDHHMMSS[1] == 1 ? " " : "s "))+"and " : "") + 
                (timeDDHHMMSS[0] != 0 && timeDDHHMMSS[1] != 0 && timeDDHHMMSS[2] != 0 ? (timeDDHHMMSS[2] + " minute"+ (timeDDHHMMSS[2] == 1 ? " " : "s ")) : "") + 
                (timeDDHHMMSS[3] != 0 ? (timeDDHHMMSS[3] + " second"+ (timeDDHHMMSS[3] == 1 ? " " : "s ")) : "");
    }
    public static String getTimeHHMMSSString(long time){
        int[] timeDDHHMMSS = getTimeDDHHMMSS(time);
        return 
                timeDDHHMMSS[1] +" hour" + (timeDDHHMMSS[1] == 1 ? " " : "s ") + 
                timeDDHHMMSS[2] +" minute" + (timeDDHHMMSS[2] == 1 ? " " : "s ") + 
                " and " + timeDDHHMMSS[3] +" second" + (timeDDHHMMSS[3] == 1 ? "" : "s"); 
    }
    public static String getTimeHHMMSSStringShort(long time){
        int[] timeDDHHMMSS = getTimeDDHHMMSS(time);
        boolean useHours = timeDDHHMMSS[1] != 0;
        boolean useMinuts = timeDDHHMMSS[2] != 0;
        boolean useSeconds = timeDDHHMMSS[3] != 0;
        return  (useHours ? (timeDDHHMMSS[1] + " hour"+ (timeDDHHMMSS[1] == 1 ? " " : "s ")): "") + 
                (useMinuts ? (timeDDHHMMSS[2] + " minute"+ (timeDDHHMMSS[2] == 1 ? " " : "s ")): "") + 
                ((useHours || useMinuts) && useSeconds ? "and " : "")+
                (useSeconds ? (timeDDHHMMSS[3] +" second" + (timeDDHHMMSS[3] == 1 ? "" : "s")) : "");
    }

    public static String getSimpleLocationInfo(Location location){
        if(location != null){
            return  location.getWorld().getName() +
                    "{"+location.getX()+
                    ","+location.getY()+
                    ","+location.getZ()+
                    "} Direction: "+yawToDirection(location.getYaw());
        }
        return "";
    }
    
    
}
