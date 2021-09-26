package j.shop.app.shop.service;

import j.shop.app.shop.domain.Shop;

import java.util.List;

public interface ShopService {
    List<Shop> getShops();

    Shop addShop(Shop shop);

    Shop deleteShopById(int shopId);

    Shop updateShop(Shop shop);

    Shop getShopById(int shopId);
}
