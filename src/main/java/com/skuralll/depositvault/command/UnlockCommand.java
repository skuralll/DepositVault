package com.skuralll.depositvault.command;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.TimerCache;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnlockCommand extends SubCommand {

  TimerCache<UUID> cache;

  public UnlockCommand(DepositVault plugin) {
    super(plugin);
    executer = CommandExecuter.PLAYER;
    cache = DepositVault.getInstance().getCacheStore().getUnlockCommandCache();
  }

  @Override
  public boolean onCommand(CommandSender sender, String[] args) {
    Player player = (Player) sender;
    cache.put(player.getUniqueId(), 60000L);
    sender.sendMessage(messages.please_click.apply());
    return true;
  }

}
