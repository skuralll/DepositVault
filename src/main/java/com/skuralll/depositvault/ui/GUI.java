package com.skuralll.depositvault.ui;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.config.MessageConfig;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

abstract public class GUI {

  protected MessageConfig message;

  public static HolderMenuGUI getHolderMenuGUI(Player player, Location location) {
    return new HolderMenuGUI(player, location);
  }

  protected Player player;

  public GUI(Player player) {
    message = DepositVault.getInstance().getConfigLoader().getMessagesConfig();
    this.player = player;
  }

  public String getTitle() {
    return "DepositVault";
  }

  abstract public Gui getGui();

  public void open() {
    Window window = window = Window.single()
        .setViewer(player)
        .setTitle(getTitle())
        .setGui(getGui())
        .build();
    window.open();
  }
}
