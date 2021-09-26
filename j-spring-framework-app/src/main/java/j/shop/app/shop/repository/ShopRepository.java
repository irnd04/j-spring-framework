package j.shop.app.shop.repository;

import j.shop.app.shop.domain.Shop;

import java.util.List;

public interface ShopRepository {
    List<Shop> findAll();

    Shop insert(Shop shop);

    Shop delete(Integer shopId);

    Shop update(Shop shop);

    Shop findById(int shopId);
}
