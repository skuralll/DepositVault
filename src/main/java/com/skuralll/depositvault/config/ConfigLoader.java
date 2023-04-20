package com.skuralll.depositvault.config;

import com.skuralll.depositvault.DepositVault;
import jp.jyn.jbukkitlib.config.YamlLoader;

public class ConfigLoader {

  private final YamlLoader main_loader;
  private MainConfig main_config;

  public ConfigLoader() {
    DepositVault plugin = DepositVault.getInstance();
    main_loader = new YamlLoader(plugin, "config.yml");
  }

  // reload all configs
  public void reload(){
    main_loader.saveDefaultConfig();

    if(main_config != null){
      main_loader.reloadConfig();
    }

    main_config = new MainConfig(main_loader.getConfig());
  }

  public MainConfig getMainConfig() {
    return main_config;
  }

}
