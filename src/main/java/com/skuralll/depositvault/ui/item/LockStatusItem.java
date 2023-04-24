package com.skuralll.depositvault.ui.item;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.config.MessageConfig;
import com.skuralll.depositvault.handler.LockHandler;
import com.skuralll.depositvault.model.LockData;
import com.skuralll.depositvault.utils.Utils;
import java.util.ArrayList;
import jp.jyn.jbukkitlib.config.parser.template.StringVariable;
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
              MessageConfig message = DepositVault.getInstance().getConfigLoader()
                  .getMessagesConfig();
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
              meta.setDisplayName(message.gui_status.apply());
              ArrayList<String> lores = new ArrayList<>();

              lores.add(message.gui_status_lock.apply(
                  StringVariable.init()
                      .put("state", data == null ? "False" : "True")
                      .put("id", data.getLockId())
              ));
              if (data != null) {
                int user_id = data.getUserId();
                String user_name = handler.getUserName(user_id);
                lores.add(message.gui_status_owner.apply(
                    StringVariable.init()
                        .put("name", user_name)
                        .put("id", user_id)
                ));
                lores.add(message.gui_status_expiration.apply(
                    StringVariable.init()
                        .put("time", data.getExpireDate())
                ));
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
  public void handleClick(@NotNull ClickType clickType, @NotNull Player player,
      @NotNull InventoryClickEvent event) {
  }
}
