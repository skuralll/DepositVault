package com.skuralll.depositvault.command;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.CheckCache;
import org.bukkit.command.CommandSender;

public class CheckCommand extends SubCommand {

  private CheckCache cache;

  public CheckCommand() {
    executer = CommandExecuter.PLAYER;
    cache = DepositVault.getInstance().getCacheStore().getCheckCache();
  }

  @Override
  public boolean onCommand(CommandSender sender, String[] args) {
    return true;
  }

}
