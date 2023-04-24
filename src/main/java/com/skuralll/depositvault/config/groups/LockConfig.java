package com.skuralll.depositvault.config.groups;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.model.LockUnit;
import java.sql.Time;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class LockConfig {

  private LockUnit unit;
  private int price;
  private int max;

  public LockConfig(ConfigurationSection config) {
    DepositVault plugin = DepositVault.getInstance();

    try {
      unit = LockUnit.fromChar(config.getString("lock.unit"));
    } catch (IllegalArgumentException e) {
      plugin.getLogger().warning("Invalid lock unit. Set to default value: DAY");
      unit = LockUnit.DAY;
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

  public Time getMaxTime() {
    return new Time(unit.getMillis() * max);
  }

}
