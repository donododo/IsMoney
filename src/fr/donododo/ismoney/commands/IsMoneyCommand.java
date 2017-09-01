package fr.donododo.ismoney.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import fr.donododo.ismoney.utils.SkyBank;
import fr.donododo.main.ismoney.IsMoney;

public class IsMoneyCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("ismoney")) {
			if(args.length == 0) {
				SkyBank bank = new SkyBank();
				if(sender instanceof Player) {
					Player p = (Player) sender;
					if(ASkyBlockAPI.getInstance().getIslandLevel(p.getUniqueId()) != 0) {
						if(bank.getMultiplier(p) != 0) {
							if(bank.canGetSalary(p)) {
								bank.addMoney(p, bank.getMoneyToPay(p));
							}else{
								
							}
						}else {
							p.sendMessage("§cVous n’’avez pas un grade assez élevé pour exécuter cette commande!");
						}
					}else {
						p.sendMessage("§cAvec une ile de niveau 0 c'est compliqué :c.");
					}
				}else {
					sender.sendMessage("§cCette commande ne peut etre utillise par la console :c (bah oui t'a pas d'ile ).");
				}
			}else {
				if(args[0].equalsIgnoreCase("reload")) {
					if(sender.hasPermission("ismoney.reload")) {

						IsMoney.configYml = YamlConfiguration.loadConfiguration(IsMoney.configFile);
						IsMoney.playersYml = YamlConfiguration.loadConfiguration(IsMoney.playersFile);
						sender.sendMessage("§AConfiguration recharge !");

					}else{
						sender.sendMessage("§cVous n’’avez la permission d'exécuter cette commande!");
					}
				}else {
	
				}
			}
		}else {
			
		}
		return false;
	}

}
