package com.skuralll.depositvault.handler;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.config.LockConfig;
import com.skuralll.depositvault.db.Database;
import com.skuralll.depositvault.model.LockData;
import java.sql.Time;
import java.time.LocalDateTime;
import javax.annotation.CheckForNull;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;

public class LockHandler {

  private final DepositVault plugin;
  private final LockConfig config;
  private final Database db;

  public LockHandler() {
    plugin = DepositVault.getInstance();
    config = plugin.getConfigLoader().getLockConfig();
    db = plugin.getDatabase();
  }

  @CheckForNull
  public Integer getUserId(Player player) {
    return db.getUserId(player);
  }

  @CheckForNull
  public String getUserName(int user_id) {
    return db.getUserName(user_id);
  }

  @CheckForNull
  public LockData getLockData(Location location) {
    return db.getLockData(location);
  }

  public String getLockDataMessage(Location location) {
    LockData lock_data = getLockData(location);

    int user_id = lock_data.getUserId();
    String user_name = getUserName(user_id);

    String message = "";
    message += "[Status]" + "\n";
    message += "Locked: " + (lock_data != null ? "Yes" : "No") + "\n";
    if (lock_data != null) {
      message += "Expiration: " + lock_data.getExpireDate().toString() + "\n";
      message += "User: " + (user_name != null ? user_name : "UNKNOWN") + " (ID:"
          + lock_data.getUserId() + ")\n";
      message += "Lock ID: " + lock_data.getLockId() + "\n";
    }
    return message;
  }

  // lock inventory holder
  public LockResult lock(Player player, Location location, Time length) {
    // check locked or not
    if (getLockData(location) != null)
      return LockResult.LOCKED;
    // check max expire
    if (length.getTime() > config.getMaxTime().getTime())
      return LockResult.MAX_EXPIRE;
    // add length to now
    LocalDateTime expire = LocalDateTime.now()
        .plus(length.getTime(), java.time.temporal.ChronoUnit.MILLIS);
    // lock process
    boolean result = db.setLockData(player, location, expire);
    if (!result)
      return LockResult.SQL_ERROR;
    return LockResult.SUCCESS;
  }

  // unlock inventory holder
  public LockResult unlock(Player player, Location location) {
    // check locked or not
    LockData lock_data = getLockData(location);
    if (lock_data == null)
      return LockResult.NOT_LOCKED;
    // check owner
    if (!isOwner(player, lock_data))
      return LockResult.NOT_OWNER;
    // unlock process
    boolean result = db.removeLockData(location);
    if (!result)
      return LockResult.SQL_ERROR;
    return LockResult.SUCCESS;
  }

  public boolean isOwner(Player player, LockData data) {
    return data.getUserId() == getUserId(player);
  }

  // Derive one coordinate from a block with two coordinates
  public Location getFixedLocation(Location location) {
    Block block = location.getBlock();
    // check block state
    BlockState state = block.getState();
    // If it were a large chest, replace the block and state with the left side
    if (state instanceof Chest) {
      Inventory chest_inv = ((Chest) block.getState()).getInventory();
      if (chest_inv instanceof DoubleChestInventory dcchest_inv) {
        Location left_location = dcchest_inv.getLeftSide().getLocation();
        if (left_location == null)
          return location;
        return left_location;
      }
    }
    return location;
  }

  // return true if lock is expired
  public boolean validate(LockData data) {
    if (data.getExpireDate().isBefore(LocalDateTime.now())) {
      db.removeLockData(data.getLockId());
      return true;
    }
    return false;
  }

}
