package j.shop.app;

import com.fasterxml.jackson.core.type.TypeReference;
import j.shop.app.exception.ShopErrorCode;
import j.shop.app.shop.domain.Shop;
import j.shop.app.util.ObjectMapperUtils;
import j.shop.app.web.ShopResponse;
import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.ioc.ComponentFactory;
import j.spring.framework.core.ioc.ComponentScanner;
import j.spring.framework.core.web.server.lifecycle.LifeCycleEventBus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTestBase {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    private final String BASE_URL = "http://localhost:8080/shops";

    protected void startApplication() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        LifeCycleEventBus.put(lifeCycle -> latch.countDown());
        ShopApplication.main(new String[] {});
        latch.await();
    }

    protected ShopResponse<List<Shop>> all() throws IOException {
        HttpGet req = new HttpGet(BASE_URL);
        req.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        CloseableHttpResponse res = HttpClientBuilder.create().build().execute(req);
        return ObjectMapperUtils.mapper().readValue(res.getEntity().getContent(),
                new TypeReference<ShopResponse<List<Shop>>>() {});
    }

    protected ShopResponse<Shop> get(int id) throws IOException {
        HttpGet req = new HttpGet(BASE_URL + "/" + id);
        req.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        CloseableHttpResponse res = HttpClientBuilder.create().build().execute(req);
        return ObjectMapperUtils.mapper().readValue(res.getEntity().getContent(),
                new TypeReference<ShopResponse<Shop>>() {});
    }

    protected ShopResponse<Shop> put(int id, Shop shop) throws IOException {
        HttpPut req = new HttpPut(BASE_URL + "/" + id);
        req.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        req.setEntity(new StringEntity(ObjectMapperUtils.mapper().writeValueAsString(shop)));
        CloseableHttpResponse res = HttpClientBuilder.create().build().execute(req);
        return ObjectMapperUtils.mapper().readValue(res.getEntity().getContent(),
                new TypeReference<ShopResponse<Shop>>() {});
    }

    protected ShopResponse<Shop> post(Shop shop) throws IOException {
        HttpPost req = new HttpPost(BASE_URL);
        req.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        req.setEntity(new StringEntity(ObjectMapperUtils.mapper().writeValueAsString(shop)));
        CloseableHttpResponse res = HttpClientBuilder.create().build().execute(req);
        return ObjectMapperUtils.mapper().readValue(res.getEntity().getContent(),
                new TypeReference<ShopResponse<Shop>>() {});
    }

    protected ShopResponse<Shop> delete(int id) throws IOException {
        HttpDelete req = new HttpDelete(BASE_URL + "/" + id);
        req.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        CloseableHttpResponse res = HttpClientBuilder.create().build().execute(req);
        return ObjectMapperUtils.mapper().readValue(res.getEntity().getContent(),
                new TypeReference<ShopResponse<Shop>>() {});
    }

    protected void mustBeSuccess(ShopResponse<?> mr) {
        assertTrue(mr.getHeader().isSuccess());
    }

    protected void mustBeFailure(ShopResponse<?> mr, ShopErrorCode errorCode) {
        assertFalse(mr.getHeader().isSuccess());
        assertEquals(errorCode.getCode(), mr.getHeader().getCode());
    }

    protected ApplicationContext getApplicationContext() {
        ComponentScanner scanner = new ComponentScanner(ShopApplication.class.getPackage().getName());
        return new ComponentFactory(scanner.scan());
    }

}
