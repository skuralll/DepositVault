package com.skuralll.depositvault.command;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.NormalCache;
import com.skuralll.depositvault.config.LockConfig;
import java.sql.Time;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LockCommand extends SubCommand {

  LockConfig config;
  NormalCache<UUID, Time> cache;

  public LockCommand() {
    executer = CommandExecuter.PLAYER;
    config = DepositVault.getInstance().getConfigLoader().getLockConfig();
    cache = DepositVault.getInstance().getCacheStore().getLockCommandCache();
  }

  @Override
  public boolean onCommand(CommandSender sender, String[] args) {
    if (args.length < 2) {
      sender.sendMessage("Usage: /dvault lock <time>");
      return true;
    }

    // handle time
    int time = Integer.parseInt(args[1]);
    if (time < 1 || time > config.getMax()) {
      // time is out of range
      sender.sendMessage(
          "Time is out of range. Max time is " + config.getMax() + " " + config.getUnit().name()
              .toLowerCase() + "s.");
      return true;
    }

    Player player = (Player) sender;
    cache.put(player.getUniqueId(), new Time(config.getUnit().getMillis() * time));
    sender.sendMessage("Right click on the chest you want to lock.");
    return true;
  }

}
