package com.skuralll.depositvault.listener;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.CheckCommandCache;
import com.skuralll.depositvault.cache.LockCommandCache;
import com.skuralll.depositvault.handler.LockHandler;
import com.skuralll.depositvault.handler.LockResult;
import com.skuralll.depositvault.model.LockData;
import java.util.UUID;
import org.bukkit.ChatColor;
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
  private final CheckCommandCache check_cache;
  private final LockCommandCache lock_cache;

  public PlayerListener() {
    plugin = DepositVault.getInstance();
    handler = plugin.getHandler();
    check_cache = plugin.getCacheStore().getCheckCommandCache();
    lock_cache = plugin.getCacheStore().getLockCommandCache();
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

    Location location = block.getLocation();
    Player player = event.getPlayer();
    UUID uuid = player.getUniqueId();

    // check command process
    if (check_cache.check(uuid)) {
      event.setCancelled(true);
      player.sendMessage(handler.getLockDataMessage(location));
      return;
    }

    // lock command process
    Double deposit = lock_cache.pop(player.getUniqueId());
    if (deposit != null) {
      event.setCancelled(true);
      LockResult result = handler.lock(player, deposit, location);
      player.sendMessage(result.toString());
      return;
    }

    // protect locked inventory-holder
    LockData lock_data = handler.getLockData(location);
    if (lock_data != null) {
      if (!handler.isOwner(player, lock_data)) {
        String owner_name = handler.getUserName(lock_data.getUserId());
        if (owner_name == null)
          owner_name = "Unknown";
        player.sendMessage(ChatColor.RED + "This block is locked by " + owner_name + ".");
        event.setCancelled(true);
      }
    }
  }

}
