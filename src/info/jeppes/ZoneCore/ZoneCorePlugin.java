/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore;

import info.jeppes.ZoneCore.Commands.DefaultCommand;
import info.jeppes.ZoneCore.Commands.ZoneCommand;
import info.jeppes.ZoneCore.Users.ZoneUserManager;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Jeppe
 */
public class ZoneCorePlugin extends ZonePlugin{

    private ZoneUserManager userManager;
    
    @Override
    public String[] preLoadConfig() {
        return new String[]{"users.yml"};
    }

    @Override
    public void loadDefaultConfig(ZoneConfig config) {
        
        
        ZoneCore.addConfigDefault(config);
        InputStream defConfigStream = getResource("default-plugin-config.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            ZoneCore.addConfigDefault(defConfig);
        }
        
        Class[] classesInPackage = getClassesInPackage(ZoneCore.getDefaultCommandsPackageDirectory(),null);
        for(Class commandClass : classesInPackage){
            if(DefaultCommand.class.isAssignableFrom(commandClass)){
                try {
                    try {
                        DefaultCommand defaultCommand = (DefaultCommand)commandClass.getConstructor(ZonePlugin.class).newInstance(this);
                        ZoneCore.addDefaultCommand(defaultCommand);
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(ZoneCorePlugin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (NoSuchMethodException | SecurityException ex) {
                    Logger.getLogger(ZoneCorePlugin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void loadConfig(ZoneConfig config) {
        userManager = new ZoneUserManager(config);
    }

    @Override
    public Object initAPI() {
        getZoneCore().setCore(this);
        return getZoneCore();
    }

    @Override
    public ZoneCore getAPI(){
        return getZoneCore();
    }
    
    @Override
    public String[] initCommandAliases() {
        return new String[]{"zonecore","zc"};
    }

    @Override
    public String initCommandPackageDirectory() {
        return "info.jeppes.ZoneCore.Commands.Commands";
    }
    
    @Override
    public void onEnable(){
        super.onEnable();
        loadCommandsFromPluginDirectory();
        AsynchroizedStorageManager asynchroizedStorageManager = new AsynchroizedStorageManager();
        this.addSchedueledBukkitTask(Bukkit.getScheduler().runTaskTimerAsynchronously(this, asynchroizedStorageManager,5,5));
    }
    
    @Override
    public void onDisable() {
        AsynchroizedStorageManager.stop();
        AsynchroizedStorageManager.save();
        ZoneUserManager.getUsersConfig().save();
        ZoneUserManager.getUsers().clear();
        super.onDisable();
    }
    
    public ZoneUserManager getUserManager() {
        return userManager;
    }
    
    public void reloadEveryPlugin(final CommandSender cs) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
            @Override
            public void run() {
                for(ZonePlugin zonePlugin : ZoneCore.getZonePluginsList()){
                    zonePlugin.reloadPlugin(cs);
                }
            }
        });
    }
    public void loadCommandsFromPluginDirectory(){
        //load commands from plugin specific command folder
        File commandsDirectory = new File(ZoneCore.getMainPluginDirectory()+File.separator+"commands");
        if(commandsDirectory != null){
            if(commandsDirectory.isDirectory()){
                ArrayList<String> commandClassFiles = getFilesFromDirectoryAndSubDirectories(commandsDirectory,new ArrayList<String>(),"");
        
                ClassLoader loader = null;
                try {
                    URL url = commandsDirectory.toURI().toURL(); 
                    URL[] urls = new URL[]{url};

                    loader = new URLClassLoader(urls);

                } catch (MalformedURLException ex) {
                    Logger.getLogger(ZonePlugin.class.getName()).log(Level.SEVERE, null, ex);
                }
                for(String classPackageAndName : commandClassFiles){
                    try {
                        Class commandClass = loader.loadClass(classPackageAndName);
                        Object commandObject = commandClass.newInstance();
                        if(commandObject != null && commandObject instanceof ZoneCommand){
                            ZoneCommand zoneCommand = (ZoneCommand) commandObject;
                            getCommandManager().registreCommand(zoneCommand);
                        }
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
                        Logger.getLogger(ZonePlugin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    public ArrayList<String> getFilesFromDirectoryAndSubDirectories(File aFile, ArrayList<String> files, String startPath) {
        if(aFile.isFile()){
            String fileName = aFile.getName();
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            files.add(startPath + "." + fileName);
        } else if (aFile.isDirectory()) {
            File[] listOfFiles = aFile.listFiles();
            if(listOfFiles!=null) {
                for (File file : listOfFiles) {
                    files = getFilesFromDirectoryAndSubDirectories(file,files,startPath + "." + file.getName());
                }
            } else {
                Logger.getLogger(ZonePlugin.class.getName()).log(Level.SEVERE, null, "FILE ACCESS DENIED");
            }
        }
        return files;
    }

    public void addScedualedConfigSave(ZoneConfig config) {
        
    }
}
