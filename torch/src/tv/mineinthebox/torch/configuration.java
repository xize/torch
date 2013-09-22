package tv.mineinthebox.torch;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;

public class configuration {

	public static int range;

	public static void createConfig() {
		try {
			File f = new File(torch.getPlugin.getDataFolder() + File.separator + "config.yml");
			if(!f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				FileConfigurationOptions opt = con.options();
				opt.header("Default configuration for torch");
				con.set("glowstoneRange", 10);
				con.save(f);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateRange() {
		try {
			File f = new File(torch.getPlugin.getDataFolder() + File.separator + "config.yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				range = con.getInt("glowstoneRange");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
