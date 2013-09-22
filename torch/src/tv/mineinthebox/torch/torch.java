package tv.mineinthebox.torch;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class torch extends JavaPlugin {
	
	static String pluginVersion = null;
	
	public static torch getPlugin = null;
	
	static Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable() {
		pluginVersion = this.getName() + " " + this.getDescription().getVersion();
		getPlugin = this;
		configuration.createConfig();
		configuration.updateRange();
		setLogMessage("has been enabled!", logType.info);
		getCommand("torch").setExecutor(new command());
		torchEvent.checkPlayers();
		handler.launch();
	}
	
	public void onDisable() {
		setLogMessage("has been disabled!", logType.info);
		torchEvent.savePlayers();
	}
	
	public static void setLogMessage(String message, logType type) {
		if(type == logType.info) {
			log.info(pluginVersion + ": " + message);
		} else if(type == logType.servere) {
			log.severe(pluginVersion + ": " + message);
		}
	}

}
