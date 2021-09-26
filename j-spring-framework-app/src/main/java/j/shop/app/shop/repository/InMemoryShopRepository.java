package j.shop.app.shop.repository;

import j.shop.app.exception.ShopErrorCode;
import j.shop.app.exception.ShopException;
import j.shop.app.shop.domain.Shop;
import j.spring.framework.core.web.annotation.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryShopRepository implements ShopRepository {

    private final AtomicInteger atomicInteger = new AtomicInteger(1);
    private final Map<Integer, Shop> database = new ConcurrentHashMap<>();
    private final Object lock = new Object();

    @Override
    public List<Shop> findAll() {
        List<Shop> results = new ArrayList<>(database.values());
        Collections.sort(results);
        return results;
    }

    @Override
    public Shop insert(Shop shop) {
        int seq = getSeq();
        shop.setId(seq);
        database.put(seq, shop);
        return shop;
    }

    @Override
    public Shop delete(Integer shopId) {
        return database.remove(shopId);
    }

    @Override
    public Shop update(Shop shop) {
        synchronized (lock) {
            Shop originShop = findById(shop.getId());
            if (originShop == null) {
                throw ShopException.of(ShopErrorCode.SHOP_NOT_FOUND, String.valueOf(shop.getId()));
            }
            return database.put(shop.getId(), shop);
        }
    }

    @Override
    public Shop findById(int shopId) {
        Shop shop = database.get(shopId);
        if (shop == null) {
            throw ShopException.of(ShopErrorCode.SHOP_NOT_FOUND, String.valueOf(shopId));
        }
        return shop;
    }

    private int getSeq() {
        return atomicInteger.getAndIncrement();
    }

}
