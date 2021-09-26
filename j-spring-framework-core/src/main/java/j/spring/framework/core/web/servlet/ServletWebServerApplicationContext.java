package j.spring.framework.core.web.servlet;

import j.spring.framework.core.web.lifecycle.LifeCycle;
import j.spring.framework.core.web.lifecycle.LifeCycleEventBus;
import j.spring.framework.core.web.server.TomcatWebServer;
import j.spring.framework.core.web.server.TomcatWebServerFactory;

public class ServletWebServerApplicationContext {

    private TomcatWebServer webServer;
    private final Class<?> primarySource;

    public ServletWebServerApplicationContext(Class<?> primarySource) {
        this.primarySource = primarySource;
    }

    public void refresh() {
        createWebServer();
        registerShutdownHandler();
        start();
        LifeCycleEventBus.send(LifeCycle.AFTER_START);
    }

    private void registerShutdownHandler() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> webServer.stop()));
    }

    private void start() {
        this.webServer.start();
    }

    private void createWebServer() {
        TomcatWebServerFactory tomcatWebServerFactory = new TomcatWebServerFactory(primarySource);
        this.webServer = tomcatWebServerFactory.getWebServer();
    }

}
