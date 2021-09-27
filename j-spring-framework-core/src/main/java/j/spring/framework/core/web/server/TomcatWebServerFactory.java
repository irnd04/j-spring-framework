package j.spring.framework.core.web.server;

import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.ioc.ComponentFactory;
import j.spring.framework.core.ioc.ComponentScanner;
import j.spring.framework.core.web.servlet.DefaultServletContextInitializer;
import j.spring.framework.core.web.servlet.ErrorHandleServlet;
import j.spring.framework.core.web.servlet.WebResourceInitializer;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class TomcatWebServerFactory {

    private static final Logger logger = LoggerFactory.getLogger(TomcatWebServerFactory.class);

    private final Class<?> primarySource;
    private final ApplicationContext applicationContext;

    public TomcatWebServerFactory(Class<?> primarySource) {
        this.primarySource = primarySource;
        ComponentScanner scanner = new ComponentScanner(primarySource.getPackage().getName());
        this.applicationContext = new ComponentFactory(scanner.scan());
    }

    public TomcatWebServer createWebServer() {
        Tomcat tomcat = new Tomcat();
        prepareContext(tomcat);
        return new TomcatWebServer(tomcat);
    }

    private void prepareContext(Tomcat tomcat) {
        StandardContext context = new StandardContext();
        File docBase = createTempDir("tomcat-docbase", tomcat.getConnector().getPort());
        context.setDocBase(docBase.getAbsolutePath());
        context.setDisplayName("application");
        context.setName("");
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());
        tomcat.getHost().addChild(context);
        context.addServletContainerInitializer(
                new TomcatStarter(new DefaultServletContextInitializer(applicationContext)), Collections.emptySet());
        ErrorPage errorPage = new ErrorPage();
        errorPage.setLocation(ErrorHandleServlet.LOCATION);
        context.addErrorPage(errorPage);
        WebResourceInitializer.initialize(context, primarySource);
    }

    private File createTempDir(String prefix, int port) {
        try {
            File tempDir = File.createTempFile(prefix + ".", "." + port);
            tempDir.delete();
            tempDir.mkdir();
            tempDir.deleteOnExit();
            return tempDir;
        }
        catch (IOException ex) {
            throw new WebServerException(
                    "Unable to create tempDir. java.io.tmpdir is set to "
                            + System.getProperty("java.io.tmpdir"),
                    ex);
        }
    }
}
