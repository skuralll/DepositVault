package com.skuralll.depositvault.command;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.NormalCache;
import com.skuralll.depositvault.config.LockConfig;
import com.skuralll.depositvault.handler.LockHandler;
import java.sql.Time;
import java.util.UUID;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LockCommand extends SubCommand {

  Economy economy;
  LockConfig config;
  LockHandler handler;
  NormalCache<UUID, Time> cache;

  public LockCommand() {
    executer = CommandExecuter.PLAYER;
    DepositVault plugin = DepositVault.getInstance();
    economy = plugin.getEconomy();
    config = plugin.getConfigLoader().getLockConfig();
    handler = plugin.getHandler();
    cache = plugin.getCacheStore().getLockCommandCache();
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

    // check vault money
    Time length = new Time(config.getUnit().getMillis() * time);
    int price = handler.getLockPrice(length);
    if (economy.getBalance((Player) sender) < price) {
      sender.sendMessage("You don't have enough money to lock the vault.");
      return true;
    }

    Player player = (Player) sender;
    cache.put(player.getUniqueId(), length);
    sender.sendMessage("Right click on the chest you want to lock.");
    return true;
  }

}
