package j.spring.framework.core.web.util;

import java.util.Collection;

public class CollectionUtils {

    private CollectionUtils() {}

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

}
