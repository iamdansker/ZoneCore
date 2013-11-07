/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.jeppes.ZoneCore;

import info.jeppes.ZoneCore.Commands.DefaultCommand;
import info.jeppes.ZoneCore.Commands.ZoneCommand;
import info.jeppes.ZoneCore.Commands.ZoneCommandManager;
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
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author Jeppe
 */
public abstract class ZonePlugin extends JavaPlugin{
    private ZoneCore zoneCore = null;
    private Object API = null;
    private boolean enabled = true;
    private boolean debugMode = false;
    
    private ZoneConfig pluginConfig = null;
    private ArrayList<ZoneConfig> configs = new ArrayList<>();
    private String[] pluginCommandAliases = null;
    private ZoneCommandManager commandManager = null;
    private ArrayList<Integer> schedueledTaskIds = new ArrayList<>();
    private ArrayList<BukkitTask> schedueledBukkitTask = new ArrayList<>();
    

    @Override
    public void onLoad() {
        super.onLoad();
        
        //initialize ZoneCore instance specific for the plugin
        zoneCore = new ZoneCore(this);
        
        //initialize API
        initAPI();
        
        //Init default plugin command aliases
        pluginCommandAliases = initCommandAliases();
        if(pluginCommandAliases == null){
            pluginCommandAliases = new String[]{this.getName()};
        }
        
        //load the default plugin config
        preLoadDefaultConfig();
    }
    
    @Override
    public void onEnable() {
        if(!enabled){
            ZoneCore.removeZonePlugin(this);
            return;
        }
        boolean zonePluginRunning = ZoneCore.isZonePluginRunning(this.getName());
        if(!zonePluginRunning){
            ZoneCore.addZonePlugin(this);
        }
        
        //Init command listener
        initCommands();
        //Loading config files
        if(ZoneCore.getCorePlugin().inDebugMode() || inDebugMode()) {
            getLogger().log(Level.INFO, "Loading config files...");
        }
        String[] configFilesToLoad = preLoadConfig();
        if(configFilesToLoad != null){
            for(int i = 0; i < configFilesToLoad.length ; i++){
                String configFilePath = getZoneCore().getPluginDirectory()+File.separator+configFilesToLoad[i];
                ZoneConfig zoneConfig = new ZoneConfig(this, new File(configFilePath));
                configs.add(zoneConfig);
                loadConfig(zoneConfig);
            }
        }
        if(ZoneCore.getCorePlugin().inDebugMode() || inDebugMode()) {
            getLogger().log(Level.INFO, "Loaded config files!");
        }
        
        getLogger().log(Level.INFO, "Enabled !");
    }

    @Override
    public void onDisable() {
        ZoneCore.removeZonePlugin(this);
        Bukkit.getScheduler().cancelTasks(this);
        for(int id : getSchedueledTaskIds()){
            Bukkit.getScheduler().cancelTask(id);
        }
        for(BukkitTask task : schedueledBukkitTask){
            task.cancel();
        }
        super.onDisable();
    }
    
    private boolean preLoadDefaultConfig(){
        String pluginConfigDirecotry = ZoneCore.getMainPluginDirectory()+File.separator+getName()+".yml";
        File pluginConfigFile = new File(pluginConfigDirecotry);
        pluginConfig = new ZoneConfig(this,pluginConfigFile);
        ArrayList<YamlConfiguration> configDefaults = ZoneCore.getConfigDefaults();
        for(YamlConfiguration defConfig : configDefaults){
            pluginConfig.loadDefaults(this, defConfig);
        }
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
        }
        if(config.contains("debug")){
            debugMode = config.getBoolean("debug");
            if(debugMode){
                getLogger().log(Level.INFO, "Debug mode enabled");
            }
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return super.onCommand(sender, command, label, args);
    }
    
    public String[] getCommandAliases(){
        return pluginCommandAliases;
    }
    
    public void reloadPlugin(final CommandSender cs){
        final ZonePlugin instance = this;
        final File file = this.getFile();
        Bukkit.getScheduler().runTask(this, new Runnable(){
            @Override
            public void run() {
                sendMessage(cs,"Reloading "+getName()+" Version "+getDescription().getVersion()+"...");
                PluginManager pluginManager = getServer().getPluginManager();
                pluginManager.disablePlugin(instance);
                pluginManager.enablePlugin(instance);
//                try {
//                    Plugin loadPlugin = pluginManager.loadPlugin(file);
//                    loadPlugin.onLoad();
//                    pluginManager.enablePlugin(loadPlugin);
//                } catch (InvalidPluginException | InvalidDescriptionException | UnknownDependencyException ex) {
//                    Logger.getLogger(ZonePlugin.class.getName()).log(Level.SEVERE, null, ex);
//                    pluginManager.enablePlugin(instance);
//                }
                sendMessage(cs,"Reloaded "+getName()+" Version "+getDescription().getVersion()+"!");
            }
        });
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

    public boolean inDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
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
    public void addSchedueledBukkitTask(BukkitTask task){
        schedueledBukkitTask.add(task);
    }
    
    public void sendMessage(CommandSender cs, String message){
        cs.sendMessage(ChatColor.BLUE + getName() + ": "+ChatColor.GREEN+message);
    }
    public void sendErrorMessage(CommandSender cs, String message){
        cs.sendMessage(ChatColor.LIGHT_PURPLE + getName() + " Error: "+ChatColor.RED+message);
    }

    public boolean hasPermission(Player player, String permission){
        return ZoneCore.hasPermission(player,permission);
    }
    public boolean hasPermission(CommandSender cs, String permission){
        return ZoneCore.hasPermission(cs,permission);
    }
    
    public void initCommandsFromPackage(String packageName) {
        //Load commands from plugin package
        Class[] classesInPackage = null;
        if(packageName != null){
            classesInPackage = getClassesInPackage(packageName,null);
            for(Class commandClass : classesInPackage){
                try {
                    if(ZoneCommand.class.isAssignableFrom(commandClass)){
                        ZoneCommand zoneCommand = (ZoneCommand) commandClass.newInstance();
                        commandManager.registreCommand(zoneCommand);
                    }
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(ZonePlugin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    private void initCommands() {
        commandManager = new ZoneCommandManager(this);
        //Load commands from plugin package
        Class[] classesInPackage = null;
        for(DefaultCommand defaultCommand : ZoneCore.getDefaultCommands()){
            commandManager.registreCommand(defaultCommand.clone(this));
        }
//                classesInPackage = getClassesInPackage(ZoneCore.getDefaultCommandsPackageDirectory());
//                for(Class commandClass : classesInPackage){
//                    try {
//                        Object commandInstance = commandClass.newInstance();
//                        if(commandInstance instanceof ZoneCommand){
//                            ZoneCommand zoneCommand = (ZoneCommand) commandInstance;
//                            commandManager.registreCommand(zoneCommand);
//                        }
//                    } catch (InstantiationException | IllegalAccessException ex) {
//                        Logger.getLogger(ZonePlugin.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
        String initCommandPackageDirectory = initCommandPackageDirectory();
        initCommandsFromPackage(initCommandPackageDirectory);
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
		if (regexFilter != null) {
                    regex = Pattern.compile(regexFilter);
                }

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
                try{
                    classList.add(Class.forName(clazz));
                }catch(Exception e){
                    //In case some class doesn't load, it shouldn't shut down the entire plugin
                }
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
                        if (className.startsWith(packageName) && (regex == null || regex.matcher(className).matches())) {
                            classes.add(className);
                        }
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
                    if (regex == null || regex.matcher(className).matches()) {
                        classes.add(className);
                    }
                }
            }
            return classes;
    }
}
