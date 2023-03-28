package com.skuralll.depositvault.config;

import com.skuralll.depositvault.DepositVault;

public class ConfigLoader {

  private DepositVault plugin;

  // configs
  private DBConfig db_config;

  public ConfigLoader() {
    plugin = DepositVault.getInstance();
    db_config = new DBConfig();
  }

  public DBConfig getDBConfig() {
    return db_config;
  }
}
