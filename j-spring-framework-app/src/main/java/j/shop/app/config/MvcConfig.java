package j.shop.app.config;

import com.google.common.collect.Lists;
import j.spring.framework.core.web.annotation.Bean;
import j.spring.framework.core.web.annotation.Configuration;
import j.spring.framework.core.web.config.StaticResourceConfig;

@Configuration
public class MvcConfig {
    
    @Bean
    public StaticResourceConfig staticResourceConfig() {
        return new StaticResourceConfig("/static",
                Lists.newArrayList("*.html", "*.css", "*.js"));
    }

}
