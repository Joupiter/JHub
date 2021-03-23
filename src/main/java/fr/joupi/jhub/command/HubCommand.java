package fr.joupi.jhub.command;

import fr.joupi.jhub.JHub;
import fr.joupi.jhub.common.hub.Hub;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Optional;

public class HubCommand extends Command {

    private final JHub jHub;

    public HubCommand(JHub jHub) {
        super(jHub.getConfig().getCommandName(), "", jHub.getConfig().getCommandAliases());
        this.jHub = jHub;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (args.length == 0) {
                ServerInfo server = player.getServer().getInfo();
                Optional<Hub> hub = Optional.ofNullable(jHub.getLoader().getHubManager().getHubWithLessPlayers());

                if (!jHub.getLoader().getHubManager().contains(server.getName()))

                    if (hub.isPresent())
                        hub.get().connect(player);
                    else
                        jHub.getConfig().sendMessage(player, jHub.getConfig().getHubNotFoundMessage());

                else jHub.getConfig().sendMessage(player, jHub.getConfig().getAlreadyInHubMessage());
            }

        } else sender.sendMessage(new TextComponent(jHub.getConfig().getPrefix() + " "+ ChatColor.RED + "You must be a player !"));
    }

}
