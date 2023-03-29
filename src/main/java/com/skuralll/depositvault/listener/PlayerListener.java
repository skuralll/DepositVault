package com.skuralll.depositvault.listener;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.CheckCache;
import com.skuralll.depositvault.handler.LockHandler;
import com.skuralll.depositvault.model.DepositData;
import com.skuralll.depositvault.model.LockData;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class PlayerListener implements Listener {

  private final DepositVault plugin;
  private final LockHandler handler;
  private final CheckCache check_cache;

  public PlayerListener() {
    plugin = DepositVault.getInstance();
    handler = plugin.getHandler();
    check_cache = plugin.getCacheStore().getCheckCache();
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    // check plugin status
    if (!plugin.isEnabled())
      return;
    if (event.isCancelled())
      return;

    // check block action
    if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK
        || event.getAction() == Action.LEFT_CLICK_BLOCK
        || event.getAction() == Action.PHYSICAL
    ))
      return;

    // check block type
    Block block = event.getClickedBlock();
    if (block == null)
      return;

    // check block state
    BlockState state = block.getState();
    if (!(state instanceof InventoryHolder))
      return;

    // If it were a large chest, replace the block and state with the left side
    if (state instanceof Chest) {
      Inventory chest_inv = ((Chest) block.getState()).getInventory();
      if (chest_inv instanceof DoubleChestInventory dcchest_inv) {
        Location left_location = dcchest_inv.getLeftSide().getLocation();
        if (left_location == null)
          return;
        block = left_location.getBlock();
        state = block.getState();
      }
    }

    Player player = event.getPlayer();
    // check command process
    boolean check_processed = handleCheckCommand(player, block.getLocation());
    event.setCancelled(check_processed);
  }

  // Process CheckCommand
  private boolean handleCheckCommand(Player player, Location location) {
    if (check_cache.check(player.getUniqueId())) {
      DepositData deposit_data = handler.getPurchaseCost();
      LockData lock_data = handler.getLockData(location);
      player.sendMessage("[Purchase Cost]");
      player.sendMessage("Interval: " + deposit_data.getInterval());
      player.sendMessage("Payment: " + deposit_data.getPayment());
      player.sendMessage("Minimum Cost: " + deposit_data.getMin_pay());
      if (lock_data != null) {
        DepositData deposit_data_locked = lock_data.getDepositData();
        player.sendMessage("[Maintenance Cost]");
        player.sendMessage("User ID: " + lock_data.getUserID());
        player.sendMessage("Lock ID: " + lock_data.getLockID());
        player.sendMessage("Interval: " + deposit_data_locked.getInterval());
        player.sendMessage("Payment: " + deposit_data_locked.getPayment());
        player.sendMessage("Minimum Cost: " + deposit_data_locked.getMin_pay());
      }
      return true;
    }
    return false;
  }

}
