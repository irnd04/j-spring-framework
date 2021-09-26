package j.spring.framework.core.testdata.ioc.ioc.bean;

import j.spring.framework.core.web.annotation.Bean;
import j.spring.framework.core.web.annotation.Configuration;
import j.spring.framework.core.web.config.StaticResourceConfig;

import java.util.Collections;

@Configuration
public class MyConfig {

    @Bean
    public StaticResourceConfig config(ConfigVar configVar) {
        return new StaticResourceConfig("2", Collections.singletonList("/*"));
    }

}
