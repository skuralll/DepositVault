package com.skuralll.depositvault.ui.item;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.handler.LockHandler;
import com.skuralll.depositvault.model.LockData;
import com.skuralll.depositvault.utils.Utils;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AsyncItem;

public class LockStatusItem extends AsyncItem {

  private LockHandler handler;
  private Location location;

  public LockStatusItem(Location location) {
    // register provider
    super(
        new ItemBuilder(Material.COMPASS).setDisplayName("Loading..."),
        () -> {
          return new ItemProvider() {

            @Override
            public ItemStack get(@Nullable String lang) {
              LockHandler handler = DepositVault.getInstance().getHandler();
              LockData data = handler.getLockData(location);
              // create itemstack
              ItemStack item;
              if (data == null) {
                item = new ItemStack(Material.COMPASS);
              } else {
                item = Utils.getPlayerHead(handler.getUserUUID(data.getUserId()));
              }
              // set meta
              ItemMeta meta = item.getItemMeta();
              meta.setDisplayName("" + ChatColor.RESET + ChatColor.YELLOW + ChatColor.BOLD + "[Status]" + ChatColor.RESET);
              ArrayList<String> lores = new ArrayList<>();
              lores.add(ChatColor.YELLOW + "Locked: " + ChatColor.RESET + (data == null ? "False" : "True" + " (ID:" + data.getLockId() + ")"));
              if (data != null) {
                int user_id = data.getUserId();
                String user_name = handler.getUserName(user_id);
                lores.add(ChatColor.YELLOW + "Owner: " + ChatColor.RESET + (user_name == null ? "UNKNOWN" : user_name) + " (ID:" + user_id + ")");
                lores.add(ChatColor.YELLOW + "Expiration: " + ChatColor.RESET + data.getExpireDate().toString());
              }
              meta.setLore(lores);
              item.setItemMeta(meta);
              return item;
            }
          };

        }
    );
  }

  @Override
  public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
  }
}
