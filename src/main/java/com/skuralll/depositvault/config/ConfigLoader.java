package com.skuralll.depositvault.config;

import com.skuralll.depositvault.DepositVault;

public class ConfigLoader {

  private DepositVault plugin;

  // configs
  private DBConfig db_config;
  private LockConfig lock_config;

  public ConfigLoader() {
    plugin = DepositVault.getInstance();
    db_config = new DBConfig();
    lock_config = new LockConfig();
  }

  public DBConfig getDBConfig() {
    return db_config;
  }

  public LockConfig getLockConfig() {
    return lock_config;
  }

}
