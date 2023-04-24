package com.skuralll.depositvault.ui.item;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.config.MessageConfig;
import com.skuralll.depositvault.handler.LockHandler;
import com.skuralll.depositvault.handler.LockResult;
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

public class UnLockItem extends AbstractItem {

  private MessageConfig message;
  private LockHandler handler;
  private Player player;
  private Location location;

  private boolean clicked = false;

  public UnLockItem(Player player, Location location) {
    this.message = DepositVault.getInstance().getConfigLoader().getMessagesConfig();
    this.handler = DepositVault.getInstance().getHandler();
    this.player = player;
    this.location = location;
  }

  @Override
  public ItemProvider getItemProvider() {
    ItemBuilder item = new ItemBuilder(Material.ENDER_CHEST).setDisplayName(
        message.gui_unlock.apply());
    if (clicked)
      item.addLoreLines(message.click_again.apply());
    return item;
  }

  @Override
  public void handleClick(@NotNull ClickType clickType, @NotNull Player player,
      @NotNull InventoryClickEvent event) {
    if (clicked) {
      player.closeInventory();
      LockResult result = handler.unlock(player, location);
      player.sendMessage(result.toString());
    } else {
      clicked = true;
      notifyWindows();
    }
  }
}
