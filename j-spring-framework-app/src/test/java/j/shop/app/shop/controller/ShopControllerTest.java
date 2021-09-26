package j.shop.app.shop.controller;

import j.shop.app.ShopTestBase;
import j.shop.app.exception.ErrorCode;
import j.shop.app.exception.ShopErrorCode;
import j.shop.app.exception.ShopException;
import j.shop.app.shop.domain.Shop;
import j.shop.app.shop.testdata.TestData;
import j.shop.app.shop.web.ShopRequest;
import j.shop.app.web.ShopResponse;
import j.spring.framework.core.ioc.ApplicationContext;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShopControllerTest extends ShopTestBase {

    private ShopController shopController;

    @BeforeEach
    public void beforeEach() {
        ApplicationContext applicationContext = getApplicationContext();
        shopController = applicationContext.get(ShopController.class);
    }

    @Test
    void findShops() {
        List<ShopRequest> shopRequests = Lists.newArrayList(TestData.shopRequest1(),
                TestData.shopRequest2(),
                TestData.shopRequest3(),
                TestData.shopRequest4());

        List<Shop> expected = new ArrayList<>();
        for (int i = 0; i < shopRequests.size(); i++) {
            ShopRequest shopRequest = shopRequests.get(i);
            expected.add(shopRequest.toShop(i + 1));
            shopController.addShop(shopRequest);
        }

        ShopResponse<List<Shop>> response = shopController.findShops();
        mustBeSuccess(response);

        Assertions.assertThat(response.getResult())
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }

    @Test
    void getShop() {
        Shop shop = TestData.shop1();
        ShopResponse<Shop> response = shopController.getShop(shop);
        mustBeSuccess(response);
        Assertions.assertThat(response.getResult())
                .isEqualToComparingFieldByField(shop);
    }

    @Test
    void addShop() {
        ShopRequest shopRequest = TestData.shopRequest1();
        ShopResponse<Shop> response = shopController.addShop(shopRequest);
        mustBeSuccess(response);
        Assertions.assertThat(response.getResult())
                .isEqualToComparingFieldByField(shopRequest.toShop(1));
    }

    @Test
    void addShopInvalidRequest() {
        ShopException shopException = assertThrows(ShopException.class, () -> {
            ShopResponse<Shop> response =
                    shopController.addShop(TestData.shopRequestError());
        });
        assertTrue(ErrorCode.equals(ShopErrorCode.SHOP_REQUEST_DATA_INVALID, shopException.getErrorCode()));
    }

    @Test
    void deleteShop() {
        Shop result = shopController
                .addShop(TestData.shopRequest1()).getResult();
        ShopResponse<Shop> response = shopController.deleteShop(result.getId());
        mustBeSuccess(response);
        Assertions.assertThat(response.getResult())
                .isEqualToComparingFieldByField(result);
    }

    @Test
    void deleteShopNotFoundId() {
        ShopException shopException = assertThrows(ShopException.class, () -> {
            ShopResponse<Shop> response = shopController.deleteShop(10);
        });
        assertTrue(ErrorCode.equals(ShopErrorCode.SHOP_NOT_FOUND, shopException.getErrorCode()));
    }

    @Test
    void updateShop() {
        int id = 1;
        shopController.addShop(TestData.shopRequest1());
        ShopRequest shopRequest = TestData.shopRequest2();
        ShopResponse<Shop> response =
                shopController.updateShop(id, shopRequest);
        Assertions.assertThat(response.getResult())
                .isEqualToComparingFieldByField(shopRequest.toShop(id));
    }

    @Test
    void updateShopNotFoundId() {
        ShopException shopException = assertThrows(ShopException.class, () -> {
            ShopResponse<Shop> response =
                    shopController.updateShop(1, TestData.shopRequest1());
        });
        assertTrue(ErrorCode.equals(ShopErrorCode.SHOP_NOT_FOUND, shopException.getErrorCode()));
    }
}
