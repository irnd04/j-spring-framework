package j.shop.app;

import j.shop.app.exception.ShopErrorCode;
import j.shop.app.shop.domain.Shop;
import j.shop.app.shop.testdata.TestData;
import j.shop.app.web.ShopResponse;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class ShopIntegrationTest extends ShopTestBase {

    @Test
    @DisplayName("통합 테스트")
    void test() throws InterruptedException, IOException {
        startApplication();
        postTest(TestData.shop1());
        postTest(TestData.shop2());
        postTest(TestData.shop3());
        postRequestDataInvalid(TestData.shopError());
        getTest(1, TestData.shop1());
        deleteTest(1, TestData.shop1());
        putFailNotFoundId(1, TestData.shop1());
        Shop shop = putTest(2, TestData.shop3());
        allTest(Lists.newArrayList(shop, TestData.shop3()));
    }

    private void allTest(List<Shop> expected) throws IOException {
        ShopResponse<List<Shop>> mr = all();
        mustBeSuccess(mr);
        Assertions.assertThat(mr.getResult())
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }

    private Shop putTest(int id, Shop shop) throws IOException {
        ShopResponse<Shop> mr = put(id, shop);
        mustBeSuccess(mr);
        shop.setId(id);
        Assertions.assertThat(mr.getResult())
                .isEqualToComparingFieldByField(shop);
        return shop;
    }

    private void putFailNotFoundId(int id, Shop shop) throws IOException {
        ShopResponse<Shop> mr = put(id, shop);
        mustBeFailure(mr, ShopErrorCode.SHOP_NOT_FOUND);
    }

    private void postTest(Shop shop) throws IOException {
        ShopResponse<Shop> mr = post(shop);
        mustBeSuccess(mr);
        Assertions.assertThat(mr.getResult())
                .isEqualToComparingFieldByField(shop);
    }

    private void postRequestDataInvalid(Shop shop) throws IOException {
        ShopResponse<Shop> mr = post(shop);
        mustBeFailure(mr, ShopErrorCode.SHOP_REQUEST_DATA_INVALID);
    }

    private void getTest(int id, Shop expected) throws IOException {
        ShopResponse<Shop> mr = get(id);
        mustBeSuccess(mr);
        Assertions.assertThat(mr.getResult())
                .isEqualToComparingFieldByField(expected);
    }

    private void deleteTest(int id, Shop expected) throws IOException {
        ShopResponse<Shop> mr = delete(id);
        mustBeSuccess(mr);
        Assertions.assertThat(mr.getResult())
                .isEqualToComparingFieldByField(expected);
    }

}
