package com.skuralll.depositvault.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

public class Utils {

  // get player head skull
  public static ItemStack getPlayerHead(String url) {
    ItemStack item = new ItemStack(Material.PLAYER_HEAD);
    SkullMeta meta = (SkullMeta) item.getItemMeta();
    // set player
    meta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString("c837fc70-9efe-46de-8591-284033993be2"))); // UUID of skura_lll
    // set skin
    PlayerProfile profile = meta.getPlayerProfile();
    if (profile != null) {
      PlayerTextures textures = profile.getTextures();
      try {
        textures.setSkin(new URL(url));
      } catch (MalformedURLException e) {
        throw new RuntimeException(e);
      }
      profile.setTextures(textures);
    }
    meta.setPlayerProfile(profile);
    // meta
    item.setItemMeta(meta);
    return item;
  }

}
