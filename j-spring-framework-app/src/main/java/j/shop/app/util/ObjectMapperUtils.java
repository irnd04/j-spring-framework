package j.shop.app.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ObjectMapper mapper() {
        return OBJECT_MAPPER;
    }
}
