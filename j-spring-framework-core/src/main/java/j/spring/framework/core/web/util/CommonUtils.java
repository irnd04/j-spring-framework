package j.spring.framework.core.web.util;

public class CommonUtils {

    private CommonUtils() {

    }

    public static String getExtension(String resourceName) {
        int i = resourceName.lastIndexOf('.');
        if (i > 0) {
            return resourceName.substring(i + 1);
        }
        return "";
    }

}
