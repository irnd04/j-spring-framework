package j.shop.app.shop.testdata;

import j.shop.app.shop.domain.Shop;
import j.shop.app.shop.web.ShopRequest;

public class TestData {

    public static Shop shop1() {
        Shop shop = new Shop();
        shop.setId(1);
        shop.setName("name");
        shop.setAddress("address");
        return shop;
    }

    public static Shop shop2() {
        Shop shop = new Shop();
        shop.setId(2);
        shop.setName("name2");
        shop.setAddress("address2");
        return shop;
    }

    public static Shop shop3() {
        Shop shop = new Shop();
        shop.setId(3);
        shop.setName("name3");
        shop.setAddress("address3");
        return shop;
    }

    public static Shop shopError() {
        Shop shop = new Shop();
        shop.setName("name3");
        shop.setAddress("");
        return shop;
    }

    public static ShopRequest shopRequest1() {
        ShopRequest shopRequest = new ShopRequest();
        shopRequest.setAddress("address");
        shopRequest.setName("name");
        return shopRequest;
    }

    public static ShopRequest shopRequest2() {
        ShopRequest shopRequest = new ShopRequest();
        shopRequest.setAddress("address2");
        shopRequest.setName("name2");
        return shopRequest;
    }

    public static ShopRequest shopRequest3() {
        ShopRequest shopRequest = new ShopRequest();
        shopRequest.setAddress("address3");
        shopRequest.setName("name3");
        return shopRequest;
    }

    public static ShopRequest shopRequest4() {
        ShopRequest shopRequest = new ShopRequest();
        shopRequest.setAddress("address4");
        shopRequest.setName("name4");
        return shopRequest;
    }

    public static ShopRequest shopRequestError() {
        ShopRequest shopRequest = new ShopRequest();
        shopRequest.setAddress("address4");
        shopRequest.setName("");
        return shopRequest;
    }

}
