package com.skuralll.depositvault.ui.item;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.NormalCache;
import com.skuralll.depositvault.config.MessageConfig;
import com.skuralll.depositvault.config.groups.LockConfig;
import jp.jyn.jbukkitlib.config.parser.template.StringVariable;
import jp.jyn.jbukkitlib.config.parser.template.TemplateVariable;
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
  private MessageConfig message;
  private Player player;
  private Location location;

  private String title;
  private NormalCache cache;

  public ProtectItem(Player player, Location location, String title, NormalCache cache) {
    this.plugin = DepositVault.getInstance();
    this.message = plugin.getConfigLoader().getMessagesConfig();
    this.player = player;
    this.location = location;
    this.title = title;
    this.cache = cache;
  }

  @Override
  public ItemProvider getItemProvider() {
    LockConfig config = plugin.getConfigLoader().getMainConfig().getLock();
    ItemBuilder item = new ItemBuilder(Material.CHEST);
    item.setDisplayName(title);
    TemplateVariable variable = StringVariable.init()
        .put("price", config.getPrice())
        .put("unit", config.getUnit().getName());
    item.addLoreLines(message.gui_price.apply(variable));
    return item;
  }

  @Override
  public void handleClick(@NotNull ClickType clickType, @NotNull Player player,
      @NotNull InventoryClickEvent event) {
    player.closeInventory();
    player.sendMessage(message.enter_the_time.apply());
    // put cache
    cache.put(player.getUniqueId(), location);
  }
}
