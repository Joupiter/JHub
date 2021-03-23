package fr.joupi.jhub.utils.loader;

import fr.joupi.jhub.JHub;
import fr.joupi.jhub.utils.loader.impl.ILoader;
import lombok.Data;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;

import java.util.Arrays;

@Data
public abstract class BungeeLoader implements ILoader {

    private final JHub plugin;

    public void registerListeners(Listener... listeners) {
        Arrays.asList(listeners)
                .forEach(listener -> plugin.getProxy().getPluginManager().registerListener(plugin, listener));
    }

    public void registerCommands(Command... commands) {
        Arrays.asList(commands)
                .forEach(command -> getPlugin().getProxy().getPluginManager().registerCommand(plugin, command));
    }

    public void loadLoaders(BungeeLoader... loaders) {
        Arrays.asList(loaders)
                .forEach(BungeeLoader::load);
    }

}