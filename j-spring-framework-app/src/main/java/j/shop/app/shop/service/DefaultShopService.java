package j.shop.app.shop.service;

import j.shop.app.shop.domain.Shop;
import j.shop.app.shop.repository.ShopRepository;
import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Service;

import java.util.List;

@Service
public class DefaultShopService implements ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public DefaultShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    @Override
    public Shop addShop(Shop shop) {
        return shopRepository.insert(shop);
    }

    @Override
    public Shop deleteShopById(int shopId) {
        getShopById(shopId);
        return shopRepository.delete(shopId);
    }

    @Override
    public Shop updateShop(Shop shop) {
        return shopRepository.update(shop);
    }

    @Override
    public Shop getShopById(int shopId) {
        return shopRepository.findById(shopId);
    }
}
