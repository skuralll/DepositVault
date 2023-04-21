package com.skuralll.depositvault.listener;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.config.MessageConfig;
import com.skuralll.depositvault.handler.LockHandler;
import com.skuralll.depositvault.model.LockData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEventListener implements Listener {

  private final DepositVault plugin;
  private final LockHandler handler;
  private final MessageConfig message;

  public BlockEventListener() {
    plugin = DepositVault.getInstance();
    handler = plugin.getHandler();
    message = plugin.getConfigLoader().getMessagesConfig();
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    // check plugin status
    if (!plugin.isEnabled())
      return;
    if (event.isCancelled())
      return;

    Location location = handler.getFixedLocation(event.getBlock().getLocation());
    // protect locked inventory-holder
    LockData lock_data = handler.getLockData(location);
    if (lock_data != null) {
      handler.validate(lock_data);
      Player player = event.getPlayer();
      event.setCancelled(true);
      if (handler.isOwner(player, lock_data)) {
        // owner
        player.sendMessage(message.locked_by_myself.apply());
      } else {
        // not owner
        String owner_name = handler.getUserName(lock_data.getUserId());
        if (owner_name == null)
          owner_name = "Unknown";
        player.sendMessage(message.locked_by_other.apply("player", owner_name));
      }
    }
  }

  private final int[][] arounds = new int[][]{
      {-1, -1},
      {-1, 0},
      {-1, 1},
      {0, -1},
      {0, 1},
      {1, -1},
      {1, 0},
      {1, 1}
  };

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    Block block = event.getBlock();
    // check is block chest
    if (!block.getType().equals(Material.CHEST))
      return;
    BlockFace direction = ((Chest) block.getBlockData()).getFacing();

    Location location = block.getLocation();
    // protect locked inventory-holder
    for (int[] pos : arounds) {
      // check is around block chest
      Location around_location = location.clone().add(pos[0], 0, pos[1]);
      Block around_block = around_location.getBlock();
      if (!around_block.getType().equals(Material.CHEST))
        continue;
      // check is around block same direction
      if (!((Chest) around_block.getBlockData()).getFacing().equals(direction))
        continue;
      // check is around block locked
      LockData lock_data = handler.getLockData(around_location);
      if (lock_data == null)
        continue;
      // event cancel
      event.setCancelled(true);
    }
  }

}
