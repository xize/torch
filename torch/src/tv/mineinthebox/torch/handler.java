package tv.mineinthebox.torch;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class handler {
	
	public static void launch() {
		setListener(new torchEvent());
	}
	
	public static void setListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, torch.getPlugin);
	}

}
