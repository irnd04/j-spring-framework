package j.shop.app.shop.repository;

import j.shop.app.ShopTestBase;
import j.shop.app.exception.ErrorCode;
import j.shop.app.exception.ShopErrorCode;
import j.shop.app.exception.ShopException;
import j.shop.app.shop.domain.Shop;
import j.shop.app.shop.testdata.TestData;
import j.spring.framework.core.ioc.ApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryShopRepositoryTest extends ShopTestBase {

    private ShopRepository shopRepository;
    private ExecutorService executorService;

    @BeforeEach
    void beforeEach() {
        ApplicationContext applicationContext = getApplicationContext();
        shopRepository = applicationContext.get(InMemoryShopRepository.class);
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Test
    void multiThreadTest() throws InterruptedException {
        final int size = 20000;
        shopRepository.insert(TestData.shop1());
        shopRepository.insert(TestData.shop2());
        shopRepository.insert(TestData.shop3());
        CountDownLatch latch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            executorService.execute(() -> {
                shopRepository.findAll();
                shopRepository.findById(1);
                shopRepository.insert(TestData.shop1());
                latch.countDown();
            });
        }
        latch.await();
        List<Shop> all = shopRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            assertEquals(i + 1, all.get(i).getId());
        }
        assertEquals(size + 3, all.size());

    }

    @Test
    void findByIdNotFoundId() {
        ShopException shopException = assertThrows(ShopException.class, () -> {
            shopRepository.findById(1);
        });
        assertTrue(ErrorCode.equals(ShopErrorCode.SHOP_NOT_FOUND, shopException.getErrorCode()));
    }

}