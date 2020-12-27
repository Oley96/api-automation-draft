package api;

import org.aeonbits.owner.Config;

/**
 * @author Vladimir Oleynik
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources("classpath:config.properties")
public interface ProjectConfig extends Config {

    @Key("dev.base.uri")
    String baseUri();

    @Key("dev.base.path")
    String basePath();

    @Key("dev.logging")
    boolean logging();

}
