package com.skuralll.depositvault.ui;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.CacheStore;
import com.skuralll.depositvault.handler.LockHandler;
import com.skuralll.depositvault.model.LockData;
import com.skuralll.depositvault.ui.item.LockStatusItem;
import com.skuralll.depositvault.ui.item.ProtectItem;
import com.skuralll.depositvault.ui.item.UnLockItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.item.impl.SimpleItem;

public class HolderMenuGUI extends GUI {

  protected Location location;

  public HolderMenuGUI(Player player, Location location) {
    super(player);
    this.location = location;
  }

  public String getTitle() {
    return "DepositVault";
  }

  @Override
  public Gui getGui() {
    DepositVault plugin = DepositVault.getInstance();
    LockHandler handler = plugin.getHandler();
    CacheStore caches = plugin.getCacheStore();
    LockData data = handler.getLockData(location);

    // items
    AbstractItem lock_item;
    AbstractItem extend_item = new SimpleItem(new ItemBuilder(Material.AIR));
    if (data == null) {
      // not locked
      lock_item = new ProtectItem(player, location, "Lock", caches.getLockUICache());
    } else {
      // locked
      if (handler.isOwner(player, data)) {
        lock_item = new UnLockItem(player, location);
        extend_item = new ProtectItem(player, location, "Extend", caches.getExtendUICache());
      } else {
        lock_item = new SimpleItem(
            new ItemBuilder(Material.BARRIER).setDisplayName(message.not_your_chest.apply()));
      }
    }

    // return gui
    return Gui.normal() // Creates the GuiBuilder for a normal GUI
        .setStructure(
            "# # # # # # # # #",
            "# . L . E . S . #",
            "# . . . . . . . #",
            "# # # # # # # # #")
        .addIngredient('#', new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)))
        .addIngredient('S', new LockStatusItem(location))
        .addIngredient('L', lock_item)
        .addIngredient('E', extend_item)
        .build();
  }
}
