/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore;

import info.jeppes.ZoneCore.Commands.ZoneCommand;
import info.jeppes.ZoneCore.Commands.ZoneCommandManager;
import info.jeppes.ZoneGate.ZoneGateAPI;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Jeppe
 */
public abstract class ZonePlugin extends JavaPlugin{
    private ZoneCore zoneCore = null;
    private Object API = null;
    private boolean showLoadMessages = true;
    private boolean enabled = true;
    
    private ZoneConfig pluginConfig = null;
    private ArrayList<ZoneConfig> configs = new ArrayList<>();
    private String[] pluginCommandAliases = null;
    private ZoneCommandManager commandManager = null;
    private ArrayList<Integer> schedueledTaskIds = new ArrayList<>();
    

    @Override
    public void onLoad() {
        super.onLoad();
        zoneCore = new ZoneCore(this);
        initAPI();
        preLoadDefaultConfig();
    }
    
    @Override
    public void onEnable() {
        boolean zonePluginRunning = ZoneCore.isZonePluginRunning(this.getName());
        if(!zonePluginRunning){
            ZoneCore.addZonePlugin(this);
        }
        
        //Loading config files
        getLogger().log(Level.INFO, "Loading config files...");
        String[] configFilesToLoad = preLoadConfig();
        if(configFilesToLoad != null){
            for(int i = 0; i < configFilesToLoad.length ; i++){
                String configFilePath = getZoneCore().getPluginDirectory()+File.separator+configFilesToLoad[i];
                ZoneConfig zoneConfig = new ZoneConfig(this, new File(configFilePath));
                configs.add(zoneConfig);
                loadConfig(zoneConfig);
            }
        }
        getLogger().log(Level.INFO, "Loaded config files!");
        
        //Init default plugin command aliases
        pluginCommandAliases = initCommandAliases();
        //Init command listener
        initCommands();
        getLogger().log(Level.INFO, "Enabled !");
    }

    @Override
    public void onDisable() {
        ZoneCore.removeZonePlugin(this);
        Bukkit.getScheduler().cancelTasks(this);
        for(int id : getSchedueledTaskIds()){
            Bukkit.getScheduler().cancelTask(id);
        }
        super.onDisable();
    }
    
    private boolean preLoadDefaultConfig(){
        String pluginConfigDirecotry = ZoneCore.getMainPluginDirectory()+File.separator+getName()+".yml";
        File pluginConfigFile = new File(pluginConfigDirecotry);
        pluginConfig = new ZoneConfig(this,pluginConfigFile);
        loadDefaultConfigSettings(pluginConfig);
        loadDefaultConfig(pluginConfig);
        return true;
    }
    private void loadDefaultConfigSettings(ZoneConfig config){
        if(config.contains("enabled")){
            enabled = config.getBoolean("enabled");
            if(!enabled){
                getLogger().log(Level.INFO, "Disabling "+getName()+", enable set to false in config");
                getServer().getPluginManager().disablePlugin(this);
            }
            showLoadMessages = config.getBoolean("show-load-messages");
        }
    }
    
    public abstract String[] preLoadConfig();
    public abstract void loadDefaultConfig(ZoneConfig config);
    public abstract void loadConfig(ZoneConfig config);
    
    public abstract Object initAPI();
    public abstract Object getAPI();
    
    public abstract String[] initCommandAliases();
    public abstract String initCommandPackageDirectory();
    
    
    /**
     * Method used to get the API Object. In case that the class that extends 
     * this class does not do anything with the getAPI() function, this can be 
     * called as a last option to get the APIthough it is then need to know 
     * what kind of object it is, also it is assuming that the class that 
     * extends this class did return something else than null when initAPI() 
     * was called.
     * @return the API object
    */
    public Object getObjectAPI(){
        return API;
    }
    
    public String[] getCommandAliases(){
        return pluginCommandAliases;
    }
    
    public void reloadPlugin(final CommandSender cs){
        final ZonePlugin instance = this;
        new Thread(new Runnable(){
            @Override
            public void run() {
                sendMessage(cs,"Reloading "+getName()+" Version "+getDescription().getVersion()+"...");
                PluginManager pluginManager = getServer().getPluginManager();
                pluginManager.disablePlugin(instance);
                pluginManager.enablePlugin(instance);
                sendMessage(cs,"Reloaded "+getName()+" Version "+getDescription().getVersion()+"!");
            }
        }).start();
    }
    
    public ZoneCore getZoneCore(){
        return zoneCore;
    }
    public ZoneConfig getPluginConfig(){
        return pluginConfig;
    }
    @Override
    public ZoneConfig getConfig(){
        return getPluginConfig();
    }
    public ZoneConfig getConfig(String configFileName){
        for(ZoneConfig config : configs){
            if(config.getName().equalsIgnoreCase(configFileName)){
                return config;
            }
        }
        return null;
    }
    public ZoneCommandManager getCommandManager(){
        return commandManager;
    }
    public ArrayList<Integer> getSchedueledTaskIds(){
        return schedueledTaskIds;
    }
    public void addSchedueledTaskId(int id){
        schedueledTaskIds.add(id);
    }
    
    public void sendMessage(CommandSender cs, String message){
        cs.sendMessage(ChatColor.BLUE + getName() + ": "+ChatColor.GREEN+message);
    }
    public void sendErrorMessage(CommandSender cs, String message){
        cs.sendMessage(ChatColor.LIGHT_PURPLE + getName() + " Error: "+ChatColor.RED+message);
    }

    private void initCommands() {
        commandManager = new ZoneCommandManager(this);
        //Load commands from plugin package
        Class[] classesInPackage = null;
        //        classesInPackage = getClassesInPackage(ZoneCore.getDefaultCommandsPackageDirectory());
        //        for(Class commandClass : classesInPackage){
        //            try {
        //                Object commandInstance = commandClass.newInstance();
        //                if(commandInstance instanceof ZoneCommand){
        //                    ZoneCommand zoneCommand = (ZoneCommand) commandInstance;
        //                    commandManager.registreCommand(zoneCommand);
        //                }
        //            } catch (InstantiationException | IllegalAccessException ex) {
        //                Logger.getLogger(ZonePlugin.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        String initCommandPackageDirectory = initCommandPackageDirectory();
        if(initCommandPackageDirectory != null){
            classesInPackage = getClassesInPackage(initCommandPackageDirectory,null);
            for(Class commandClass : classesInPackage){
                try {
                    Object commandInstance = commandClass.newInstance();
                    if(commandInstance instanceof ZoneCommand){
                        ZoneCommand zoneCommand = (ZoneCommand) commandInstance;
                        commandManager.registreCommand(zoneCommand);
                    }
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(ZonePlugin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 * Adapted from http://snippets.dzone.com/posts/show/4831 and extended to support use of JAR files
	 *
	 * @param packageName The base package
	 * @param regexFilter an optional class name pattern.
	 * @return The classes
	 */
	public Class[] getClassesInPackage(String packageName, String regexFilter) {
		Pattern regex = null;
		if (regexFilter != null)
			regex = Pattern.compile(regexFilter);

		try {
			ClassLoader classLoader = this.getClassLoader();
			assert classLoader != null;
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = classLoader.getResources(path);
			List<String> dirs = new ArrayList<>();
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(resource.getFile());
			}
			TreeSet<String> classes = new TreeSet<>();
			for (String directory : dirs) {
				classes.addAll(findClasses(directory, packageName, regex));
			}
			ArrayList<Class> classList = new ArrayList<>();
			for (String clazz : classes) {
				classList.add(Class.forName(clazz));
			}
			return classList.toArray(new Class[classes.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Recursive method used to find all classes in a given path (directory or zip file url).  Directories
	 * are searched recursively.  (zip files are
	 * Adapted from http://snippets.dzone.com/posts/show/4831 and extended to support use of JAR files
	 *
	 * @param path   The base directory or url from which to search.
	 * @param packageName The package name for classes found inside the base directory
	 * @param regex       an optional class name pattern.  e.g. .*Test
	 * @return The classes
	 */
	private TreeSet<String> findClasses(String path, String packageName, Pattern regex) throws Exception {
            TreeSet<String> classes = new TreeSet<>();
            if (path.startsWith("file:") && path.contains("!")) {
                String[] split = path.split("!");
                URL jar = new URL(split[0]);
                ZipInputStream zip = new ZipInputStream(jar.openStream());
                ZipEntry entry;
                while ((entry = zip.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".class")) {
                        String className = entry.getName().replaceAll("[$].*", "").replaceAll("[.]class", "").replace('/', '.');
                        if (className.startsWith(packageName) && (regex == null || regex.matcher(className).matches()))
                            classes.add(className);
                    }
                }
            }
            File dir = new File(path);
            if (!dir.exists()) {
                return classes;
            }
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    assert !file.getName().contains(".");
                    classes.addAll(findClasses(file.getAbsolutePath(), packageName + "." + file.getName(), regex));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                    if (regex == null || regex.matcher(className).matches())
                            classes.add(className);
                }
            }
            return classes;
    }
}
