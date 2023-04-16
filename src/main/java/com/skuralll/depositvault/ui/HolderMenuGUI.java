package com.skuralll.depositvault.ui;

import com.skuralll.depositvault.ui.item.LockStatusItem;
import com.skuralll.depositvault.ui.item.UnLockItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;

public class HolderMenuGUI extends GUI {

  protected Location location;

  public HolderMenuGUI(Player player, Location location) {
    super(player);
    this.location = location;
  }

  public String getTitle() {
    return "DepositVault >> Menu";
  }

  @Override
  public Gui getGui() {
    return Gui.normal() // Creates the GuiBuilder for a normal GUI
        .setStructure(
            "# # # # # # # # #",
            "# . U . . . S . #",
            "# . . . . . . . #",
            "# # # # # # # # #")
        .addIngredient('#', new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)))
        .addIngredient('S', new LockStatusItem(location))
        .addIngredient('U', new UnLockItem(player, location))
        .build();
  }
}
