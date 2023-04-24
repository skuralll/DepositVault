package com.skuralll.depositvault.config.groups;

import com.skuralll.depositvault.DepositVault;
import java.time.ZoneId;
import java.util.Objects;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class GeneralConfig {

  private int version;
  private ZoneId zone_id;

  public GeneralConfig(ConfigurationSection config) {
    DepositVault plugin = DepositVault.getInstance();

    version = config.getInt("version");

    try {
      zone_id = ZoneId.of(Objects.requireNonNull(config.getString("timezone")));
    } catch (Exception e) {
      plugin.getLogger().warning("Invalid timezone. Set to default value: Asia/Tokyo");
      zone_id = ZoneId.of("Asia/Tokyo");
    }
  }

  public int getVersion() {
    return version;
  }

  public ZoneId getZoneId() {
    return zone_id;
  }

}
