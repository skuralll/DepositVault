package com.skuralll.depositvault.command;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.LockCommandCache;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LockCommand extends SubCommand {

  LockCommandCache cache;

  public LockCommand() {
    executer = CommandExecuter.PLAYER;
    cache = DepositVault.getInstance().getCacheStore().getLockCommandCache();
  }

  @Override
  public boolean onCommand(CommandSender sender, String[] args) {
    if (args.length < 2) {
      sender.sendMessage("Usage: /dvault lock <deposit>");
      return false;
    }

    Double deposit;
    try {
      deposit = Double.parseDouble(args[1]);
    } catch (NumberFormatException e) {
      sender.sendMessage("Deposit must be a number.");
      return false;
    }

    Player player = (Player) sender;
    cache.put(player.getUniqueId(), deposit);
    sender.sendMessage("Right click on the chest you want to lock.");
    return true;
  }

}
