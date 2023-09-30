package de.tamion.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("FileSay.filesay")) {
            sender.sendMessage("You aren't allowed to execute this Command");
            return false;
        }
        if(args.length == 0) {
            sender.sendMessage("/FileSay {FilePath}");
            return false;
        }
        try {
            Files.readAllLines(Paths.get(String.join(" ", args))).forEach(s -> Bukkit.dispatchCommand(sender, s));
            sender.sendMessage("Executed Commands");
            return true;
        } catch (Exception e) {
            sender.sendMessage("File doesn't exist");
            return false;
        }
    }
}