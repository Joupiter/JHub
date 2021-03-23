package fr.joupi.jhub.utils.logger.impl;

import fr.joupi.jhub.utils.logger.ILog;

public class Log implements ILog {

    @Override
    public String prefix() {
        return "[JHub]";
    }

    @Override
    public void sucess(String... strings) {
        log("(Success)", strings);
    }

    @Override
    public void info(String... strings) {
        log("(Info)", strings);
    }

    @Override
    public void error(String... strings) {
        log("(Error)", strings);
    }

    @Override
    public void warning(String... strings) {
        log("(Warning)", strings);
    }

    @Override
    public void debug(String... strings) {
        log("(Debug)", strings);
    }
}
