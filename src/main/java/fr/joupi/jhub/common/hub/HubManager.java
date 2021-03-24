package fr.joupi.jhub.common.hub;

import com.google.common.collect.Comparators;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Getter
public class HubManager {

    private final Plugin plugin;

    private final ConcurrentMap<String, Hub> hubMap;

    public HubManager(Plugin plugin) {
        this.plugin = plugin;
        this.hubMap = new ConcurrentHashMap<>();
    }

    public Optional<Hub> get(String id) {
        return Optional.ofNullable(getHubMap().get(id));
    }

    public void add(Hub hub) {
        getHubMap().put(hub.getName(), hub);
    }

    public void remove(String id) {
        getHubMap().remove(id);
    }

    public void remove(Hub hub) {
        getHubMap().remove(hub.getName());
    }

    public boolean contains(String id) {
        return getHubMap().containsKey(id);
    }

    public int getSize() {
        return getHubMap().size();
    }

    public List<Hub> getHubsOrdained() {
        return getHubMap()
                .values()
                .stream()
                .filter(hub -> isOnline(hub.getAddress()))
                .sorted(Comparator.comparingInt(Hub::getPlayerCount))
                .collect(Collectors.toList());
    }

    public List<Hub> getHubsDesordered() {
        return getHubMap()
                .values()
                .stream()
                .sorted(Comparator.comparingInt(Hub::getPlayerCount).reversed())
                .collect(Collectors.toList());
    }

    public Hub getHubWithMorePlayers() {
        return getHubsOrdained()
                .stream()
                .reduce((first, second) -> second)
                .orElse(null);
    }

    public Optional<Hub> getHubWithLessPlayers() {
        return getHubsOrdained()
                .stream()
                .findFirst();
    }

    public boolean isOnline(SocketAddress address) {
        try {
            Socket socket = new Socket();
            socket.connect(address, 15);
            socket.close();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

}
