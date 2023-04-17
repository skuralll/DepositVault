package com.skuralll.depositvault.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

public class Utils {

  private static final NetHttpTransport http_transport = new NetHttpTransport();
  private static final HttpRequestFactory http_factory = http_transport.createRequestFactory();

  // get player head from player uuid
  public static String getSkinURL(UUID uuid) {
    GenericUrl url = new GenericUrl("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", ""));
    try {
      // get and parse profile json
      ObjectMapper mapper = new ObjectMapper();
      String props_raw = http_factory.buildGetRequest(url).execute().parseAsString();
      JsonNode props_json = mapper.readTree(props_raw);
      String profile_raw = props_json.get("properties").get(0).get("value").asText();
      // decode profile (base64) to json
      String profile_str = new String(Base64.getDecoder().decode(profile_raw));
      JsonNode profile_json = mapper.readTree(profile_str);
      // get texture url
      String texture_url = profile_json.get("textures").get("SKIN").get("url").asText();
      return texture_url;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // get player head from texture url
  public static ItemStack getPlayerHead(String skin_url) {
    ItemStack item = new ItemStack(Material.PLAYER_HEAD);
    SkullMeta meta = (SkullMeta) item.getItemMeta();
    // set player
    meta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString("c837fc70-9efe-46de-8591-284033993be2"))); // UUID of skura_lll
    // set skin
    PlayerProfile profile = meta.getPlayerProfile();
    if (profile != null) {
      PlayerTextures textures = profile.getTextures();
      try {
        textures.setSkin(new URL(skin_url));
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

  // get player head from uuid
  public static ItemStack getPlayerHead(UUID uuid) {
    return getPlayerHead(getSkinURL(uuid));
  }


}
