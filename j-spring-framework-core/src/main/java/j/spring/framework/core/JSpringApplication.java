package j.spring.framework.core;

import j.spring.framework.core.web.servlet.ServletWebServerApplicationContext;

public class JSpringApplication {

    private final Class<?> primarySource;
    private ServletWebServerApplicationContext context;

    private JSpringApplication(Class<?> primarySource) {
        this.primarySource = primarySource;
    }

    private void run(String[] args) {
        this.context = new ServletWebServerApplicationContext(primarySource);
        context.refresh();
    }

    public static void run(Class<?> primarySource, String... args) {
        new JSpringApplication(primarySource).run(args);
    }
}
