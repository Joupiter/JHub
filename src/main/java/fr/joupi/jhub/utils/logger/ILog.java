package fr.joupi.jhub.utils.logger;

import java.util.Arrays;

public interface ILog {

    String prefix();

    void sucess(String... message);

    void info(String... message);

    void error(String... message);

    void warning(String... message);

    void debug(String... message);

    default void log(String logType, String... message) {
        Arrays.asList(message)
                .forEach(s -> System.out.println(prefix() + logType + " " + s + "\n"));
    }

}
