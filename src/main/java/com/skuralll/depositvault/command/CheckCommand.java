package com.skuralll.depositvault.command;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.CheckCache;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckCommand extends SubCommand {

  private CheckCache cache;

  public CheckCommand() {
    executer = CommandExecuter.PLAYER;
    cache = DepositVault.getInstance().getCacheStore().getCheckCache();
  }

  @Override
  public boolean onCommand(CommandSender sender, String[] args) {
    Player player = (Player) sender;
    cache.put(player.getUniqueId(), System.currentTimeMillis());
    sender.sendMessage("Right click on the chest you want to check.");
    return true;
  }

}
