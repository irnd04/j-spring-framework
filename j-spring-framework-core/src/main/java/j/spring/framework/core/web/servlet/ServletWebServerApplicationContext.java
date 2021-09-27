package j.spring.framework.core.web.servlet;

import j.spring.framework.core.web.server.lifecycle.LifeCycle;
import j.spring.framework.core.web.server.lifecycle.LifeCycleEventBus;
import j.spring.framework.core.web.server.TomcatWebServer;
import j.spring.framework.core.web.server.TomcatWebServerFactory;

public class ServletWebServerApplicationContext {

    private final Class<?> primarySource;

    public ServletWebServerApplicationContext(Class<?> primarySource) {
        this.primarySource = primarySource;
    }

    public void refresh() {
        TomcatWebServerFactory tomcatWebServerFactory = new TomcatWebServerFactory(primarySource);
        TomcatWebServer webServer = tomcatWebServerFactory.createWebServer();
        registerShutdownHandler(webServer);
        webServer.start();
        LifeCycleEventBus.send(LifeCycle.AFTER_START);
    }

    private void registerShutdownHandler(TomcatWebServer webServer) {
        Runtime.getRuntime().addShutdownHook(new Thread(webServer::stop));
    }
}
