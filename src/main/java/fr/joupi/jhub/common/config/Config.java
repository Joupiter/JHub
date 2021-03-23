package fr.joupi.jhub.common.config;

import fr.joupi.jhub.common.hub.Hub;
import fr.joupi.jhub.utils.config.impl.AbstractConfig;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class Config extends AbstractConfig {

    public Config(Plugin plugin) {
        super(plugin, "config.yml");
    }

    public List<String> getHubs() {
        return getConfiguration().getStringList("hubs");
    }

    public boolean forcePlayerToGoHubOnConnect() {
        return getConfiguration().getBoolean("force-player-to-go-hub-on-connect");
    }

    public boolean redirectPlayerToHubOnServerClose() {
        return getConfiguration().getBoolean("redirect-player-to-hub-on-server-close");
    }

    public String getCommandName() {
        return getConfiguration().getString("command-name");
    }

    public String[] getCommandAliases() {
        String[] aliases = new String[getConfiguration().getStringList("command-aliases").size()];
        return getConfiguration().getStringList("command-aliases").toArray(aliases);
    }

    public String getPrefix() {
        return colorize(getConfiguration().getString("prefix"));
    }

    public String getHubNotFoundMessage() {
        return replacePlaceholders(getConfiguration().getString("hub-not-found-message"));
    }

    public String getRedirectPlayerToHubMessage(Hub hub) {
        return replacePlaceholders(getConfiguration().getString("redirect-to-a-hub-message"), hub);
    }

    public String getAlreadyInHubMessage() {
        return replacePlaceholders(getConfiguration().getString("already-in-hub-message"));
    }

    private String replacePlaceholders(String s) {
        return colorize(s
                .replace("<prefix>", getConfiguration().getString("prefix")));
    }

    private String replacePlaceholders(String s, Hub hub) {
        return colorize(s
                .replace("<prefix>", getPrefix())
                .replace("<hubName>", hub.getName())
                .replace("<online>", String.valueOf(hub.getPlayers().size())));
    }

    private String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void sendMessage(ProxiedPlayer player, String... messages) {
        Arrays.asList(messages)
                .forEach(s -> player.sendMessage(new TextComponent(s)));
    }

}
