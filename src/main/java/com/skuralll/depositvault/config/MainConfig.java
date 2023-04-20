package com.skuralll.depositvault.config;

import com.skuralll.depositvault.config.groups.DBConfig;
import com.skuralll.depositvault.config.groups.GeneralConfig;
import com.skuralll.depositvault.config.groups.LockConfig;
import org.bukkit.configuration.ConfigurationSection;

public class MainConfig {

  GeneralConfig general;
  DBConfig db;
  LockConfig lock;

  public MainConfig(ConfigurationSection config){
    general = new GeneralConfig(config);
    db = new DBConfig(config);
    lock = new LockConfig(config);
  }

  public GeneralConfig getGeneral() {
    return general;
  }

  public DBConfig getDB() {
    return db;
  }

  public LockConfig getLock() {
    return lock;
  }

}
