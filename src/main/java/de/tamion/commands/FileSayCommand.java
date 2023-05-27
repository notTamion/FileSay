package de.tamion.commands;

import de.tamion.FileSay;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FileSayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length < 2) {
            sender.sendMessage("/FileSay {Interval in milliseconds} {FilePath}");
            return false;
        }
        Bukkit.getScheduler().scheduleAsyncDelayedTask(FileSay.getPlugin(), () -> {
            try {
                int interval = Integer.parseInt(args[0]);
                List<String> commands = new ArrayList<>();
                if(args[1].startsWith("http")) {
                    if(!sender.hasPermission("FileSay.websay")) {
                        sender.sendMessage("You aren't allowed to execute this Command");
                        return;
                    }
                    HttpURLConnection connection = (HttpURLConnection) new URL(args[1]).openConnection();
                    commands = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
                } else {
                    if(!sender.hasPermission("FileSay.filesay")) {
                        sender.sendMessage("You aren't allowed to execute this Command");
                        return;
                    }
                    commands = Files.readAllLines(Paths.get(String.join(" ", Arrays.copyOfRange(args, 1, args.length))));
                }
                commands.forEach(s -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(interval);
                    } catch (InterruptedException ignored) {}
                    Bukkit.getScheduler().callSyncMethod(FileSay.getPlugin(), () -> Bukkit.dispatchCommand(sender, s));
                });
                sender.sendMessage("Executed Commands");
            } catch (IOException e) {
                sender.sendMessage("Source doesn't exist");
            } catch (NumberFormatException e) {
                sender.sendMessage("Please provide a valid interval");
            }
        });
        return true;
    }
}