package fr.joupi.jhub.utils.logger.impl;

import fr.joupi.jhub.utils.logger.ILog;

public class Log implements ILog {

    @Override
    public String prefix() {
        return "[JHub]";
    }

    @Override
    public void sucess(String... messages) {
        log(LogType.SUCCESS, messages);
    }

    @Override
    public void info(String... messages) {
        log(LogType.INFO, messages);
    }

    @Override
    public void error(String... messages) {
        log(LogType.ERROR, messages);
    }

    @Override
    public void warning(String... messages) {
        log(LogType.WARNING, messages);
    }

    @Override
    public void debug(String... messages) {
        log(LogType.DEBUG, messages);
    }
}
