package fr.joupi.jhub;

import fr.joupi.jhub.common.config.Config;
import fr.joupi.jhub.common.loader.Loader;
import fr.joupi.jhub.utils.logger.impl.Log;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public class JHub extends Plugin {

    private Log log;
    private Config config;
    private Loader loader;

    @Override
    public void onEnable() {
        this.log = new Log();
        this.config = new Config(this);
        this.loader = new Loader(this);
    }

    @Override
    public void onDisable() {
        getLoader().unload();
    }

}
