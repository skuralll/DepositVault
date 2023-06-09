package com.skuralll.depositvault.handler;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.config.MessageConfig;
import com.skuralll.depositvault.config.groups.GeneralConfig;
import com.skuralll.depositvault.config.groups.LockConfig;
import com.skuralll.depositvault.db.Database;
import com.skuralll.depositvault.model.LockData;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.CheckForNull;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;

public class LockHandler {

  private final DepositVault plugin;
  private final Economy economy;
  private final LockConfig lock_config;
  private final GeneralConfig general_config;
  private final MessageConfig message_config;
  private final Database db;

  public LockHandler() {
    plugin = DepositVault.getInstance();
    economy = plugin.getEconomy();
    lock_config = plugin.getConfigLoader().getMainConfig().getLock();
    general_config = plugin.getConfigLoader().getMainConfig().getGeneral();
    message_config = plugin.getConfigLoader().getMessagesConfig();
    db = plugin.getDatabase();
  }

  @CheckForNull
  public Integer getUserId(Player player) {
    return db.getUserId(player);
  }

  @CheckForNull
  public UUID getUserUUID(int user_id) {
    return db.getUserUUID(user_id);
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

    String message = "";
    message += "[Status]" + "\n";
    message += "Locked: " + (lock_data != null ? "Yes" : "No") + "\n";
    if (lock_data != null) {
      int user_id = lock_data.getUserId();
      String user_name = getUserName(user_id);
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
    if (length.getTime() > lock_config.getMaxTime().getTime())
      return LockResult.MAX_EXPIRE;
    // check money
    int price = getLockPrice(length);
    if (economy.getBalance(player) < price)
      return LockResult.NOT_ENOUGH_MONEY;
    // add length to now
    LocalDateTime expire = LocalDateTime.now(general_config.getZoneId())
        .plus(length.getTime(), java.time.temporal.ChronoUnit.MILLIS);
    // lock process
    boolean result = db.setLockData(player, location, expire);
    if (!result)
      return LockResult.SQL_ERROR;
    // reduce money
    economy.withdrawPlayer(player, price);
    return LockResult.SUCCESS_LOCK;
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
    return LockResult.SUCCESS_UNLOCK;
  }

  // extend expiration
  public LockResult extend(Player player, Location location, Time length) {
    // check locked or not
    LockData lock_data = getLockData(location);
    if (lock_data == null)
      return LockResult.NOT_LOCKED;
    // check owner
    if (!isOwner(player, lock_data))
      return LockResult.NOT_OWNER;
    // check max expire
    LocalDateTime new_expire = lock_data.getExpireDate()
        .plus(length.getTime(), java.time.temporal.ChronoUnit.MILLIS);
    if (new_expire.isAfter(LocalDateTime.now(general_config.getZoneId())
        .plus(lock_config.getMaxTime().getTime(), java.time.temporal.ChronoUnit.MILLIS)))
      return LockResult.MAX_EXPIRE;
    // extend process
    boolean result = db.removeLockData(location) && db.setLockData(player, location, new_expire);
    if (!result)
      return LockResult.SQL_ERROR;
    return LockResult.SUCCESS_EXTEND;
  }

  // get lock price
  public int getLockPrice(Time length) {
    return (int) (lock_config.getPrice() * Math.ceil(
        length.getTime() / lock_config.getUnit().getMillis()));
  }

  public boolean isOwner(Player player, LockData data) {
    return data.getUserId() == getUserId(player);
  }

  public boolean isValidTime(int time) {
    return 0 < time && time <= lock_config.getMax();
  }

  public Time getTimeFromUnit(int time) {
    return new Time(lock_config.getUnit().getMillis() * time);
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
    if (data.getExpireDate().isBefore(LocalDateTime.now(general_config.getZoneId()))) {
      db.removeLockData(data.getLockId());
      return true;
    }
    return false;
  }

  // get lock result message
  public String getLockResultMessage(LockResult result) {
    switch (result) {
      case SUCCESS_LOCK:
        return message_config.success_lock.apply();
      case SUCCESS_UNLOCK:
        return message_config.success_unlock.apply();
      case SUCCESS_EXTEND:
        return message_config.success_extend.apply();
      case NOT_LOCKED:
        return message_config.not_locked.apply();
      case LOCKED:
        return message_config.locked.apply();
      case NOT_OWNER:
        return message_config.not_your_chest.apply();
      case NOT_ENOUGH_MONEY:
        return message_config.not_enough_money.apply();
      case MAX_EXPIRE:
        return message_config.max_expiration.apply();
      case SQL_ERROR:
        return message_config.sql_error.apply();
      default:
        return "Unknown error";
    }
  }


}
