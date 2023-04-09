package com.skuralll.depositvault.config;

import com.skuralll.depositvault.DepositVault;
import org.bukkit.configuration.file.FileConfiguration;

public class LockConfig extends Config {

  private LockUnit unit;
  private int price;
  private int max;

  public LockConfig() {
    DepositVault plugin = DepositVault.getInstance();
    FileConfiguration config = plugin.getConfig();

    try {
      unit = LockUnit.fromChar(config.getString("lock.unit"));
    } catch (IllegalArgumentException e) {
      plugin.getLogger().warning("Invalid lock unit. Set to default value: DAY");
    }

    price = config.getInt("lock.price");
    max = config.getInt("lock.max");
  }

  public LockUnit getUnit() {
    return unit;
  }

  public int getPrice() {
    return price;
  }

  public int getMax() {
    return max;
  }

}
