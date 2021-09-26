package j.spring.framework.core.web.servlet;

import com.google.common.collect.Lists;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.JarResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public final class WebResourceInitializer {

    private static final Logger logger = LoggerFactory.getLogger(WebResourceInitializer.class);
    private static final String DEFAULT_ENCODING = "UTF-8";

    enum WebResourceType {
        FILE("file:") {
            @Override
            public WebResourceSet getResourceSet(WebResourceRoot resources, URL url) {
                try {
                    String result
                            = new File(url.toURI()).getAbsolutePath();
                    result = decode(result);
                    if (result.endsWith("/build/resources/main") ||
                            result.endsWith("/build/resources/test")) {
                        logger.debug("{} resource added..", result);
                        return new DirResourceSet(resources, "/", result, "/");
                    }
                } catch (URISyntaxException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        },
        JAR("jar:file:") {
            @Override
            public WebResourceSet getResourceSet(WebResourceRoot resources, URL url) {
                try {
                    JarURLConnection jarURLConnection
                            = (JarURLConnection) url.openConnection();
                    String result = jarURLConnection.getJarFileURL().getFile();
                    result = decode(result);
                    logger.debug("{} jar resource added..", result);
                    return new JarResourceSet(resources, "/", result, "/WEB-INF");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        },
        OTHER(null)
        ;

        public static WebResourceSet getWebResourceSet(WebResourceRoot resources, URL url) {
            return WebResourceType.fromUrl(url)
                    .getResourceSet(resources, url);
        }

        protected String decode(String url) throws UnsupportedEncodingException {
            String result = java.net.URLDecoder.decode(url, DEFAULT_ENCODING);
            if (File.separatorChar != '/') {
                result = result.replace(File.separatorChar, '/');
            }
            return result;
        }

        public WebResourceSet getResourceSet(WebResourceRoot resources, URL url) {
            return null;
        }

        private final String prefix;

        WebResourceType(String prefix) {
            this.prefix = prefix;
        }

        static WebResourceType fromUrl(URL url) {
            return Arrays.stream(values())
                    .filter(t -> t.prefix != null)
                    .filter(t -> url.toString().startsWith(t.prefix))
                    .findFirst()
                    .orElse(OTHER);
        }
    }

    private final Context context;
    private final Class<?> primarySource;

    public WebResourceInitializer(Context context, Class<?> primarySource) {
        this.context = context;
        this.primarySource = primarySource;
    }

    public void initialize() {

        WebResourceRoot resources = new StandardRoot(context);

        List<URL> urls = Lists.newArrayList();
        try {
            Enumeration<URL> trs =
                    Thread.currentThread().getContextClassLoader().getResources("");
            urls.addAll(Collections.list(trs));
        } catch (IOException e) {
            e.printStackTrace();
        }

        urls.add(primarySource.getResource(""));

        for (URL url : urls) {
            if (url == null) {
                continue;
            }

            WebResourceSet resourceSet = WebResourceType.getWebResourceSet(resources, url);
            if (resourceSet != null) {
                resources.addPreResources(resourceSet);
            }
        }

        context.setResources(resources);
    }
}
