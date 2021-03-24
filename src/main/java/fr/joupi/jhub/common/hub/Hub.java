package fr.joupi.jhub.common.hub;

import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.SocketAddress;
import java.util.Collection;

@Getter
public class Hub {

    private final Plugin plugin;
    private final String name;

    private final ServerInfo serverInfo;

    public Hub(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.serverInfo = ProxyServer.getInstance().getServerInfo(name);
    }

    public Collection<ProxiedPlayer> getPlayers() {
        return getServerInfo().getPlayers();
    }

    public int getPlayerCount() {
        return getPlayers().size();
    }

    public SocketAddress getAddress() {
        return getServerInfo().getSocketAddress();
    }

    public void connect(ProxiedPlayer player) {
        player.connect(getServerInfo());
    }

}
