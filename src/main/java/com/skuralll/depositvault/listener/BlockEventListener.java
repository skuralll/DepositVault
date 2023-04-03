package com.skuralll.depositvault.listener;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.handler.LockHandler;
import com.skuralll.depositvault.model.LockData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockEventListener implements Listener {

  private final DepositVault plugin;
  private final LockHandler handler;

  public BlockEventListener() {
    plugin = DepositVault.getInstance();
    handler = plugin.getHandler();
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
      Player player = event.getPlayer();
      event.setCancelled(true);
      if (handler.isOwner(player, lock_data)) {
        // owner
        player.sendMessage(
            ChatColor.YELLOW + "You have this chest locked. Please use /dv unlock to unlock it.");
      } else {
        // not owner
        String owner_name = handler.getUserName(lock_data.getUserId());
        if (owner_name == null)
          owner_name = "Unknown";
        player.sendMessage(ChatColor.RED + "This block is locked by " + owner_name + ".");
      }
    }
  }

}
