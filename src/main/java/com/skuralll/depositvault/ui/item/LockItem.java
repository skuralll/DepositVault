package com.skuralll.depositvault.ui.item;

import com.skuralll.depositvault.DepositVault;
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

  private LockHandler handler;
  private Player player;
  private Location location;

  private boolean clicked = false;

  public LockItem(Player player, Location location) {
    this.handler = DepositVault.getInstance().getHandler();
    this.player = player;
    this.location = location;
  }

  @Override
  public ItemProvider getItemProvider() {
    ItemBuilder item = new ItemBuilder(Material.CHEST).setDisplayName(
        "" + ChatColor.RESET + ChatColor.YELLOW + ChatColor.BOLD + "[Lock]" + ChatColor.RESET
    );
    return item;
  }

  @Override
  public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
    player.closeInventory();

  }
}
