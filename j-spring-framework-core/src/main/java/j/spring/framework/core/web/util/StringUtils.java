package j.spring.framework.core.web.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class StringUtils {

    private StringUtils() {}

    public static boolean isBlank(String s) {
        return s == null || s.trim().equals("");
    }

    public static String urlJoin(String first, String... more) {

        String result = Paths.get(first, more)
                .toString();

        if (File.separatorChar != '/') {
            result = result.replace(File.separatorChar, '/');
        }

        if (!result.startsWith("/")) {
            result = "/" + result;
        }

        return result;
    }

    public static String fromInputStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder builder = new StringBuilder();
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                if (builder.length() > 0) {
                    builder.append("\n");
                }
                builder.append(buffer);
            }
            return builder.toString();
        }
    }

}
