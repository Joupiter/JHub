package fr.joupi.jhub.common.loader;

import fr.joupi.jhub.JHub;
import fr.joupi.jhub.command.HubCommand;
import fr.joupi.jhub.command.JHubCommand;
import fr.joupi.jhub.common.hub.Hub;
import fr.joupi.jhub.common.hub.HubManager;
import fr.joupi.jhub.listener.ConnectionListener;
import fr.joupi.jhub.utils.loader.BungeeLoader;
import lombok.Getter;

@Getter
public class Loader extends BungeeLoader {

    private final HubManager hubManager;

    public Loader(JHub plugin) {
        super(plugin);
        this.hubManager = new HubManager(getPlugin());
        this.load();
    }

    @Override
    public void load() {
        getPlugin().getConfig().getHubs()
                .stream()
                .filter(s -> getPlugin().getProxy().getServers().containsKey(s))
                .forEach(s -> getHubManager().add(new Hub(getPlugin(), s)));

        registerListeners(new ConnectionListener(getPlugin()));

        registerCommands(
                new JHubCommand(getPlugin()),
                new HubCommand(getPlugin()));
    }

    @Override
    public void unload()
    { }
}
