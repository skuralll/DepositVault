package com.skuralll.depositvault.handler;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.config.LockConfig;
import com.skuralll.depositvault.db.Database;

public class LockHandler {

  private final DepositVault plugin;
  private final LockConfig config;
  private final Database db;

  public LockHandler() {
    plugin = DepositVault.getInstance();
    config = plugin.getConfigLoader().getLockConfig();
    db = plugin.getDatabase();
  }

}
