package com.skuralll.depositvault.command;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.config.MessageConfig;
import org.bukkit.command.CommandSender;

abstract public class SubCommand {

  protected DepositVault plugin;
  protected MessageConfig message;
  protected CommandExecuter executer = CommandExecuter.BOTH;

  public SubCommand(DepositVault plugin) {
    this.plugin = plugin;
    this.message = plugin.getConfigLoader().getMessagesConfig();
  }

  abstract public boolean onCommand(CommandSender sender, String[] args);

  public CommandExecuter getExecuter() {
    return executer;
  }

}
