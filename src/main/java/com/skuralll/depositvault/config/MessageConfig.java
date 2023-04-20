package com.skuralll.depositvault.config;

import com.skuralll.depositvault.config.groups.DBConfig;
import com.skuralll.depositvault.config.groups.GeneralConfig;
import com.skuralll.depositvault.config.groups.LockConfig;
import jp.jyn.jbukkitlib.config.parser.template.TemplateParser;
import org.bukkit.configuration.ConfigurationSection;

public class MessageConfig {

  public int version;
  // messages
  public final TemplateParser click_to_check;

  public MessageConfig(ConfigurationSection config) {
    version = config.getInt("version");
    // messages
    click_to_check = parse(config.getString("click-to-check"));
  }

  private TemplateParser parse(String value) {
    return TemplateParser.parse(value);
  }

}
