package j.spring.framework.core.web.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import j.spring.framework.core.web.util.ResponseUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonView implements View {

    private final Object result;
    private final HttpServletResponse response;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public JsonView(Object result, HttpServletResponse response) {
        this.result = result;
        this.response = response;
    }

    public String getJson() throws JsonProcessingException {
        if (result == null) {
            return "";
        }
        if (result instanceof String) {
            return (String) result;
        }
        return objectMapper.writeValueAsString(result);
    }

    @Override
    public void render() throws IOException {
        ResponseUtils.write(response, ContentType.JSON, getJson());
    }
}
