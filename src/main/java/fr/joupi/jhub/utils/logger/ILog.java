package fr.joupi.jhub.utils.logger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Arrays;

public interface ILog {

    String prefix();

    void sucess(String... message);

    void info(String... message);

    void error(String... message);

    void warning(String... message);

    void debug(String... message);

    default void log(LogType logType, String... messages) {
        Arrays.asList(messages)
                .forEach(message -> ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(prefix() + " " + logType.getColor() + logType.getType() + " " + message + ChatColor.RESET + "\n")));
    }

    @AllArgsConstructor
    @Getter
    enum LogType {

        SUCCESS (ChatColor.GREEN, "(Sucess)"),
        INFO (ChatColor.BLUE, "(Info)"),
        ERROR (ChatColor.RED, "(Error)"),
        WARNING (ChatColor.GOLD, "(Warning)"),
        DEBUG (ChatColor.DARK_PURPLE, "(Debug)");

        public ChatColor color;
        public String type;

    }

}
