package com.skuralll.depositvault.command;

import org.bukkit.command.CommandSender;

abstract public class SubCommand {

  protected CommandExecuter executer = CommandExecuter.BOTH;

  abstract public boolean onCommand(CommandSender sender, String[] args);

  public CommandExecuter getExecuter() {
    return executer;
  }

}
