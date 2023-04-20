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

public class ProtectItem extends AbstractItem {

  private DepositVault plugin;
  private Player player;
  private Location location;

  private String title;
  private NormalCache cache;

  public ProtectItem(Player player, Location location, String title, NormalCache cache) {
    this.plugin = DepositVault.getInstance();
    this.player = player;
    this.location = location;
    this.title = title;
    this.cache = cache;
  }

  @Override
  public ItemProvider getItemProvider() {
    LockConfig config = plugin.getConfigLoader().getLockConfig();
    ItemBuilder item = new ItemBuilder(Material.CHEST);
    item.setDisplayName("" + ChatColor.RESET + ChatColor.YELLOW + ChatColor.BOLD + "[" + title + "]" + ChatColor.RESET);
    item.addLoreLines(ChatColor.YELLOW + "Price: " + ChatColor.DARK_PURPLE + config.getPrice() + "/" + config.getUnit().getName());
    return item;
  }

  @Override
  public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
    player.closeInventory();
    player.sendMessage("Please enter the time in the chat.");
    // put cache
    cache.put(player.getUniqueId(), location);
  }
}