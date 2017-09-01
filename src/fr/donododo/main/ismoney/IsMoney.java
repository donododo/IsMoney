package fr.donododo.main.ismoney;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.donododo.ismoney.commands.IsMoneyCommand;
import net.milkbowl.vault.economy.Economy;

public class IsMoney extends JavaPlugin{
	
	static Plugin plugin;
	
    public static File configFile = new File("plugins" + "/IsMoney"+"");
    public static FileConfiguration configYml = YamlConfiguration.loadConfiguration(configFile);
    
    public static File playersFile = new File("plugins" + "/IsMoney"+"");
    public static FileConfiguration playersYml = YamlConfiguration.loadConfiguration(playersFile);
    public static Economy economy = null;
    
	public void onEnable() {
        if (!setupEconomy() ) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

		plugin = this;
		setupConfig();
		IsMoney.configYml = YamlConfiguration.loadConfiguration(IsMoney.configFile);
		IsMoney.playersYml = YamlConfiguration.loadConfiguration(IsMoney.playersFile);
		configYml.set(".money" + ".grade.d9", 1.0);
		configYml.set(".money" + ".grade.d10", 2.0);
		configYml.set(".money" + ".grade.d11", 2.5);
		configYml.set(".money" + ".grade.d12", 3);
		configYml.set(".money" + ".grade.d13", 4);
		configYml.set(".money" + ".grade.d14", 5);
		configYml.set(".money" + ".grade.d15", 7);
		configYml.set(".money" + ".grade.d16", 9);
		try {
			configYml.save(configFile);
			IsMoney.configYml = YamlConfiguration.loadConfiguration(IsMoney.configFile);
			IsMoney.playersYml = YamlConfiguration.loadConfiguration(IsMoney.playersFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getCommand("ismoney").setExecutor(new IsMoneyCommand());
	}
	
	public void onDisable() {
		
	}

	public void setupConfig() {
		try {
			if(!configFile.getPath().isEmpty()) {
				configFile = new File("plugins" + "/IsMoney"+"/Config.yml");
				if(configFile.exists()) {
					
				}else {
					configYml.save(configFile);
				}
			}else {
				configFile.mkdirs();
				configFile = new File("plugins" + "/IsMoney"+"/Config.yml");
				configYml.save(configFile);


			}
			if(!playersFile.getPath().isEmpty()) {
				playersFile = new File("plugins" + "/IsMoney"+"/Players.yml");
				if(playersFile.exists()) {
					
				}else {
					playersYml.save(playersFile);
				}
			}else {
				playersFile.mkdirs();
				playersFile = new File("plugins" + "/IsMoney"+"/Players.yml");
				playersYml.save(playersFile);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }
    
    public static Economy getEcononomy() {
        return economy;
    }
}
