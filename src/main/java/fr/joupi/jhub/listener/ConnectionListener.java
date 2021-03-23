package fr.joupi.jhub.listener;

import fr.joupi.jhub.JHub;
import fr.joupi.jhub.common.hub.Hub;
import lombok.Data;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Optional;

@Data
public class ConnectionListener implements Listener {

    private final JHub jHub;

    @EventHandler
    public void onConnect(ServerConnectEvent event) {
        if (getJHub().getConfig().forcePlayerToGoHubOnConnect()) {
            ProxiedPlayer player = event.getPlayer();
            Optional<Hub> hub = getJHub().getLoader().getHubManager().getHubWithLessPlayers();

            if (hub.isPresent())
                event.setTarget(hub.get().getServerInfo());
            else
                player.disconnect(new TextComponent(getJHub().getConfig().getHubNotFoundMessage()));
        }
    }

    @EventHandler
    public void onServerKick(ServerKickEvent event) {
        if (getJHub().getConfig().redirectPlayerToHubOnServerClose()) {
            ProxiedPlayer player = event.getPlayer();
            Optional<Hub> hub = getJHub().getLoader().getHubManager().getHubWithLessPlayers();

            if (hub.isPresent()) {
                event.setCancelled(true);
                event.setCancelServer(hub.get().getServerInfo());
                getJHub().getConfig().sendMessage(player, getJHub().getConfig().getRedirectPlayerToHubMessage(hub.get()));
                return;
            }

            event.setKickReasonComponent(new BaseComponent[]{ new TextComponent(getJHub().getConfig().getHubNotFoundMessage()) });
        }
    }

    @EventHandler
    public void onHubNotFound(PreLoginEvent event) {
        if (getJHub().getConfig().forcePlayerToGoHubOnConnect()) {

            if (getJHub().getLoader().getHubManager().getHubsOrdained().isEmpty()) {
                event.setCancelReason(new TextComponent(getJHub().getConfig().getHubNotFoundMessage()));
                event.setCancelled(true);
            }
        }
    }

}
