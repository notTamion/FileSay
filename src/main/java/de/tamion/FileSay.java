package de.tamion;

import de.tamion.commands.FileSayCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class FileSay extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("filesay").setExecutor(new FileSayCommand());
    }

    @Override
    public void onDisable() {

    }
}
