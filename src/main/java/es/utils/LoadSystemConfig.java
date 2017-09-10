package es.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * Created by cao on 14/11/17.
 */

public class LoadSystemConfig {
    private static final Logger logger = LoggerFactory.getLogger(LoadSystemConfig.class);

    @PostConstruct
    public void loadConfigure() {
        logger.info("开始加载系统配置!!!");
        ConfigurationUtils.loadConfigure();
    }
}
