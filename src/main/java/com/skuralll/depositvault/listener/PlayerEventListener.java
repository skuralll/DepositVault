package com.skuralll.depositvault.listener;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.NormalCache;
import com.skuralll.depositvault.cache.TimerCache;
import com.skuralll.depositvault.config.groups.LockConfig;
import com.skuralll.depositvault.handler.LockHandler;
import com.skuralll.depositvault.handler.LockResult;
import com.skuralll.depositvault.model.LockData;
import io.papermc.paper.event.player.AsyncChatEvent;
import java.sql.Time;
import java.util.UUID;

import com.skuralll.depositvault.ui.HolderMenuGUI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

public class PlayerEventListener implements Listener {

  private final DepositVault plugin;
  private final LockHandler handler;
  private final LockConfig config;
  private final TimerCache<UUID> check_cache;
  private final TimerCache<UUID> unlock_cache;
  private final TimerCache<UUID> ui_cache;
  private final NormalCache<UUID, Time> lock_cache;
  private final NormalCache<UUID, Time> extend_cache;
  private final NormalCache<UUID, Location> lock_ui_cache;
  private final NormalCache<UUID, Location> extend_ui_cache;

  public PlayerEventListener() {
    plugin = DepositVault.getInstance();
    handler = plugin.getHandler();
    config = plugin.getConfigLoader().getMainConfig().getLock();
    check_cache = plugin.getCacheStore().getCheckCommandCache();
    unlock_cache = plugin.getCacheStore().getUnlockCommandCache();
    ui_cache = plugin.getCacheStore().getUICommandCache();
    lock_cache = plugin.getCacheStore().getLockCommandCache();
    extend_cache = plugin.getCacheStore().getExtendCommandCache();
    lock_ui_cache = plugin.getCacheStore().getLockUICache();
    extend_ui_cache = plugin.getCacheStore().getExtendUICache();
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    // check plugin status
    if (!plugin.isEnabled())
      return;
    if (event.isCancelled())
      return;

    // check block action
    Action action = event.getAction();
    if (!(action == Action.RIGHT_CLICK_BLOCK || action == Action.PHYSICAL))
      return;

    // check block type
    Block block = event.getClickedBlock();
    if (block == null)
      return;

    // check block state
    BlockState state = block.getState();
    if (!(state instanceof InventoryHolder))
      return;

    Location location = handler.getFixedLocation(block.getLocation());
    Player player = event.getPlayer();
    UUID uuid = player.getUniqueId();

    // validate
    LockData lock_data = handler.getLockData(location);
    if (lock_data != null)
      handler.validate(lock_data);

    // check command process
    if (check_cache.check(uuid)) {
      event.setCancelled(true);
      player.sendMessage(handler.getLockDataMessage(location));
      return;
    }

    // lock command process
    Time lock_length = lock_cache.pop(player.getUniqueId());
    if (lock_length != null) {
      event.setCancelled(true);
      LockResult result = handler.lock(player, location, lock_length);
      player.sendMessage(result.toString());
      return;
    }

    // unlock command process
    if (unlock_cache.check(uuid)) {
      event.setCancelled(true);
      LockResult result = handler.unlock(player, location);
      player.sendMessage(result.toString());
      return;
    }

    // extend command process
    Time extend_length = extend_cache.pop(player.getUniqueId());
    if (extend_length != null) {
      event.setCancelled(true);
      LockResult result = handler.extend(player, location, extend_length);
      player.sendMessage(result.toString());
      return;
    }

    // ui command chache
    if (ui_cache.check(uuid)) {
      event.setCancelled(true);
      HolderMenuGUI gui = new HolderMenuGUI(player, location);
      gui.open();
      return;
    }

    // protect locked inventory-holder
    if (lock_data != null) {
      if (!handler.isOwner(player, lock_data)) {
        String owner_name = handler.getUserName(lock_data.getUserId());
        if (owner_name == null)
          owner_name = "Unknown";
        player.sendMessage(ChatColor.RED + "This block is locked by " + owner_name + ".");
        event.setCancelled(true);
        return;
      }
    }
  }

  @EventHandler
  public void onPlayerChat(AsyncChatEvent event){
    Player player = event.getPlayer();
    String message = event.signedMessage().message();

    // lock-ui process
    Location lock_location = lock_ui_cache.pop(event.getPlayer().getUniqueId());
    if(lock_location != null){
      event.setCancelled(true);
      int time = Integer.parseInt(message);
      LockResult result = handler.lock(player, lock_location, handler.getTimeFromUnit(time));
      player.sendMessage(result.toString());
      return;
    }

    // extend-ui process
    Location extend_location = extend_ui_cache.pop(event.getPlayer().getUniqueId());
    if(extend_location != null){
      event.setCancelled(true);
      int time = Integer.parseInt(message);
      LockResult result = handler.extend(player, extend_location, handler.getTimeFromUnit(time));
      player.sendMessage(result.toString());
      return;
    }
  }

}
