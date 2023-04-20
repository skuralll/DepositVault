package com.skuralll.depositvault.config;

import com.skuralll.depositvault.DepositVault;
import jp.jyn.jbukkitlib.config.YamlLoader;

public class ConfigLoader {

  // main config handlers
  private final YamlLoader main_loader;
  private MainConfig main_config;
  // messages config handlers
  private final YamlLoader messages_loader;
  private MessageConfig messages_config;

  public ConfigLoader() {
    DepositVault plugin = DepositVault.getInstance();
    main_loader = new YamlLoader(plugin, "config.yml");
    messages_loader = new YamlLoader(plugin, "messages.yml");
  }

  // reload all configs
  public void reload() {
    main_loader.saveDefaultConfig();
    messages_loader.saveDefaultConfig();

    if (main_config != null) {
      main_loader.reloadConfig();
    }
    if (messages_config != null) {
      messages_loader.reloadConfig();
    }

    main_config = new MainConfig(main_loader.getConfig());
    messages_config = new MessageConfig(messages_loader.getConfig());
  }

  public MainConfig getMainConfig() {
    return main_config;
  }

  public MessageConfig getMessagesConfig() {
    return messages_config;
  }

}
