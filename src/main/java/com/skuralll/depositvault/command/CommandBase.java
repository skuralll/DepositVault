package com.skuralll.depositvault.command;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBase implements CommandExecutor {

  private HashMap<String, SubCommand> commands;
  private String default_command = null;

  public CommandBase() {
    this.commands = new HashMap<String, SubCommand>();
  }

  public void register(String name, SubCommand cmd) {
    this.commands.put(name, cmd);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    String cmd = default_command;
    if (args.length > 0) {
      cmd = args[0];
    }

    SubCommand sub = (cmd == null ? null : commands.get(cmd));
    if (sub == null) {
      return false;
    }

    // Check Executer Type
    if (sender instanceof Player) {
      if (sub.getExecuter() == CommandExecuter.CONSOLE) {
        sender.sendMessage(ChatColor.RED + "This command can only be executed by the console.");
        return true;
      }
    } else {
      if (sub.getExecuter() == CommandExecuter.PLAYER) {
        sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
        return true;
      }
    }

    boolean result = sub.onCommand(sender, args);

    return result;
  }

}

