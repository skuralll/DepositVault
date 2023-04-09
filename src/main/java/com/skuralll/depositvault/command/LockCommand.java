package com.skuralll.depositvault.command;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.NormalCache;
import java.sql.Time;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LockCommand extends SubCommand {

  NormalCache<UUID, Time> cache;

  public LockCommand() {
    executer = CommandExecuter.PLAYER;
    cache = DepositVault.getInstance().getCacheStore().getLockCommandCache();
  }

  @Override
  public boolean onCommand(CommandSender sender, String[] args) {
    if (args.length < 1) {
      sender.sendMessage("Usage: /dvault lock <time>");
      return false;
    }

    // TODO
    // time process

    Player player = (Player) sender;
    cache.put(player.getUniqueId(), new Time(3000000L));
    sender.sendMessage("Right click on the chest you want to lock.");
    return true;
  }

}
