package fr.joupi.jhub.common.hub;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;
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

    public Hub get(String id) {
        return getHubMap()
                .values()
                .stream()
                .filter(hub -> hub.getName().equals(id))
                .findAny()
                .orElse(null);
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
                .sorted(((o1, o2) -> (o1.getPlayers().size() < o2.getPlayers().size()) ? -1 : 0))
                .collect(Collectors.toList());
    }

    public List<Hub> getHubsDesordered() {
        return getHubMap()
                .values()
                .stream()
                .sorted(((o1, o2) -> (o1.getPlayers().size() > o2.getPlayers().size()) ? -1 : 0))
                .collect(Collectors.toList());
    }

    public Hub getHubWithMorePlayers() {
        return getHubsOrdained()
                .stream()
                .reduce((first, second) -> second)
                .orElse(null);
    }

    public Hub getHubWithLessPlayers() {
        return getHubsOrdained()
                .stream()
                .findFirst()
                .orElse(null);
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
