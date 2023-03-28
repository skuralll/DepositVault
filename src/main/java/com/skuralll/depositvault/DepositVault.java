package com.skuralll.depositvault;

import com.skuralll.depositvault.command.CommandBase;
import com.skuralll.depositvault.db.Database;
import com.skuralll.depositvault.listener.PlayerListener;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class DepositVault extends JavaPlugin {

  private static DepositVault instance = null;
  // Database Handler
  private static Database db;

  private static final Logger log = Logger.getLogger("Minecraft");

  // Vault Economy API
  private static Economy economy;
  // Vault Permission API
  private static Permission permission;

  @Override
  public void onEnable() {
    instance = this;

    // set up vault
    if (!setupEconomy()) {
      log.severe(String.format("[%s] - Disabled due to no Vault dependency found!",
          getDescription().getName()));
      getServer().getPluginManager().disablePlugin(this);
      return;
    }
    setupPermissions();

    // set up database
    if (!setupDatabase()) {
      log.severe(String.format("[%s] - Disabled due to no database connection!",
          getDescription().getName()));
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    // register events
    getServer().getPluginManager().registerEvents(new PlayerListener(), this);

    // register commands
    CommandBase commandBase = new CommandBase();
    getCommand("dvault").setExecutor(commandBase);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

  // set up vault economy api
  private boolean setupEconomy() {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      return false;
    }
    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager()
        .getRegistration(Economy.class);
    if (rsp == null) {
      return false;
    }
    economy = rsp.getProvider();
    return economy != null;
  }

  // set up vault permission api
  private boolean setupPermissions() {
    RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager()
        .getRegistration(Permission.class);
    permission = rsp.getProvider();
    return permission != null;
  }

  // set up database
  private boolean setupDatabase() {
    Database _db = new Database("mariaDB", 3306, "root", "root", "deposit_vault");
    if (_db.connect()) {
      db = _db;
      return true;
    } else {
      return false;
    }
  }

  /*Getter and Setter*/

  public static DepositVault getInstance() {
    return instance;
  }

}
