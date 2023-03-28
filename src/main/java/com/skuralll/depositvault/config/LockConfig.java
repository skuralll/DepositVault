package com.skuralll.depositvault.config;

import com.skuralll.depositvault.DepositVault;
import org.bukkit.configuration.file.FileConfiguration;

public class LockConfig extends Config {

  private int interval;
  private double payment;
  private double min_pay;

  public LockConfig() {
    FileConfiguration config = DepositVault.getInstance().getConfig();
    interval = config.getInt("lock.interval");
    payment = config.getDouble("lock.payment");
    min_pay = config.getDouble("lock.min_pay");
  }

  public int getInterval() {
    return interval;
  }

  public double getPayment() {
    return payment;
  }

  public double getMinPay() {
    return min_pay;
  }

}
