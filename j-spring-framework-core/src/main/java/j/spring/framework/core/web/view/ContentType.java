package j.spring.framework.core.web.view;

import j.spring.framework.core.web.util.CommonUtils;

import java.util.stream.Stream;

public enum ContentType {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "application/javascript"),
    JSON("json", "application/json; charset=utf-8"),
    TEXT("txt", "text/plain; charset=utf-8"),
    OTHER(null, "text/plain");

    private final String extension;
    private final String contentType;

    ContentType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public String getExtension() {
        return extension;
    }

    public static ContentType fromResourceName(String resourceName) {
        String extention = CommonUtils.getExtension(resourceName);
        return Stream.of(values())
                .filter(e -> extention.toLowerCase().equals(e.getExtension()))
                .findFirst()
                .orElse(ContentType.OTHER);
    }

}
