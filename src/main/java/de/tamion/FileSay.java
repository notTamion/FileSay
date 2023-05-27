package de.tamion;

import de.tamion.commands.FileSayCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class FileSay extends JavaPlugin {


    private static FileSay plugin;
    @Override
    public void onEnable() {
        plugin = this;
        getCommand("filesay").setExecutor(new FileSayCommand());
    }

    @Override
    public void onDisable() {
    }

    public static FileSay getPlugin() {
        return plugin;
    }
}
