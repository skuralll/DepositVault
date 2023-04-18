package com.skuralll.depositvault.ui.item;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.NormalCache;
import com.skuralll.depositvault.config.LockConfig;
import com.skuralll.depositvault.handler.LockHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class LockItem extends AbstractItem {

  private DepositVault plugin;
  private LockHandler handler;
  private Player player;
  private Location location;

  private boolean clicked = false;

  public LockItem(Player player, Location location) {
    this.plugin = DepositVault.getInstance();
    this.handler = plugin.getHandler();
    this.player = player;
    this.location = location;
  }

  @Override
  public ItemProvider getItemProvider() {
    LockConfig config = plugin.getConfigLoader().getLockConfig();
    ItemBuilder item = new ItemBuilder(Material.CHEST);
    item.setDisplayName("" + ChatColor.RESET + ChatColor.YELLOW + ChatColor.BOLD + "[Lock]" + ChatColor.RESET);
    item.addLoreLines(ChatColor.YELLOW + "Price: " + ChatColor.DARK_PURPLE + config.getPrice() + "/" + config.getUnit().getName());
    return item;
  }

  @Override
  public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
    player.closeInventory();
    if(handler.getLockData(location) != null){
      player.sendMessage("This chest is already locked.");
      return;
    }
    player.sendMessage("Please enter the time in the chat.");
    // put cache
    NormalCache cache = plugin.getCacheStore().getLockUICache();
    cache.put(player.getUniqueId(), location);
  }
}
