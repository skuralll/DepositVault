package com.skuralll.depositvault.config;

import com.skuralll.depositvault.DepositVault;
import java.time.ZoneId;
import java.util.Objects;
import org.bukkit.configuration.file.FileConfiguration;

public class GeneralConfig extends Config{

  private ZoneId zone_id;

  public GeneralConfig() {
    DepositVault plugin = DepositVault.getInstance();
    FileConfiguration config = plugin.getConfig();

    try {
      zone_id = ZoneId.of(Objects.requireNonNull(config.getString("timezone")));
    }catch (Exception e) {
      plugin.getLogger().warning("Invalid timezone. Set to default value: Asia/Tokyo");
      zone_id = ZoneId.of("Asia/Tokyo");
    }
  }

  public ZoneId getZoneId() {
    return zone_id;
  }

}
