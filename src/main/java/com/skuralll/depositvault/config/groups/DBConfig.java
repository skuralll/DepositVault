package com.skuralll.depositvault.config.groups;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.config.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class DBConfig extends Config {

  private String host;
  private int port;
  private String user;
  private String password;
  private String database;

  public DBConfig(ConfigurationSection config) {
    host = config.getString("db.host");
    port = config.getInt("db.port");
    user = config.getString("db.user");
    password = config.getString("db.password");
    database = config.getString("db.database");
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public String getUser() {
    return user;
  }

  public String getPassword() {
    return password;
  }

  public String getDatabase() {
    return database;
  }
}
