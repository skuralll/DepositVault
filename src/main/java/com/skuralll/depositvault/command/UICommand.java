package com.skuralll.depositvault.command;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.cache.TimerCache;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UICommand extends SubCommand {

  private TimerCache<UUID> cache;

  public UICommand(DepositVault plugin) {
    super(plugin);
    executer = CommandExecuter.PLAYER;
    cache = DepositVault.getInstance().getCacheStore().getUICommandCache();
  }

  @Override
  public boolean onCommand(CommandSender sender, String[] args) {
    Player player = (Player) sender;
    cache.put(player.getUniqueId(), 60000L);
    sender.sendMessage(messages.please_click.apply());
    return true;
  }

}
