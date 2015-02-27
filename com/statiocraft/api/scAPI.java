package com.statiocraft.api;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.statiocraft.api.config.ConfigFile;
import com.statiocraft.api.config.VirtualConfigFile;
import com.statiocraft.api.config.userdata.UserData;
import com.statiocraft.api.config.userdata.VirtualUser;
import com.statiocraft.api.network.DataBridge;
import com.statiocraft.api.network.VirtualDataServer;
import com.statiocraft.api.network.packages.ConfigPackage;
import com.statiocraft.api.network.packages.StringPackage;
import com.statiocraft.api.recipes.Recipe;
import com.statiocraft.api.scheduler.Scheduler;
import com.statiocraft.api.scoreboard.Example;
import com.statiocraft.api.util.*;

public class scAPI extends JavaPlugin {
	
	private icEventManager eventManager;
	
	public void onLoad(){
		console = new Console(this);
		recipe = new Recipe(this);
		scheduler = new Scheduler(this);
		
		blockUtil = new blockUtil(this);
		bungeeUtil = new bungeeUtil(this);
		colorUtil = new colorUtil(this);
		dateUtil = new dateUtil(this);
		fireworkUtil = new fireworkUtil(this);
		inventoryUtil = new inventoryUtil(this);
		itemUtil = new itemUtil(this);
		mathUtil = new mathUtil(this);
		numberUtil = new numberUtil(this);
		playerUtil = new playerUtil(this);
		stringUtil = new stringUtil(this);
		timeUtil = new timeUtil(this);
		
		UserData.e(this);
		VirtualUser.e(this);
	}
	
	public void onEnable(){		
		eventManager = new icEventManager(this);
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		//Register Packages
		DataBridge.registerNewPackage(ConfigPackage.class);
		DataBridge.registerNewPackage(StringPackage.class);
		
		//Enabled auto-saver for configFiles
		scAPI.scheduler.scheduleSyncRepeatingTask(new Runnable(){
			public void run(){
				try{
					Field f = VirtualConfigFile.class.getDeclaredField("configFileList");
					f.setAccessible(true);
					List<?> l = (List<?>)f.get(null);
					for(Object o : l){
						if(o instanceof ConfigFile){
							if(((ConfigFile)o).getAutosave())((ConfigFile)o).save();
						}
					}
				}catch(Exception e){
					scAPI.console.warn("Failed to auto-save configuration files!");
				}
			}
		}, 15*20, 30*20);
	}
	
	public void onDisable(){
		for(Player t : Bukkit.getOnlinePlayers()){
			//if(Menu.getByInventory(t.getInventory()) != null || GUI.getByInventory(t.getInventory()) != null){
				t.closeInventory();
			//}
		}
		
		//Save all configuration
		UserData.saveAll();
		eventManager.uuidLoader.save();
		
		VirtualDataServer.d(this);
		DataBridge.d(this);
	}
	
	//private Location p1 = null, p2 = null;
	//private scSchematic schem = null;
		
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("test")){
			Player p = (Player)sender;
			
			//playerUtil.setNameplatePrefix(p, scAPI.stringUtil.toSingleString(args, 0));
			
			Example.castSpiral(Effect.HEART, p.getTargetBlock(null, 4).getLocation(), 1d, 24, 3, true, 2, 3);
			
			p.sendMessage("§aCast!");
			return true;
			
			/*if(args[0].equalsIgnoreCase("1")){
				p1 = p.getLocation();
				p.sendMessage("p1 set!");
			}
			else if(args[0].equalsIgnoreCase("2")){
				p2 = p.getLocation();
				p.sendMessage("p2 set!");
			}
			else if(args[0].equalsIgnoreCase("load")){
				if(p1 == null){
					p.sendMessage("p1 not set!");
				}
				else if(p2 == null){
					p.sendMessage("p2 not set!");
				}else{
					schem = new scSchematic(p.getLocation(), p1, p2);
					p.sendMessage("Schematic loaded!");
				}
			}
			else if(args[0].equalsIgnoreCase("save")){
				if(schem == null){
					p.sendMessage("No schematic found!");
				}else{
					ConfigFile cf = new ConfigFile(new File(Bukkit.getWorldContainer().getPath() + "/schematic.icdb"), false);
					//cf.put("data", schem);
					schem.save(cf.getFile());
					//cf.save();
					p.sendMessage("Saved!");
				}
			}
			else if(args[0].equalsIgnoreCase("import")){
				File f = new File(Bukkit.getWorldContainer().getPath() + "/schematic.icdb");
				if(!f.exists()){
					p.sendMessage("File not found!");
				}else{
					schem = scSchematic.load(f);
					p.sendMessage("Succesfully imported!");
				}
			}
			else if(args[0].equalsIgnoreCase("paste")){
				boolean b = args.length > 1 && args[1].equalsIgnoreCase("-a");
				if(schem == null){
					p.sendMessage("No schematic found!");
				}else{
					schem.generate(p.getLocation(), !b);
					p.sendMessage("Schematic generated " + ((b) ? ("without air") : ("with air")));
				}
			}*/
		}else{
			return false;
		}
	}
	
	public static Console console;
	
	public static Recipe recipe;
	
	public static Scheduler scheduler;
	
	public static blockUtil blockUtil;
	public static bungeeUtil bungeeUtil;
	public static colorUtil colorUtil;
	public static dateUtil dateUtil;
	public static fireworkUtil fireworkUtil;
	public static inventoryUtil inventoryUtil;
	public static itemUtil itemUtil;
	public static mathUtil mathUtil;
	public static numberUtil numberUtil;
	public static playerUtil playerUtil;
	public static stringUtil stringUtil;
	public static timeUtil timeUtil;
}
