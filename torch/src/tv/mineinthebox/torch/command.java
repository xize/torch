package tv.mineinthebox.torch;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class command implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("torch")) {
			if(sender instanceof Player) {
				if(sender.hasPermission("torch.command")) {
					if(args.length == 0) {
						Player p = (Player) sender;
						if(torchEvent.isTorch(p)) {
							sender.sendMessage(ChatColor.GREEN + "successfully disabled torch mode!");
							torchEvent.torchPlayers.put(sender.getName(), false);
						} else {
							sender.sendMessage(ChatColor.GREEN + "successfully enabled torch mode!");
							torchEvent.torchPlayers.put(sender.getName(), true);
						}
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("reload")) {
							configuration.updateRange();
							sender.sendMessage(ChatColor.GREEN + "glowstone range has been updated!");
						}
					}
				} else {
					sender.sendMessage(ChatColor.RED + "you are not allowed to use this command!");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "a console cannot move as a living entity into the world!");
			}
		}
		return false;
	}

}
