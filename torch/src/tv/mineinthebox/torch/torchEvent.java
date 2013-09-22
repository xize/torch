package tv.mineinthebox.torch;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class torchEvent implements Listener {

	public static Map<String, LinkedList<BlockState>> list = new HashMap<String, LinkedList<BlockState>>();

	public static HashMap<String, Boolean> torchPlayers = new HashMap<String, Boolean>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public static void torch(PlayerMoveEvent e) {
		if(isTorch(e.getPlayer())) {
			if(e.getPlayer().getItemInHand().getType() == Material.TORCH) {
				Block block = e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
				if(block.getType() == Material.AIR || block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER || block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA) {
					return;
				}
				e.getPlayer().sendBlockChange(block.getLocation(), Material.GLOWSTONE, block.getData());
				getTrailList(e.getPlayer()).add(block.getState());
				removeGlow(e.getPlayer());
			}
		}
	}

	public static LinkedList<BlockState> getTrailList(Player p) {
		LinkedList<BlockState> blocklist = list.get(p.getName());
		if(blocklist == null) {
			blocklist = new LinkedList<BlockState>();
			list.put(p.getName(), blocklist);
		}
		return blocklist;
	}

	public static void removeGlow(Player p) {
		LinkedList<BlockState> blocklist = list.get(p.getName());
		if(blocklist.size() > configuration.range) {
			BlockState old = blocklist.removeFirst();
			old.update();
		}
	}

	public static boolean hasTrail(Player p) {
		return list.containsKey(p.getName());
	}

	public static boolean isTorch(Player p) {
		if(torchPlayers.containsKey(p.getName())) {
			Boolean bol = torchPlayers.get(p.getName());
			if(bol) {
				return true;
			}
		}
		return false;
	}

	@EventHandler
	public void checkTorchOnJoin(PlayerJoinEvent e) {
		try {
			File f = new File(torch.getPlugin.getDataFolder() + File.separator + "players" + File.separator + e.getPlayer().getName() + ".yml");
			if(f.exists()) {
				FileConfiguration con = YamlConfiguration.loadConfiguration(f);
				Boolean bol = con.getBoolean("torchEnabled");
				if(bol) {
					torchPlayers.put(e.getPlayer().getName(), bol);	
				}
			}
		} catch(Exception r) {
			r.printStackTrace();
		}
	}
	
	@EventHandler
	public void removeTorch(PlayerQuitEvent e) {
		if(torchPlayers.containsKey(e.getPlayer().getName())) {
			Boolean bol = torchPlayers.get(e.getPlayer().getName());
			try {
				File f = new File(torch.getPlugin.getDataFolder() + File.separator + "players" + File.separator + e.getPlayer().getName() + ".yml");
				if(f.exists() || !f.exists()) {
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					con.set("torchEnabled", bol);
					con.save(f);
				}
			} catch(Exception r) {
				r.printStackTrace();
			}
		}
		
		if(list.containsKey(e.getPlayer().getName())) {
			LinkedList<BlockState> blocklist = list.get(e.getPlayer().getName());
			blocklist.clear();
			list.remove(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void removeTorch(PlayerKickEvent e) {
		if(torchPlayers.containsKey(e.getPlayer().getName())) {
			Boolean bol = torchPlayers.get(e.getPlayer().getName());
			try {
				File f = new File(torch.getPlugin.getDataFolder() + File.separator + "players" + File.separator + e.getPlayer().getName() + ".yml");
				if(f.exists() || !f.exists()) {
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					con.set("torchEnabled", bol);
					con.save(f);
				}
			} catch(Exception r) {
				r.printStackTrace();
			}
		}
		
		if(list.containsKey(e.getPlayer().getName())) {
			LinkedList<BlockState> blocklist = list.get(e.getPlayer().getName());
			blocklist.clear();
			list.remove(e.getPlayer().getName());
		}
	}
	
	public static void checkPlayers() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			try {
				File f = new File(torch.getPlugin.getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml");
				if(f.exists()) {
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					Boolean bol = con.getBoolean("torchEnabled");
					if(bol) {
						torchPlayers.put(p.getPlayer().getName(), bol);	
					}
				}
			} catch(Exception r) {
				r.printStackTrace();
			}
		}
	}
	
	public static void savePlayers() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(torchPlayers.containsKey(p.getName())) {
				Boolean bol = torchPlayers.get(p.getName());
				try {
					File f = new File(torch.getPlugin.getDataFolder() + File.separator + "players" + File.separator + p.getName() + ".yml");
					FileConfiguration con = YamlConfiguration.loadConfiguration(f);
					con.set("torchEnabled", bol);
					con.save(f);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
