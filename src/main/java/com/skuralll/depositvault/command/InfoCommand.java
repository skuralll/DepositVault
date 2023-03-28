package com.skuralll.depositvault.command;

import com.skuralll.depositvault.DepositVault;
import com.skuralll.depositvault.db.Database;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class InfoCommand extends SubCommand {

  private final PluginDescriptionFile description;

  protected CommandExecuter executer = CommandExecuter.BOTH;

  public InfoCommand() {
    executer = CommandExecuter.BOTH;
    description = DepositVault.getInstance().getDescription();
  }

  @Override
  public boolean onCommand(CommandSender sender, String[] args) {
    sender.sendMessage(description.getName() + " : v" + description.getVersion());
    sender.sendMessage("Authors : " + String.join(",", description.getAuthors()));
    sender.sendMessage("Website : " + description.getWebsite());
    sender.sendMessage("DB-Version : v" + Database.DB_VERSION);
    return true;
  }

}
