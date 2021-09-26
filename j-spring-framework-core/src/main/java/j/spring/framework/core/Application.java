package j.spring.framework.core;

import j.spring.framework.core.web.servlet.ServletWebServerApplicationContext;

public class Application {

    private final Class<?> primarySource;
    private ServletWebServerApplicationContext context;

    public Application(Class<?> primarySource) {
        this.primarySource = primarySource;
    }

    private void run(String[] args) {
        this.context = new ServletWebServerApplicationContext(primarySource);
        context.refresh();
    }

    public static void run(Class<?> primarySource, String... args) {
        new Application(primarySource).run(args);
    }
}
