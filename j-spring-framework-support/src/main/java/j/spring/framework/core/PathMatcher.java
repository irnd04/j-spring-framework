package j.spring.framework.core;

import org.springframework.util.AntPathMatcher;

import java.util.Map;

public class PathMatcher {
    private AntPathMatcher antPathMatcher;

    public PathMatcher() {
        antPathMatcher = new AntPathMatcher();
    }

    public Map<String, String> extractUriTemplateVariables(String pattern, String path) {
        return antPathMatcher.extractUriTemplateVariables(pattern, path);
    }

    public boolean match(String pattern, String path) {
        return antPathMatcher.match(pattern, path);
    }
}
