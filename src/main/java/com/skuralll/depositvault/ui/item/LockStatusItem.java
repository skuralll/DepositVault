package com.skuralll.depositvault.ui.item;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.handler.LockHandler;
import com.skuralll.depositvault.model.LockData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class LockStatusItem extends AbstractItem {

    private LockHandler handler;
    private Location location;

    public LockStatusItem(Location location) {
        this.handler = DepositVault.getInstance().getHandler();
        this.location = location;
    }

    @Override
    public ItemProvider getItemProvider() {
        LockData data = handler.getLockData(location);
        ItemBuilder item = new ItemBuilder(Material.COMPASS)
                .setDisplayName("§l[Status]§r")
                .addLoreLines("§eLocked:§r " + (data == null ? "False" : "True" + " (ID:" + data.getLockId() + ")") );
        if(data != null){
            int user_id = data.getUserId();
            String user_name = handler.getUserName(user_id);
            item.addLoreLines("§eOwner:§r " + (user_name == null ? "UNKNOWN" : user_name) + " (ID:" + user_id + ")");
            item.addLoreLines("§eExpiration:§r " + data.getExpireDate().toString());
        }
        return item;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {}
}
