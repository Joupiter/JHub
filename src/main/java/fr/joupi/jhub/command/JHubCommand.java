package fr.joupi.jhub.command;

import fr.joupi.jhub.JHub;
import fr.joupi.jhub.common.hub.Hub;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

public class JHubCommand extends Command {

    private final JHub jHub;

    public JHubCommand(JHub jHub) {
        super("jhub");
        this.jHub = jHub;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (sender.hasPermission("jhub.admin"))

            if (args.length == 0) {
                sendMessages(sender, "&7Hubs:");
                jHub.getLoader()
                        .getHubManager()
                        .getHubsDesordered()
                        .forEach(hub -> sendInfoMessage(sender, hub, ChatColor.translateAlternateColorCodes('&',
                                (jHub.getLoader().getHubManager().isOnline(hub.getAddress()) ? "&a✔" : "&c✘") + " &7Server: &b" + hub.getName() + " &7(&b" + hub.getPlayers().size() + "&7)")));
            }

        else sendMessages(sender, "&eJHub &bv" + jHub.getDescription().getVersion() + " &emade by &bJoupi");

    }

    private void sendInfoMessage(CommandSender sender, Hub hub, String text) {
        TextComponent serverInfo = new TextComponent(text);
        serverInfo.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/server " + hub.getName()));
        serverInfo.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + "Click to connect to " + ChatColor.AQUA + hub.getName()).create()));
        sender.sendMessage(serverInfo);
    }

    private void sendMessages(CommandSender sender, String... messages) {
        Arrays.asList(messages)
                .forEach(s -> sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', s))));
    }

}
