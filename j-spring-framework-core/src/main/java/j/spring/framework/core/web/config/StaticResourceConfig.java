package j.spring.framework.core.web.config;

import j.spring.framework.core.web.util.CollectionUtils;

import java.util.List;

public class StaticResourceConfig {

    private final String resourcePrefix;
    private final List<String> mappingUrlPatterns;

    public StaticResourceConfig(String resourcePrefix, List<String> mappingUrlPatterns) {

        if (resourcePrefix == null || CollectionUtils.isNullOrEmpty(mappingUrlPatterns)) {
            throw new IllegalArgumentException("resourcePrefix and mappingUrlPatterns must be not empty.");
        }

        this.resourcePrefix = resourcePrefix;
        this.mappingUrlPatterns = mappingUrlPatterns;
    }


    public String getResourcePrefix() {
        return resourcePrefix;
    }

    public List<String> getResourceMappingUrlPatterns() {
        return mappingUrlPatterns;
    }

}
