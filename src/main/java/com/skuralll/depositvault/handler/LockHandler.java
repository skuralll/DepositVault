package com.skuralll.depositvault.handler;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.config.LockConfig;
import com.skuralll.depositvault.db.Database;
import com.skuralll.depositvault.model.DepositData;
import com.skuralll.depositvault.model.LockData;
import javax.annotation.CheckForNull;
import org.bukkit.Location;

public class LockHandler {

  private final DepositVault plugin;
  private final LockConfig config;
  private final Database db;

  public LockHandler() {
    plugin = DepositVault.getInstance();
    config = plugin.getConfigLoader().getLockConfig();
    db = plugin.getDatabase();
  }

  public DepositData getPurchaseCost() {
    return new DepositData(config.getInterval(), config.getPayment(), config.getMinPay(), 0d);
  }

  @CheckForNull
  public LockData getLockData(Location location) {
    return db.getLockData(location);
  }

}
