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
  public final TemplateParser locked_by_myself;
  public final TemplateParser locked_by_other;
  public final TemplateParser success_lock;
  public final TemplateParser success_unlock;
  public final TemplateParser success_extend;
  public final TemplateParser not_locked;
  public final TemplateParser locked;
  public final TemplateParser not_your_chest;
  public final TemplateParser max_expiration;
  public final TemplateParser sql_error;
  public final TemplateParser enter_the_time;
  public final TemplateParser click_again;
  public final TemplateParser loading;

  public MessageConfig(ConfigurationSection config) {
    version = config.getInt("version");
    // messages
    please_click = parse(config.getString("please_click"));
    command_usage = parse(config.getString("command_usage"));
    time_is_out_of_range = parse(config.getString("time_is_out_of_range"));
    not_enough_money = parse(config.getString("not_enough_money"));
    locked_by_myself = parse(config.getString("locked_by_myself"));
    locked_by_other = parse(config.getString("locked_by_other"));
    success_lock = parse(config.getString("success_lock"));
    success_unlock = parse(config.getString("success_unlock"));
    success_extend = parse(config.getString("success_extend"));
    not_locked = parse(config.getString("not_locked"));
    locked = parse(config.getString("locked"));
    not_your_chest = parse(config.getString("not_your_chest"));
    max_expiration = parse(config.getString("max_expiration"));
    sql_error = parse(config.getString("sql_error"));
    enter_the_time = parse(config.getString("enter_the_time"));
    click_again = parse(config.getString("click_again"));
    loading = parse(config.getString("loading"));
  }

  private TemplateParser parse(String value) {
    return TemplateParser.parse(value);
  }

}
