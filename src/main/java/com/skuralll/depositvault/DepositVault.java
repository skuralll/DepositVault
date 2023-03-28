package com.skuralll.depositvault;

import com.skuralll.depositvault.command.CommandBase;
import com.skuralll.depositvault.command.InfoCommand;
import com.skuralll.depositvault.config.ConfigLoader;
import com.skuralll.depositvault.config.DBConfig;
import com.skuralll.depositvault.db.Database;
import com.skuralll.depositvault.listener.PlayerListener;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class DepositVault extends JavaPlugin {

  private static DepositVault instance = null;

  // Vault Economy API
  private Economy economy;
  // Vault Permission API
  private Permission permission;

  // Logger
  private final Logger log = Logger.getLogger("Minecraft");
  // Database Handler
  private Database db;
  // Config Handler
  private ConfigLoader config;

  @Override
  public void onEnable() {
    instance = this;

    saveDefaultConfig();
    config = new ConfigLoader();

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
    commandBase.register("info", new InfoCommand());
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
    DBConfig db_config = config.getDBConfig();
    Database _db = new Database(
        db_config.getHost(),
        db_config.getPort(),
        db_config.getUser(),
        db_config.getPassword(),
        db_config.getDatabase()
    );
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
