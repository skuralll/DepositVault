package com.skuralll.depositvault.config;

import com.skuralll.depositvault.config.groups.DBConfig;
import com.skuralll.depositvault.config.groups.GeneralConfig;
import com.skuralll.depositvault.config.groups.LockConfig;
import jp.jyn.jbukkitlib.config.parser.template.TemplateParser;
import org.bukkit.configuration.ConfigurationSection;

public class MessageConfig {

  public int version;
  // messages
  public final TemplateParser please_click;
  public final TemplateParser command_usage;
  public final TemplateParser time_is_out_of_range;
  public final TemplateParser not_enough_money;

  public MessageConfig(ConfigurationSection config) {
    version = config.getInt("version");
    // messages
    please_click = parse(config.getString("please_click"));
    command_usage = parse(config.getString("command_usage"));
    time_is_out_of_range = parse(config.getString("time_is_out_of_range"));
    not_enough_money = parse(config.getString("not_enough_money"));
  }

  private TemplateParser parse(String value) {
    return TemplateParser.parse(value);
  }

}
