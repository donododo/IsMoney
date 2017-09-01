package fr.donododo.ismoney.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.entity.Player;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import fr.donododo.main.ismoney.IsMoney;
import net.milkbowl.vault.economy.EconomyResponse;

public class SkyBank {
	
	
	public SkyBank() {
		
	}

	public double getMoneyToPay(Player p) {
	
		int level = ASkyBlockAPI.getInstance().getIslandLevel(p.getUniqueId());
		double multiplier = getMultiplier(p);
		double salary = level * multiplier;
		
		return salary;
	}

	public double getMultiplier(Player p) {
		List <String> listed = new ArrayList<String>();
		List<String> keying = new ArrayList<String>();
		List<String> keyeding = new ArrayList<String>();
		for(String key : IsMoney.configYml.getConfigurationSection("money").getKeys(false)){
		   	for(String keyed : IsMoney.configYml.getConfigurationSection("money." + key).getKeys(false)) {
		   		listed.add(key + "." + keyed);
		   		keying.add(key);
		   		keyeding.add(keyed);
		   	}
		    //the rest of your code here
		}
		for(String str : listed) {
			for (int i = listed.size(); i != 0; i--) {
				int st = i - 1;
				if(p.hasPermission(listed.get(st))) {
					return IsMoney.configYml.getDouble(".money" + "." + keying.get(st) + "." + keyeding.get(st));
				}else {
					continue;
				}
			}
		}
		return 0;
	}
	
	public boolean canGetSalary(Player p) {
		if(p.hasPermission("ismoney.gain")) {
			Date date = new Date();
			if(getlastSalary(p) != null) {
				if (getlastSalary(p).getDay() == date.getDay() && getlastSalary(p).getMonth() == date.getMonth()) {
					p.sendMessage("§cVous devez attendre demain pour exécuter cette commande.");
					return false;
				}else{
					return true;
				}
			}else {
				return true;
			}

		}else {
			p.sendMessage("§cVous n’avez pas la permission d'exécuter cette commande!");
			return false;
		}
	}
	
	public void saveSalaryDate(Player p) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		String date = formatter.format(new Date());
		
		IsMoney.playersYml.set(".Players" + "." + p.getName(), date);
		try {
			IsMoney.playersYml.save(IsMoney.playersFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Date getlastSalary(Player p) {
		Date date = null;
		if(IsMoney.playersYml.get(".Players" + "." + p.getName()) != null) {
			String str = IsMoney.playersYml.getString(".Players" + "." + p.getName());
	        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			try {
				date = formatter.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			return null;
		}

		
		return date;
	}
	
	public void addMoney(Player p, double money) {
		saveSalaryDate(p);
        EconomyResponse r = IsMoney.getEcononomy().depositPlayer(p, money);
        if(r.transactionSuccess()) {
    		p.sendMessage("§eVous avez reçu : §A" + money + "§e.");
        } else {
            p.sendMessage(String.format("§cErrreur : §e", r.errorMessage));
        }
	}
}
