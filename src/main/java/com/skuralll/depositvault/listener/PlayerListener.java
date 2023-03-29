package com.skuralll.depositvault.listener;

import com.skuralll.depositvault.DepositVault;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class PlayerListener implements Listener {

  private final DepositVault plugin;

  public PlayerListener() {
    plugin = DepositVault.getInstance();
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
  }

}
