package com.skuralll.depositvault.handler;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.config.LockConfig;
import com.skuralll.depositvault.db.Database;
import com.skuralll.depositvault.model.DepositData;
import com.skuralll.depositvault.model.LockData;
import javax.annotation.CheckForNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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

  public String getLockDataMessage(Location location) {
    DepositData deposit_data = getPurchaseCost();
    LockData lock_data = getLockData(location);
    String message = "";
    message += "[Status]" + "\n";
    message += "Locked: " + (lock_data != null ? "Yes" : "No") + "\n";
    message += "[Purchase Cost]" + "\n";
    message += "Interval: " + deposit_data.getInterval() + "\n";
    message += "Payment: " + deposit_data.getPayment() + "\n";
    message += "Minimum Cost: " + deposit_data.getMin_pay() + "\n";
    if (lock_data != null) {
      DepositData deposit_data_locked = lock_data.getDepositData();
      message += "[Maintenance Cost]" + "\n";
      message += "User ID: " + lock_data.getUserID() + "\n";
      message += "Lock ID: " + lock_data.getLockID() + "\n";
      message += "Interval: " + deposit_data_locked.getInterval() + "\n";
      message += "Payment: " + deposit_data_locked.getPayment() + "\n";
      message += "Minimum Cost: " + deposit_data_locked.getMin_pay() + "\n";
    }
    return message;
  }

  // lock inventory holder
  public boolean lock(Player player, Double deposit, Location location) {
    player.sendMessage("This is Lock Message");
    return true;
  }

}
